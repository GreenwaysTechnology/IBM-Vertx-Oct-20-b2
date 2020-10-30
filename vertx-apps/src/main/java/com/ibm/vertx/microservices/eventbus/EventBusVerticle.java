package com.ibm.vertx.microservices.eventbus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.MessageConsumer;
import io.vertx.example.util.Runner;

//pub-sub; one to many; one message can be delivered to many vertilces at  a time.
class Address {
  public final static String PUB_SUB_ADDRESS = "news.in.covid";
  public final static String POINT_TO_POINT = "covid.fin.request";
  public final static String REQUEST_REPLY = "covid.lab.report";
}

class LabVerticle extends AbstractVerticle {
  public void consume() {
    EventBus eventBus = vertx.eventBus();
    //pub-sub
    MessageConsumer<String> messageConsumer = eventBus.consumer(Address.REQUEST_REPLY);
    //handle /process the message/news
    messageConsumer.handler(news -> {
      System.out.println("Request -  : " + news.body());
      //sending reply /ack
      news.reply("Patient is Crictal, Need More attention");
    });
  }

  @Override
  public void start() throws Exception {
    super.start();
    consume();
  }
}

class ReportVerticle extends AbstractVerticle {

  public void sendReport() {
    vertx.setTimer(5000, ar -> {
      String message = "Report of Mr.x";
      vertx.eventBus().request(Address.REQUEST_REPLY, message, asyncResult -> {
        if (asyncResult.succeeded()) {
          System.out.println(asyncResult.result().body());
        } else {
          System.out.println(asyncResult.cause());
        }
      });
    });
  }

  @Override
  public void start() throws Exception {
    super.start();
    sendReport();
  }
}


/////////////////////////////////////////////////////////////////////////////////////////////
class CenertalFinanceVerticle extends AbstractVerticle {

  public void consume() {
    EventBus eventBus = vertx.eventBus();
    MessageConsumer<String> messageConsumer = eventBus.consumer(Address.POINT_TO_POINT);
    //handle /process the message/news
    messageConsumer.handler(news -> {
      System.out.println("Request 1 -  : " + news.body());
    });
  }

  public void consume2() {
    EventBus eventBus = vertx.eventBus();
    MessageConsumer<String> messageConsumer = eventBus.consumer(Address.POINT_TO_POINT);
    //handle /process the message/news
    messageConsumer.handler(news -> {
      System.out.println("Request 2 -  : " + news.body());
    });
  }

  @Override
  public void start() throws Exception {
    super.start();
    consume();
    consume2();
  }
}




class FinanceRequestVerticle extends AbstractVerticle {

  public void requestFinance() {
    System.out.println("Finance Request started....");
    vertx.setTimer(5000, ar -> {
      //point to point : send method
      String message = "Dear Team, We request that we want 1 Billion Money for Covid";
      //point to point ; send
      vertx.eventBus().send(Address.POINT_TO_POINT, message);
    });
  }

  @Override
  public void start() throws Exception {
    super.start();
    requestFinance();
  }
}







///////////////////////////////////
class NewsSevenVerticle extends AbstractVerticle {
  public void consume() {
    EventBus eventBus = vertx.eventBus();
    MessageConsumer<String> messageConsumer = eventBus.consumer(Address.PUB_SUB_ADDRESS);
    messageConsumer.handler(message -> {
      System.out.println(this.getClass().getSimpleName() + " " + message.body());
    });
  }

  @Override
  public void start() throws Exception {
    super.start();
    consume();
  }
}

class BBCVerticle extends AbstractVerticle {
  public void consume() {
    EventBus eventBus = vertx.eventBus();
    MessageConsumer<String> messageConsumer = eventBus.consumer(Address.PUB_SUB_ADDRESS);
    messageConsumer.handler(message -> {
      System.out.println(this.getClass().getSimpleName() + " " + message.body());
    });
  }

  @Override
  public void start() throws Exception {
    super.start();
    consume();
  }
}

class NDTVVerticle extends AbstractVerticle {
  public void consume() {
    EventBus eventBus = vertx.eventBus();
    MessageConsumer<String> messageConsumer = eventBus.consumer(Address.PUB_SUB_ADDRESS);
    messageConsumer.handler(message -> {
      System.out.println(this.getClass().getSimpleName() + " " + message.body());
    });
  }

  @Override
  public void start() throws Exception {
    super.start();
    consume();
  }
}

//publisher publish news to all news tv channels
class PublisherVerticle extends AbstractVerticle {

  public void publish() {
    //one to many
    EventBus eventBus = vertx.eventBus();
    String message = "In India more than 80 lakh people affected covid";
    //publish inside timer
    vertx.setTimer(5000, h -> {
      System.out.println("Started Publishing message.....");
      eventBus.publish(Address.PUB_SUB_ADDRESS, message);
    });

  }

  @Override
  public void start() throws Exception {
    super.start();
    publish();
  }
}


public class EventBusVerticle extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(EventBusVerticle.class);
  }

  public void pubsub() {
    vertx.deployVerticle(new PublisherVerticle());
    vertx.deployVerticle(new NDTVVerticle());
    vertx.deployVerticle(new BBCVerticle());
    vertx.deployVerticle(new NewsSevenVerticle());
  }
  public void pointToPoint() {
    vertx.deployVerticle(new CenertalFinanceVerticle());
    vertx.deployVerticle(new FinanceRequestVerticle());
  }
  public void requestReply() {
    vertx.deployVerticle(new ReportVerticle());
    vertx.deployVerticle(new LabVerticle());
  }

  @Override
  public void start() throws Exception {
    super.start();
//    pubsub();
//    pointToPoint();
    requestReply();
  }
}
