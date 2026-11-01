package dataaccess;

import static org.junit.jupiter.api.Assertions.*;
import chess.ChessGame;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;

public class SqlDataAccessTests {
    private SqlDataAccess myDao;
    @BeforeEach
    public void setUp() throws DataAccessException {
        myDao = new SqlDataAccess();
        myDao.clear();
    }

    @Test //Should only need a positive test for clear()
    public void clearTest() throws DataAccessException {
        //We need stuff in order to later delete it.
        var user = new UserData("Balor", "THEUNDERWORLD", "ofashes@gmail.com");
        myDao.createUser(user);
        //Make sure the user actually exists first (it should)
        assertNotNull(myDao.getUser("Balor"));
        //Now to try and clear it all.
        assertDoesNotThrow(myDao::clear);
        assertNull(myDao.getUser("Balor")); //Need to make sure Balor actually got banished.

        //assertNull(myDao); //Can this actually work to make sure everything is gone?
        //I sure hope so, at least.
    }

    @Test
    public void createUserPositiveTest() throws DataAccessException {
        var user = new UserData("emily", "RatioSimp", "emily@gmail.com");
        myDao.createUser(user);

        var result =  myDao.getUser("emily");
        assertNotNull(result);
        assertEquals("emily", result.username());
        assertNotEquals("RatioSimp", result.password()); //We want it to be encrypted.
        assertEquals("emily@gmail.com",  result.email());
    }

    @Test
    public void createUserNegativeTest() throws DataAccessException {
        var daoDao = new SqlDataAccess();
        daoDao.clear();

        var user = new UserData("Raksha", "myMask", "RakshaSecret@yahoo.com");
        daoDao.createUser(user);

        assertThrows(DataAccessException.class, () -> daoDao.createUser(user));
        //This shouldn't work because we are trying to create a duplicate.
    }

    @Test
    public void getUserPositiveTest() throws DataAccessException {
        var user = new UserData("Cai", "myPassword123", "orniusLover@dagda.com");
        myDao.createUser(user);

        var result = myDao.getUser("Cai");

        assertNotNull(result);
        assertEquals("Cai", result.username());
        assertNotEquals("myPassword123", result.password());
        assertTrue(BCrypt.checkpw("myPassword123", result.password()));
        assertEquals("orniusLover@dagda.com",  result.email());

        //These tests are pretty similar to the createUserPositiveTest assertions.
        //Change these tests maybe?
    }

    @Test
    public void getUserNegativeTest() throws DataAccessException {
        var result = myDao.getUser("Starlight");

        assertNull(result);
    }

    @Test
    public void createGamePositiveTest() throws DataAccessException {
        //the gameID we put in should be overwritten.
        GameData newGame = new GameData(
                0, null, null,
                "Grandmaster Showdown", new ChessGame()
        );

        myDao.createGame(newGame);
        int testID = myDao.getIdForGame("Grandmaster Showdown");

        assertTrue(testID > 0,
                "Database should create a generated ID > 0");

        GameData result = myDao.getGame(testID);
        assertNotNull(result, "Game should be retrievable after creation");
        assertEquals("Grandmaster Showdown", result.gameName());
    }

    //Trying to get a game from a gameID that does not exist.
    @Test
    public void createGameNegativeTest() throws DataAccessException {
        int nonExistentID = 1234567890;

        GameData retrievedGame = myDao.getGame(nonExistentID);
        assertNull(retrievedGame,
                "Got a game with an ID that should not exist");
    }

    //now we test for trying to get invalid data
    @Test
    public void createGameInvalidDataTest(){
        GameData badGame = new GameData(0, null, null,
                null, new ChessGame());

        assertThrows(DataAccessException.class, () -> myDao.createGame(badGame),
                "Failed to throw DataAccessException if provided fields are missing/ null");
    }

    @Test
    public void getGamePositiveTest() throws DataAccessException {
        //First we need to create a game that we can then retrieve.
        GameData game = new GameData(0, null, null,
                "Blitz Battle", new ChessGame()
        );

        myDao.createGame(game);
        int testID = myDao.getIdForGame("Blitz Battle");
        GameData retrieved = myDao.getGame(testID);

        assertNotNull(retrieved, "Game should be retrievable after creation");
        assertEquals("Blitz Battle", retrieved.gameName());
        assertEquals(testID, retrieved.gameID());
        assertNotNull(retrieved.game());
    }

    @Test
    public void getGameNegativeTest() throws DataAccessException {
        GameData retrievedGame = myDao.getGame(123456789);
        assertNull(retrievedGame);
    }

    @Test
    public void listGamesEmptyTest() throws DataAccessException {
        var games = myDao.listGames();
        assertNotNull(games);
        assertEquals(0, games.size());
    }

    @Test
    public void listGamesPositiveTest() throws DataAccessException {
        myDao.createGame(new GameData(0,
                null, null,
                "Game A", new ChessGame()));
        myDao.createGame(new GameData(0,
                null, null,
                "Game B", new ChessGame()));

        var games = myDao.listGames();

        assertEquals(2, games.size());
        assertTrue(games.stream().anyMatch(g -> g.gameName().equals("Game A")));
        assertTrue(games.stream().anyMatch(g -> g.gameName().equals("Game B")));
    }

    @Test
    public void listGamesNegativeTest() throws DataAccessException {
        var games = myDao.listGames();

        assertNotNull(games);
        assertEquals(0, games.size(),
                "if no games exist, listGames should return an empty list.");
    }

    @Test
    public void updateGamePositiveTest() throws DataAccessException {
        myDao.createGame(new GameData(0,
                null, null,
                "Original Name", new ChessGame())
        );
        int id = myDao.getIdForGame("Original Name");

        GameData updated = new GameData(id, "WhitePlayer",
                "BlackPlayer",
                "Updated Name", new ChessGame()
        );

        myDao.updateGame(updated);
        GameData retrieved = myDao.getGame(id);
        //The id shouldn't have changed, or I am in big trouble.

        assertNotNull(retrieved);
        assertEquals("WhitePlayer", retrieved.whiteUsername());
        assertEquals("BlackPlayer", retrieved.blackUsername());
        assertEquals("Updated Name", retrieved.gameName());
    }

    @Test
    void updateGameNegativeTest() throws DataAccessException{
        GameData fakeData = new GameData(1234567890,
                "White", "Black",
                "Ghost Game", new ChessGame()
        );

        assertDoesNotThrow(() -> myDao.updateGame(fakeData));

        //We shouldn't be able to get this fakeData from the dao
        //It should be impossible for this to ever fail, but just in case...
        assertNull(myDao.getGame(1234567890));
    }
}
