package com.ibm.vertx.core.deployments;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.example.util.Runner;

public class MainVerticle extends AbstractVerticle {
  public static void main(String[] args) {
//     Vertx vertx = Vertx.vertx();
//     vertx.deployVerticle(new MainVerticle());
    Runner.runExample(MainVerticle.class);
  }

  @Override
  public void start() throws Exception {
    super.start();
    System.out.println("Main verticle");
    vertx.deployVerticle(new GreeterVerticle());
  }
}
