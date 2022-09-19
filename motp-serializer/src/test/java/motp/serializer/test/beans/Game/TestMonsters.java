package motp.serializer.test.beans.Game;

import java.util.Date;

/**
 * @program: macaw-v3
 * @description:
 * @author: zlei
 * @create: 2022-02-07 15:17
 **/
public class TestMonsters {
   private String name;
   int blood;
   Date flushTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBlood() {
        return blood;
    }

    public void setBlood(int blood) {
        this.blood = blood;
    }

    public Date getFlushTime() {
        return flushTime;
    }

    public void setFlushTime(Date flushTime) {
        this.flushTime = flushTime;
    }
}
