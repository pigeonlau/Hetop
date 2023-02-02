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
//    private List<TestCourse> preCourse;
    private List<TestBook> books;



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



    public List<TestBook> getBooks() {
        return books;
    }

    public void setBooks(List<TestBook> books) {
        this.books = books;
    }
}
