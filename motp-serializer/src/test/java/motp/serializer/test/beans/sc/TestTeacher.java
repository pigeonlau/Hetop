package motp.serializer.test.beans.sc;

/**
 * @program: macaw-v3
 * @description:
 * @author: zlei
 * @create: 2022-01-17 21:11
 **/
public class TestTeacher extends TestUser {
    private String teacherId;

    public TestTeacher(String teacherId, String cardId, int age, String name, String phoneNumber,boolean sex) {
        super(cardId, name, age, phoneNumber,sex);
        this.teacherId = teacherId;

    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }


}
