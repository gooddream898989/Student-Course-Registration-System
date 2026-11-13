public class Student extends Person {
    
    private int Id;
    private String dept;
    private int sem;

    private String[] completedCourses = new String[50];
    private String[] registerations = new String[50];
    
    private int completed = 0;
    private int registered = 0;

    public Student(int Id, String name,
                   String dept, int sem) {
    
        super(name);
        this.Id = Id;
        this.dept = dept;
        this.sem = sem;
    }

    public int getId() {
        return Id;
    }

    public String getDept() {
        return dept;
    }

    public int getSem() {
        return sem;
    }

    public void addCompletedCourse(String Code) {

        if (CheckIfCompleted(Code)) {
            return;
        }

        if (completed < completedCourses.length) {
            completedCourses[completed] = Code;
            completed++;
        } else {
            System.out.println("Course Limit Exceeded");
        }
    }

    public boolean CheckIfCompleted(String Code) {
        
        for (int i = 0; i < completed; i++) {
            if (completedCourses[i].equalsIgnoreCase(Code)) {
                return true;
            }
        }

        return false;
    }

    public boolean addRegistration(String courseCode) {
         
        if (registered < registerations.length) {
            registerations[registered] = courseCode;
            registered++;
            return true;
        }

        return false;
    }
// used for removing the courses(drop)
    public void removeRegistration(String courseCode) {

        for (int i = 0; i < registered; i++) {

            if (registerations[i].equalsIgnoreCase(courseCode)) {

                for (int j = i; j < registered - 1; j++) {
                    registerations[j] = registerations[j + 1];
                }

                registered--;
                registerations[registered] = null;
                return;
            }
        }
    }

    public int getCompletedCount() {
        return completed;
    }

    public String getCompletedCourse(int index) {
        return completedCourses[index];
    }

    public int getRegisteredCount() {
        return registered;
    }

    public String getRegisteredCourse(int index) {
        return registerations[index];
    }

    @Override
    public void display() {

        System.out.println("Student ID: " + Id);
        System.out.println("Name: " + getName());
        System.out.println("Department: " + dept);
        System.out.println("Semester: " + sem);

        System.out.print("Completed Courses: ");

        if (completed == 0) {
            System.out.print("None");
        } else {
            for (int i = 0; i < completed; i++) {
                System.out.print(completedCourses[i] + " ");
            }
        }

        System.out.println();

        System.out.print("Registered Courses: ");

        if (registered == 0) {
            System.out.print("None");
        } else {
            for (int i = 0; i < registered; i++) {
                System.out.print(registerations[i] + " ");
            }
        }

        System.out.println();
    }
}