package motp.serializer.test.beans.Game;

/**
 * @program: macaw-v3
 * @description:
 * @author: zlei
 * @create: 2022-02-14 10:52
 **/
public class TestScore {
    private int totalHurt;
    private int totalInjury;
    private double attendRate;
    private double scorer;

    public int getTotalHurt() {
        return totalHurt;
    }

    public void setTotalHurt(int totalHurt) {
        this.totalHurt = totalHurt;
    }

    public int getTotalInjury() {
        return totalInjury;
    }

    public void setTotalInjury(int totalInjury) {
        this.totalInjury = totalInjury;
    }

    public double getAttendRate() {
        return attendRate;
    }

    public void setAttendRate(double attendRate) {
        this.attendRate = attendRate;
    }

    public double getScorer() {
        return scorer;
    }

    public void setScorer(double scorer) {
        this.scorer = scorer;
    }
}
