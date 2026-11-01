package dataaccess;


import java.util.ArrayList;
import java.util.Collection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.AuthData;
import model.GameData;
import model.UserData;
import chess.ChessGame;

public class SqlDataAccess implements DataAccess {

    private final Gson customGson = createGson();

    private Gson createGson() {
        return new Gson();
    }

    @Override
    public void clear() throws DataAccessException {
        String[] statements = {
                "TRUNCATE TABLE user",
                "TRUNCATE TABLE atuh",
                "TRUNCATE TABLE game",
        };
        try(var connection = DatabaseManager.getConnection()){
            for (String statement : statements) {
                try (var pstmt = connection.prepareStatement(statement)) {
                    pstmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Unable to clear database: " +
                    e.getMessage());
        }
    }

    @Override
    public void createUser(UserData user) throws DataAccessException {

    }

    @Override
    public UserData getUser(String username) throws DataAccessException {
        return null;
    }

    @Override
    public void createGame(GameData game) throws DataAccessException {

    }

    @Override
    public GameData getGame(int gameID) throws DataAccessException {
        return null;
    }

    @Override
    public Collection<GameData> listGames() throws DataAccessException {
        return List.of();
    }

    @Override
    public void updateGame(GameData game) throws DataAccessException {

    }

    @Override
    public void createAuth(AuthData auth) throws DataAccessException {

    }

    @Override
    public AuthData getAuth(String token) throws DataAccessException {
        return null;
    }

    @Override
    public void deleteAuth(String token) throws DataAccessException {

    }
}
