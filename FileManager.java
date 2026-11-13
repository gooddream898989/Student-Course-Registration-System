import java.io.*;

public class FileManager {

    public void saveStudents(RegistrationSystem system)
            throws IOException {
   
        try (PrintWriter writer =
                     new PrintWriter(new FileWriter("students.txt"))) {

            for (Student s : system.getStudents()) {

                String completed = "";

                for (int i = 0; i < s.getCompletedCount(); i++) {

                    if (i > 0) {
                        completed += ";";
                    }

                    completed += s.getCompletedCourse(i);
                }

                writer.println(
                        s.getId() + ","
                                + s.getName() + ","
                                + s.getDept() + ","
                                + s.getSem() + ","
                                + completed
                );
            }
        }
    }

    public void saveCourses(RegistrationSystem system)
            throws IOException {

        try (PrintWriter writer =
                     new PrintWriter(new FileWriter("courses.txt"))) {

            for (Course c : system.getCourses()) {

                writer.println(
                        c.getCourseCode() + ","
                                + c.getCourseName() + ","
                                + c.getCredits() + ","
                                + (c.getPrerequisite() == null
                                ? "" : c.getPrerequisite()) + ","
                                + c.getCapacity()
                );
            }
        }
    }

    public void saveRegistrations(RegistrationSystem system)
            throws IOException {

        try (PrintWriter writer =
                     new PrintWriter(new FileWriter("registrations.txt"))) {

            for (Registration r : system.getRegistrations()) {

                writer.println(
                        r.getStudent().getId() + ","
                                + r.getCourse().getCourseCode() + ","
                                + r.getStatus()
                );
            }
        }
    }

    public void loadStudents(RegistrationSystem system)
            throws IOException {

        File file = new File("students.txt");

        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader =
                     new BufferedReader(new FileReader(file))) {

            String line;

            while ((line = reader.readLine()) != null) {

                String[] data = line.split(",", -1);

                try {

                    Student student = new Student(
                            Integer.parseInt(data[0]),
                            data[1],
                            data[2],
                            Integer.parseInt(data[3])
                    );

                    if (data.length > 4 && !data[4].isEmpty()) {

                        String[] completed = data[4].split(";");

                        for (String code : completed) {
                            student.addCompletedCourse(code);
                        }
                    }

                    system.addStudent(student);

                } catch (RegistrationException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    public void loadCourses(RegistrationSystem system)
            throws IOException {

        File file = new File("courses.txt");

        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader =
                     new BufferedReader(new FileReader(file))) {

            String line;

            while ((line = reader.readLine()) != null) {

                String[] data = line.split(",", -1);

                try {

                    Course course = new Course(
                            data[0],
                            data[1],
                            Integer.parseInt(data[2]),
                            data[3].isEmpty() ? null : data[3],
                            Integer.parseInt(data[4])
                    );

                    system.addCourse(course);

                } catch (RegistrationException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    public void loadRegistrations(RegistrationSystem system)
            throws IOException {

        File file = new File("registrations.txt");

        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader =
                     new BufferedReader(new FileReader(file))) {

            String line;

            while ((line = reader.readLine()) != null) {

                String[] data = line.split(",", -1);

                try {

                    system.restoreRegistration(
                            Integer.parseInt(data[0]),
                            data[1],
                            data[2]
                    );

                } catch (RegistrationException e) {
                    System.out.println(
                            "Registration could not be restored: "
                                    + e.getMessage()
                    );
                }
            }
        }
    }

    public void saveAll(RegistrationSystem system)
            throws IOException {

        saveStudents(system);
        saveCourses(system);
        saveRegistrations(system);

        System.out.println("All application data saved successfully.");
    }

    public void loadAll(RegistrationSystem system)
            throws IOException {

        loadStudents(system);
        loadCourses(system);
        loadRegistrations(system);
    }
}