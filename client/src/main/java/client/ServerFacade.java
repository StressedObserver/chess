package client;

import model.AuthData;
import model.GameData;

import java.util.Collection;
import java.util.List;

public class ServerFacade {
    private final String serverUrl;

    public ServerFacade(int port){
        serverUrl = "http://localhost:" + port;
    }


    public AuthData register(String testUser, String password, String mail) {
        return new AuthData("not here", "default");
        /* TODO:
        * Send POST request to /user with username, password, email
        * Return parsed AuthData object (contains username and authToken)
        */

    }

    public void clear() throws Exception{
        //TODO: Send delete request to the database.
    }

    public AuthData login(String username, String password, String email) throws Exception{
        /* TODO:
        //Send POST request to /session with username and password
        //Return parsed AuthData object.
        */
        return null;
    }

    public void logout(String authToken) throws Exception{
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
