package dataaccess;

import static org.junit.jupiter.api.Assertions.*;
import chess.ChessGame;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
}
