package motp.serializer.test.beans.sc;

import java.util.List;

/**
 * @program: macaw-v3
 * @description:
 * @author: zlei
 * @create: 2022-01-18 20:25
 **/
public class TestSelectCourse {
    String courseId;
    List<TestTeacher> teachers;
    List<TestStudent> students;

    public TestSelectCourse(String courseId, List<TestTeacher> teachers, List<TestStudent> students) {
        this.courseId = courseId;

        this.teachers = teachers;
        this.students = students;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public List<TestTeacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<TestTeacher> teachers) {
        this.teachers = teachers;
    }

    public List<TestStudent> getStudents() {
        return students;
    }

    public void setStudents(List<TestStudent> students) {
        this.students = students;
    }

    public boolean addTeacher(TestTeacher teacher) {
        teachers.add(teacher);
        return teachers.contains(teacher);
    }

    public boolean deleteTeacher(String teacherId) {
        for (TestTeacher t : teachers) {
            if (t.getTeacherId().equals(teacherId)) {
                teachers.remove(t);
                return true;
            }
        }
        return false;
    }

    public boolean addStudent(TestStudent student) {
        students.remove(student);
        return students.contains(student);
    }

    public boolean deleteStudent(String studentId) {
        for (TestStudent s : students) {
            if (s.getStudentId().equals(studentId)) {
                students.remove(s);
                return true;
            }
        }
        return false;
    }
}
