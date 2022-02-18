package com.knoldus.vertex.eventbus;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReceiverVerticle extends AbstractVerticle {
  
  Logger logger = LoggerFactory.getLogger(ReceiverVerticle.class);
  @Override
  public void start() throws Exception {
    vertx.eventBus().consumer("incoming.message.event", this::onMessage);
  }
//  onMessage() will be called when a message is received.
  private <T> void onMessage(Message<T> tMessage) {
      JsonObject message = (JsonObject) tMessage.body();
      logger.info("Message Received " + message);
      tMessage.reply(message);
  }
}
