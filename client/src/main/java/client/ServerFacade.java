package client;

import com.google.gson.Gson;
import model.AuthData;
import model.GameData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import java.net.*;
public class ServerFacade {
    private final String serverUrl;

    public ServerFacade(int port){
        serverUrl = "http://localhost:" + port;
    }


    public AuthData register(String testUser, String password, String mail) throws Exception {
        /*
         * Send POST request to /user with username, password, email
         * Return parsed AuthData object (contains username and authToken)
         */
        URL myUrl = new URI(serverUrl + "/user").toURL();
        HttpURLConnection connection = (HttpURLConnection) myUrl.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true); //this shows that we intend to use this connection for output.
        connection.setRequestProperty("Content-Type", "application/json");

        String requestBody = String.format(
                "{\"username\":\"%s\",\"password\":\"%s\",\"email\":\"%s\"}",
                testUser, password, mail
        );

        try(var outputSteam = connection.getOutputStream()){
            byte[] input = requestBody.getBytes(StandardCharsets.UTF_8);
            outputSteam.write(input, 0, input.length);
        }

        int responseCode = connection.getResponseCode();

        if(responseCode / 100 != 2){ //We didn't get a 200 response code
            throw new Exception("Registration failed with status code: " + responseCode);
        }

        try(var bufferedReader = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))){
            StringBuilder response = new StringBuilder();
            String responseLine;
            while((responseLine = bufferedReader.readLine()) != null){
                response.append(responseLine.trim());
            }
            return new Gson().fromJson(response.toString(), AuthData.class);
        }
    }

    public void clear() throws Exception{
        //Should send a delete request to the database.
        URL myUrl = new URI(serverUrl + "/db").toURL();
        HttpURLConnection connection = (HttpURLConnection) myUrl.openConnection();
        connection.setRequestMethod("DELETE");
        connection.connect();

        int responseCode = connection.getResponseCode();
        if(responseCode / 100 != 2){ //We didn't get a 200 response code back.
            throw new Exception("Status failed with status code: " + responseCode);
        }
    }

    public AuthData login(String username, String password) throws Exception{
        URL myUrl = new URI(serverUrl + "/session").toURL();
        HttpURLConnection connection = (HttpURLConnection) myUrl.openConnection();
        connection.setRequestMethod("POST");
        connection.setDoOutput(true); //this shows that we intend to use this connection for output.
        connection.setRequestProperty("Content-Type", "application/json");

        String requestBody = String.format(
                "{\"username\":\"%s\",\"password\":\"%s\"}",
                username, password
        );

        try(var outputSteam = connection.getOutputStream()){
            byte[] input = requestBody.getBytes(StandardCharsets.UTF_8);
            outputSteam.write(input, 0, input.length);
        }

        int responseCode = connection.getResponseCode();

        if(responseCode / 100 != 2){ //We didn't get a 200 response code
            throw new Exception("Registration failed with status code: " + responseCode);
        }

        try(var bufferedReader = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))){
            StringBuilder response = new StringBuilder();
            String responseLine;
            while((responseLine = bufferedReader.readLine()) != null){
                response.append(responseLine.trim());
            }
            return new Gson().fromJson(response.toString(), AuthData.class);
        }
        /* TODO:
        //Send POST request to /session with username and password
        //Return parsed AuthData object.
        */
    }

    public void logout(String authToken) throws Exception{
        URL myUrl = new URI(serverUrl + "/session").toURL();
        HttpURLConnection connection = (HttpURLConnection) myUrl.openConnection();
        connection.setRequestMethod("DELETE");
        connection.setRequestProperty("authorization", authToken);
        connection.connect();

        int responseCode = connection.getResponseCode();

        if(responseCode / 100 != 2){ //We didn't get a 200 response code
            throw new Exception("Registration failed with status code: " + responseCode);
        }

        //TODO: Send DELETE request to /session with authToken in headers
    }

    public GameData createGame(String gameName, String authToken) throws Exception{
        /*TODO:
        //Send POST request to /game with gameName and authToken in headers
        //Return parsed GameData object (contains gameID, gameName, etc.)
        */
        return null;
    }

    public Collection<GameData> listGames(String authToken) throws Exception {
        /*TODO:
        //Send GET request to /game with authToken in headers
        //Return parsed array or collection of GameData objects
        */
        return List.of();
    }

    public void joinGame(String playerColor, int gameID, String authToken) throws Exception{
        // TODO: Send PUT request to /game with playerColor ("WHITE", "BLACK", or null/OBSERVER), gameID, and authToken
    }
}
