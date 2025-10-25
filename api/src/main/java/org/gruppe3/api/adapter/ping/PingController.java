package org.gruppe3.api.adapter.ping;

import io.javalin.http.Handler;

public class PingController {
  public static Handler pingHandler = ctx -> ctx.result("pong");
}
