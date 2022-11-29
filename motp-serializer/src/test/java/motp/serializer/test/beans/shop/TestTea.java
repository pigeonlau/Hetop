package motp.serializer.test.beans.shop;

/**
 * @program: macaw-v3
 * @description:
 * @author: zlei
 * @create: 2022-01-18 21:09
 **/
public class TestTea extends TestDrink {
    TestTaste taste;


    public TestTaste getTaste() {
        return taste;
    }

    public void setTaste(TestTaste taste) {
        this.taste = taste;
    }
}
