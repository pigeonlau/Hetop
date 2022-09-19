package motp.serializer.test.beans.shop;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: macaw-v3
 * @description:
 * @author: zlei
 * @create: 2022-01-18 20:45
 **/
public class TestOrder {
    List<TestDrink> testDrinks;
    double totalPrice;

    public TestOrder() {
        this.testDrinks = new ArrayList<>();
        this.totalPrice = 0;
    }

    public List<TestDrink> getDrinks() {
        return testDrinks;
    }

    public void setDrinks(List<TestDrink> testDrinks) {
        this.testDrinks = testDrinks;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public boolean addDrink(TestDrink testDrink) {
        testDrinks.add(testDrink);
        if (testDrinks.contains(testDrink)) {
            this.totalPrice += testDrink.getPrice();
            return true;
        } else
            return false;
    }

    public boolean delete(TestDrink testDrink) {
        testDrinks.remove(testDrink);
        if (!testDrinks.contains(testDrink)) {
            this.totalPrice -= testDrink.getPrice();
            return true;
        } else
            return false;
    }
}
