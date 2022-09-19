package motp.serializer.test.beans.Game;

import java.io.File;

/**
 * @program: macaw-v3
 * @description:
 * @author: zlei
 * @create: 2022-02-07 15:13
 **/
public class TestRole {
    private String name;
    private File description;
    private TestSkill skill1;
    private TestSkill skill2;
    private TestSkill skill3;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public File getDescription() {
        return description;
    }

    public void setDescription(File description) {
        this.description = description;
    }

    public TestSkill getSkill1() {
        return skill1;
    }

    public void setSkill1(TestSkill skill1) {
        this.skill1 = skill1;
    }

    public TestSkill getSkill2() {
        return skill2;
    }

    public void setSkill2(TestSkill skill2) {
        this.skill2 = skill2;
    }

    public TestSkill getSkill3() {
        return skill3;
    }

    public void setSkill3(TestSkill skill3) {
        this.skill3 = skill3;
    }
}
