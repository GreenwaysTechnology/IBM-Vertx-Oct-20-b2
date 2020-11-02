package com.ibm.vertx.microservices.jdbc;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.example.util.Runner;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.SQLConnection;

public class SimpleJDBC extends AbstractVerticle {
  public static void main(String[] args) {
    Runner.runExample(SimpleJDBC.class);
  }

  @Override
  public void start() throws Exception {
    super.start();
    final JDBCClient client = JDBCClient.createShared(vertx, new JsonObject()
      .put("url", "jdbc:hsqldb:mem:test?shutdown=true")
      .put("driver_class", "org.hsqldb.jdbcDriver")
      .put("max_pool_size", 30)
      .put("user", "SA")
      .put("password", ""));

    client.getConnection(connection -> {
      if (connection.succeeded()) {
        System.out.println("COnnection is ready");
        //get conneciton object
        final SQLConnection con = connection.result();
        String CREATE_TABLE = "create table test(id int primary key, name varchar(255))";
        con.execute(CREATE_TABLE, tableCreate -> {
          if (tableCreate.failed()) {
            System.out.println(tableCreate.cause());
          } else {
            System.out.println("Tables are created");

            String INSERT_QUERY = "insert into test values(1, 'Hello')";
            con.execute(INSERT_QUERY, tableInsert -> {

              if (tableInsert.failed()) {
                System.out.println(tableInsert.cause());
              } else {
                //show sample data; write select query
                String SELECT_QUERY = "select * from test";
                con.query(SELECT_QUERY, selectResult -> {
                  for (JsonArray line : selectResult.result().getResults()) {
                    System.out.println(line.encode());
                  }
                  // and close the connection
                  con.close(done -> {
                    if (done.failed()) {
                      throw new RuntimeException(done.cause());
                    }
                  });
                });
              }
            });
          }

        });

      } else {
        System.out.println(connection.cause());
      }
    });

  }
}
