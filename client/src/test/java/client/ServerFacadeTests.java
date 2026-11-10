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

    @BeforeEach
    void clearDatabase() throws Exception {
        facade.clear();
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
    public void sampleRegister() throws Exception{
        ServerFacade facade = new ServerFacade(8080);
        AuthData authData = facade.register("testUser", "password", "user@email.com");
        assertNotNull(authData.authToken());
    }

    //For now I am only implementing tests for clear that don't rely on other methods to test the functionality of
    // the clear function alone. Once I ensure that I have implmented other functions successfully with respective
    // tests, I will either add more tests that call other functions to test clear or just rewrite the tests I already
    // have.

    @Test
    public void clearNothingSuccess() throws Exception {
        assertDoesNotThrow(facade::clear);
    }

    @Test
    public void clearMultipleTimes() throws Exception{
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

    @Test
    public void registerSuccess() throws Exception {
        AuthData authData = facade.register("Theodora",
                "password123",
                "SaramisOfficial@email.com"
        );

        assertNotNull(authData);
        assertEquals("Theodora", authData.username());
        assertNotNull(authData.authToken());
        assertTrue(authData.authToken().length() > 10);
    }

    @Test
    public void registerDuplicateFail() throws Exception{
        assertDoesNotThrow(() -> facade.register("Theodora",
                "password123",
                "SaramisOfficial@email.com")
        );

        assertThrows(Exception.class, () -> {facade.register("Theodora",
                "differentPassword",
                "RockThrow@email.com");
                    }
        );
    }

    @Test
    void loginSuccess() throws Exception{
        facade.register("Dietrich",
                "ShadowFromElsewhere2",
                "LamineFamily@yahoo.net");

        AuthData authData = facade.login("Dietrich",
                "ShadowFromElsewhere2");

        assertNotNull(authData);
        assertEquals("Dietrich", authData.username());
        assertNotNull(authData.authToken());
    }

    @Test
    void loginFailureWrongPassword() throws Exception{
        facade.register("Dietrich",
                "ShadowFromElsewhere2",
                "LamineFamily@yahoo.net");
        assertThrows(Exception.class, () -> {facade.login("Dietrich",
                "ShadowFromElsewhere3"); });
    }

    @Test
    void logoutSuccess() throws Exception{
        facade.register("Dietrich",
                "ShadowFromElsewhere2",
                "LamineFamily@yahoo.net");

        AuthData authData = facade.login("Dietrich",
                "ShadowFromElsewhere2");
        assertNotNull(authData.authToken());

        assertDoesNotThrow(() -> {facade.logout(authData.authToken());});
    }

    @Test
    void logoutFailureInvalidToken() throws Exception{
        //A fake authToken should not be able to log out any users and throw an exception.
        assertThrows(Exception.class, () -> facade.logout("TheFriendsWeMade"));
    }

    @Test
    void registerLoginLogoutSuccess() throws Exception{
        var registerData = facade.register("Leda",
                "SecretRevengeSeeker",
                "RoseofCaelus@gmail.com");
        assertNotNull(registerData.authToken());
        facade.logout(registerData.authToken());

        AuthData loginData = facade.login("Leda",
                "SecretRevengeSeeker");
        assertNotNull(loginData.authToken());
        assertDoesNotThrow(() -> {facade.logout(loginData.authToken());});
        assertEquals("Leda", loginData.username());
    }

    @Test
    void createGameSuccess() throws Exception{
        var authData = facade.register("Leda",
                "SecretRevengeSeeker",
                "RoseofCaelus@gmail.com");
        var gameData = facade.createGame("Beginner's Match", authData.authToken());
        assertNotNull(gameData);
        assertTrue(gameData.gameID() > 0);
    }

    @Test
    void listGamesSuccess() throws Exception{
        var authData = facade.register("Theodora",
                "password123",
                "SaramisOfficial@email.com");
        facade.createGame("A", authData.authToken());
        facade.createGame("B", authData.authToken());

        var games = facade.listGames(authData.authToken());
        assertNotNull(games);
        assertEquals(2, games.size());
    }

    @Test
    void joinGameSuccess() throws Exception{
        var authData = facade.register("Theodora",
                "password123",
                "SaramisOfficial@email.com");
        var gameData = facade.createGame("Queen's Gambit",authData.authToken());
        assertDoesNotThrow(() -> { facade.joinGame(
                "WHITE", gameData.gameID(), authData.authToken());
        });

        var authData2 = facade.register(
                "Bertrand", "TrialOfChariot7", "WhiteManedDemon@sbcglobal.net"
        );
        assertDoesNotThrow(() -> { facade.joinGame(
                "BLACK", gameData.gameID(), authData2.authToken());
        });

    }
}
