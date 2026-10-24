package service;

import dataaccess.*;
import service.*;
import requests.*;
import results.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserServiceTest {

    // The register tests:
    @Test
    public void registerPositive() throws Exception {
        DataAccess dao = new MemoryDataAccess();
        UserService service = new UserService(dao);

        RegisterRequest req = new RegisterRequest("liz", "pw", "email");
        RegisterResult result = service.register(req);

        Assertions.assertEquals("liz", result.username());
        Assertions.assertNotNull(result.authToken());
    }

    @Test
    public void registerNegativeAlreadyTaken() throws Exception {
        DataAccess dao = new MemoryDataAccess();
        UserService service = new UserService(dao);

        service.register(new RegisterRequest("liz", "pw", "email"));

        var ex = Assertions.assertThrows(IllegalStateException.class, () ->
                service.register(new RegisterRequest("liz", "pw2", "email2"))
        );

        Assertions.assertEquals("Error: already taken", ex.getMessage());
    }
    // Extra register test: registering with a blank username.
    @Test
    public void registerNegativeBlankUsername() {
        DataAccess dao = new MemoryDataAccess();
        UserService service = new UserService(dao);

        var ex = Assertions.assertThrows(IllegalArgumentException.class, () ->
                service.register(new RegisterRequest("", "pw", "email"))
        );

        Assertions.assertEquals("Error: bad request", ex.getMessage());
    }


    // The login tests:
    @Test
    public void loginPositive() throws Exception {
        DataAccess dao = new MemoryDataAccess();
        UserService service = new UserService(dao);

        service.register(new RegisterRequest("liz", "pw", "email"));
        var result = service.login(new LoginRequest("liz", "pw"));

        Assertions.assertEquals("liz", result.username());
        Assertions.assertNotNull(result.authToken());
    }

    @Test
    public void loginNegativeBadPassword() throws Exception {
        DataAccess dao = new MemoryDataAccess();
        UserService service = new UserService(dao);

        service.register(new RegisterRequest("liz", "pw", "email"));

        var ex = Assertions.assertThrows(UnauthorizedException.class, () ->
                service.login(new LoginRequest("liz", "wrong"))
        );

        Assertions.assertEquals("Error: unauthorized", ex.getMessage());
    }

    //Extra login test: Trying to log in with a blank username.
    @Test
    public void loginNegativeBlankPassword() throws DataAccessException {
        DataAccess dao = new MemoryDataAccess();
        UserService service = new UserService(dao);

        service.register(new RegisterRequest("liz", "pw", "email"));

        var ex = Assertions.assertThrows(IllegalArgumentException.class, () ->
                service.login(new LoginRequest("liz", ""))
        );

        Assertions.assertEquals("Error: bad request", ex.getMessage());
    }


    // The logout tests:
    @Test
    public void logoutPositive() throws Exception {
        DataAccess dao = new MemoryDataAccess();
        UserService service = new UserService(dao);

        var reg = service.register(new RegisterRequest("liz", "pw", "email"));
        var result = service.logout(new LogoutRequest(reg.authToken()));

        Assertions.assertNotNull(result);
        Assertions.assertNull(dao.getAuth(reg.authToken()));
    }

    @Test
    public void logoutNegativeBadToken() {
        DataAccess dao = new MemoryDataAccess();
        UserService service = new UserService(dao);

        var ex = Assertions.assertThrows(UnauthorizedException.class, () ->
                service.logout(new LogoutRequest("bad-token"))
        );

        Assertions.assertEquals("Error: unauthorized", ex.getMessage());
    }

    //Extra logout test: Trying to log out twice (I probably would try that unironically).
    @Test
    public void logoutNegativeDoubleLogout() throws Exception {
        DataAccess dao = new MemoryDataAccess();
        UserService service = new UserService(dao);

        var reg = service.register(new RegisterRequest("liz", "pw", "email"));
        service.logout(new LogoutRequest(reg.authToken()));

        var ex = Assertions.assertThrows(UnauthorizedException.class, () ->
                service.logout(new LogoutRequest(reg.authToken()))
        );

        Assertions.assertEquals("Error: unauthorized", ex.getMessage());
    }
}
