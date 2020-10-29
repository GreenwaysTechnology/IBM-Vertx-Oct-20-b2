package com.ibm.vertx.core;

import io.vertx.core.Vertx;

public class VertEngineCreation {
  public static void main(String[] args) {
    Vertx vertxEngine = Vertx.vertx();
    System.out.println(vertxEngine.getClass().getName());
  }
}
