package motp.serializer.test.beans.train;

import java.util.Date;

/**
 * @program: macaw-v3
 * @description:
 * @author: zlei
 * @create: 2022-02-07 14:50
 **/
public class TestTrain {
    private String trainNumber;
    private Date date;
    private TestPlace startPlace;
    private TestPlace endPlace;
    private int costTime;
    private TestTrainType testTrainType;

    public TestTrainType getTestTrainType() {
        return testTrainType;
    }

    public void setTestTrainType(TestTrainType testTrainType) {
        this.testTrainType = testTrainType;
    }

    public String getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }



    public TestPlace getStartPlace() {
        return startPlace;
    }

    public void setStartPlace(TestPlace startPlace) {
        this.startPlace = startPlace;
    }

    public TestPlace getEndPlace() {
        return endPlace;
    }

    public void setEndPlace(TestPlace endPlace) {
        this.endPlace = endPlace;
    }

    public int getCostTime() {
        return costTime;
    }

    public void setCostTime(int costTime) {
        this.costTime = costTime;
    }
}
