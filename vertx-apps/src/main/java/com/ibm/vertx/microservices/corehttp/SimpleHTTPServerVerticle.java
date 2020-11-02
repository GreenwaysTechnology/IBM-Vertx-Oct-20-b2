package com.ibm.vertx.microservices.corehttp;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.example.util.Runner;

public class SimpleHTTPServerVerticle extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(SimpleHTTPServerVerticle.class);
  }

  public void createHttpServer() {
    HttpServerOptions options = new HttpServerOptions();
    options.setPort(3000);
    //server creaton
    HttpServer server = vertx.createHttpServer(options);
    //request handler
    server.requestHandler(request -> {
      //handle client request
      HttpServerResponse response = request.response();
      response.end("Hello ,Vertx Web Server");
    });
    //start web container
    server.listen(httpServerAsyncResult -> {
      if (httpServerAsyncResult.succeeded()) {
        System.out.println("server is running at " + httpServerAsyncResult.result().actualPort());
      } else {
        System.out.println("Server has failed to start");
      }
    });
  }

  public void createHttpServerFluentPattern() {
    vertx.createHttpServer()
      .requestHandler(request -> {
        request.response().end("Hello ,Vertx Web Server");
      })
      .listen(3000, httpServerAsyncResult -> {
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
    createHttpServerFluentPattern();

  }
}
