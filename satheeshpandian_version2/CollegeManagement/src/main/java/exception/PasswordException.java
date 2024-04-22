package exception;

import java.util.regex.*;

// Custom exception for invalid passwords
public class PasswordException extends Exception {
	public PasswordException(String message) {
		super(message);}
	

// Class to verify password data
	// regex pattern for a valid password (modify as needed)
	// Example: At least one digit, one lower case, one upper case, one special
	// character, no whitespace, 8-20 characters long
	
	// Method to set the password after validating it
	public void setPassword(String password) throws PasswordException {
		String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$";

		Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
		Matcher matcher = pattern.matcher(password);
		if (!matcher.matches()) {
			throw new PasswordException("Invalid password. Enter a correct password...");
		}
	}
 }
