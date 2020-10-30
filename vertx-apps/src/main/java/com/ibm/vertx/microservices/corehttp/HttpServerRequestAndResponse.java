package com.ibm.vertx.microservices.corehttp;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;
import io.vertx.example.util.Runner;

public class HttpServerRequestAndResponse extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(HttpServerRequestAndResponse.class);
  }

  public void readRequest() {
    vertx.createHttpServer().requestHandler(request -> {
      //read request
      request.bodyHandler(buffer -> {
        System.out.println(buffer.toString());
        //echo
        request.response().end(buffer);
      });
    }).listen(3000, httpServerAsyncResult -> {
      if (httpServerAsyncResult.succeeded()) {
        System.out.println("server is running at " + httpServerAsyncResult.result().actualPort());
      } else {
        System.out.println("Server has failed to start");
      }
    });
  }

  //how to send json
  public void sendJson() {
    vertx.createHttpServer().requestHandler(request -> {
      //send json
      JsonObject message = new JsonObject().put("message", "Hello how are you");
      request.response()
        .setStatusCode(200)
        .putHeader("content-type", "application/json")
        .end(message.encodePrettily());

    }).listen(3000, httpServerAsyncResult -> {
      if (httpServerAsyncResult.succeeded()) {
        System.out.println("server is running at " + httpServerAsyncResult.result().actualPort());
      } else {
        System.out.println("Server has failed to start");
      }
    });
  }

  public void readJson() {
    vertx.createHttpServer().requestHandler(request -> {
      //read request
      request.bodyHandler(buffer -> {
        JsonObject jsonObject = buffer.toJsonObject();
        System.out.println(jsonObject.getString("message"));
        System.out.println(jsonObject.encodePrettily());
        request.response().end(jsonObject.getString("message"));
      });

    }).listen(3000, httpServerAsyncResult -> {
      if (httpServerAsyncResult.succeeded()) {
        System.out.println("server is running at " + httpServerAsyncResult.result().actualPort());
      } else {
        System.out.println("Server has failed to start");
      }
    });
  }

  public void buildREST() {
    vertx.createHttpServer().requestHandler(request -> {
      //URL MAPPING AND METHOD MAPPING
      if (request.uri().equals("/api/users") && request.method() == HttpMethod.GET) {
        request.response().setStatusCode(200).end("users-GET");
      }
      if (request.uri().equals("/api/users") && request.method() == HttpMethod.POST) {
        request.response().setStatusCode(200).end("users-POST");
      }
      if (request.uri().equals("/api/users") && request.method() == HttpMethod.PUT) {
        request.response().setStatusCode(200).end("users-PUT");
      }
    }).listen(3000, httpServerAsyncResult -> {
      if (httpServerAsyncResult.succeeded()) {
        System.out.println("server is running at " + httpServerAsyncResult.result().actualPort());
      } else {
        System.out.println("Server has failed to start");
      }
    });
  }

  @Override
  public void start() throws Exception {
    super.start();
    //readRequest();
    //sendJson();
    //readJson();
    buildREST();

  }
}
