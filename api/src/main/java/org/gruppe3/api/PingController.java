package org.gruppe3.api;

import io.javalin.http.Handler;

public class PingController {
  public static Handler pingHandler = ctx -> ctx.result("Pong fra backend");
}
