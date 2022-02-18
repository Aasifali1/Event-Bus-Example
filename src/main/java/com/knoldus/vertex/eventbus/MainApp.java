package com.knoldus.vertex.eventbus;

import io.vertx.core.Vertx;

public class MainApp {
  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new SenderVerticle());
    vertx.deployVerticle(new ReceiverVerticle());
  }
}
