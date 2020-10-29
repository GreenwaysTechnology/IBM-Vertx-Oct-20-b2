package com.ibm.vertx.core.deployments;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.example.util.Runner;

class MyVerticle extends AbstractVerticle {
  @Override
  public void start() throws Exception {
    super.start();
    System.out.println("My verticle " + Thread.currentThread().getName());
  }
}


public class EventLoopThreadMainVerticle extends AbstractVerticle {

  public static void main(String[] args) {
    Runner.runExample(EventLoopThreadMainVerticle.class);
  }

  @Override
  public void start() throws Exception {
    super.start();
    System.out.println("Main Verticle " + Thread.currentThread().getName());
    for (int i = 1; i <= 25; i++)
      vertx.deployVerticle(new MyVerticle());
  }
}
