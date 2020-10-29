package com.ibm.vertx.core.deployments;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;

public class HelloWorldVerticle extends AbstractVerticle {

  public static void main(String[] args) {
    Vertx vertEngine = Vertx.vertx();
    //deploy on vertx Engine
    vertEngine.deployVerticle(new HelloWorldVerticle());
    vertEngine.close();
  }

  @Override
  public void start() throws Exception {
    super.start();
    System.out.println("Hello World Verticle");
  }
}
