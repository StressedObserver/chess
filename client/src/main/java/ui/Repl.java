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
                processCommand(line); //TODO: Also implement this
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
    }
}
