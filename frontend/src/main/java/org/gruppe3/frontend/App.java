package org.gruppe3.frontend;

import io.javalin.Javalin;

public class App {

  public static final Javalin createFrontend(int port) {
    // https://javalin.io/plugins/javalinvue#configuration
    Javalin app =
        Javalin.create(
                config -> {
                  config.staticFiles.enableWebjars();
                  config.vue.vueInstanceNameInJs = "app";
                })
            .start(port);
    return app;
  }
}
