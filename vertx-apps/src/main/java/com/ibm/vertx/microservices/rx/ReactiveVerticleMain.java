package com.ibm.vertx.microservices.rx;

import io.vertx.example.util.Runner;
import io.vertx.reactivex.core.AbstractVerticle;
import io.vertx.reactivex.core.http.HttpServer;
import io.vertx.reactivex.core.http.HttpServerResponse;

//reactive web server
class ReactiveWebServer extends AbstractVerticle {
  @Override
  public void start() throws Exception {
    super.start();
    HttpServer httpServer = vertx.createHttpServer();

    //handle client request
    httpServer.requestHandler(request -> {
      HttpServerResponse response = request.response();
      response.setStatusCode(200).end("Hello,This is Reactive Web Server");
    });
    httpServer.rxListen(3000).subscribe(server -> {
      System.out.println("Server is Running :  " + server.actualPort());
    });
  }
}


public class ReactiveVerticleMain extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(ReactiveVerticleMain.class);
  }

  @Override
  public void start() throws Exception {
    super.start();
    //   vertx.deployVerticle(new ReactiveWebServer(),cb);
    vertx.rxDeployVerticle(new ReactiveWebServer())
      .subscribe(deploymentID -> {
        System.out.println(deploymentID);
      }, error -> System.out.println(error));
  }
}
