package com.ibm.vertx.microservices.web;

import io.vertx.core.AbstractVerticle;
import io.vertx.example.util.Runner;
import io.vertx.ext.web.Router;

public class SimpleRouter extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(SimpleRouter.class);
  }

  @Override
  public void start() throws Exception {
    super.start();
    //create Router object
    Router router = Router.router(vertx);
    //http get method with url
//    Route route1 = router.get("/api/message/hello");
//    route1.handler(routingContext -> {
//      routingContext.response().end("Hello");
//    });
//    router.get("/api/message/hello")
//      .handler(routingContext -> {
//        routingContext.response().end("Hello");
//      });
//    router.get("/api/message/hai")
//      .handler(routingContext -> {
//        routingContext.response().end("Hai");
//      });
//    router.get("/api/message/greet")
//      .handler(routingContext -> {
//        routingContext.response().end("Greet");
//      });
     router.get("/api/message/hello")
      .handler(routingContext -> {
        routingContext.response().end("Hello");
      });
    router.get("/api/message/hai")
      .handler(routingContext -> {
        routingContext.response().end("Hai");
      });
    router.get("/api/message/greet")
      .handler(routingContext -> {
        routingContext.response().end("Greet");
      });
    vertx.createHttpServer().requestHandler(router).listen(3000, httpServerAsyncResult -> {
      if (httpServerAsyncResult.succeeded()) {
        System.out.println("server is running at " + httpServerAsyncResult.result().actualPort());
      } else {
        System.out.println("Server has failed to start");
      }
    });


  }

}
