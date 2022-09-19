package motp.serializer.test.beans.sc;

import java.util.ArrayList;
import java.util.List;
/**
 * @program: macaw-v3
 * @description:
 * @author: zlei
 * @create: 2022-01-17 21:08
 **/
public class TestStudent extends TestUser {
    private String studentId;
    private List<TestCourse> courses;

    public TestStudent(String cardId, String name, int age, String phoneNumber,boolean sex, String studentId) {
        super(cardId, name, age, phoneNumber,sex);
        this.studentId = studentId;
        this.courses=new ArrayList<>();
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public List<TestCourse> getCourses() {
        return courses;
    }

    public void setCourses(List<TestCourse> courses) {
        this.courses = courses;
    }
}
