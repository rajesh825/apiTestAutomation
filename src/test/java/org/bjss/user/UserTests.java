package org.bjss.user;

import static io.restassured.RestAssured.given;

import io.restassured.response.Response;
import org.bjss.testdata.builder.UserDetailsBuilder;
import org.bjss.testdata.datamodel.Request.UserWithJobDetails;
import org.bjss.testdata.datamodel.Response.UserDataResponse;
import org.bjss.testdata.datamodel.Response.UserDetails;
import org.bjss.utils.TestBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

public class UserTests extends TestBase {

  private static final Logger LOG = LoggerFactory.getLogger(UserTests.class);


  @Test
  public void createUserTest() {

    UserWithJobDetails userResource = UserDetailsBuilder.anyUserDetails().build();
    UserDetails userDetails = createUser(userResource);
    Assert.assertNotNull(userDetails.getId());

  }

  @Test
  public void updateUserTest(){
    UserWithJobDetails userResource = UserDetailsBuilder.anyUserDetails().build();
    UserDetails userDetails = createUser(userResource);
    //Modify UserDetils
    userDetails.setName("Hello");
    userDetails.setJob("TestJob");
    UserDetails updatedUserDetails = updateUser(userDetails);

    //Check User details correctly updated
    Assert.assertEquals(userDetails.getName(),updatedUserDetails.getName());
    Assert.assertEquals(userDetails.getJob(),updatedUserDetails.getJob());


    //Check Get User details return Updated user details.
    UserDataResponse userData = getUserDetails(userDetails);
    Assert.assertEquals(userData.getData().getFirst_name(),updatedUserDetails.getName());



  }
  /**
   * This method creates Specified user and returns user ID
   *
   * @param userdetails
   * @return userId
   */
  public static UserDetails createUser(UserWithJobDetails userdetails) {

    Response createUserResponse =

        given()
            .relaxedHTTPSValidation()
            .contentType("application/json;charset=UTF-8")
            .body(userdetails)
        .when()
            .post(TestBase.apiBaseUrl + "/api/users")
        .then()
            //.log().all()
            .statusCode(201)
        .extract()
            .response();

    UserDetails userDetails = createUserResponse.as(UserDetails.class);
    return userDetails;

  }

  public static UserDetails updateUser(UserDetails userdetails) {

    Response createUserResponse =

        given()
            .relaxedHTTPSValidation()
            .contentType("application/json;charset=UTF-8")
            .body(userdetails)
        .when()
            .put(TestBase.apiBaseUrl + "/api/users/" + userdetails.getId())
        .then()
         //   .log().all()
            .statusCode(200)
        .extract()
            .response();

    return createUserResponse.as(UserDetails.class);

  }

  public static UserDataResponse getUserDetails(UserDetails userdetails) {

    Response createUserResponse =

        given()
            .relaxedHTTPSValidation()
            .contentType("application/json;charset=UTF-8")

        .when()
            .get(TestBase.apiBaseUrl + "/api/users/" + userdetails.getId())
        .then()
          //  .log().all()
            .statusCode(200)
        .extract()
            .response();

    return createUserResponse.as(UserDataResponse.class);

  }

}
