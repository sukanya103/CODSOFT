import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class StudentManagementSystemApp extends Application {
    private static final String FILE_NAME = "students.dat";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        StudentManagementSystem sms = loadStudentsFromFile();

        primaryStage.setTitle("Student Management System");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setHgap(10);
        grid.setVgap(10);

        Label nameLabel = new Label("Name:");
        TextField nameField = new TextField();
        Label rollNumberLabel = new Label("Roll Number:");
        TextField rollNumberField = new TextField();
        Label gradeLabel = new Label("Grade:");
        TextField gradeField = new TextField();

        Button addButton = new Button("Add");
        Button editButton = new Button("Edit");
        Button searchButton = new Button("Search");
        Button displayButton = new Button("Display All");
        Button exitButton = new Button("Exit");

        grid.add(nameLabel, 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(rollNumberLabel, 0, 1);
        grid.add(rollNumberField, 1, 1);
        grid.add(gradeLabel, 0, 2);
        grid.add(gradeField, 1, 2);
        grid.add(addButton, 0, 3);
        grid.add(editButton, 1, 3);
        grid.add(searchButton, 0, 4);
        grid.add(displayButton, 1, 4);
        grid.add(exitButton, 0, 5);

        addButton.setOnAction(e -> addStudent(nameField.getText(), rollNumberField.getText(), gradeField.getText(), sms));
        editButton.setOnAction(e -> editStudent(nameField.getText(), rollNumberField.getText(), gradeField.getText(), sms));
        searchButton.setOnAction(e -> searchStudent(rollNumberField.getText(), sms));
        displayButton.setOnAction(e -> displayAllStudents(sms));
        exitButton.setOnAction(e -> saveAndExit(sms));

        Scene scene = new Scene(grid, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void addStudent(String name, String rollNumberStr, String grade, StudentManagementSystem sms) {
        if (!name.isEmpty() && !rollNumberStr.isEmpty() && !grade.isEmpty()) {
            try {
                int rollNumber = Integer.parseInt(rollNumberStr);
                Student newStudent = new Student(name, rollNumber, grade);
                sms.addStudent(newStudent);
                System.out.println("Student added successfully!");
            } catch (NumberFormatException e) {
                System.out.println("Invalid Roll Number. Please enter a valid integer.");
            }
        } else {
            System.out.println("Please fill in all the fields.");
        }
    }

    private void editStudent(String name, String rollNumberStr, String grade, StudentManagementSystem sms) {
        if (!name.isEmpty() && !rollNumberStr.isEmpty() && !grade.isEmpty()) {
            try {
                int rollNumber = Integer.parseInt(rollNumberStr);
                Student student = sms.searchStudent(rollNumber);
                if (student != null) {
                    student.setName(name);
                    student.setGrade(grade);
                    System.out.println("Student information updated successfully!");
                } else {
                    System.out.println("Student not found!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid Roll Number. Please enter a valid integer.");
            }
        } else {
            System.out.println("Please fill in all the fields.");
        }
    }

    private void searchStudent(String rollNumberStr, StudentManagementSystem sms) {
        if (!rollNumberStr.isEmpty()) {
            try {
                int rollNumber = Integer.parseInt(rollNumberStr);
                Student student = sms.searchStudent(rollNumber);
                if (student != null) {
                    System.out.println("Student found:");
                    System.out.println(student);
                } else {
                    System.out.println("Student not found!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid Roll Number. Please enter a valid integer.");
            }
        } else {
            System.out.println("Please enter the Roll Number to search.");
        }
    }

    private void displayAllStudents(StudentManagementSystem sms) {
        for (Student student : sms.getAllStudents()) {
            System.out.println(student);
        }
    }

    private void saveAndExit(StudentManagementSystem sms) {
        saveStudentsToFile(sms);
        System.out.println("Student data saved. Exiting...");
        System.exit(0);
    }

    private void saveStudentsToFile(StudentManagementSystem sms) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(sms);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private StudentManagementSystem loadStudentsFromFile() {
        File file = new File(FILE_NAME);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
                return (StudentManagementSystem) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return new StudentManagementSystem();
    }

    public class Student implements Serializable {
        private String name;
        private int rollNumber;
        private String grade;

        public Student(String name, int rollNumber, String grade) {
            this.name = name;
            this.rollNumber = rollNumber;
            this.grade = grade;
        }

        public String getName() {
            return name;
        }

        public int getRollNumber() {
            return rollNumber;
        }

        public String getGrade() {
            return grade;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setRollNumber(int rollNumber) {
            this.rollNumber = rollNumber;
        }

        public void setGrade(String grade) {
            this.grade = grade;
        }

        @Override
        public String toString() {
            return "Name: " + name + ", Roll Number: " + rollNumber + ", Grade: " + grade;
        }
    }

    public class StudentManagementSystem {
        private List<Student> students;

        public StudentManagementSystem() {
            students = new ArrayList<>();
        }

        public void addStudent(Student student) {
            students.add(student);
        }

        public void removeStudent(Student student) {
            students.remove(student);
        }

        public Student searchStudent(int rollNumber) {
            for (Student student : students) {
                if (student.getRollNumber() == rollNumber) {
                    return student;
                }
            }
            return null;
        }

        public List<Student> getAllStudents() {
            return students;
        }
    }
}
