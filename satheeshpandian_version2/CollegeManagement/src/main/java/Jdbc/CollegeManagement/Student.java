package Jdbc.CollegeManagement;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import Database.DBConnection;

public class Student {
	DBConnection connect = new DBConnection();
	Scanner scan = new Scanner(System.in);

	public void Studentoption() {
		boolean continueLoop = true; // Control loop continuation

		while (continueLoop) {
			System.out.println("-------------------------------------------");
			System.out.println("STUDENT");
			System.out.println("-------------------------------------------");
			System.out.println("1. Registration");
			System.out.println("2. Login");
			System.out.println("3. Exit");
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

	// register method starts here
	public void register() {

		try {
			System.out.println("-------------------------------------------");
			System.out.println("STUDENT registration");
			System.out.println("-------------------------------------------");
			System.out.print("enter the user Id (etc:U001):");
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

	// Method to check if the user can log in with the provided username and
	// password
	public boolean canUserLogin(String userid, String password) {
		boolean isValidUser = false;
		try {
			connect.InitiateDB(); // Ensure the database connection is initiated

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

	public void works() {
		boolean continueLoop = true; // Control loop continuation

		while (continueLoop) {
			System.out.println("-------------------------------------------");
			System.out.println("STUDENT ");
			System.out.println("-------------------------------------------");

			System.out.println("1.personal details ");
			System.out.println("2.course");
			System.out.println("3.fees");
			System.out.println("4.exit");
			System.out.print("Enter your choice: ");
			int choice = scan.nextInt();

			switch (choice) {
			case 1:
				Pinfo();
				break;
			case 2:
				course();
				break;
			case 3:
				fees();
				break;
			case 4:
				continueLoop = false;
				System.out.println("Exiting...");
				break;
			default:
				System.out.println("Invalid choice!");
			}
		}
	}

	public void Pinfo() {
		boolean continueLoop = true; // Control loop continuation

		while (continueLoop) {

			System.out.println("1.Add personal details ");
			System.out.println("2.view personaldetails");
			System.out.println("3.exit");
			System.out.print("Enter your choice: ");
			int choice = scan.nextInt();

			switch (choice) {
			case 1:
				register1();
				break;
			case 2:
				viewdetails();
				break;
			case 3:
				continueLoop = false;
				System.out.println("Exiting...");
				break;
			default:
				System.out.println("Invalid choice!");
			}
		}
	}

	public void register1() {
		try {
			System.out.print("Enter Student id (U001): ");
			String STUDENT_ID = scan.next();
			System.out.print("Name: ");
			String NAME = scan.next();
			System.out.print("Email: ");
			String EMAIL = scan.next();
			System.out.print("Contact no: ");
			String PHONE_number = scan.next();
			System.out.print("D.O.B : ");
			String date_of_birth = scan.next();
			System.out.print("Address: ");
			String address = scan.next();
			System.out.print("Enrollment date : ");
			String enrollment_date = scan.next();
			System.out.print("Course id(MCA:C001)(MBA:C002): ");
			String Course_id = scan.next();

			connect.InitiateDB();
			String sql = "INSERT INTO STUDENT (User_id,NAME,EMAIL,PHONE_number,Date_of_birth,address,enrollment_date,course_id) VALUES (?,?,?,?,?,?,?,?)";
			Object[] params = { STUDENT_ID, NAME, EMAIL, PHONE_number, date_of_birth, address, enrollment_date,
					Course_id };
			int rowsInserted = connect.InsertDB(sql, params);
			if (rowsInserted > 0) {
				System.out.println("A new Student data inserted!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void viewdetails() {
		try {
			System.out.print("Enter Student id to view details: ");
			String student_Id = scan.next();
			scan.nextLine(); // Consume extra newline character

			connect.InitiateDB();
			String sql = "SELECT * FROM student WHERE user_id = ?";
			PreparedStatement statement = connect.getConnection().prepareStatement(sql);
			statement.setString(1, student_Id);

			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				String Name = resultSet.getString("NAME");
				String email = resultSet.getString("EMAIL");
				String phone = resultSet.getString("PHONE_Number");
				String dob = resultSet.getString("DATE_OF_BIRTH");
				String address = resultSet.getString("Address");
				String enroll = resultSet.getString("Enrollment_date");
				String course_id = resultSet.getString("Course_id");
				System.out.println("id: " + student_Id);
				System.out.println("Name: " + Name);
				System.out.println("email: " + email);
				System.out.println("phone no: " + phone);
				System.out.println("Date of birth: " + dob);
				System.out.println("Address: " + address);
				System.out.println("Enrollment date: " + enroll);
				System.out.println("Course id: " + course_id);
			} else {
				System.out.println("Student not found in this id: " + student_Id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

//-------------------------------------------------------------------------------------	
	public void course() {

		boolean continueLoop = true; // Control loop continuation

		while (continueLoop) {

			System.out.println("1.New course");
			System.out.println("2.Your course");
			System.out.println("3.Access materials");
			System.out.println("4.exit");
			System.out.print("Enter your choice: ");
			int choice = scan.nextInt();

			switch (choice) {
			case 1:
				new_course();
				break;
			case 2:
				your_course();
				break;
			case 3:
				Materials();
				break;
			case 4:
				continueLoop = false;
				System.out.println("Exiting...");
				break;
			default:
				System.out.println("Invalid choice!");
			}
		}
	}

	public void new_course()

	{
		try {
			connect.InitiateDB();
			String sql = "SELECT * FROM course";
			PreparedStatement statement = connect.getConnection().prepareStatement(sql);
			ResultSet rs = statement.executeQuery();

			// Display the header
			System.out.println(" Course ID   |  Course Name ");
			System.out.println("---------------------------------");

			// Process the result set
			while (rs.next()) {
				String courseId = rs.getString("course_id"); // Corrected this line
				String courseName = rs.getString("course_name");

				// Display course data
				System.out.printf("%-11s | %-11s%n", courseId, courseName);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void your_course() {
		try {
			System.out.print("Enter Student id to view details: ");
			String COURSE_ID = scan.next();

			connect.InitiateDB();
			String sql = "SELECT c.course_name FROM student s INNER JOIN course c ON s.course_id = c.course_id WHERE s.user_id = ?";
			PreparedStatement statement = connect.getConnection().prepareStatement(sql);
			statement.setString(1, COURSE_ID);

			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				String courseName = resultSet.getString("COURSE_NAME");
				System.out.println("Course Name: " + courseName);
			} else {
				System.out.println("Course not found with code: " + COURSE_ID);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void Materials() {

		Scanner scanner = new Scanner(System.in);

		System.out.print("Enter student ID: ");
		String studentId = scanner.nextLine();

		System.out.print("Enter material ID: ");
		String materialId = scanner.nextLine();

		try {
			// Establish database connection
			connect.InitiateDB();
			// Prepare SQL query to retrieve material information
			String sql = "SELECT file_name FROM materials WHERE material_id = ?";
			try (PreparedStatement preparedStatement = connect.getConnection().prepareStatement(sql)) {
				preparedStatement.setString(1, materialId);

				// Execute the query
				ResultSet resultSet = preparedStatement.executeQuery();

				// Process the result
				if (resultSet.next()) {
					String fileName = resultSet.getString("file_name");
					String downloadDirectory = "E:\\aji\\";
					File downloadedFile = new File(downloadDirectory, fileName);

					// Provide the file path to the student
					System.out.println("The material can be accessed at: " + downloadedFile.getAbsolutePath());
				} else {
					System.out.println("Material not found.");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void fees() {
		try {
			System.out.print("Enter Student id to view fees details: ");
			String COURSE_ID = scan.next();

			connect.InitiateDB();
			String sql = "SELECT c.fees FROM fees JOIN student s ON c.course_id = s.course_id WHERE s.user_id = ?";
			PreparedStatement statement = connect.getConnection().prepareStatement(sql);
			statement.setString(1, COURSE_ID);

			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				String courseName = resultSet.getString("COURSE_NAME");
				System.out.println("Course Name: " + courseName);
			} else {
				System.out.println("Course not found with code: " + COURSE_ID);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
