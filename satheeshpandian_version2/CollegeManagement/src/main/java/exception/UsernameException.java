package exception;
import java.util.regex.*;

// Custom exception for invalid usernames
public class UsernameException extends Exception {
    public UsernameException(String message) {
        super(message);
    }
}

// Class to verify username data
class VerifyUsernameData {
    // Regex pattern for a valid username (modify as needed)
    String USERNAME_PATTERN = "^[A-Za-z0-9_]{3,15}$";

    // Method to set the username after validating it
    public void setUsername(String username) throws UsernameException {
        Pattern pattern = Pattern.compile(USERNAME_PATTERN);
        Matcher matcher = pattern.matcher(username);
        if (!matcher.matches()) {
            throw new UsernameException("Invalid username. Enter a valid username...");
        }
    }
}

