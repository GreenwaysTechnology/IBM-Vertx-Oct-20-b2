package com.ibm.vertx.core.deployments;

import io.vertx.core.AbstractVerticle;

public class GreeterVerticle extends AbstractVerticle {
  @Override
  public void start() throws Exception {
    super.start();
    System.out.println("Greeter Verticle");
  }
}
