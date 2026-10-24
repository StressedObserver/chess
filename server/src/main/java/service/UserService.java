package service;

import dataaccess.DataAccess;
import dataaccess.DataAccessException;
import dataaccess.UnauthorizedException;
import requests.RegisterRequest;
import requests.*;
import results.*;
import model.*;

import java.util.UUID;

public class UserService {

    private final DataAccess dao;

    public UserService(DataAccess dao){
        this.dao = dao;
    }

    public RegisterResult register(RegisterRequest request) throws DataAccessException{
        if(request.username() == null || request.username().isBlank() ||
           request.password() == null || request.password().isBlank() ||
           request.email() == null || request.email().isBlank()){
            throw new IllegalArgumentException("Error: bad request");
        }

        UserData existing = dao.getUser(request.username());
        if(existing != null){
            throw new IllegalStateException("Error: already taken");
        }

        UserData newUser = new UserData(request.username(), request.password(), request.email());
        dao.createUser(newUser);
        String token = UUID.randomUUID().toString();
        AuthData authData = new AuthData(token, request.username());
        dao.createAuth(authData);

        return new RegisterResult(request.username(), token);
    }

    public LoginResult login(LoginRequest request) throws UnauthorizedException, DataAccessException{
        if(request.username() == null || request.username().isBlank() ||
           request.password() == null || request.password().isBlank()){
            throw new IllegalArgumentException("Error: bad request");
        }
        UserData userData = dao.getUser(request.username());
        if(userData == null || !(request.password().equals(userData.password()))){
            throw new UnauthorizedException("Error: unauthorized");
        }

        String token = UUID.randomUUID().toString();
        AuthData authData = new AuthData(token, request.username());
        dao.createAuth(authData);

        return new LoginResult(request.username(), token);
    }

    public LogoutResult logout(LogoutRequest request) throws UnauthorizedException, DataAccessException{
        if(request.authToken() == null || request.authToken().isBlank()){
            throw new IllegalArgumentException("Error: unauthorized");
        }

        var authData = dao.getAuth(request.authToken());
        if(authData == null){
            //throw new IllegalStateException("Error: unauthorized");
            throw new UnauthorizedException("Error: unauthorized");
        }

        dao.deleteAuth(request.authToken());
        return new LogoutResult();
    }
}
