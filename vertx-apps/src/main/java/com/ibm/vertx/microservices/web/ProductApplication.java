package com.ibm.vertx.microservices.web;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.example.util.Runner;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

import java.util.HashMap;
import java.util.Map;

public class ProductApplication extends AbstractVerticle {
  //products-db
  private Map<String, JsonObject> products = new HashMap<>();

  public static void main(String[] args) {
    Runner.runExample(ProductApplication.class);
  }

  @Override
  public void start() throws Exception {
    super.start();
    setUpInitialData();

    Router productRouter = Router.router(vertx);
    //global settings for application; body handler ; to read entire body.
    //vertx will execute this handler for every in flight request.
    productRouter.route().handler(BodyHandler.create());

    productRouter.get("/").handler(this::handleListProducts);
    productRouter.get("/:productID").handler(this::handleGetProduct);
    //post / create product;
    productRouter.post("/create").handler(this::handleAddProduct);

    //update a existing product
    productRouter.put("/:productID").handler(this::handleUpdateProduct);
    //delete existing product
    productRouter.delete("/:productID").handler(this::handleDeleteHandler);


    vertx.createHttpServer().requestHandler(productRouter)
      .listen(3000, httpServerAsyncResult -> {
        if (httpServerAsyncResult.succeeded()) {
          System.out.println("Products Server is Running " + httpServerAsyncResult.result().actualPort());
        }
      });
  }

  private void handleDeleteHandler(RoutingContext routingContext) {
    String productID = routingContext.request().getParam("productID");
    HttpServerResponse response = routingContext.response();
    if (productID == null) {
      sendError(400, response);
    } else {
      products.remove(productID);
      JsonArray arr = new JsonArray();
      products.forEach((k, v) -> arr.add(v));
      routingContext.response().putHeader("content-type", "application/json").end(arr.encodePrettily());
    }
  }

  private void handleUpdateProduct(RoutingContext routingContext) {
    String productID = routingContext.request().getParam("productID");
    HttpServerResponse response = routingContext.response();
    if (productID == null) {
      sendError(400, response);
    } else {
      JsonObject product = routingContext.getBodyAsJson();
      System.out.println(product);
      if (product == null) {
        sendError(400, response);
      } else {
        products.put(productID, product);
        JsonArray arr = new JsonArray();
        products.forEach((k, v) -> arr.add(v));
        routingContext.response().putHeader("content-type", "application/json").end(arr.encodePrettily());
      }
    }
  }

  private void handleAddProduct(RoutingContext routingContext) {
    // clients send input as json(String),inside application; we need to parse into JsonObject
    //read payload(body) from request object.
    JsonObject product = routingContext.getBodyAsJson();
    System.out.println(product);
    addProduct(product);
    JsonArray arr = new JsonArray();
    products.forEach((k, v) -> arr.add(v));
    routingContext.response()
      .putHeader("content-type", "application/json")
      .end(arr.encodePrettily());
  }

  private void handleGetProduct(RoutingContext routingContext) {
    String productID = routingContext.request().getParam("productID");
    HttpServerResponse response = routingContext.response();
    if (productID == null) {
      //send error
      sendError(400, response);
    } else {
      JsonObject product = products.get(productID);
      if (product == null) {
        // sendError(404, response);
        sendError(400, response);
      } else {
        response.putHeader("content-type", "application/json")
          .setStatusCode(200)
          .end(product.encodePrettily());
      }
    }

  }

  private void sendError(int statusCode, HttpServerResponse response) {
    response.setStatusCode(statusCode).end();
  }


  private void handleListProducts(RoutingContext routingContext) {
    JsonArray arr = new JsonArray();
    products.forEach((k, v) -> arr.add(v));
    routingContext.response()
      .putHeader("content-type", "application/json")
      .setStatusCode(200)
      .end(arr.encodePrettily());
  }


  private void setUpInitialData() {
    addProduct(new JsonObject().put("id", "prod3568").put("name", "Egg Whisk").put("price", 3.99).put("weight", 150));
    addProduct(new JsonObject().put("id", "prod7340").put("name", "Tea Cosy").put("price", 5.99).put("weight", 100));
    addProduct(new JsonObject().put("id", "prod8643").put("name", "Spatula").put("price", 1.00).put("weight", 80));
  }

  private void addProduct(JsonObject product) {
    products.put(product.getString("id"), product);
  }
}
