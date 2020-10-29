package com.ibm.vertx.core.deployments;

import io.vertx.core.Vertx;

public class DeployerMain {
  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    //deploy greeter
    vertx.deployVerticle(new GreeterVerticle());
    //Verticle factory deployment
    vertx.deployVerticle(GreeterVerticle.class.getName());
    vertx.deployVerticle("com.ibm.vertx.core.deployments.GreeterVerticle");
  }
}
