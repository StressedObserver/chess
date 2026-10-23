package handler;

import com.google.gson.Gson;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;
import service.*;
import requests.*;
import results.*;
import dataaccess.*;

public class GameHandler {
    private final GameService gameService;
    private final Gson gson = new Gson();

    public GameHandler(GameService gameService){
        this.gameService = gameService;
    }

    public void listGames(@NotNull Context context){
        try{
            String token = context.header("authorization");
            ListGamesRequest request = new ListGamesRequest(token);
            ListGamesResult result = gameService.listGames(request);
            context.status(200).json(result);
        } catch(IllegalArgumentException | IllegalStateException e){
            context.status(401).json(new ErrorResult(e.getMessage()));
        } catch(DataAccessException e){
            context.status(500).json(new ErrorResult("Error: " + e.getMessage()));
        }
    }

    public void createGame(@NotNull Context context){
        try{
            String token = context.header("authorization");
            CreateGameRequest body = gson.fromJson(context.body(), CreateGameRequest.class);
            CreateGameRequest request = new CreateGameRequest(token, body.gameName());
            CreateGameResult result = gameService.createGame(request);
            context.status(200).json(result);
        } catch(IllegalArgumentException e){
            String message = e.getMessage();
            if(message.equals("Error: unauthorized")){
                context.status(401).json(new ErrorResult(message));
            } else{
                context.status(400).json(new ErrorResult(e.getMessage()));
            }
        } catch(IllegalStateException e){
            context.status(401).json(new ErrorResult(e.getMessage()));
        } catch(DataAccessException e){
            context.status(500).json(new ErrorResult("Error: " + e.getMessage()));
        }
    }

    public void joinGame(@NotNull Context context){
        try{
            String token = context.header("authorization");
            JoinGameRequest body = gson.fromJson(context.body(), JoinGameRequest.class);
            JoinGameRequest request = new JoinGameRequest(token, body.playerColor(), body.gameID());
            JoinGameResult result = gameService.joinGame(request);
            context.status(200).json(result);
        } catch(IllegalArgumentException e){
            String message = e.getMessage();
            if(message.equals("Error: unauthorized")){
                context.status(401).json(new ErrorResult(message));
            }else{
                context.status(400).json(new ErrorResult(message));
            }
        } catch(IllegalStateException e){
            context.status(403).json(new ErrorResult(e.getMessage()));
        } catch(DataAccessException e){
            context.status(500).json(new ErrorResult("Error: " + e.getMessage()));
        }
    }
}
