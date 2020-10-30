package com.ibm.vertx.core.async.timers;

import io.vertx.core.*;
import io.vertx.core.json.JsonObject;
import io.vertx.example.util.Runner;

import java.util.Date;

public class TimersVerticle extends AbstractVerticle {

  public static void main(String[] args) {
    Runner.runExample(TimersVerticle.class);
  }

  public void blockMe(String message) {
    System.out.println(message);
  }

  public void delay(long timeout) {
    //non blocking api
    vertx.setTimer(timeout, asynHandler -> {
      //logic for timer
      System.out.println("I am ready after " + timeout);
    });
  }

  //getasyn
  public Future<JsonObject> getAsynMessage(long timeout) {
    Promise<JsonObject> promise = Promise.promise();
    vertx.setTimer(timeout, asynHandler -> {
      JsonObject jsonObject = new JsonObject().put("message", "Hello");
      promise.complete(jsonObject);
    });
    return promise.future();
  }

  public void tick(long timeout, Handler<AsyncResult<String>> aHandler) {
    long timerId = vertx.setPeriodic(timeout, han -> {
      aHandler.handle(Future.succeededFuture(new Date().toString()));
    });
    //stopping timer
    vertx.setTimer(10000, myHandler -> {
      System.out.println("Stopping emitting Date values");
      vertx.cancelTimer(timerId);
    });

  }

  @Override
  public void start() throws Exception {
    super.start();
    blockMe("start");
    delay(1000);
    getAsynMessage(2000).onComplete(jsonObjectAsyncResult -> {
      if (jsonObjectAsyncResult.succeeded()) {
        System.out.println(jsonObjectAsyncResult.result().encodePrettily());
      }
    });
    tick(1000, response -> {
      if (response.succeeded()) {
        System.out.println(response.result());
      }
    });
    blockMe("end");
  }
}
