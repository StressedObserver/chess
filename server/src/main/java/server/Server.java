package server;

import dataaccess.DataAccess;
import dataaccess.MemoryDataAccess;
import handler.UserHandler;
import io.javalin.*;
import service.ClearService;
import service.GameService;
import service.UserService;

public class Server {

    private final Javalin javalin;

    public Server() {
        javalin = Javalin.create(config -> config.staticFiles.add("web"));

        // Register your endpoints and exception handlers here.

    }

    public int run(int desiredPort) {

        try{
            final DataAccess dataAccess = new MemoryDataAccess();

            UserService userService = new UserService(dataAccess);
            GameService gameService = new GameService(dataAccess);
            ClearService clearService = new ClearService(dataAccess);

            UserHandler userHandler = new UserHandler(userService);
        } catch (Exception e){
            System.out.printf("Unable to start server: %s%n", e.getMessage());
            return -1;
        }

        javalin.start(desiredPort);
        return javalin.port();
    }

    public void stop() {
        javalin.stop();
    }
}
