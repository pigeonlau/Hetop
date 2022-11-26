package motp.serializer.test.beans.sc;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: macaw-v3
 * @description:
 * @author: zlei
 * @create: 2022-01-17 21:13
 **/
public class TestCourse {
    private String courseId;
    private String courseName;
    private String courseDes;
    private TestTeacher teacher;
    private List<TestCourse> preCourse;
    private List<TestBook> books;

    public TestCourse(String courseId, String courseName, String courseDes, TestTeacher teacher) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.courseDes = courseDes;
        this.teacher = teacher;
        this.preCourse = new ArrayList<>();
        this.books = new ArrayList<>();
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseDes() {
        return courseDes;
    }

    public void setCourseDes(String courseDes) {
        this.courseDes = courseDes;
    }

    public TestTeacher getTeacher() {
        return teacher;
    }

    public void setTeacher(TestTeacher teacher) {
        this.teacher = teacher;
    }

    public List<TestCourse> getPreCourse() {
        return preCourse;
    }

    public void setPreCourse(List<TestCourse> preCourse) {
        this.preCourse = preCourse;
    }

    public List<TestBook> getBooks() {
        return books;
    }

    public void setBooks(List<TestBook> books) {
        this.books = books;
    }
}
