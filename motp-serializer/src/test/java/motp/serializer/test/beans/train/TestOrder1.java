package motp.serializer.test.beans.train;

import java.util.Date;

/**
 * @program: macaw-v3
 * @description:
 * @author: zlei
 * @create: 2022-02-07 14:59
 **/
public class TestOrder1 {
    private String userId;
    private String trainOrder;
    private Date time;
    private double price;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTrainOrder() {
        return trainOrder;
    }

    public void setTrainOrder(String trainOrder) {
        this.trainOrder = trainOrder;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
