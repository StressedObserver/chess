package service;

import chess.ChessGame;
import dataaccess.DataAccess;
import dataaccess.DataAccessException;
import model.*;
import requests.*;
import results.*;
import java.util.Collection;
import java.util.UUID;

public class GameService {
    private final DataAccess dao;

    public GameService(DataAccess dao){
        this.dao = dao;
    }

    public ListGamesResult listGames(ListGamesRequest request) throws DataAccessException{

        if(request.authToken() == null || request.authToken().isBlank()){
            throw new IllegalArgumentException("Error: unauthorized");
        }

        AuthData auth = dao.getAuth(request.authToken());

        if(auth == null){
            throw new IllegalStateException("Error: unauthorized");
        }

        Collection<GameData> games = dao.listGames();

        return new ListGamesResult(games);
    }

    public CreateGameResult createGame(CreateGameRequest request) throws DataAccessException{
        if(request.authToken() == null || request.authToken().isBlank()){
            throw new IllegalArgumentException("Error: unauthorized");
        }
        AuthData authData = dao.getAuth(request.authToken());
        if(authData == null){
            throw new IllegalArgumentException("Error: unauthorized");
        }

        if(request.gameName() == null || request.gameName().isBlank()){
            throw new IllegalArgumentException("Error: bad request");
        }

        GameData gameData = new GameData(0, null, null, request.gameName(), new ChessGame());
        int actualGameID = dao.createGame(gameData);
        return new CreateGameResult(actualGameID);
    }

    public JoinGameResult joinGame(JoinGameRequest request) throws DataAccessException{
        if(request.authToken() == null || request.authToken().isBlank()){
            throw new IllegalArgumentException("Error: unauthorized");
        }

        AuthData authData = dao.getAuth(request.authToken());
        if(authData == null){
            throw new IllegalArgumentException("Error: unauthorized");
        }

        if(request.playerColor() == null || request.playerColor().isBlank()){
            throw new IllegalArgumentException("Error: bad request");
        }
        GameData gameData = dao.getGame(request.gameID());
        if(gameData == null){
            throw new IllegalArgumentException("Error: bad request");
        }

        String username = authData.username();

        switch(request.playerColor().toUpperCase()){
            case "WHITE" -> {
                if(gameData.whiteUsername() != null){
                    throw new IllegalStateException("Error: already taken");
                }
                gameData = new GameData(
                        gameData.gameID(),
                        username,
                        gameData.blackUsername(),
                        gameData.gameName(),
                        gameData.game()
                );
            }
            case "BLACK" -> {
                if(gameData.blackUsername() != null){
                    throw new IllegalStateException("Error: already taken");
                }
                gameData = new GameData(
                        gameData.gameID(),
                        gameData.whiteUsername(),
                        username,
                        gameData.gameName(),
                        gameData.game()
                );
            }
            default -> throw new IllegalArgumentException("Error: bad request");
        }

        dao.updateGame(gameData);
        return new JoinGameResult();
    }
}
