package com.ibm.vertx.core.asyncwrappers;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.example.util.Runner;

class UserVerticle extends AbstractVerticle {

  public Future<String> getUser() {
    System.out.println("Get user is called");

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
    System.out.println("login is called");
    Promise<String> promise = Promise.promise();
    if (userName.equals("admin")) {
      promise.complete("login success");
    } else {
      promise.fail(new RuntimeException("login failed"));
    }
    return promise.future();
  }

  public Future<String> showPage(String loginStatus) {
    System.out.println("show page is called");
    Promise<String> promise = Promise.promise();
    if (loginStatus.equals("login success")) {
      promise.complete("You are admin");
    } else {
      promise.fail(new RuntimeException("You are guest"));
    }
    return promise.future();
  }

  //callback hell code
  public void processUsingCallbackHell() {
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

  //soultion to callback
  public void compose() {

    getUser().compose(userName -> {
      return login(userName);
    }).compose(loginStatus -> {
      return showPage(loginStatus);
    }).onComplete(asyncResult -> {
      if (asyncResult.succeeded()) {
        System.out.println(asyncResult.result());
      } else {
        System.out.println(asyncResult.cause());
      }
    });
    //refactoring
    getUser()
      .compose(this::login)
      .compose(this::showPage)
      .onSuccess(System.out::println)
      .onFailure(System.out::println);
  }


  @Override
  public void start() throws Exception {
    super.start();
    compose();


  }
}

public class CallbackHellSoution extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(CallbackHellSoution.class);
  }

  @Override
  public void start() throws Exception {
    super.start();
    vertx.deployVerticle(new UserVerticle());
  }
}
