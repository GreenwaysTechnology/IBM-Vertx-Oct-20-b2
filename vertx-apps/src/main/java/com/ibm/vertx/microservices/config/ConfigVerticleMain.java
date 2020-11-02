package com.ibm.vertx.microservices.config;

import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.json.JsonObject;
import io.vertx.example.util.Runner;
import io.vertx.ext.web.Router;

class ApplicationConfig extends AbstractVerticle {

  public void readFromJson() {
    //storage options
    ConfigStoreOptions options = new ConfigStoreOptions();
    options.setType("file");
    options.setFormat("json");
    options.setConfig(new JsonObject().put("path", "conf/app.config.json"));

    ConfigRetriever retriever = ConfigRetriever.create(vertx,
      new ConfigRetrieverOptions().addStore(options));

    retriever.getConfig(config -> {
      if (config.succeeded()) {
        System.out.println("Config is Ready");
        //System.out.println(config.result());
        JsonObject configRes = config.result();
        System.out.println(configRes.getString("app.name"));
      } else {
        System.out.println("Config Error : " + config.cause());
      }
    });

  }

  @Override
  public void start() throws Exception {
    super.start();
    readFromJson();
    System.out.println(config().getString("message", "default Message"));
    Router router = Router.router(vertx);
    router.get("/api/message")
      .handler(routingContext -> routingContext
        .response()
        .end(config().getString("message", "Default message")));
    vertx.createHttpServer()
      .requestHandler(router)
      .listen(config().getInteger("http.port", 8080), server -> {
        System.out.println("Server is running " + server.result().actualPort());
      });
  }
}

public class ConfigVerticleMain extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(ConfigVerticleMain.class);
  }

  @Override
  public void start() throws Exception {
    super.start();
    JsonObject appConfig = new JsonObject()
      .put("message", "Welcome to Ibm")
      .put("http.port", 3000);

    DeploymentOptions options = new DeploymentOptions().setConfig(appConfig);
    vertx.deployVerticle(new ApplicationConfig(), options);
  }
}
