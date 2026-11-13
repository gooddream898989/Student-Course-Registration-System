import java.util.ArrayList;// Ye dono Java Collections Framework ki classes hain
import java.util.HashMap;

public class RegistrationSystem {

    private ArrayList<Student> students = new ArrayList<>();
    private ArrayList<Course> courses = new ArrayList<>();
    private ArrayList<Registration> registrations = new ArrayList<>();

    private HashMap<Integer, Student> studentMap = new HashMap<>();
    private HashMap<String, Course> courseMap = new HashMap<>();

    public void addStudent(Student student)
            throws RegistrationException {

        if (studentMap.containsKey(student.getId())) {
            throw new RegistrationException(
                    "Student ID already exists."
                    
            );
        }

        students.add(student);
        studentMap.put(student.getId(), student);

        System.out.println("Student added successfully.");
    }

    public void displayStudents() {

        if (students.isEmpty()) {
            System.out.println("No students available.");
            return;
        }

        for (Student s : students) {

            Person p = s;
            p.display();

            System.out.println();
        }
    }

    public Student searchStudent(int studentId) {
        return studentMap.get(studentId);
    }

    public void addCourse(Course course)
            throws RegistrationException {

        String code = course.getCourseCode().toUpperCase();

        if (courseMap.containsKey(code)) {
            throw new RegistrationException(
                    "Course code already exists."
            );
        }

        courses.add(course);
        courseMap.put(code, course);

        System.out.println("Course added successfully.");
    }

    public void displayCourses() {

        if (courses.isEmpty()) {
            System.out.println("No courses available.");
            return;
        }

        System.out.println("\n      COURSE CATALOGUE ");

        for (Course c : courses) {
            c.displayCourseDetails();
            System.out.println("       ");
        }
    }

    public Course searchCourse(String courseCode) {

        return courseMap.get(courseCode.toUpperCase());
    }

    public void registerStudent(int studentId, String courseCode)
            throws RegistrationException {

        Student student = searchStudent(studentId);
        Course course = searchCourse(courseCode);

        if (student == null) {
            throw new RegistrationException("Student not found.");
        }

        if (course == null) {
            throw new RegistrationException("Course not found.");
        }

        for (Registration r : registrations) {

            if (r.getStudent().getId() == studentId
                    && r.getCourse().getCourseCode()
                    .equalsIgnoreCase(courseCode)
                    && r.getStatus().equals("Active")) {

                throw new RegistrationException(
                        "Student is already registered for this course."
                );
            }
        }

        String prerequisite = course.getPrerequisite();

        if (prerequisite != null && !prerequisite.isEmpty()) {

            if (!student.CheckIfCompleted(prerequisite)) {

                throw new RegistrationException(
                        "Prerequisite " + prerequisite
                                + " has not been completed."
                );
            }
        }

        if (!course.hasAvailableSeats()) {

            throw new RegistrationException(
                    "Course capacity is full."
            );
        }

        int totalCredits = 0;

        for (Registration r : registrations) {

            if (r.getStudent().getId() == studentId
                    && r.getStatus().equals("Active")) {

                totalCredits += r.getCourse().getCredits();
            }
        }

        if (totalCredits + course.getCredits() > 20) {

            throw new RegistrationException(
                    "Registration exceeds the 20-credit limit."
            );
        }

        Registration registration =
                new Registration(student, course);

        if (!student.addRegistration(courseCode)) {

            throw new RegistrationException(
                    "Student registration limit reached."
            );
        }

        registrations.add(registration);
        course.Enroll_Student();

        System.out.println("\nRegistration successful!");
        System.out.println("Student: " + student.getName());
        System.out.println("Course: " + course.getCourseName());
        System.out.println("Credits: " + course.getCredits());
        System.out.println(
                "Remaining seats: " + course.getAvailableSeats()
        );
    }

    public void displayRegistrations() {

        if (registrations.isEmpty()) {
            System.out.println("No registrations available.");
            return;
        }

        for (Registration r : registrations) {
            r.displayRegistration();
            System.out.println();
        }
    }

    public void displayStudentsInCourse(String courseCode)
            throws RegistrationException {

        Course course = searchCourse(courseCode);

        if (course == null) {
            throw new RegistrationException("Course not found.");
        }

        boolean found = false;

        System.out.println(
                "\nStudents registered in "
                        + course.getCourseName() + ":"
        );

        for (Registration r : registrations) {

            if (r.getCourse().getCourseCode()
                    .equalsIgnoreCase(courseCode)
                    && r.getStatus().equals("Active")) {

                System.out.println(
                        r.getStudent().getId() + " - "
                                + r.getStudent().getName()
                );

                found = true;
            }
        }

        if (!found) {
            System.out.println("No students registered in this course.");
        }
    }

    public void dropCourse(int studentId, String courseCode)
            throws RegistrationException {

        for (Registration r : registrations) {

            if (r.getStudent().getId() == studentId
                    && r.getCourse().getCourseCode()
                    .equalsIgnoreCase(courseCode)
                    && r.getStatus().equals("Active")) {

                r.dropRegistration();

                r.getCourse().dropping();

                r.getStudent().removeRegistration(courseCode);

                System.out.println("Course dropped successfully.");
                return;
            }
        }

        throw new RegistrationException(
                "No active registration found for this student and course."
        );
    }

    public void markCourseCompleted(int studentId, String courseCode)
            throws RegistrationException {

        Student student = searchStudent(studentId);
        Course course = searchCourse(courseCode);

        if (student == null) {
            throw new RegistrationException("Student not found.");
        }

        if (course == null) {
            throw new RegistrationException("Course not found.");
        }

        boolean active = false;

        for (Registration r : registrations) {

            if (r.getStudent().getId() == studentId
                    && r.getCourse().getCourseCode()
                    .equalsIgnoreCase(courseCode)
                    && r.getStatus().equals("Active")) {

                active = true;
                break;
            }
        }

        if (!active) {
            throw new RegistrationException(
                    "The student is not actively registered for this course."
            );
        }

        student.addCompletedCourse(courseCode);

        dropCourse(studentId, courseCode);

        System.out.println(
                "Course marked as completed successfully."
        );
    }

    public void displayAcademicSummary(int studentId)
            throws RegistrationException {

        Student student = searchStudent(studentId);

        if (student == null) {
            throw new RegistrationException("Student not found.");
        }

        System.out.println("\n                                       ");
        System.out.println("          ACADEMIC SUMMARY");
        System.out.println("                                         ");

        System.out.println("Student ID: " + student.getId());
        System.out.println("Name: " + student.getName());
        System.out.println("Department: " + student.getDept());
        System.out.println("Semester: " + student.getSem());

        System.out.println("\nCompleted Courses:");

        if (student.getCompletedCount() == 0) {
            System.out.println("None");
        } else {
            for (int i = 0; i < student.getCompletedCount(); i++) {
                System.out.println(
                        student.getCompletedCourse(i)
                );
            }
        }

        System.out.println("\nCurrently Registered Courses:");

        int totalCredits = 0;
        boolean found = false;

        for (Registration r : registrations) {

            if (r.getStudent().getId() == studentId
                    && r.getStatus().equals("Active")) {

                Course c = r.getCourse();

                System.out.println(
                        c.getCourseCode() + " - "
                                + c.getCourseName()
                                + " (" + c.getCredits() + " credits)"
                );

                totalCredits += c.getCredits();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No active registrations.");
        }

        System.out.println("\nTotal Registered Credits: " + totalCredits);
        System.out.println("Remaining Credit Allowance: "
                + (20 - totalCredits));

        System.out.println("                                    ");
    }

    public void restoreRegistration(
            int studentId, String courseCode, String status)
            throws RegistrationException {

        Student student = searchStudent(studentId);
        Course course = searchCourse(courseCode);

        if (student == null || course == null) {
            throw new RegistrationException(
                    "Invalid student or course in saved registration."
            );
        }

        Registration registration =
                new Registration(student, course);

        if (status.equals("Dropped")) {
            registration.dropRegistration();
        } else {

            if (!course.hasAvailableSeats()) {
                throw new RegistrationException(
                        "Cannot restore registration: course is full."
                );
            }

            if (!student.addRegistration(courseCode)) {
                throw new RegistrationException(
                        "Student registration limit reached."
                );
            }

            course.Enroll_Student();
        }

        registrations.add(registration);
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public ArrayList<Registration> getRegistrations() {
        return registrations;
    }
}