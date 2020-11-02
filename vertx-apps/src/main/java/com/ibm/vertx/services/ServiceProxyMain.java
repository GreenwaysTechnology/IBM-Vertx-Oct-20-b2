package com.ibm.vertx.services;

import io.vertx.codegen.annotations.Fluent;
import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.*;
import io.vertx.example.util.Runner;
import io.vertx.serviceproxy.ServiceBinder;

/**
 * Service Proxy Interface
 * 1.annotate with two annotations
 * 2.we need unique event bus address: this interface is exposed via event only.
 * 3.we need to write biz apis.
 *    Rules;
 *      1.api must return void
 *      2.args must be Handler<AsyncResult<T>> handler
 *      3.extra data arg may be present
 *      4.if you want to return  type Can be interface type itself and must be annotated with
 * @Fluent
 * 4.you must have an api called
 *    static GreeterService createProxy(Vertx vertx, String address) {
 *     return new GreeterServiceVertxEBProxy(vertx, address);
 *   }
 *
 * @ProxyGen
 * @VertxGen
 */

@ProxyGen //generate proxy classes
@VertxGen //generate proxy classes for other languages
interface GreeterService {
  //unique event bus Address
  static String EVENTBUS_ADDRESS = GreeterService.class.getName();
  //api to create Service Proxy Object
  static GreeterService createProxy(Vertx vertx, String address) {
    return new GreeterServiceVertxEBProxy(vertx, address);
  }

  //biz apis
  void sayHello(String name, Handler<AsyncResult<String>> handler);

  @Fluent
  GreeterService doSomething();
}

class GreeterServiceImpl implements GreeterService {
  @Override
  public void sayHello(String name, Handler<AsyncResult<String>> handler) {
    handler.handle(Future.succeededFuture("Hello" + name));
  }

  @Override
  public GreeterService doSomething() {
    return this;
  }
}


class HaiVerticle extends AbstractVerticle {
  @Override
  public void start() throws Exception {
    super.start();
    //Call service
    GreeterService service = GreeterService.createProxy(vertx, GreeterService.EVENTBUS_ADDRESS);

    service.sayHello("Subramanian", ar -> {
      if (ar.succeeded()) {
        System.out.println(ar.result());
      }
    });
    service.doSomething().sayHello("Ram", ar -> {
      if (ar.succeeded()) {
        System.out.println(ar.result());
      }
    });
  }
}
public class ServiceProxyMain extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(ServiceProxyMain.class);
  }

  @Override
  public void start() throws Exception {
    super.start();
    //Service Registeration
    GreeterService greeterService = new GreeterServiceImpl();
    new ServiceBinder(vertx).setAddress(GreeterService.EVENTBUS_ADDRESS).register(GreeterService.class, greeterService);

    //deploy
    vertx.deployVerticle(new HaiVerticle());

  }
}
