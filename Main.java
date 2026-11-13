import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        
        Scanner in = new Scanner(System.in);

        RegistrationSystem system = new RegistrationSystem();
        FileManager file = new FileManager();

        int ch = 0;
        

        // Load previously saved data when the program starts.
        try {
            file.loadAll(system);
            System.out.println("Saved data loaded successfully.");
        } catch (IOException e) {
            System.out.println("Unable to load data: " + e.getMessage());
        }

        do {

            System.out.println();

            System.out.println("  Student Course Registration System");

            System.out.println("1. Add Student");
            System.out.println("2. View/Search Students");
            System.out.println("3. Add Course");
            System.out.println("4. View/Search Courses");
            System.out.println("5. Register Student for Course");
            System.out.println("6. View Student Academic Summary");
            System.out.println("7. View Students Registered in a Course");
            System.out.println("8. Mark Course as Completed");
            System.out.println("9. Save Data");
            System.out.println("10. Exit");


            System.out.print("Enter your choice: ");
            try {

                ch = Integer.parseInt(in.nextLine());

                switch (ch) {

                    case 1:

                        System.out.print("Enter Student ID: ");
                        int id = Integer.parseInt(in.nextLine());

                        System.out.print("Enter Student Name: ");
                        String name = in.nextLine();

                        System.out.print("Enter Department: ");
                        String dept = in.nextLine();

                        System.out.print("Enter Semester: ");
                        int sem = Integer.parseInt(in.nextLine());

                        Student student = new Student(
                                id, name, dept, sem
                        );

                        system.addStudent(student);
                        break;

                    case 2:


                        System.out.print("Enter Student ID: ");
                        int searchId = Integer.parseInt(in.nextLine());

                        Student foundStudent = system.searchStudent(searchId);

                        if (foundStudent != null) {
                            foundStudent.display();
                        } else {
                            System.out.println("Student not found.");
                        }


                        break;

                    case 3:

                        System.out.print("Enter Course Code: ");
                        String code = in.nextLine();

                        System.out.print("Enter Course Name: ");
                        String courseName = in.nextLine();

                        System.out.print("Enter Course Credits: ");
                        int credits =
                                Integer.parseInt(in.nextLine());

                        System.out.print(
                                "Enter Prerequisite Course Code "
                                        + "(press Enter for none): "
                        );

                        String prerequisite = in.nextLine();

                        if (prerequisite.trim().isEmpty()) {
                            prerequisite = null;
                        }

                        System.out.print("Enter Maximum Capacity: ");
                        int capacity =
                                Integer.parseInt(in.nextLine());

                        if (credits <= 0 || capacity <= 0) {
                            System.out.println(
                                    "Credits and capacity must be positive."
                            );
                            break;
                        }

                        Course course = new Course(
                                code, courseName, credits,
                                prerequisite, capacity
                        );

                        system.addCourse(course);
                        break;

                    case 4:

                        System.out.println("\n1. View All Courses");
                        System.out.println("2. Search Course by Code");
                        System.out.print("Enter your choice: ");

                        int courseChoice =
                                Integer.parseInt(in.nextLine());

                        if (courseChoice == 1) {

                            system.displayCourses();

                        } else if (courseChoice == 2) {

                            System.out.print("Enter Course Code: ");
                            String searchCode = in.nextLine();

                            Course foundCourse =
                                    system.searchCourse(searchCode);

                            if (foundCourse != null) {
                                foundCourse.displayCourseDetails();
                            } else {
                                System.out.println("Course not found.");
                            }

                        } else {
                            System.out.println("Invalid choice.");
                        }

                        break;

                    case 5:

                        System.out.print("Enter Student ID: ");
                        int registerId =
                                Integer.parseInt(in.nextLine());

                        System.out.print("Enter Course Code: ");
                        String registerCode = in.nextLine();

                        system.registerStudent(
                                registerId, registerCode
                        );

                        break;

                    case 6:

                        System.out.print("Enter Student ID: ");
                        int summaryId =
                                Integer.parseInt(in.nextLine());

                        system.displayAcademicSummary(summaryId);
                        break;

                    case 7:

                        System.out.print("Enter Course Code: ");
                        String viewCode = in.nextLine();

                        system.displayStudentsInCourse(viewCode);
                        break;

                    case 8:

                        System.out.print("Enter Student ID: ");
                        int completedId =
                                Integer.parseInt(in.nextLine());

                        System.out.print("Enter Course Code: ");
                        String completedCode = in.nextLine();

                        system.markCourseCompleted(
                                completedId, completedCode
                        );

                        break;

                    case 9:

                        file.saveAll(system);
                        break;

                    case 10:

                        // Save data before exiting.
                        file.saveAll(system);

                        System.out.println(
                                "Thank you for using CampusEnroll!"
                        );

                        break;

                    default:

                        System.out.println(
                                "Invalid choice. Enter a number from 1 to 10."
                        );
                }

            } catch (NumberFormatException e) {

                System.out.println(
                        "Please enter a valid number."
                );

            } catch (RegistrationException e) {

                System.out.println(
                        "Operation failed: " + e.getMessage()
                );

            } catch (IOException e) {

                System.out.println(
                        "File error: " + e.getMessage()
                );
            }

        } while (ch != 10);

        in.close();
    }
}