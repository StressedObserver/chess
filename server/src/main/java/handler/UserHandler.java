package handler;

import dataaccess.*;
import io.javalin.http.Context;
import com.google.gson.Gson;
import requests.*;
import results.*;
import service.UserService;
import org.jetbrains.annotations.NotNull;

public class UserHandler {
    private final UserService userService;
    private final Gson gson = new Gson();

    public UserHandler(UserService userService){
        this.userService = userService;
    }

    public void register(@NotNull Context context){
        try{
            RegisterRequest registerRequest = gson.fromJson(context.body(), RegisterRequest.class);
            RegisterResult registerResult = userService.register(registerRequest);
            context.status(200).json(registerResult);
        } catch(IllegalArgumentException e){
            context.status(400).json(new ErrorResult(e.getMessage()));
        } catch(IllegalStateException e){
            context.status(403).json(new ErrorResult(e.getMessage()));
        } catch(DataAccessException e){
            context.status(500).json(new ErrorResult("Error: " + e.getMessage()));
        }
    }

    public void login(@NotNull Context context){
        try {
            LoginRequest request = gson.fromJson(context.body(), LoginRequest.class);
            LoginResult result = userService.login(request);
            context.status(200).json(result);
            context.json(result);
        } catch(UnauthorizedException e){
            context.status(401).json(new ErrorResult((e.getMessage())));
        } catch (IllegalArgumentException e){
            context.status(400).json(new ErrorResult(e.getMessage()));
        } catch (DataAccessException e){
            context.status(500).json(new ErrorResult("Error: " + e.getMessage()));
        }
    }

    public void logout(@NotNull Context context){
        try{
            String token = context.header("authorization");
            LogoutRequest request = new LogoutRequest(token);
            LogoutResult result = userService.logout(request);
            context.status(200).json(result);
        } catch (IllegalArgumentException | UnauthorizedException e) {
            context.status(401).json(new ErrorResult(e.getMessage()));
        } catch (DataAccessException e){
            context.status(500).json(new ErrorResult("Error: " + e.getMessage()));
        }
    }
}
