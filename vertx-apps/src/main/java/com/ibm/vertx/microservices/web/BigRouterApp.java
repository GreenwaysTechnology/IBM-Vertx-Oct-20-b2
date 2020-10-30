package com.ibm.vertx.microservices.web;

import io.vertx.core.AbstractVerticle;
import io.vertx.example.util.Runner;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

public class BigRouterApp extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(BigRouterApp.class);
  }

  @Override
  public void start() throws Exception {
    super.start();
    Router productRouter = Router.router(vertx);
    Router userRouter = Router.router(vertx);
    Router accountRouter = Router.router(vertx);
    Router appRouter = Router.router(vertx);
    ///routes
    productRouter.get("/").handler(routingContext -> {
      System.out.println(routingContext.get("message").toString());
      routingContext.response().end("proudcts list");
    });
    userRouter.get("/").handler(routingContext -> {
      routingContext.response().end("User list");
    });
    accountRouter.get("/").handler(routingContext -> {
      routingContext.response().end("Acount list");
    });
    //mount/bind app with sub routers
    appRouter.route().handler(BodyHandler.create());
    appRouter.route().handler(routingContext -> {
      routingContext.put("message", "Ibm");
      routingContext.next();
    });
    appRouter.mountSubRouter("/api/products", productRouter);
    appRouter.mountSubRouter("/api/users", userRouter);
    appRouter.mountSubRouter("/api/accounts", accountRouter);

    vertx.createHttpServer().requestHandler(appRouter)
      .listen(3000, httpServerAsyncResult -> {
        if (httpServerAsyncResult.succeeded()) {
          System.out.println("Products Server is Running " + httpServerAsyncResult.result().actualPort());
        }
      });

  }
}
