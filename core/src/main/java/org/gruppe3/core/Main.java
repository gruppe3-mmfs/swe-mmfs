package org.gruppe3.core;

import io.javalin.Javalin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

  private static final Logger logger = LoggerFactory.getLogger(Main.class);

  public static void main(String[] args) {

    Javalin app =
        Javalin.create(
                config -> {
                  config.bundledPlugins.enableCors(
                      cors -> {
                        cors.addRule(
                            it -> { // For å tillate requests fra frontend server (Vite)
                              it.allowHost("http://localhost:5173");
                            });
                      });
                })
            .start(7000);

    app.get("/api/message", ctx -> ctx.json(new APIResponse("ITF20319-1 25H - gruppe 3")));

    // Log: Info om endepunkt
    logger.info("API Endpoint created -> /api/message");

    // https://javalin.io/documentation#server-setup
    // Kode for å stenge ned server på en skånsom og harmonisk måte
    //
    //   Runtime.getRuntime()
    //       .addShutdownHook(
    //           new Thread(
    //               () -> {
    //                 app.stop();
    //               }));
    //
    //   app.events(
    //       event -> {
    //         event.serverStopping(
    //             () -> {
    //           logger.info("Server stopping...");
    //             });
    //         event.serverStopped(
    //             () -> {
    // 			logger.info("Server stopped!");
    //             });
    //       });
    // }

    // Pseudokode for MVP:
    //
    // 1. Lage en billett-stub; hva er en billett? -> vi kan f.eks. tenke oss at denne ramler inn
    // som en SHA-256 hash: ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad
    //
    // 2. Lage en bruker-stub; hva er en bruker? -> Registrere bruker med informasjon (navn, mail,
    // telefon f.eks.) -> DTO (storage) -> db
    //
    // 3. Tilordne billett eierskap -> billett knyttes mot registrert bruker (billettId,
    // billettHash, billettOwner)
    //
    // 4. Lage en bruker-stub #2 -> en bruker som billett kan deles med/til
    //
    // 5. Dele billett -> endre eierskap i database -> Service(?) -> DTO (storage) -> endre felt for
    // billettOwner i database

  }

  // Typ controller(?): må se nærmere på hvordan dette skal gjøres presist
  public record APIResponse(String message) {}
}
