package service;

import dataaccess.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import requests.*;

public class ClearServiceTest {
    @Test
    public void clearPositive() throws Exception {
        DataAccess dao = new MemoryDataAccess();
        ClearService clear = new ClearService(dao);
        UserService userService = new UserService(dao);

        userService.register(new RegisterRequest("liz", "pw", "email"));

        clear.clear(new ClearRequest());

        Assertions.assertNull(dao.getUser("liz"));
    }

    //Testing clearing on already empty database because I'm paranoid:
    @Test
    public void clearEmptyDatabase() throws Exception {
        DataAccess dao = new MemoryDataAccess();
        ClearService clear = new ClearService(dao);

        clear.clear(new ClearRequest());

        Assertions.assertTrue(true); // No exception means success
    }
}
