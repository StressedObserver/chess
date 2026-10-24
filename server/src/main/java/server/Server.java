package server;

import dataaccess.DataAccess;
import dataaccess.MemoryDataAccess;
import handler.ClearHandler;
import handler.GameHandler;
import handler.UserHandler;
import io.javalin.*;
import service.ClearService;
import service.GameService;
import service.UserService;

public class Server {

    private final Javalin javalin;

    public Server() {
        javalin = Javalin.create(config -> {
            config.staticFiles.add("web");
            config.jsonMapper(new io.javalin.json.JavalinGson()); //Using this to avoid Jackson warnings. Hopefully.
        });


        // Register your endpoints and exception handlers here.

    }

    public int run(int desiredPort) {

        try{
            final DataAccess dataAccess = new MemoryDataAccess();

            UserService userService = new UserService(dataAccess);
            GameService gameService = new GameService(dataAccess);
            ClearService clearService = new ClearService(dataAccess);

            UserHandler userHandler = new UserHandler(userService);
            GameHandler gameHandler = new GameHandler((gameService));
            ClearHandler clearHandler = new ClearHandler(clearService);
            //Register the endpoints and exception handlers:
            javalin.post("/user",userHandler::register);
            javalin.post("/session", userHandler::login);
            javalin.delete("/session", userHandler::logout);
            javalin.get("/game", gameHandler::listGames);
            javalin.post("/game", gameHandler::createGame);
            javalin.put("/game", gameHandler::joinGame);
            javalin.delete("/db", clearHandler::clear);

            //Now starting the server:
            javalin.start(desiredPort);
        } catch (Exception e){
            System.out.printf("Unable to start server: %s%n", e.getMessage());
            return -1;
        }
        return javalin.port();
    }

    public void stop() {
        javalin.stop();
    }
}
