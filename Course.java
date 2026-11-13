public class Course {

    private String courseCode;
    private String courseName;
    private int credits;
    private String prerequisite;
    private int Capacity;

    private int Enrolled_Students = 0;

    public Course(String courseCode, String courseName,
                  int credits, String prerequisite,
                  int Capacity) {

        this.courseCode = courseCode;
        this.courseName = courseName;
        this.credits = credits;
        this.prerequisite = prerequisite;
        this.Capacity = Capacity;
    }
      

    public String getCourseCode() {
        return courseCode;
    }

    public String getCourseName() {
        return courseName;
    }
    public int getCredits() {
        return credits;
    }

    public String getPrerequisite() {
        return prerequisite;
    }

    public int getCapacity() {
        return Capacity;
    }

    public int getEnrolledStudents() {
        return Enrolled_Students;
    }
 public int getAvailableSeats() {
        return Capacity - Enrolled_Students;
    }

    public boolean hasAvailableSeats() {
        return Enrolled_Students < Capacity;
    }

    public void Enroll_Student() {

        if (hasAvailableSeats()) {
            Enrolled_Students++;
        } else {
            System.out.println("Course capacity is full.");
        }
    }

    public void dropping() {

        if (Enrolled_Students > 0) {
            Enrolled_Students--;
        }
    }

    public void displayCourseDetails() {

        System.out.println("Course Code: " + courseCode);
        System.out.println("Course Name: " + courseName);
        System.out.println("Credits: " + credits);

        if (prerequisite == null || prerequisite.isEmpty()) {
            System.out.println("Prerequisite: None");
        } else {
            System.out.println("Prerequisite: " + prerequisite);
        }

        System.out.println("Maximum Capacity: " + Capacity);
        System.out.println("Enrolled Students: " + Enrolled_Students);
        System.out.println("Available Seats: " + getAvailableSeats());
    }
}