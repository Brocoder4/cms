package Jdbc.CollegeManagement;

import java.io.*;
import java.sql.*;
import java.util.*;

import Database.DBConnection;

public class Staff {
	DBConnection connect = new DBConnection();
	Scanner scan = new Scanner(System.in);

	public void register() {

		try {
			System.out.println("Staff registered with the following details");
			System.out.print("enter the user Id (S001):");
			String userid = scan.next();

			System.out.print("enter the user name:");
			String username = scan.next();

			System.out.print("enter the Email:");
			String Email = scan.next();

			System.out.print("enter the contact no:");
			int contact = scan.nextInt();

			System.out.print("enter the Password:");
			String Password = scan.next();

			connect.InitiateDB();
			String sql = "INSERT INTO UserTable (user_id,user_name,user_email,user_phone,user_psw) VALUES (?, ?, ?, ?, ?)";
			Object[] params = { userid, username, Email, contact, Password };
			int rowsInserted = connect.InsertDB(sql, params);
			if (rowsInserted > 0) {
				System.out.println("A new user was inserted!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Method to check if the user can log in with the provided username and
	// password
	public boolean canUserLogin(String userid, String password) {
		boolean isValidUser = false;
		connect.InitiateDB(); // Ensure the database connection is initiated

		try {
			// Prepare SQL query to select the user
			String sql = "SELECT * FROM UserTable WHERE user_id = ? AND user_psw = ?";
			PreparedStatement statement = connect.getConnection().prepareStatement(sql);

			// Set the parameters for the prepared statement
			statement.setString(1, userid);
			statement.setString(2, password);

			// Execute the query
			ResultSet resultSet = statement.executeQuery();

			// If the result set is not empty, then the user exists
			isValidUser = resultSet.next();

			// Close the resultSet and statement
			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return isValidUser;
	}

	public void staffOption() {
		boolean continueLoop = true; // Control loop continuation

		while (continueLoop) {
			System.out.println("1. Registration");
			System.out.println("2. Login");
			System.out.print("Enter your choice: ");
			int choice1 = scan.nextInt();

			switch (choice1) {
			case 1:

				register();
				break;
			case 2:
				login();
				break;
			case 3:
				continueLoop = false; // Exit the loop
				System.out.println("Exiting...");
				break;
			default:
				System.out.println("Invalid choice. Please enter 1, 2, or 3.");
				break;
			}
		}
	}

	public void login() {
		System.out.println("-------------------------------------------");
		System.out.println("login");
		System.out.println("-------------------------------------------");
		System.out.print("enter the user id:");
		String userid = scan.next();
		System.out.print("enter the Password:");
		String Password = scan.next();
		if (canUserLogin(userid, Password)) {
			System.out.println("Login successful!");
			works();
		} else {
			System.out.println("Login failed. Invalid username or password.");
		}
	}

	public void works() {
		boolean continueLoop = true; // Control loop continuation

		while (continueLoop) {
			System.out.println("-------------------------------------------");
			System.out.println("Staff works");
			System.out.println("-------------------------------------------");

			System.out.println("1. course");
			System.out.println("2. exit");
			System.out.print("Enter your choice: ");
			int choice = scan.nextInt();

			switch (choice) {
			case 1:
				course();
				break;

			case 2:
				continueLoop = false;
				System.out.println("Exiting...");
				break;
			default:
				System.out.println("Invalid choice!");
			}
		}
	}

	public void course() {

		Scanner scanner = new Scanner(System.in);

		System.out.print("Enter faculty ID: ");
		String facultyId = scanner.nextLine();

		System.out.print("Enter material ID: ");
		String materialId = scanner.nextLine();

		System.out.print("Enter module name: ");
		String moduleName = scanner.nextLine();

		System.out.println("Enter the path to the uploaded file: ");
		String filePath = scanner.nextLine().replace("\"", "");

		String dbUrl = "jdbc:oracle:thin:@localhost:1521:XE";
		String dbUser = "CMS";
		String dbPassword = "$Oracle4";

		try {
			// Establish database connection
			Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);

			// Create a unique filename
			String fileName = facultyId + "_" + moduleName + ".pdf";

			// Save the uploaded file to a directory
			String uploadDirectory = "C:\\Users\\GOPINATH\\Desktop\\New folder";
			File uploadedFile = new File(uploadDirectory, fileName);

			try  (InputStream fileStream = new FileInputStream(filePath);
					OutputStream outputStream = new FileOutputStream(uploadedFile)) {
				byte[] buffer = new byte[1024];
				int bytesRead;
				while ((bytesRead = fileStream.read(buffer)) != -1) {
					outputStream.write(buffer, 0, bytesRead);
				}
			}

			String sql = "INSERT INTO materials (material_id,staff_id, module_name, file_name) VALUES (?,?, ?, ?)";
			try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
				preparedStatement.setString(1, materialId);
				preparedStatement.setString(2, facultyId);
				preparedStatement.setString(3, moduleName);
				preparedStatement.setString(4, fileName);
				preparedStatement.executeUpdate();
			}

			System.out.println("Material uploaded successfully!");
			scanner.close();

		} 
		catch (IOException | SQLException e) {
			System.err.println("Error uploading material: " + e.getMessage());
		}
	}

}
