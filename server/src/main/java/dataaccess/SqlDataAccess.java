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
import org.mindrot.jbcrypt.BCrypt;

public class SqlDataAccess implements DataAccess {

    private final Gson customGson = createGson();

    private Gson createGson() {
        return new Gson();
    }

    @Override
    public void clear() throws DataAccessException {
        String[] statements = {
                "TRUNCATE TABLE user",
                "TRUNCATE TABLE auth",
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
        String sql = "INSERT INTO user (username, password, email) VALUES (?, ?, ?)";
        String hashedPassword = BCrypt.hashpw(user.password(), BCrypt.gensalt());

        try(var connection = DatabaseManager.getConnection()){
            try(var pstmt = connection.prepareStatement(sql)) {
                pstmt.setString(1, user.username());
                pstmt.setString(2, hashedPassword);
                pstmt.setString(3, user.email());
                pstmt.executeUpdate();
            }
        }catch (SQLException exception){
            throw new DataAccessException(exception.getMessage());
        }
    }

    @Override
    public UserData getUser(String username) throws DataAccessException {
        String sql = "SELECT username, password, email FROM user WHERE username = ?";
        try(var  connection = DatabaseManager.getConnection()){
            try(var pstmt = connection.prepareStatement(sql)) {
                pstmt.setString(1, username);
                try (var result = pstmt.executeQuery()) {
                    if (result.next()) {
                        return new UserData(
                                result.getString("username"),
                                result.getString("password"),
                                result.getString("email")
                        );
                    }
                }
            }
        }catch(SQLException exception){
            throw new DataAccessException("Unable to retrieve user: " + exception.getMessage());
        }
        return null; //This will be returned only if we don't find the user.
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
