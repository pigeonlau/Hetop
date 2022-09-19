package motp.serializer.test.beans.shop;

/**
 * @program: macaw-v3
 * @description:
 * @author: zlei
 * @create: 2022-01-18 20:52
 **/
public class TestJuice extends TestDrink {
    private TestTaste tem;
    public TestJuice(String name, double price, String des, TestTaste tem) {
        super(name, price, des);
        this.tem=tem;
    }

    public TestTaste getTem() {
        return tem;
    }

    public void setTem(TestTaste tem) {
        this.tem = tem;
    }
}
