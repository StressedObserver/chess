package client;

import org.junit.jupiter.api.*;
import server.Server;
import dataaccess.*;

import model.AuthData;
import chess.ChessGame;
import client.ServerFacade;

import static org.junit.jupiter.api.Assertions.*;


public class ServerFacadeTests {

    private static Server myServer;
    private static ServerFacade facade;
    @BeforeAll
    public static void init() {
        myServer = new Server();
        var port = myServer.run(0);
        System.out.println("Started test HTTP server on " + port);
        facade = new ServerFacade(port);
    }

    @AfterAll
    static void stopServer() {
        myServer.stop();
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

    //For now I am only implementing tests for clear that don't rely on other methods to test the functionality of
    // the clear function alone. Once I ensure that I have implmented other functions successfully with respective
    // tests, I will either add more tests that call other functions to test clear or just rewrite the tests I already
    // have.

    @Test
    public void clearNothingSuccessTest() throws Exception {
        assertDoesNotThrow(facade::clear);
    }

    @Test
    public void clearMultipleTimesTest() throws Exception{
        assertDoesNotThrow(() -> {
            facade.clear();
            facade.clear();
            facade.clear();
            facade.clear();
        });
        //I sure hope that four clears is enough to test that multiple repeated clears will still work.
    }

    @Test
    public void clearBadServer() throws Exception{
        facade = new ServerFacade(1234567890); //This is an EVIL facade.
        //Shouldn't be valid because ports should only be 4 digits total.

        assertThrows(Exception.class, facade::clear);
    }

}
