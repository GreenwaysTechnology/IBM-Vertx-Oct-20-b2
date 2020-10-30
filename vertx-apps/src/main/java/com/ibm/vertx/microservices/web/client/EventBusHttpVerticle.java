package com.ibm.vertx.microservices.web.client;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.example.util.Runner;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

class Address {
  public final static String MESSAGE_ADDRESS = "ibm.alert";
  public final static String PRODUCT_FETCH = "product.findall";

}

//front end verticle
class HttpServerVerticle extends AbstractVerticle {
  @Override
  public void start() throws Exception {
    super.start();
    System.out.println("http server ");
    Router router = Router.router(vertx);
    router.route().handler(BodyHandler.create());
    router.get("/api/:message").handler(routingContext -> {
      String message = routingContext.request().getParam("message");
      //send message via event bus
      vertx.eventBus().send(Address.MESSAGE_ADDRESS, message);
      routingContext.response().end("message has been published");
    });
    vertx.createHttpServer().requestHandler(router).listen(3001, httpServerAsyncResult -> {
      if (httpServerAsyncResult.succeeded()) {
        System.out.println("Server is Running " + httpServerAsyncResult.result().actualPort());
      }
    });

  }
}

//back end service
class EventBusMessageVerticle extends AbstractVerticle {
  @Override
  public void start() throws Exception {
    super.start();
    System.out.println("Event bus message ");
    MessageConsumer<String> messageConsumer = vertx.eventBus().consumer(Address.MESSAGE_ADDRESS);
    messageConsumer.handler(message -> {
      System.out.println("Back end Service " + message.body());
    });
  }
}

public class EventBusHttpVerticle extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(EventBusHttpVerticle.class);
  }

  @Override
  public void start() throws Exception {
    super.start();
    vertx.deployVerticle(new HttpServerVerticle());
    vertx.deployVerticle(new EventBusMessageVerticle());
  }
}
