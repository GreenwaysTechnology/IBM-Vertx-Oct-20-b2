package com.ibm.vertx.microservices.web.client;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;
import io.vertx.example.util.Runner;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.handler.BodyHandler;

class ProviderVerticle extends AbstractVerticle {
  @Override
  public void start() throws Exception {
    super.start();
    Router router = Router.router(vertx);

    router.route().handler(BodyHandler.create());

    router.get("/api/message/:message").handler(routingContext -> {
      String message = routingContext.request().getParam("message");
      routingContext.response().end(message);
    });
    //post
    router.post("/api/message/create").handler(routingContext -> {
      System.out.println(routingContext.getBodyAsJson());
      routingContext.response().end("Created");
    });

    vertx.createHttpServer()
      .requestHandler(router)
      .listen(3000, httpServerAsyncResult -> {
        if (httpServerAsyncResult.succeeded()) {
          System.out.println("Provider Verticle Server is Running " + httpServerAsyncResult.result().actualPort());
        }
      });
  }
}

class ConsumerVerticle extends AbstractVerticle {
  WebClient webClient;

  @Override
  public void start() throws Exception {
    super.start();
    //create webclient object
    webClient = WebClient.create(vertx);
    Router router = Router.router(vertx);

    router.get("/:message").handler(routingContext -> {
      String message = routingContext.request().getParam("message");
      //webclient
      webClient
        .get(3000, "localhost", "/api/message/" + message)
        .send(httpResponseAsyncResult -> {
          if (httpResponseAsyncResult.succeeded()) {
            routingContext.response().setStatusCode(200).end(httpResponseAsyncResult.result().bodyAsString());
          }
        });
    });

    router.post("/create").handler(routingContext -> {
      JsonObject user = new JsonObject()
        .put("firstName", "Subramanian")
        .put("lastName", "Murugan")
        .put("male", true);
      //webclient
      webClient
        .post(3000, "localhost", "/api/message/create")
        .sendJson(user, ar -> {
          if (ar.succeeded()) {
            System.out.println("Got HTTP response with status " + ar.result().statusCode());
            routingContext.response().setStatusCode(201).end("created");
          } else {
            ar.cause().printStackTrace();
          }
        });
    });

    vertx.createHttpServer().requestHandler(router).listen(3001, httpServerAsyncResult -> {
      if (httpServerAsyncResult.succeeded()) {
        System.out.println("Consumer Verticle Server is Running " + httpServerAsyncResult.result().actualPort());
      }
    });

  }
}


public class WebClientMain extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(WebClientMain.class);
  }

  @Override
  public void start() throws Exception {
    super.start();
    vertx.deployVerticle(new ProviderVerticle());
    vertx.deployVerticle(new ConsumerVerticle());
  }
}
