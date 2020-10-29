package com.ibm.vertx.core.asyncwrappers;

import io.vertx.core.*;
import io.vertx.example.util.Runner;


class MessagePromiseServiceVerticle extends AbstractVerticle {
  //send simple success response only
  public Future<String> getSuccessMessage() {
    //Create Promise Object
    Promise<String> promise = Promise.promise();
    //encapsulate response
    promise.complete("Hello,I am Promise");
    //return the fututure , so that somebody can handle.
    return promise.future();
  }

  @Override
  public void start() throws Exception {
    super.start();
//    getSuccessMessage().future().onComplete(asyncResult -> {
//      if (asyncResult.succeeded()) {
//        System.out.println(asyncResult.result());
//      }
//    });
    getSuccessMessage().onComplete(asyncResult -> {
      if (asyncResult.succeeded()) {
        System.out.println(asyncResult.result());
      }
    });
  }
}


class MessageServiceVerticle extends AbstractVerticle {

  //apis to send data via future;

  //send empty response
  public Future<Void> getEmptyMessage() {
    //Create Future Object
    Future<Void> future = Future.future();
    //encapsulate response
    future.complete();
    //return the fututure , so that somebody can handle.
    return future;
  }

  //send simple success response only
  public Future<String> getSuccessMessage() {
    //Create Future Object
    Future<String> future = Future.future();
    //encapsulate response
    future.complete("Hello,I am Future");
    //return the fututure , so that somebody can handle.
    return future;
  }

  //send simple error response only
  public Future<String> getErrorMessage() {
    //Create Future Object
    Future<String> future = Future.future();
    //encapsulate response
    future.fail("Something went wrong!!");
    //return the fututure , so that somebody can handle.
    return future;
  }

  //how to send success or error message based on logic
  public Future<String> getGreetingMessage() {
    //Create Future Object
    Future<String> future = Future.future();
    //encapsulate response
    String message = "hello";
    if (message.equals("hello")) {
      future.complete("Yes Hello Message i got it");
    } else {
      future.fail(new RuntimeException("No, i did not get any message"));
    }

    //return the fututure , so that somebody can handle.
    return future;
  }

  //create Future object using factory apis
  public Future<String> getHelloMessage() {
    //encapsulate response
    String message = "hello";
    if (message.equals("hello")) {
      return Future.succeededFuture("Yes Hello Message i got it");
    } else {
      return Future.failedFuture(new RuntimeException("No, i did not get any message"));
    }
  }

  //create Future object and encapulate via function as parameter
  public void getHaiMessage(Handler<AsyncResult<String>> aHandler) {
    String message = "hai";
    if (message.equals("hai")) {
      //create Future object and encapsulate data
      aHandler.handle(Future.succeededFuture("Yes Hello Message i got it"));
    } else {
      aHandler.handle(Future.failedFuture(new RuntimeException("No, i did not get any message")));
    }
  }

  @Override
  public void start() throws Exception {
    super.start();
    //handler
    if (getEmptyMessage().succeeded()) {
      System.out.println("Empty message");
    }
    //handler attachment ; we have more handler attachment api
    getSuccessMessage().onComplete(new Handler<AsyncResult<String>>() {
      @Override
      public void handle(AsyncResult<String> asyncResult) {
        if (asyncResult.succeeded()) {
          System.out.println(asyncResult.result());
        } else {
          System.out.println(asyncResult.cause());
        }
      }
    });
    getSuccessMessage().onComplete(asyncResult -> {
      if (asyncResult.succeeded()) {
        System.out.println(asyncResult.result());
      } else {
        System.out.println(asyncResult.cause());
      }
    });
    getSuccessMessage().onSuccess(response -> System.out.println(response));
    getSuccessMessage().onSuccess(System.out::println);
    ////////////////////////////////////////////////////////////////////////////
    getErrorMessage().onComplete(asyncResult -> {
      if (asyncResult.failed()) {
        System.out.println(asyncResult.cause());
      }
    });
    getErrorMessage().onFailure(errors -> System.out.println(errors));
    getErrorMessage().onFailure(System.out::println);
    ////////////////////////////////////////////////////////////////////////////////////////////////

    getGreetingMessage().onComplete(asyncResult -> {
      if (asyncResult.succeeded()) {
        System.out.println(asyncResult.result());
      } else {
        System.out.println(asyncResult.cause());
      }
    });
    getGreetingMessage()
      .onSuccess(System.out::println)
      .onFailure(System.out::println);
    ////////////////////////////////////////////////////////////////////////////////////////////////
    getHelloMessage()
      .onSuccess(System.out::println)
      .onFailure(System.out::println);
    //////////////////////////////////////////////////////////////////////////////////////////////////
    //function as parameter
    getHaiMessage(response -> {
      if (response.succeeded()) {
        System.out.println("Got Response" + response.result());
      } else {
        System.out.println("Got Error Response " + response.cause());
      }
    });


  }
}

public class FutureAppMainVerticle extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(FutureAppMainVerticle.class);
  }

  @Override
  public void start() throws Exception {
    super.start();
    //vertx.deployVerticle(new MessageServiceVerticle());
    vertx.deployVerticle(new MessagePromiseServiceVerticle());
  }
}
