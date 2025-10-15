package org.gruppe3.core.domain;

public class Server {

  public Server() {
    // Use default port (:8080) if no params
    this.port = 8080;
  }

  public Server(int port) {
    this.port = port;
  }

  private int port;

  public int getPort() {
    return port;
  }

  public void setPort(int port) {
    this.port = port;
  }
}
