public class Registration {

    private Student student;
    private Course course;
    private String status;

    public Registration(Student student, Course course) {

        this.student = student;
        this.course = course;
        this.status = "Active";
    }

    public Student getStudent() {
        return student;
    }

    public Course getCourse() {
        return course;
    }

    public String getStatus() {
        return status;
    }

    public void dropRegistration() {
        status = "Dropped";
    }

    public void displayRegistration() {

        System.out.println("Student ID: " + student.getId());
        System.out.println("Student Name: " + student.getName());
        System.out.println("Course Code: " + course.getCourseCode());
        System.out.println("Course Name: " + course.getCourseName());
        System.out.println("Credits: " + course.getCredits());
        System.out.println("Status: " + status);
    }
}