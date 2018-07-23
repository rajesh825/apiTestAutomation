package org.bjss.Login;

import static io.restassured.RestAssured.given;

import io.restassured.response.Response;
import org.bjss.testdata.datamodel.Request.User;
import org.bjss.utils.TestBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class LoginTests extends TestBase {
  private static final Logger LOG = LoggerFactory.getLogger(LoginTests.class);


  @DataProvider(name = "validUserData")
  public Object[][] createValidUserData() {
    return new Object[][] {
        { new User("peter@klaven", "cityslicka") }

    };
  }

  @DataProvider(name = "invalidUserData")
  public Object[][] createInvalidUserData() {
    return new Object[][] {
        { new User("peter@klaven", "casd") },
        { new User("peter@klaven", "") }
    };
  }


  /**
   * Test verifies Login can be successful using Valid User Credentials
   * @param userCredentials
   */
  @Test(dataProvider = "validUserData")
  public void LoginWithValidUserTest(User userCredentials){

     Response response = given()
                            .relaxedHTTPSValidation()
                            .contentType("application/json;charset=UTF-8")
                            .body(userCredentials)
                            .log().all()
                        .when()
                          .post("/api/login")
                        .then()
                          .log().all()
                         .extract()
                          .response();

    Assert.assertEquals(200, response.getStatusCode(), "Response Code should be 200");

    String token = response.path("token");
    Assert.assertNotNull(token);
//
//    response = null;

  }

  /**
   * Test verifies Login fails for invalid User Credentials
   * @param userCredentials
   */
  @Test(dataProvider = "invalidUserData")
  public void LoginWithInValidUserTest(User userCredentials){

    Response response = given()
                          .relaxedHTTPSValidation()
                          .contentType("application/json;charset=UTF-8")
                          .body(userCredentials)
                          .log().all()
                      .when()
                          .post("/api/login")
                      .then()
                         .log().all()
                      .extract()
                         .response();

    Assert.assertEquals(400, response.getStatusCode(), "Response Code should be 400");

    String errorMessage = response.path("error");
    Assert.assertNotNull(errorMessage);

  }


}
