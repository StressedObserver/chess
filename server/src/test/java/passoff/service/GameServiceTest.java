package passoff.service;

import dataaccess.*;
import model.GameData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import requests.*;
import results.*;
import service.*;

public class GameServiceTest {
    //The tests for creating games:
    @Test
    public void createGamePositive() throws Exception {
        DataAccess dao = new MemoryDataAccess();
        GameService service = new GameService(dao);

        // First register a user to get an auth token
        UserService userService = new UserService(dao);
        var reg = userService.register(new RegisterRequest("liz", "pw", "email"));

        CreateGameRequest request = new CreateGameRequest(reg.authToken(), "MyGame");
        CreateGameResult result = service.createGame(request);
        //I need some way of checking what result.gameID() currently is to debug this.
        Assertions.assertTrue(result.gameID() > 0);
    }

    @Test
    public void createGameNegativeBadAuth() throws Exception {
        DataAccess dao = new MemoryDataAccess();
        GameService service = new GameService(dao);

        var ex = Assertions.assertThrows(IllegalArgumentException.class, () ->
                service.createGame(new CreateGameRequest("bad-token", "Game"))
        );

        Assertions.assertEquals("Error: unauthorized", ex.getMessage());
    }

    //Extra test for createGame(): Trying to create a game with a blank name.
    @Test
    public void createGameNegativeBlankName() throws Exception {
        DataAccess dao = new MemoryDataAccess();
        UserService userService = new UserService(dao);
        GameService gameService = new GameService(dao);

        var reg = userService.register(new RegisterRequest("liz", "pw", "email"));

        var ex = Assertions.assertThrows(IllegalArgumentException.class, () ->
                gameService.createGame(new CreateGameRequest(reg.authToken(), ""))
        );

        Assertions.assertEquals("Error: bad request", ex.getMessage());
    }


    //The tests for listGames():
    @Test
    public void listGamesPositive() throws Exception {
        DataAccess dao = new MemoryDataAccess();
        UserService userService = new UserService(dao);
        GameService gameService = new GameService(dao);

        var reg = userService.register(new RegisterRequest("liz", "pw", "email"));
        gameService.createGame(new CreateGameRequest(reg.authToken(), "MyGame"));

        var result = gameService.listGames(new ListGamesRequest(reg.authToken()));

        Assertions.assertEquals(1, result.games().size());
    }

    @Test
    public void listGamesNegativeBadAuth() throws Exception {
        DataAccess dao = new MemoryDataAccess();
        GameService gameService = new GameService(dao);

        var ex = Assertions.assertThrows(IllegalArgumentException.class, () ->
                gameService.listGames(new ListGamesRequest(""))
        );

        Assertions.assertEquals("Error: unauthorized", ex.getMessage());
    }

    //Extra listGames() test:

    //The join game tests:
    @Test
    public void joinGamePositive() throws Exception {
        DataAccess dao = new MemoryDataAccess();
        UserService userService = new UserService(dao);
        GameService gameService = new GameService(dao);

        var reg = userService.register(new RegisterRequest("liz", "pw", "email"));
        var game = gameService.createGame(new CreateGameRequest(reg.authToken(), "Chess"));

        var result = gameService.joinGame(new JoinGameRequest(reg.authToken(), "WHITE", game.gameID()));

        Assertions.assertNotNull(result);
        GameData updated = dao.getGame(game.gameID());
        Assertions.assertEquals("liz", updated.whiteUsername());
    }

    @Test
    public void joinGameNegativeColorTaken() throws Exception {
        DataAccess dao = new MemoryDataAccess();
        UserService userService = new UserService(dao);
        GameService gameService = new GameService(dao);

        var reg1 = userService.register(new RegisterRequest("liz", "pw", "email"));
        var reg2 = userService.register(new RegisterRequest("bob", "pw", "email2"));

        var game = gameService.createGame(new CreateGameRequest(reg1.authToken(), "Chess"));

        gameService.joinGame(new JoinGameRequest(reg1.authToken(), "WHITE", game.gameID()));

        var ex = Assertions.assertThrows(IllegalStateException.class, () ->
                gameService.joinGame(new JoinGameRequest(reg2.authToken(), "WHITE", game.gameID()))
        );

        Assertions.assertEquals("Error: already taken", ex.getMessage());
    }

    //Extra test: Joining a game with the color being lowercase
    @Test
    public void joinGamePositiveLowercaseColor() throws Exception {
        DataAccess dao = new MemoryDataAccess();
        UserService userService = new UserService(dao);
        GameService gameService = new GameService(dao);

        var reg = userService.register(new RegisterRequest("liz", "pw", "email"));
        var game = gameService.createGame(new CreateGameRequest(reg.authToken(), "Chess"));

        gameService.joinGame(new JoinGameRequest(reg.authToken(), "white", game.gameID()));

        Assertions.assertEquals("liz", dao.getGame(game.gameID()).whiteUsername());
    }

    //Extra test: Joining a game with an invalid color
    @Test
    public void joinGameNegativeInvalidColor() throws Exception {
        DataAccess dao = new MemoryDataAccess();
        UserService userService = new UserService(dao);
        GameService gameService = new GameService(dao);

        var reg = userService.register(new RegisterRequest("liz", "pw", "email"));
        var game = gameService.createGame(new CreateGameRequest(reg.authToken(), "Chess"));

        var ex = Assertions.assertThrows(IllegalArgumentException.class, () ->
                gameService.joinGame(new JoinGameRequest(reg.authToken(), "BLUE", game.gameID()))
        );

        Assertions.assertEquals("Error: bad request", ex.getMessage());
    }

    //Extra test: Joining a game with the wrong/an invalid gameID.
    @Test
    public void joinGameNegativeInvalidGameID() throws Exception {
        DataAccess dao = new MemoryDataAccess();
        UserService userService = new UserService(dao);
        GameService gameService = new GameService(dao);

        var reg = userService.register(new RegisterRequest("liz", "pw", "email"));

        var ex = Assertions.assertThrows(IllegalArgumentException.class, () ->
                gameService.joinGame(new JoinGameRequest(reg.authToken(), "WHITE", -1))
        );

        Assertions.assertEquals("Error: bad request", ex.getMessage());
    }

}
