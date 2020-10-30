package com.ibm.vertx.core.dataformat;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.example.util.Runner;

import java.util.ArrayList;
import java.util.List;

class Address {
  private String city;

  public Address() {
    this.city = "Coimbaotre";
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  @Override
  public String toString() {
    return "Address{" +
      "city='" + city + '\'' +
      '}';
  }

}

class User {
  private int id;
  private String name;

  private List<String> skills = new ArrayList<>();
  private Address address;

  public User() {
    this.id = 1;
    this.name = "default";
    this.skills.add("java");
    this.skills.add("vertx");
    this.address = new Address();
  }

  public Address getAddress() {
    return address;
  }

  public void setAddress(Address address) {
    this.address = address;
  }

  public List<String> getSkills() {
    return skills;
  }

  public void setSkills(List<String> skills) {
    this.skills = skills;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "User{" +
      "id=" + id +
      ", name='" + name + '\'' +
      ", skills=" + skills +
      ", address=" + address +
      '}';
  }
}


public class JsonObjectVerticle extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(JsonObjectVerticle.class);
  }

  public void createJsonObject() {
    JsonObject user = new JsonObject();
    user.put("id", 1);
    user.put("name", "Subramanian");
    user.put("status", true);
    System.out.println(user.getInteger("id") + " " + user.getString("name") + " " + user.getBoolean("status"));
    System.out.println(user.encode());
    System.out.println(user.encodePrettily());
    //fulent pattern; builder ; "hello".trim().toUpperCase();
    JsonObject user2 = new JsonObject()
      .put("id", 1)
      .put("name", "Subramanian")
      .put("status", true);
    System.out.println(user2.encodePrettily());

    //nested Object
    JsonObject user3 = new JsonObject()
      .put("id", 1)
      .put("name", "Subramanian")
      .put("status", true)
      .put("address", new JsonObject().put("city", "Coimbatore").put("state", "Tamil Nadu"));
    System.out.println(user3.encodePrettily());
    System.out.println(user3.getJsonObject("address").encodePrettily());

    //merging two json into one.
    JsonObject appConfig = new JsonObject().put("appName", "Elearn").put("version", "1.0.0");
    JsonObject serverConfig = new JsonObject().put("http.host", "localhost").put("http.port", 8080);

    JsonObject config = new JsonObject()
      .put("author", "subramanian")
      .mergeIn(serverConfig)
      .mergeIn(appConfig);
    System.out.println(config.encodePrettily());

  }

  //create JSON ARRAY
  public void createJsonArray() {
    JsonArray products = new JsonArray()
      .add(new JsonObject().put("id", 1).put("price", 10))
      .add(new JsonObject().put("id", 2).put("price", 90))
      .add(new JsonObject().put("id", 3).put("price", 60))
      .add(new JsonObject().put("id", 4).put("price", 50));
    System.out.println(products.encodePrettily());
  }

  public void createJsonFromObject() {
    //Create Json from java Object
    String result = Json.encode(new User());
    System.out.println(result);
    JsonObject user = new JsonObject(result);
    System.out.println(user.encodePrettily());
    User myUser = Json.decodeValue(result, User.class);
    System.out.println(myUser.toString());
  }

  //Promise & Future with json
  public Future<JsonObject> getUser() {
    Promise<JsonObject> promise = Promise.promise();
    JsonObject user = new JsonObject()
      .put("id", 1)
      .put("name", "Subramanian")
      .put("status", true)
      .put("address", new JsonObject().put("city", "Coimbatore").put("state", "Tamil Nadu"));
    promise.complete(user);
    return promise.future();
  }


  @Override
  public void start() throws Exception {
    super.start();
    //createJsonObject();
    //createJsonArray();
    createJsonFromObject();
    getUser().onSuccess(user -> {
      System.out.println(user.encodePrettily());
    });
  }
}
