package org.bjss.testdata.datamodel.Response;

public class Data {

    private float id;
    private String first_name;
    private String last_name;
    private String avatar;


    // Getter Methods

    public float getId() {
      return id;
    }

    public String getFirst_name() {
      return first_name;
    }

    public String getLast_name() {
      return last_name;
    }

    public String getAvatar() {
      return avatar;
    }

    // Setter Methods

    public void setId(float id) {
      this.id = id;
    }

    public void setFirst_name(String first_name) {
      this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
      this.last_name = last_name;
    }

    public void setAvatar(String avatar) {
      this.avatar = avatar;
    }

}
