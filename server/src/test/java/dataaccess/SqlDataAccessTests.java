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
}
