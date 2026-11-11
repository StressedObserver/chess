package ui;

import com.google.gson.Gson;
import model.AuthData;
import model.GameData;
import client.ServerFacade;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.net.*;
import results.ListGamesResult;
import java.util.Scanner;

public class Repl {
    private final ServerFacade facade;
    private String authToken = null;
    private String currentUser = null;
    private boolean isLoggedIn = false;

    public Repl(int port){
        facade = new ServerFacade(port);
    }

    public void run() throws Exception{
        System.out.println("♕ Welcome to Chess Client ♕");
        System.out.println("Type 'help' to get started");

        Scanner scanner = new Scanner(System.in);
        String line = "";
        while(!line.equalsIgnoreCase("quit")){ //May change to just .equals if this is too broad.
            printPrompt(); //Created an implementation for this. Create unit tests for it later.
            line = scanner.nextLine();
            try{
                processCommand(line); //I have implemented this function, but not all of the functions it calls.
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        //This gets printed out if the client types in "quit"
        System.out.println("Exiting chess client. Goodbye!");
    }

    private void printPrompt(){
        if(!isLoggedIn){
            System.out.println("\n[" + currentUser + "] >>> ");
        } else{
            System.out.println("\n[Logged Out] >>> ");
        }
    }

    private void processCommand(String line) throws Exception{
        //Will have to implement this tomorrow.
        String[] tokens = line.split("\\s+");
        String cmd = tokens[0].toLowerCase();
        if(!isLoggedIn){
            switch(cmd){
                case "help": displayPreLoginHelp();
                case "register": handleRegister(tokens);
                case "login": handleLogin(tokens);
                default: System.out.println("Unknown command. Type 'help'.");
                //Not handling "quit" because the loop should have already handled it.
            }
        } else{
            switch(cmd) {
                case "help": displayPostLoginHelp();
                case "logout": handleLogout(); //TODO: Implement this
                case "create": handleCreateGame(tokens); //TODO: Implement this
                case "list": handleListGames(); //TODO: Implement this
                case "join": handleJoinGame(tokens); //TODO: Implement this
                default: System.out.println("Unknown command. Type 'help'.");
            }
        }
    }

    private void displayPreLoginHelp() throws Exception{
        //Implement this
        System.out.println("Available commands:");
        System.out.println(" register <username> <password> <email> - Create an account");
        System.out.println(" login <username> <password> - Log into your account");
        System.out.println(" quit - Exit the program");
    }

    private void handleRegister(String[] tokens) throws Exception{
        //Implement this
        if(tokens.length < 4){
            System.out.println("Usage: register <username> <password> <email>");
            return;
        }
        AuthData authData = facade.register(tokens[1], tokens[2], tokens[3]);
        authToken = authData.authToken();
        currentUser = authData.username();
        isLoggedIn = true;
        System.out.println("Successfully registered and logged in as " + currentUser);
    }

    private void handleLogin(String[] tokens) throws Exception{
        //Implement this
        if(tokens.length < 3){
            System.out.println("Usage: register <username> <password>");
            return;
        }
        AuthData authData = facade.login(tokens[1], tokens[2]);
        authToken = authData.authToken();
        currentUser = authData.username();
        isLoggedIn = true;
        System.out.println("Successfully logged in as " + currentUser);
    }

    private void displayPostLoginHelp() throws Exception{
        //Implement this
        System.out.println("Available commands:");
        System.out.println(" create <gameName> - Create a new chess game");
        System.out.println(" list - List all of the active games");
        System.out.println(" join <gameID> <WHITE|BLACK> - Join a game");
        System.out.println(" logout - Log out of your account");
    }

    private void handleLogout() throws Exception{
        //Implement this
    }

    private void handleCreateGame(String[] tokens) throws Exception{
        //Implement this
    }

    private void handleListGames() throws Exception{
        //Implement this
    }

    private void handleJoinGame(String[] tokens) throws Exception{
        //Implement this
    }

}
