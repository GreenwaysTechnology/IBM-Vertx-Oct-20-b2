package com.ibm.vertx.core;

import io.vertx.core.AbstractVerticle;

public class HelloWorld extends AbstractVerticle {

  @Override
  public void start() throws Exception {
    super.start();
    System.out.println("Hello World Verticle");
  }
}
