package motp.serializer.test.beans.sc;

/**
 * @program: macaw-v3
 * @description:
 * @author: zlei
 * @create: 2022-01-17 20:18
 **/
public abstract class TestUser {
    private String CardId;
    private String name;
    private int age;
    private String phoneNumber;
    private boolean sex;

    public TestUser(String cardId, String name, int age, String phoneNumber, boolean sex) {
        CardId = cardId;
        this.name = name;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.sex = sex;
    }

    public String getCardId() {
        return CardId;
    }

    public void setCardId(String cardId) {
        CardId = cardId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }
}
