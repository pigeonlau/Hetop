package motp.serializer.test.beans;


import cn.edu.nwpu.rj416.motp.serializer.json.MotpJsonSerializer;
import cn.edu.nwpu.rj416.motp.serializer.motp.MotpSerializer;
import cn.edu.nwpu.rj416.type.FormatUtil;
import cn.edu.nwpu.rj416.type.random.MRandom;
import cn.edu.nwpu.rj416.type.random.RandomObjectUtil;
import cn.edu.nwpu.rj416.util.basic.FileUtil;
import cn.edu.nwpu.rj416.util.time.Stopwatch;
import cn.edu.nwpu.rj416.util.types.StringUtil;
import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;
import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import motp.serializer.test.beans.Game.*;
import motp.serializer.test.beans.linkList.TestLinList;
import motp.serializer.test.beans.queue.TestQueue;
import motp.serializer.test.beans.sc.*;
import motp.serializer.test.beans.shop.*;
import motp.serializer.test.beans.stack.TestStack;
import motp.serializer.test.beans.train.*;


import java.io.File;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.*;

public class TestMotpSerializer {
    private static int totalCase = 0;
    private static double motpSeTime = 0;
    private static double motpDeSerTime = 0;
    private static double jsonSeTime = 0;
    private static double jsonDeSerTime = 0;
    private static double fastJsonSeTime = 0;
    private static double fastJsonDeSerTime = 0;
    private static double protoStuffSeTime = 0;
    private static double protoStuffDeSerTime = 0;
    private static double gsonSeTime = 0;
    private static double gsonDeSerTime = 0;
    private static double xStreamSeTime = 0;
    private static double xStreamDeSerTime = 0;

    public static void main(String[] args) throws Exception {

        // test();
        for (int i = 0; i < 1000; i++) {
            test();
        }

//		for (int i = 0; i < 1000; i++) {
//			testPrimitive();
//			testBigDecimal();
//			testDate();
//		}
        System.out.println("totalCase:" + totalCase);
        System.out.println("totalTime: [Serialize  ] motp " + motpSeTime + " ms, json " + jsonSeTime + " ms, fastjson " + fastJsonSeTime +
                " ms, protostuff " + protoStuffSeTime + " ms, Gson " + gsonSeTime + " ms, XStream " + xStreamSeTime + " ms");
        System.out.println("totalTime: [DeSerialize] motp " + motpDeSerTime + " ms, json " + jsonDeSerTime + " ms, fastjson " + fastJsonDeSerTime +
                " ms, protostuff " + protoStuffDeSerTime + " ms, Gson " + gsonDeSerTime + " ms, XStream " + xStreamDeSerTime + " ms");

        System.out.println("averageTime: [Serialize  ] motp " + averageTime(motpSeTime) + " ms, json " + averageTime(jsonSeTime) + " ms, fastjson " + averageTime(fastJsonSeTime)
                + " ms, protostuff " + averageTime(protoStuffSeTime) + " ms, Gson " + averageTime(gsonSeTime) + " ms, XStream " + averageTime(xStreamSeTime) + " ms");

        System.out.println("averageTime: [DeSerialize] motp " + averageTime(motpDeSerTime) + " ms, json " + averageTime(jsonDeSerTime) + " ms, fastjson " + averageTime(fastJsonDeSerTime
        ) + " ms, protostuff " + averageTime(protoStuffDeSerTime) + " ms, Gson " + averageTime(gsonDeSerTime) + " ms, XStream " + averageTime(xStreamDeSerTime) + " ms");
        //write
    }


    public static Object getTest1() {
        return RandomObjectUtil.randomObject(Test1.class);
    }

    private static <T> void test() throws IOException {

        String testName = Long.toString(System.currentTimeMillis());
        T view = (T) getRandomObject();
//        view = (T) getTest1();
        Class<T> clazzName = (Class<T>) view.getClass();
        while (clazzName.isInterface() || Modifier.isAbstract(clazzName.getModifiers())) {
//            if (clazzName.isArray()) {
//                break;
//            }
            view = (T) getRandomObject();
            clazzName = (Class<T>) view.getClass();
        }

        System.out.println((totalCase) + " " + clazzName);
        //FormatUtil.print(view);

        String ori = FormatUtil.toString(
                FormatUtil.format(view, 0), "    ", "\r\n");

        MotpSerializer motpSerializer = MotpSerializer.getInstance();

        MotpJsonSerializer jsonSerializer = MotpJsonSerializer.getInstance();

        Stopwatch sw = new Stopwatch();
        totalCase++;
        //motp serializer
        sw.start();
        byte[] motpBytes = motpSerializer.serialize(view);
        sw.stop();
        double motpTimeCost = sw.getMillisecond();
        long motpSize = motpBytes.length;
        motpSeTime += motpTimeCost;
//        send(motpBytes);
//        System.err.println("发送成功");

        //json serializer
        sw.start();
        byte[] jsonBytes = jsonSerializer.serialize(view);
        sw.stop();
        double jsonTimeCost = sw.getMillisecond();
        long jsonSize = jsonBytes.length;
        jsonSeTime += jsonTimeCost;

        /*
         * 在pom.xml中添加protostuff和fastjson的dependencies
         * 再在此处使用fastJson和protoStuff对随机创建出的view进行序列化和反序列化，
         * 计算其时间，并将其写入到本地txt文档中
         *  */
        //fastJson Serializer
        sw.start();
        byte[] fastJsonBytes = JSON.toJSONBytes(view);
        sw.stop();
        double fastJsonTimeCost = sw.getMillisecond();
        long fastJsonSize = fastJsonBytes.length;
        fastJsonSeTime += fastJsonTimeCost;

        //protoStuff Serializer
        sw.start();
        Schema<T> schema = RuntimeSchema.getSchema(clazzName);
        @SuppressWarnings("unchecked")
        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        //Class<T> clazz = (Class<T>) view.getClass();
        byte[] protostuff = ProtostuffIOUtil.toByteArray(view, schema, buffer);
        buffer.clear();

//        LinkedBuffer buffer = LinkedBuffer.allocate(512);
//        Schema<TestView> schema = RuntimeSchema.getSchema(TestView.class);
//        //Schema<className> schema = RuntimeSchema.getSchema(TestView.class);
//        byte[] protostuff = ProtostuffIOUtil.toByteArray(view, schema, buffer);
//        buffer.clear();
        sw.stop();
        double protoStuffSerCost = sw.getMillisecond();
        long protoStuffSize = protostuff.length;
        protoStuffSeTime += protoStuffSerCost;

        //Gson serializer
        sw.start();
        Gson gson = new Gson();
        byte[] gsonBytes = gson.toJson(view).getBytes();
        //System.out.println(gsonBytes);
        sw.stop();
        double gsonSerCost = sw.getMillisecond();
        long GsonSize = gsonBytes.length;
        gsonSeTime += gsonSerCost;

        //XStream
        sw.start();
        XStream xStream = new XStream();
        byte[] xStreamBytes = xStream.toXML(view).getBytes();
        sw.stop();
        double xStreamSerCost = sw.getMillisecond();
        long xStreamSize = xStreamBytes.length;
        xStreamSeTime += xStreamSerCost;


        //motp deserialize
        sw.start();
        T loadView = (T) motpSerializer.deserialize(motpBytes, clazzName);
//        Object deserialize = motpSerializer.deserialize(motpBytes);

//        System.out.println(ori);
//        System.out.println(FormatUtil.toString(
//                FormatUtil.format(loadView, 0), "    ", "\r\n"));
        sw.stop();
        double motpDeserializeCost = sw.getMillisecond();
        motpDeSerTime += motpDeserializeCost;


        //jackson deserialize
        sw.start();
        jsonSerializer.deserialize(jsonBytes, clazzName);
        sw.stop();
        double jsonDeserializeCost = sw.getMillisecond();
        jsonDeSerTime += jsonDeserializeCost;

        //add
        //fastJson deSerializer
        sw.start();
        JSON.parseObject(fastJsonBytes, clazzName);
        sw.stop();
        double fastJsonDeserCost = sw.getMillisecond();
        fastJsonDeSerTime += fastJsonDeserCost;

        //protoStuff deSerializer
        sw.start();
        schema = RuntimeSchema.getSchema(clazzName);
        T viewParsed = schema.newMessage();
        ProtostuffIOUtil.mergeFrom(protostuff, viewParsed, schema);
        sw.stop();
        double protoStuffDeSerCost = sw.getMillisecond();
        protoStuffDeSerTime += protoStuffDeSerCost;

        //Gson deSerializer
        sw.start();
        gson.fromJson(new String(gsonBytes), clazzName);
        sw.stop();
        double gsonDeSerCost = sw.getMillisecond();
        gsonDeSerTime += gsonDeSerCost;

        //XStream deSerializer
        sw.start();
        xStream.fromXML(new String(xStreamBytes));
        sw.stop();
        double xStreamDerCost = sw.getMillisecond();
        xStreamDeSerTime += xStreamDerCost;


        //System.out.println(view.getClass());
        System.out.println(String.format(
                "PASS: [Serialize  ]motp %f ms, json %f ms, fastJson %f ms, protoStuff %f ms,Gson %f ms ,XStream %f ms",
                motpTimeCost, jsonTimeCost, fastJsonTimeCost, protoStuffSerCost, gsonSerCost, xStreamSerCost));
        System.out.println(String.format(
                "PASS: [Deserialize]motp %f ms, json %f ms, fastJson %f ms, protoStuff %f ms,Gson %f ms,XStream %f ms",
                motpDeserializeCost, jsonDeserializeCost, fastJsonDeserCost, protoStuffDeSerCost, gsonDeSerCost, xStreamDerCost));
        System.out.println(String.format(
                "PASS: [Size       ]motp %s byte, json %s byte, fastJson %s byte, protoStuff %s byte,Gson %s byte,XStream %s byte",
                String.valueOf(motpSize), String.valueOf(jsonSize), String.valueOf(fastJsonSize), String.valueOf(protoStuffSize), String.valueOf(GsonSize), String.valueOf(xStreamSize)));



    }

    /*
     *修改测试策略，生成随机类对象进行序列化/反序列化，不再添加自定义类型到TestView中
     */
    private static Object getRandomObject() {
        Object obj = null;
        Random random = new Random();

        while (obj == null) {
            int index = random.nextInt(53) + 1;
            index = 20;
            switch (index) {
                case 1:
                    obj = RandomObjectUtil.randomObject(String.class);
                    break;
                case 2:
                    obj = RandomObjectUtil.randomObject(Boolean.class);
                    break;
                case 3:
                    obj = RandomObjectUtil.randomObject(boolean.class);
                    break;
                case 4:
                    obj = RandomObjectUtil.randomObject(Byte.class);
                    break;
                case 5:
                    obj = RandomObjectUtil.randomObject(byte.class);
                    break;
                case 6:
                    obj = RandomObjectUtil.randomObject(Short.class);
                    break;
                case 7:
                    obj = RandomObjectUtil.randomObject(short.class);
                    break;
                case 8:
                    obj = RandomObjectUtil.randomObject(Integer.class);
                    break;
                case 9:
                    obj = RandomObjectUtil.randomObject(int.class);
                    break;
                case 10:
                    obj = RandomObjectUtil.randomObject(Long.class);
                    break;
                case 11:
                    obj = RandomObjectUtil.randomObject(long.class);
                    break;
                case 12:
                    obj = RandomObjectUtil.randomObject(Float.class);
                    break;
                case 13:
                    obj = RandomObjectUtil.randomObject(float.class);
                    break;
                case 14:
                    obj = RandomObjectUtil.randomObject(Double.class);
                    break;
                case 15:
                    obj = RandomObjectUtil.randomObject(double.class);
                    break;
                case 16:
                    obj = RandomObjectUtil.randomObject(Character.class);
                    break;
                case 17:
                    obj = RandomObjectUtil.randomObject(char.class);
                    break;
                case 18:
                    obj = RandomObjectUtil.randomObject(int[].class);
                    break;
                case 19:
                    obj = RandomObjectUtil.randomObject(TestEnum.class);
                    break;
                case 20:
                    obj = RandomObjectUtil.randomObject(TestBean.class);
                    break;
                case 21:
                    obj = RandomObjectUtil.randomObject(TestTeacher.class);
                    break;
                case 22:
                    obj = RandomObjectUtil.randomObject(TestCourse.class);
                    break;
                case 23:
                    obj = RandomObjectUtil.randomObject(TestStudent.class);
                    break;
                case 24:
                    obj = RandomObjectUtil.randomObject(TestBook.class);
                    break;
                case 25:
                    obj = RandomObjectUtil.randomObject(TestSelectCourse.class);
                    break;
                case 26:
                    obj = RandomObjectUtil.randomObject(TestOrder1.class);
                    break;
                case 27:
                    obj = RandomObjectUtil.randomObject(TestAcademicSystem.class);
                    break;
                case 28:
                    obj = RandomObjectUtil.randomObject(TestDrink.class);
                    break;
                case 29:
                    obj = RandomObjectUtil.randomObject(TestIceCream.class);
                    break;
                case 30:
                    obj = RandomObjectUtil.randomObject(TestJuice.class);
                    break;
                case 31:
                    obj = RandomObjectUtil.randomObject(TestOrder.class);
                    break;
                case 32:
                    obj = RandomObjectUtil.randomObject(TestTaste.class);
                    break;
                case 33:
                    obj = RandomObjectUtil.randomObject(TestTea.class);
                    break;
                case 34:
                    obj = RandomObjectUtil.randomObject(TestCustomerType.class);
                    break;
                case 35:
                    obj = RandomObjectUtil.randomObject(TestCustomer.class);
                    break;
                case 36:
                    obj = RandomObjectUtil.randomObject(TestTrain.class);
                    break;
                case 37:
                    obj = RandomObjectUtil.randomObject(TestTrainType.class);
                    break;
                case 38:
                    obj = RandomObjectUtil.randomObject(TestHurtType.class);
                    break;
                case 39:
                    obj = RandomObjectUtil.randomObject(TestMonsters.class);
                    break;
                case 40:
                    obj = RandomObjectUtil.randomObject(TestRole.class);
                    break;
                case 41:
                    obj = RandomObjectUtil.randomObject(TestScene.class);
                    break;
                case 42:
                    obj = RandomObjectUtil.randomObject(TestSkill.class);
                    break;
                case 43:
                    obj = RandomObjectUtil.randomObject(TestScore.class);
                    break;
                case 44:
                    obj = RandomObjectUtil.randomObject(TestLinList.class);
                    break;
                case 45:
                    obj = RandomObjectUtil.randomObject(TestQueue.class);
                    break;
                case 46:
                    obj = RandomObjectUtil.randomObject(TestStack.class);
                    break;
                case 47:
//                    obj = RandomObjectUtil.randomObject(List.class);
                    obj = RandomObjectUtil.randomArrayList(Integer.class, 100);
                    break;
                case 48:
                    obj = RandomObjectUtil.randomObject(Set.class);
                    break;
                case 49:
//                    obj = RandomObjectUtil.randomObject(Map.class);
                    obj = RandomObjectUtil.randomHashMap(String.class, Integer.class, 100);
                    break;
                case 50:
                    obj = RandomObjectUtil.randomArrayList(String.class, 100);
                    break;
                case 51:
                    obj = RandomObjectUtil.randomArrayList(TestView.class, 100);
                    break;
                case 52:
                    obj = RandomObjectUtil.randomArrayList(TestBean.class, 1000);
                    break;
                case 53:
                    obj = RandomObjectUtil.randomObject(Test1.class);
                    break;
                default:
                    obj = RandomObjectUtil.randomObject(TestView.class);
            }
        }
        return obj;
    }


    private static void testPrimitive() {
        MRandom mr = new MRandom();
        MotpSerializer motpSerializer = MotpSerializer.getInstance();
        byte[] buffer = null;

        byte byte1 = mr.nextByte();
        buffer = motpSerializer.serialize(byte1);
        byte byte2 = (byte) motpSerializer.deserialize(buffer, Byte.class);
        if (byte1 != byte2) {
            System.err.println("FAIL for byte");
        }

        short short1 = mr.nextShort();
        buffer = motpSerializer.serialize(short1);
        short short2 = (short) motpSerializer.deserialize(buffer, Short.class);
        if (short1 != short2) {
            System.err.println("FAIL for short");
        }

        int int1 = mr.nextInt();
        buffer = motpSerializer.serialize(int1);
        int int2 = (int) motpSerializer.deserialize(buffer, Integer.class);
        if (int1 != int2) {
            System.err.println("FAIL for int");
        }

        long long1 = mr.nextLong();
        buffer = motpSerializer.serialize(long1);
        long long2 = (long) motpSerializer.deserialize(buffer, Long.class);
        if (long1 != long2) {
            System.err.println("FAIL for long");
        }

        float float1 = mr.nextFloat();
        buffer = motpSerializer.serialize(float1);
        float float2 = (float) motpSerializer.deserialize(buffer, Float.class);
        if (float1 != float2) {
            System.err.println("FAIL for float");
        }

        double double1 = mr.nextDouble();
        buffer = motpSerializer.serialize(double1);
        double double2 = (double) motpSerializer.deserialize(buffer, Double.class);
        if (double1 != double2) {
            System.err.println("FAIL for double");
        }

        boolean boolean1 = mr.nextBoolean();
        buffer = motpSerializer.serialize(boolean1);
        boolean boolean2 = (boolean) motpSerializer.deserialize(buffer, Boolean.class);
        if (boolean1 != boolean2) {
            System.err.println("FAIL for boolean");
        }


        char char1 = mr.nextChar();
        buffer = motpSerializer.serialize(char1);
        char char2 = (char) motpSerializer.deserialize(buffer, Character.class);
        if (char1 != char2) {
            System.err.println("FAIL for char");
        }

        System.out.println("FINISH for primitive type");
    }

    private static void testBigDecimal() {
        MRandom mr = new MRandom();
        MotpSerializer motpSerializer = MotpSerializer.getInstance();
        BigDecimal bd1 = mr.nextBigDecimal();
        byte[] buffer = motpSerializer.serialize(bd1);
        BigDecimal bd2 = (BigDecimal) motpSerializer.deserialize(buffer, BigDecimal.class);
        if (bd1.compareTo(bd2) != 0) {
            System.err.println("FAIL for BigDecimal");
        } else {
            System.out.println("SUCCESS for BigDecimal");
        }
    }

    private static void testDate() {
        MRandom mr = new MRandom();
        MotpSerializer motpSerializer = MotpSerializer.getInstance();
        Date bd1 = mr.nextDate();
        byte[] buffer = motpSerializer.serialize(bd1);
        Date bd2 = (Date) motpSerializer.deserialize(buffer, Date.class);
        if (bd1.compareTo(bd2) != 0) {
            System.err.println("FAIL for Date");
        } else {
            System.out.println("SUCCESS for Date");
        }
    }

    private static double averageTime(double time) {
        String s = String.format("%.6f", time / totalCase);
        double d = Double.parseDouble(s);
        return d;
    }
}
