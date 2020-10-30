package com.ibm.vertx.core.asyncwrappers;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.example.util.Runner;

class UserServiceVerticle extends AbstractVerticle {

  public Future<String> getUser() {
    Promise<String> promise = Promise.promise();
    String userName = "admin";
    if (userName != null) {
      promise.complete(userName);
    } else {
      promise.fail(new RuntimeException("User not found"));
    }
    return promise.future();
  }

  public Future<String> login(String userName) {
    Promise<String> promise = Promise.promise();
    if (userName.equals("admin")) {
      promise.complete("login success");
    } else {
      promise.fail(new RuntimeException("login failed"));
    }
    return promise.future();
  }

  public Future<String> showPage(String loginStatus) {
    Promise<String> promise = Promise.promise();
    if (loginStatus.equals("login successxx")) {
      promise.complete("You are admin");
    } else {
      promise.fail(new RuntimeException("You are guest"));
    }
    return promise.future();
  }


  @Override
  public void start() throws Exception {
    super.start();
    getUser().onComplete(userAsyncResult -> {
      if (userAsyncResult.succeeded()) {
        System.out.println("Get user is called");
        //call login method
        login(userAsyncResult.result()).onComplete(loginuserAsyncResult -> {
          System.out.println("login is called");
          if (loginuserAsyncResult.succeeded()) {
            System.out.println("show page is called");
            showPage(loginuserAsyncResult.result()).onComplete(pageuserAsyncResult -> {
              if (pageuserAsyncResult.succeeded()) {
                System.out.println(pageuserAsyncResult.result());
              } else {
                System.out.println(pageuserAsyncResult.cause());
              }
            });
          } else {
            System.out.println(loginuserAsyncResult.cause());
          }
        });
      } else {
        System.out.println(userAsyncResult.cause());
      }
    });
  }
}


public class CallbackVerticle extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(CallbackVerticle.class);
  }

  @Override
  public void start() throws Exception {
    super.start();
    vertx.deployVerticle(new UserServiceVerticle());
  }
}
