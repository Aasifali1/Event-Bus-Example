package com.knoldus.vertex.eventbus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

import java.util.UUID;

public class SenderVerticle extends AbstractVerticle {
  @Override
  public void start() throws Exception {
    // Create a Router
    Router router = Router.router(vertx);
//    Mount the handler for incoming request
    router.get("/send/:message").handler(this::sendMessage);
    // ******************** Creating Server *********************
    HttpServer server = vertx.createHttpServer();
    // Handle every request using the router
    server.requestHandler(router)
    // start listening on port 8282
      .listen(8282).onSuccess(msg -> {
        System.out.println("*************** Server started on "
           + server.actualPort() + " *************");
      });
  }
  private void sendMessage(RoutingContext context) {
//    generating random id for message
    String uuid = UUID.randomUUID().toString();
//    create event bus object
    final EventBus eventBus = vertx.eventBus();
    final String message = context.request().getParam("message");
//    creating json object for message
    JsonObject entries = new JsonObject();
    entries.put("id", uuid);
    entries.put("message", message);
    entries.put("time", System.currentTimeMillis());
    eventBus.request("incoming.message.event", entries, reply -> {
      if (reply.succeeded()) {
        context.json(reply.result().body());
      } else {
        System.out.println("No reply");
      }
    });
  }
}
