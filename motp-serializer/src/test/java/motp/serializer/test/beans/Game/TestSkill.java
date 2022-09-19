package motp.serializer.test.beans.Game;

/**
 * @program: macaw-v3
 * @description:
 * @author: zlei
 * @create: 2022-02-07 15:13
 **/
public class TestSkill {
    private String name;
    private String description;
    private double cd;
    private TestHurtType hurtType;
    private int hurtKey;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getCd() {
        return cd;
    }

    public void setCd(double cd) {
        this.cd = cd;
    }

    public TestHurtType getHurtType() {
        return hurtType;
    }

    public void setHurtType(TestHurtType hurtType) {
        this.hurtType = hurtType;
    }

    public int getHurtKey() {
        return hurtKey;
    }

    public void setHurtKey(int hurtKey) {
        this.hurtKey = hurtKey;
    }
}
