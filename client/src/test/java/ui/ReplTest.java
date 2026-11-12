package ui;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class ReplTest {
    private Repl repl;

    @BeforeEach
    void setUp() throws Exception{
        repl = new Repl(8080);
    }

    @Test
    void processBlankCommandSuccess() throws Exception{
        assertDoesNotThrow(() -> {
            var method = Repl.class.getDeclaredMethod("processCommand", String.class);
            method.setAccessible(true);
            method.invoke(repl, "   ");
            method.invoke(repl, (String) null);
        });
    }

    @Test
    void testPreLoginHelpSuccess(){
        assertDoesNotThrow(() -> {
            var method = Repl.class.getDeclaredMethod("processCommand", String.class);
            method.setAccessible(true);
            method.invoke(repl, "help");
        });
    }

    @Test
    void registerMissingArgsNegative(){
        assertDoesNotThrow(() -> {
            var method = Repl.class.getDeclaredMethod("handleRegister", String[].class);
            method.setAccessible(true);
            String[] tokens = {"register", "that's it?"};
            method.invoke(repl, (Object) tokens);
        });
    }

    @Test
    void loginMissingArgsNegative(){
        assertDoesNotThrow(() -> {
            var method = Repl.class.getDeclaredMethod("handleLogin", String[].class);
            method.setAccessible(true);
            String[] tokens = {"login", "wherePassword?"};
            method.invoke(repl, (Object) tokens);
        });
    }

    @Test
    void createGameMissingArgsNegative(){
        assertDoesNotThrow(() -> {
            var method = Repl.class.getDeclaredMethod("handleCreateGame", String[].class);
            method.setAccessible(true);
            String[] tokens = {"create"};
            method.invoke(repl, (Object) tokens);
        });
    }

    @Test
    void joinGameMissingArgsNegative(){
        assertDoesNotThrow(() -> {
            var method = Repl.class.getDeclaredMethod("handleJoinGame", String[].class);
            method.setAccessible(true);
            String[] tokens = {"join", "1"};
            String[] moreTokens = {"join"};
            method.invoke(repl, (Object) tokens);
            method.invoke(repl, (Object) moreTokens);
        });
    }

    @Test
    void fullClientLifecyclePositive() throws Exception{
        assertDoesNotThrow(() -> {
            var method = Repl.class.getDeclaredMethod("processCommand", String.class);
            method.setAccessible(true);
            String uniqueUser = "User_" + System.currentTimeMillis();
            method.invoke(repl, "register " +
                    uniqueUser +
                    " password123 test@email.com");
            method.invoke(repl, "create NewMatch");
            method.invoke(repl, "list");
            method.invoke(repl, "join 1 WHITE");
            //may need to debug this to ensure right ID is found and sent.
            method.invoke(repl, "logout");
        });
    }


}
