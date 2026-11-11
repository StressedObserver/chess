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
                case "help": displayPreLoginHelp(); //TODO: Implement this
                case "register": handleRegister(tokens); //TODO: Implement this
                case "login": handleLogin(tokens); //TODO: Implement this
                default: System.out.println("Unknown command. Type 'help'.");
                //Not handling "quit" because the loop should have already handled it.
            }
        } else{
            switch(cmd) {
                case "help": displayPostLoginHelp(); //TODO: Implement this
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
    }

    private void handleRegister(String[] tokens) throws Exception{
        //Implement this
    }

    private void handleLogin(String[] tokens) throws Exception{
        //Implement this
    }

    private void displayPostLoginHelp() throws Exception{
        //Implement this
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
