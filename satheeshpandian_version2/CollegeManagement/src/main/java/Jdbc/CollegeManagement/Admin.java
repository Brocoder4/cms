package Jdbc.CollegeManagement;
import exception.EmailException;
import java.sql.PreparedStatement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import Database.DBConnection;

public class Admin {
	DBConnection connect = new DBConnection();
	Scanner scan = new Scanner(System.in);
	// EmailException emailVerifier = new EmailException(null);

	public void adminoption() {
		boolean continueLoop = true; // Control loop continuation

		while (continueLoop) {
			System.out.println("-------------------------------------------");
			System.out.println("Admin");
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
			 EmailException emailException = new EmailException("Invalid email. Enter the correct Email address.");
			System.out.println("-------------------------------------------");
			System.out.println("Admin registration");
			System.out.println("-------------------------------------------");
			System.out.print("enter the user Id (etc:A001):");
			String userid = scan.next();

			System.out.print("enter the user name:");
			String username = scan.next();

			System.out.print("enter the Email:");
			String Email = scan.next();

			// emailVerifier.setEmail(Email);
			// System.out.println("Email is valid.");

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
			
				emailException.setEmail(Email);}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		 catch (EmailException e) {
	            // Handle the exception (e.g., log, display an error message, etc.)
	            System.err.println("EmailException caught: " + e.getMessage());
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

//works method starts here
	public void works() {
		boolean continueLoop = true; // Control loop continuation

		while (continueLoop) {
			System.out.println("-------------------------------------------");
			System.out.println("Admin works");
			System.out.println("-------------------------------------------");

			System.out.println("1. course");
			System.out.println("2. Faculty");
			System.out.println("3. student");
			System.out.println("4. fees");
			System.out.println("5. report");
			System.out.println("6. exit");
			System.out.print("Enter your choice: ");
			int choice = scan.nextInt();

			switch (choice) {
			case 1:
				course();
				break;
			case 2:
				Faculty();
				break;
			case 3:
				Student();
				break;

			case 4:
				Feesdetails();
				break;
			// case 5:
			// report();
			// break;

			case 6:
				continueLoop = false;
				System.out.println("Exiting...");
				break;
			default:
				System.out.println("Invalid choice!");
			}
		}

	}

	public void course() {
		boolean continueLoop = true; // Control loop continuation

		while (continueLoop) {
			System.out.println("-------------------------------------------");
			System.out.println("Course Management");
			System.out.println("-------------------------------------------");

			System.out.println("1. Add Course");
			System.out.println("2. View Courses");
			System.out.println("3. Update Course");
			System.out.println("4. Delete Course");
			System.out.println("5. Exit");
			System.out.print("Enter your choice: ");
			int choice = scan.nextInt();

			switch (choice) {
			case 1:
				addCourse();
				break;
			case 2:
				viewCourses();
				break;
			case 3:
				updateCourse();
				break;
			case 4:
				deleteCourse();
				break;
			case 5:
				continueLoop = false;
				System.out.println("Exiting...");
				break;
			default:
				System.out.println("Invalid choice!");
			}
		}
	}

	public void addCourse() {
		try {
			System.out.print("Enter course code(C001): ");
			String COURSE_ID = scan.next();
			System.out.print("Enter course name: ");
			String COURSE_NAME = scan.next();
			scan.nextLine(); // Consume extra newline character

			connect.InitiateDB();
			String sql = "INSERT INTO COURSE (COURSE_ID,COURSE_NAME) VALUES (?, ?)";
			Object[] params = { COURSE_ID, COURSE_NAME };
			int rowsInserted = connect.InsertDB(sql, params);
			if (rowsInserted > 0) {
				System.out.println("A new course was inserted!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void viewCourses() {

		try {
			System.out.print("Enter course id to view details: ");
			String COURSE_ID = scan.next();

			connect.InitiateDB();
			String sql = "SELECT * FROM COURSE WHERE COURSE_ID = ?";
			PreparedStatement statement = connect.getConnection().prepareStatement(sql);
			statement.setString(1, COURSE_ID);

			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				String courseName = resultSet.getString("COURSE_NAME");
				System.out.println("Course Code: " + COURSE_ID);
				System.out.println("Course Name: " + courseName);
			} else {
				System.out.println("Course not found with code: " + COURSE_ID);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void updateCourse() {
		try {
			System.out.print("Enter the course code of the course you want to update: ");
			String COURSE_ID = scan.next();
			System.out.print("Enter the new course name: ");
			String COURSE_NAME = scan.next();

			connect.InitiateDB();
			String sql = "UPDATE COURSE SET COURSE_NAME = ? WHERE COURSE_ID = ?";
			PreparedStatement statement = connect.getConnection().prepareStatement(sql);
			statement.setString(1, COURSE_NAME);
			statement.setString(2, COURSE_ID);

			int rowsUpdated = statement.executeUpdate();
			if (rowsUpdated > 0)
				System.out.println("Course updated successfully!");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteCourse() {
		try {
			System.out.print("Enter the course code of the course you want to delete: ");
			String COURSE_ID = scan.next();

			connect.InitiateDB();
			String sql = "DELETE FROM COURSE WHERE COURSE_ID = ?";
			PreparedStatement statement = connect.getConnection().prepareStatement(sql);
			statement.setString(1, COURSE_ID);

			int rowsDeleted = statement.executeUpdate();
			if (rowsDeleted > 0) {
				System.out.println("Course deleted successfully!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void Faculty() {
		boolean continueLoop = true; // Control loop continuation

		while (continueLoop) {
			System.out.println("-------------------------------------------");
			System.out.println("Staff Management");
			System.out.println("-------------------------------------------");

			System.out.println("1. Add staff");
			System.out.println("2. View staff");
			System.out.println("3. Delete staff");
			System.out.println("4. Exit");
			System.out.print("Enter your choice: ");
			int choice = scan.nextInt();

			switch (choice) {
			case 1:
				addStaff();
				break;
			case 2:
				viewStaff();
				break;
			case 3:
				deleteStaff();
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

	public void addStaff() {
		try {
			System.out.print("Enter Staff id (S001): ");
			String STAFF_ID = scan.next();
			System.out.print("Name: ");
			String STAFF_NAME = scan.next();
			System.out.print("Email: ");
			String STAFF_EMAIL = scan.next();
			System.out.print("Contact no: ");
			String STAFF_PHONE = scan.next();
			System.out.print("Designation: ");
			String STAFF_DESIGNATION = scan.next();
			System.out.print("Enter Salary: ");
			int STAFF_SALARY = scan.nextInt();

			connect.InitiateDB();
			String sql = "INSERT INTO STAFF (STAFF_ID,user_NAME,user_EMAIL,user_PHONE,DESIGNATION,SALARY) VALUES (?,?,?,?,?,?)";
			Object[] params = { STAFF_ID, STAFF_NAME, STAFF_EMAIL, STAFF_PHONE, STAFF_DESIGNATION, STAFF_SALARY };
			int rowsInserted = connect.InsertDB(sql, params);
			if (rowsInserted > 0) {
				System.out.println("A new Staff data was inserted!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void viewStaff() {

		try {
			System.out.print("Enter Staff id to view details: ");
			String STAFF_ID = scan.next();
			scan.nextLine(); // Consume extra newline character

			connect.InitiateDB();
			String sql = "SELECT * FROM STAFF WHERE STAFF_ID = ?";
			PreparedStatement statement = connect.getConnection().prepareStatement(sql);
			statement.setString(1, STAFF_ID);

			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				String staffName = resultSet.getString("user_NAME");
				String staffemail = resultSet.getString("user_EMAIL");
				String staffphone = resultSet.getString("user_PHONE");
				String staffdes = resultSet.getString("DESIGNATION");
				String staffsalary = resultSet.getString("SALARY");
				System.out.println("staff id: " + STAFF_ID);
				System.out.println("Staff Name: " + staffName);
				System.out.println("Staff email: " + staffemail);
				System.out.println("Staff phone no: " + staffphone);
				System.out.println("Staff Designation: " + staffdes);
				System.out.println("Staff salary: " + staffsalary);
			} else {
				System.out.println("Staff not found in this id: " + STAFF_ID);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteStaff() {
		try {
			System.out.print("Enter the Staff id  you want to delete: ");
			String STAFF_ID = scan.next();

			connect.InitiateDB();
			String sql = "DELETE FROM STAFF WHERE STAFF_ID = ?";
			PreparedStatement statement = connect.getConnection().prepareStatement(sql);
			statement.setString(1, STAFF_ID);

			int rowsDeleted = statement.executeUpdate();
			if (rowsDeleted > 0) {
				System.out.println("Staff deleted successfully!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void Student() {
		boolean continueLoop = true; // Control loop continuation

		while (continueLoop) {
			System.out.println("-------------------------------------------");
			System.out.println("Student Management");
			System.out.println("-------------------------------------------");

			System.out.println("1. Add student");
			System.out.println("2. View student");
			System.out.println("3. Delete student");
			System.out.println("4. Exit");
			System.out.print("Enter your choice: ");
			int choice = scan.nextInt();

			switch (choice) {
			case 1:
				addStudent();
				break;
			case 2:
				viewStudent();
				break;
			case 3:
				deleteStudent();
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

	public void addStudent() {
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
			String sql = "INSERT INTO STUDENT (user_id,NAME,EMAIL,PHONE_number,Date_of_birth,address,enrollment_date,course_id) VALUES (?,?,?,?,?,?,?,?)";
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

	public void viewStudent() {

		try {
			System.out.print("Enter Student id to view details: ");
			String student_Id = scan.next();
			scan.nextLine(); // Consume extra newline character

			connect.InitiateDB();
			String sql = "SELECT * FROM student WHERE USER_id = ?";
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

				System.out.println(" id: " + student_Id);
				System.out.println(" Name: " + Name);
				System.out.println(" email: " + email);
				System.out.println(" phone no: " + phone);
				System.out.println(" Designation: " + dob);
				System.out.println(" Address: " + address);
				System.out.println(" Enrollment date: " + enroll);
				System.out.print("Course id: " + course_id);
			} else {
				System.out.println("Student not found in this id: " + student_Id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteStudent() {
		try {
			System.out.print("Enter the Student id  you want to delete: ");
			String Student_ID = scan.next();

			connect.InitiateDB();
			String sql = "DELETE FROM Student WHERE User_ID = ?";
			PreparedStatement statement = connect.getConnection().prepareStatement(sql);
			statement.setString(1, Student_ID);

			int rowsDeleted = statement.executeUpdate();
			if (rowsDeleted > 0) {
				System.out.println("Student deleted successfully!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void Feesdetails() {
		boolean continueLoop = true; // Control loop continuation
		while (continueLoop) {
			System.out.println("-------------------------------------------");
			System.out.println("Fees Management");
			System.out.println("-------------------------------------------");
			System.out.println("1. Add fees");
			System.out.println("2. View fees");
			System.out.println("3. Delete fees");

			System.out.println("4. Exit");
			System.out.print("Enter your choice: ");
			int choice = scan.nextInt();

			switch (choice) {
			case 1:
				addFees();
				break;
			case 2:
				viewFees();
				break;
			case 3:
				deleteFees();
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

	void addFees() {

		try {
			System.out.print("Enter the course id (C001): ");
			String COURSE_ID = scan.next();
			System.out.print("Department: ");
			String DEPARTMENT = scan.next();
			System.out.print("Amount: ");
			int AMOUNT = scan.nextInt();

			connect.InitiateDB();
			String sql = "INSERT INTO fees (COURSE_ID,DEPARTMENT ,AMOUNT) VALUES (?,?,?)";
			Object[] params = { COURSE_ID, DEPARTMENT, AMOUNT };
			int rowsInserted = connect.InsertDB(sql, params);
			if (rowsInserted > 0) {
				System.out.println("A new fees data inserted!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void viewFees() {

		try {
			System.out.print("Enter course id to view fees details: ");
			String course_Id = scan.next();

			connect.InitiateDB();
			String sql = "SELECT * FROM fees WHERE course_Id = ?";
			PreparedStatement statement = connect.getConnection().prepareStatement(sql);
			statement.setString(1, course_Id);

			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				String course_id = resultSet.getString("COURSE_ID");
				String department = resultSet.getString("DEPARTMENT");
				String amount = resultSet.getString("AMOUNT");

				System.out.println(" course id: " + course_id);
				System.out.println(" Department: " + department);
				System.out.println(" Amount: Rs." + amount);

			} else {
				System.out.println("course id not found in this id: " + course_Id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteFees() {
		try {
			System.out.print("Enter the course id  you want to delete: ");
			String COURSE_ID = scan.next();

			connect.InitiateDB();
			String sql = "DELETE FROM Fees WHERE COURSE_ID = ?";
			PreparedStatement statement = connect.getConnection().prepareStatement(sql);
			statement.setString(1, COURSE_ID);

			int rowsDeleted = statement.executeUpdate();
			if (rowsDeleted > 0) {
				System.out.println("fees data deleted successfully!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
