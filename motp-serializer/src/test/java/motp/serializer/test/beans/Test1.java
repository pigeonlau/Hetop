package motp.serializer.test.beans;

import cn.edu.nwpu.rj416.motp.serializer.motp.MotpSerializer;
import cn.edu.nwpu.rj416.motp.serializer.motp.builder.MotpBuilder;
import cn.edu.nwpu.rj416.type.random.RandomObjectUtil;
import cn.edu.nwpu.rj416.util.time.Stopwatch;
import com.alibaba.fastjson.JSONObject;
import motp.serializer.test.beans.sc.TestSelectCourse;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;

import static motp.serializer.test.networkplus.common.TestMotpSerializer.getRandomObject;

/**
 * @program: macaw-v3-zl
 * @description:
 * @author: zlei
 * @create: 2022-04-13 21:09
 **/
public class Test1 {
    private String name;
    public int index;

//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public int getIndex() {
//        return index;
//    }
//
//    public void setIndex(int index) {
//        this.index = index;
//    }

    private static Object testReturn() {
        return 1;
    }

    public static void main(String[] args) {
        MotpSerializer motpSerializer = MotpSerializer.getInstance();

        TestView testView = RandomObjectUtil.randomObject(TestView.class);
        byte[] bytes = motpSerializer.serialize(testView);

        double sum = 0;
        for (int i = 0; i < 10; i++) {
            System.out.println(i);
            Stopwatch sw = new Stopwatch();
            sw.start();
            motpSerializer.deserialize(bytes, TestView.class);
            sw.stop();
            System.out.println("deserialize " + sw.getMicrosecond());
            System.out.println();
            sum+=sw.getMicrosecond();
        }

        System.out.println(sum);


//        Object obj = RandomObjectUtil.randomObject(TestSelectCourse.class);
//        System.out.println(obj);
//
//        obj = RandomObjectUtil.randomObject(TestBean.class);
//        System.out.println(obj);


    }
}
