package com.ibm.vertx.microservices.cluster;


import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.spi.cluster.ClusterManager;
import io.vertx.spi.cluster.hazelcast.HazelcastClusterManager;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

public class PublisherVerticle extends AbstractVerticle {

  public static void main(String[] args) {

    ClusterManager mgr = new HazelcastClusterManager();
    //vertx engine configuration
    VertxOptions options = new VertxOptions().setClusterManager(mgr);

    //cluster deployment
    Vertx.clusteredVertx(options, vertxAsyncResult -> {
      if (vertxAsyncResult.succeeded()) {
        //deploy verticles on cluster env.
        DeploymentOptions deploymentOptions = new DeploymentOptions().setInstances(5);
        vertxAsyncResult.result().deployVerticle("com.ibm.vertx.microservices.cluster.PublisherVerticle", deploymentOptions, res -> {
          if (res.succeeded()) {
            System.out.println("Deployment id is: " + res.result());
          } else {
            System.out.println("Deployment failed!");
          }
        });

      } else {
        System.out.println("Cluster up failed: " + vertxAsyncResult.cause());
      }
    });

  }

  @Override
  public void start() throws Exception {
    super.start();
    RuntimeMXBean runtimeBean = ManagementFactory.getRuntimeMXBean();
    String jvmName = runtimeBean.getName();
    //processid
    long pid = Long.valueOf(jvmName.split("@")[0]);
    vertx.setPeriodic(5000, ar -> {
      System.out.println("PID  = " + pid + " Thread = " + Thread.currentThread().getName());
      //publish message
      String news = "Last 24 hrs, 50000 covid patients in India" + " From  " + jvmName;
      vertx.eventBus().publish("news.in.covid", news);
    });

  }
}
