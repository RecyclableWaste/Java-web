package cqu.web.database;

import cqu.web.model.Student;

import java.util.ArrayList;
import java.util.List;

public class Database {
    public static List<Student> students = new ArrayList<>();

    static {
        students.add(new Student(2001, "nameA"));
        students.add(new Student(2002, "nameB"));
        students.add(new Student(2003, "nameC"));
    }

    public static Student get(int id) {
        for (Student stu : students) {
            if (stu.id == id) {
                return stu;
            }
        }
        return null;
    }

    public static List<Student> getAll() {
        return students;
    }

    public static boolean add(Student student) {
        if (student == null || student.name.equals("")) {
            return false;
        }
        for (Student stu : students) {
            if (stu.id == student.id) {
                return false;
            }
        }
        students.add(student);
        return true;
    }

    public static boolean delete(int id) {
        for (Student stu : students) {
            if (stu.id == id) {
                students.remove(stu);
                return true;
            }
        }
        return false;
    }
//    public static boolean update()
}
