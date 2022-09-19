package motp.serializer.test.beans.train;

import java.util.SimpleTimeZone;

/**
 * @program: macaw-v3
 * @description:
 * @author: zlei
 * @create: 2022-02-07 14:45
 **/
public class TestCustomer {
    private String name;
    private int age;
    private String iDNumber;
    private String trainNumber;
    private SimpleTimeZone time;
    private TestCustomerType testCustomerType;

    public TestCustomerType getTestCustomerType() {
        return testCustomerType;
    }

    public void setTestCustomerType(TestCustomerType testCustomerType) {
        this.testCustomerType = testCustomerType;
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

    public String getiDNumber() {
        return iDNumber;
    }

    public void setiDNumber(String iDNumber) {
        this.iDNumber = iDNumber;
    }

    public String getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
    }

    public SimpleTimeZone getTime() {
        return time;
    }

    public void setTime(SimpleTimeZone time) {
        this.time = time;
    }
}
