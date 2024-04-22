package Jdbc.CollegeManagement;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		while (true) {
			System.out.println("-------------------------------------------");
			System.out.println("Welcome to the College Management System");
			System.out.println("-------------------------------------------");
			System.out.println("1. Admin");
			System.out.println("2. Staff");
			System.out.println("3. Student");
			System.out.println("4. Exit");
			System.out.print("Enter your choice: ");
			int choice = scanner.nextInt();

			Admin admin = new Admin();
			Student student = new Student();
			Staff staff = new Staff();

			switch (choice) {
			case 1:
				admin.adminoption();
				break;
			case 2:
				staff.staffOption();
				break;
			case 3:
				student.Studentoption();
				break;
			case 4:
				System.out.println("Exiting the system...");
				scanner.close();
				System.exit(0);
				break;
			default:
				System.out.println("Invalid choice. Please try again.");
			}
		}
	}
}
