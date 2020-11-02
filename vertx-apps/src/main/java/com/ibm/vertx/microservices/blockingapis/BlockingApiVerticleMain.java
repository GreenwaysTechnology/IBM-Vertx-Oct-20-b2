package com.ibm.vertx.microservices.blockingapis;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.example.util.Runner;
import io.vertx.ext.web.Router;

class BlockingCodeVerticle extends AbstractVerticle {
  @Override
  public void start() throws Exception {
    super.start();
    vertx.setTimer(1000, a -> {
      System.out.println("inside timer");
      System.out.println(Thread.currentThread().getName());
    });
    System.out.println(Thread.currentThread().getName());
    //blocking code
    Thread.sleep(10000);
    System.out.println("timeout , my data is ready");
  }
}

class BlockingAndNonBlocking extends AbstractVerticle {
  private void sayHello(Promise<String> promise) {
    System.out.println("Say Hello : " + Thread.currentThread().getName());
    try {
      Thread.sleep(4000);
      System.out.println("Wake Up for sending data to Non blocking Service");
      //this result will be accessed inside non blocking code
      promise.complete("Hey this is blocking Result");

    } catch (InterruptedException es) {
      promise.fail("Something went wrong in blocking service");
    }
  }

  //read result from blocking service
  private void resultHandler(AsyncResult<String> ar) {
    System.out.println("Result Handler" + Thread.currentThread().getName());
    if (ar.succeeded()) {
      System.out.println("Blocking api Result goes Ready Here");
      System.out.println(ar.result());
    } else {
      System.out.println(ar.cause().getMessage());
    }
  }

  //http handler
  public void httpAndBlocking() {
    Router router = Router.router(vertx);

    router.get("/api/nonblocking").handler(routingContext -> {
      System.out.println(Thread.currentThread().getName());
      routingContext.response().end("Non blocking Result");
    });
    //for writing blocking code and waits for blocking result and return back
    router.get("/api/blocking").blockingHandler(routingContext -> {
      //invoke blocking service like jpa calls..
      System.out.println(Thread.currentThread().getName());
      try {
        //blocking code
        Thread.sleep(5000);
        String blockingResult = "Blocking result";
        routingContext.response().end(blockingResult);
      } catch (Exception e) {

      }
    });
    vertx.createHttpServer().requestHandler(router).listen(3000, ar -> {
      if (ar.succeeded()) {
        System.out.println("Server is ready At " + ar.result().actualPort());
      }
    });
  }


  @Override
  public void start() throws Exception {
    super.start();
    vertx.executeBlocking(this::sayHello, this::resultHandler);
    httpAndBlocking();
  }
}


public class BlockingApiVerticleMain extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(BlockingApiVerticleMain.class);
  }

  @Override
  public void start() throws Exception {
    super.start();
//    DeploymentOptions deploymentOptions = new DeploymentOptions();
//    deploymentOptions.setWorker(true); //sets as a worker verticle
    DeploymentOptions deploymentOptions = new DeploymentOptions().setWorker(true);
    //vertx.deployVerticle(new BlockingCodeVerticle(), deploymentOptions);

    vertx.deployVerticle(new BlockingAndNonBlocking());

  }
}
