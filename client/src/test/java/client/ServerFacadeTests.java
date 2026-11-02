package client;

import org.junit.jupiter.api.*;
import server.Server;
import dataaccess.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import model.AuthData;
import chess.ChessGame;
import client.ServerFacade;


public class ServerFacadeTests {

    private static Server server;

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }


    @Test
    public void sampleTest() {
        Assertions.assertTrue(true);
    }

    @Test
    public void sampleRegisterTest() throws Exception{
        ServerFacade facade = new ServerFacade(8080);
        AuthData authData = facade.register("testUser", "password", "user@email.com");
        assertNotNull(authData.authToken());
    }

}
