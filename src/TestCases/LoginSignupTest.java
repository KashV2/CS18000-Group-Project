import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LoginSignUpTest {
    private LoginSignUp loginSignUp;

    @BeforeEach
    void setUp() {
        loginSignUp = new LoginSignUp();
        loginSignUp.clearUserCredentials();  // Clear stored data before each test
    }

    @Test
    void testSignUpPasswordTooShort() {
        // Test case: Password is too short
        String userId = "purdue";
        String shortPassword = "@";
        
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            loginSignUp.signUp(userId, shortPassword);
        });
        
        String expectedMessage = "Password must be at least 6 characters";
        String actualMessage = exception.getMessage();
        
        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void testSuccessfulSignUp() {
        // Test case: Successful sign-up
        String userId = "purdue";
        String password = "purdue123";
        
        assertDoesNotThrow(() -> loginSignUp.signUp(userId, password));
        
        // Verifying if the user was added
        assertTrue(loginSignUp.isUserRegistered(userId));
    }

    @Test
    void testSuccessfulLogin() {
        // Test case: Successful login after sign-up
        String userId = "purdue";
        String password = "purdue123";
        
        // Sign up the user first
        loginSignUp.signUp(userId, password);
        
        // Test login
        assertTrue(loginSignUp.login(userId, password));
    }

    @Test
    void testLoginWithIncorrectPassword() {
        // Test case: Login with incorrect password
        String userId = "purdue";
        String correctPassword = "purdue123";
        String incorrectPassword = "wrongPass";
        
        // Sign up the user first
        loginSignUp.signUp(userId, correctPassword);
        
        // Test login with incorrect password
        assertFalse(loginSignUp.login(userId, incorrectPassword));
    }
    
    @Test
    void testLoginWithUnregisteredUser() {
        // Test case: Login with unregistered user
        String userId = "unknownUser";
        String password = "unknown123";
        
        // Test login with unregistered user
        assertFalse(loginSignUp.login(userId, password));
    }
}
