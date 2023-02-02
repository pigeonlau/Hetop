package motp.serializer.test.network.common;


import cn.edu.nwpu.rj416.motp.serializer.motp.MotpSerializer;
import cn.edu.nwpu.rj416.type.random.RandomObjectUtil;
import cn.edu.nwpu.rj416.util.time.Stopwatch;
import com.thoughtworks.xstream.XStream;
import motp.serializer.test.beans.Game.*;
import motp.serializer.test.beans.TestBean;
import motp.serializer.test.beans.TestEnum;
import motp.serializer.test.beans.TestView;
import motp.serializer.test.beans.linkList.TestLinList;
import motp.serializer.test.beans.queue.TestQueue;
import motp.serializer.test.beans.sc.*;
import motp.serializer.test.beans.shop.*;
import motp.serializer.test.beans.stack.TestStack;
import motp.serializer.test.beans.train.*;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

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

    // 传输测试
    // 传输测试中的总序列化次数
    private static int totalTransferSerializationTimes;
    //传输测试中的当前序列化次数
    private static int currentTransferSerializationTimes;

    // 传输测试中的总反序列化次数
    private static int totalTransferDeserializationTimes;
    // 传输测试中的当前反序列化次数
    private static int currentTransferDeserializationTimes;

    // 传输测试中的basic object个数
    private static int totalTransferBasicObjectNumber;
    // 传输测试中的collection object个数
    private static int totalTransferCollectionObjectNumber;
    // 传输测试中的enum object个数
    private static int totalTransferEnumObjectNumber;
    // 传输测试中的composite object个数
    private static int totalTransferCompositeObjectNumber;

    // motp
    // 传输测试中的总序列化时间
    private static double motpTotalTransferSerializationTimeCost;
    // 传输测试中的总序列化后的字节数组大小
    private static long motpTotalTransferSerializationSize;
    // 传输测试中的总反序列化时间
    private static double motpTotalTransferDeserializationTimeCost;


    // XStream
    // 传输测试中的总序列化时间
    private static double XStreamTotalTransferSerializationTimeCost;
    // 传输测试中的总序列化后的字节数组大小
    private static long XStreamTotalTransferSerializationSize;
    // 传输测试中的总反序列化时间
    private static double XStreamTotalTransferDeserializationTimeCost;


    private static double averageTime(double time) {
        String s = String.format("%.6f", time / totalCase);
        double d = Double.parseDouble(s);
        return d;
    }

    public void transferSerializeInitialize(int totalSerializationTimes) {
        totalTransferSerializationTimes = totalSerializationTimes;
        currentTransferSerializationTimes = 0;

        totalTransferBasicObjectNumber = 0;
        totalTransferCollectionObjectNumber = 0;
        totalTransferEnumObjectNumber = 0;
        totalTransferCompositeObjectNumber = 0;

        // motp
        motpTotalTransferSerializationTimeCost = 0;
        motpTotalTransferSerializationSize = 0;

        // XStream
        XStreamTotalTransferSerializationTimeCost = 0;
        XStreamTotalTransferSerializationSize = 0;
    }

    public <T> byte[][] transferSerialize() {

        String[] testArray = new String[0];
//        Macaw.initialize(TestMotpSerializer.class, testArray);

        T view = (T) getRandomObject();
        Class<T> clazzName = (Class<T>) view.getClass();
        while (clazzName.isInterface() || Modifier.isAbstract(clazzName.getModifiers())) {
            view = (T) getRandomObject();
            clazzName = (Class<T>) view.getClass();
        }

        currentTransferSerializationTimes++;

        System.out.println("第" + currentTransferSerializationTimes + "次序列化过程：");
        System.out.println("数据类型为：" + clazzName);

        // 初始化
        // motp
//        MMotpSerializer motpSerializer = Macaw.getBean(MacawMotpSerializer.class);
        MotpSerializer motpSerializer = MotpSerializer.getInstance();

        // XStream
        XStream xStream = new XStream();
        // 存放字节数组的链表
        byte[][] res = new byte[2][];
        // 计时器
        Stopwatch sw = new Stopwatch();

        // motp serialize
        // 开始计时
        sw.start();

        byte[] motpBytes = motpSerializer.serialize(view);

        // 结束计时
        sw.stop();

        // 计算序列化时间
        double motpSerializeTimeCost = sw.getMillisecond();
        // 获取序列化后的字节数组大小
        long motpSize = motpBytes.length;

        motpTotalTransferSerializationTimeCost += motpSerializeTimeCost;
        motpTotalTransferSerializationSize += motpSize;

        res[0] = motpBytes;


        // XStream serialize
        sw.start();

        byte[] XStreamBytes = xStream.toXML(view).getBytes();

        sw.stop();

        // 计算序列化时间
        double XStreamSerializeTimeCost = sw.getMillisecond();
        // 获取序列化后的字节数组大小
        long XStreamSize = XStreamBytes.length;

        XStreamTotalTransferSerializationTimeCost += XStreamSerializeTimeCost;
        XStreamTotalTransferSerializationSize += XStreamSize;

        res[1] = XStreamBytes;


        System.out.println(String.format(
                "序列化结果: [Serialize] time: motp %f ms, XStream %f ms",
                motpSerializeTimeCost, XStreamSerializeTimeCost));

        System.out.println(String.format(
                "序列化结果: [Serialize] size: motp %s byte, XStream %s byte",
                motpSize, XStreamSize));


        if (currentTransferSerializationTimes == totalTransferSerializationTimes) {
            System.out.println("共执行" + totalTransferSerializationTimes + "次序列化");
            System.out.println("basic object: " + totalTransferBasicObjectNumber);
            System.out.println("collection object: " + totalTransferCollectionObjectNumber);
            System.out.println("enum object: " + totalTransferEnumObjectNumber);
            System.out.println("composite object: " + (totalTransferSerializationTimes - totalTransferBasicObjectNumber - totalTransferCollectionObjectNumber - totalTransferEnumObjectNumber));

            System.out.println("序列化平均时间为："
                    + "motp: " + motpTotalTransferSerializationTimeCost / totalTransferSerializationTimes + "ms, "
                    + "XStream: " + XStreamTotalTransferSerializationTimeCost / totalTransferSerializationTimes + "ms");
            System.out.println("序列化后得到的字节数组平均大小为："
                    + "motp: " + motpTotalTransferSerializationSize / totalTransferSerializationTimes + "byte, "
                    + "XStream: " + XStreamTotalTransferSerializationSize / totalTransferSerializationTimes + "byte");


            ;
            String filePath = ClassLoader.getSystemClassLoader().getResource("").getPath() + "SResult.txt";

            try {
                FileWriter fw = new FileWriter(filePath, true);
                BufferedWriter bw = new BufferedWriter(fw);

                String basicObjectNumber =
                        String.valueOf(totalTransferBasicObjectNumber);
                String collectionObjectNumber =
                        String.valueOf(totalTransferCollectionObjectNumber);
                String enumObjectNumber =
                        String.valueOf(totalTransferEnumObjectNumber);
                String compositeObjectNumber =
                        String.valueOf(totalTransferSerializationTimes - totalTransferBasicObjectNumber - totalTransferCollectionObjectNumber - totalTransferEnumObjectNumber);

                String motpAverageSerializationTimeCost =
                        String.valueOf(motpTotalTransferSerializationTimeCost / totalTransferSerializationTimes);
                String XStreamAverageSerializationTimeCost =
                        String.valueOf(XStreamTotalTransferSerializationTimeCost / totalTransferSerializationTimes);

                String motpAverageSize =
                        String.valueOf(motpTotalTransferSerializationSize / totalTransferSerializationTimes);
                String XStreamAverageSize =
                        String.valueOf(XStreamTotalTransferSerializationSize / totalTransferSerializationTimes);

                bw.write(basicObjectNumber + ",");
                bw.write(collectionObjectNumber + ",");
                bw.write(enumObjectNumber + ",");
                bw.write(compositeObjectNumber + ",");

                bw.write(motpAverageSerializationTimeCost + ",");
                bw.write(XStreamAverageSerializationTimeCost + ",");

                bw.write(motpAverageSize + ",");
                bw.write(XStreamAverageSize + "\r\n");

                bw.close();
                fw.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            totalTransferSerializationTimes = 0;
            currentTransferSerializationTimes = 0;

            totalTransferBasicObjectNumber = 0;
            totalTransferCollectionObjectNumber = 0;
            totalTransferEnumObjectNumber = 0;
            totalTransferCompositeObjectNumber = 0;

            // motp
            motpTotalTransferSerializationTimeCost = 0;
            motpTotalTransferSerializationSize = 0;

            // XStream
            XStreamTotalTransferSerializationTimeCost = 0;
            XStreamTotalTransferSerializationSize = 0;
        }

        return res;
    }

    public void transferDeserializeInitialize(int totalDeserializationTimes) {
        totalTransferDeserializationTimes = totalDeserializationTimes;
        currentTransferDeserializationTimes = 0;

        // motp
        motpTotalTransferDeserializationTimeCost = 0;

        // XStream
        XStreamTotalTransferDeserializationTimeCost = 0;
    }

    public void transferDeserialize(byte[][] bytesList) throws Exception {

        String[] testArray = new String[0];
//        Macaw.initialize(TestMotpSerializer.class, testArray);

        currentTransferDeserializationTimes++;

        System.out.println("第" + currentTransferDeserializationTimes + "次反序列化过程：");

        // motp
//        MMotpSerializer motpSerializer = Macaw.getBean(MacawMotpSerializer.class);
        MotpSerializer motpSerializer = MotpSerializer.getInstance();

        // XStream
        XStream xStream = new XStream();
        Stopwatch sw = new Stopwatch();

        // motp
        byte[] motpBytes = bytesList[0];
        // XStream
        byte[] XStreamBytes = bytesList[1];

        // motp deserialize
        // 开始计时
        sw.start();

//        T loadView = (T) motpSerializer.deserialize(motpBytes);
        motpSerializer.deserialize(motpBytes);

        // 结束计时
        sw.stop();

        // 计算反序列化时间
        double motpDeserializeTimeCost = sw.getMillisecond();

        motpTotalTransferDeserializationTimeCost += motpDeserializeTimeCost;


        // XStream deSerialize
        sw.start();

        xStream.fromXML(new String(XStreamBytes));

        sw.stop();

        double XStreamDeserializeTimeCost = sw.getMillisecond();

        XStreamTotalTransferDeserializationTimeCost += XStreamDeserializeTimeCost;


        System.out.printf(
                "反序列化结果: [Deserialize] time: motp %f ms, XStream %f ms%n",
                motpDeserializeTimeCost, XStreamDeserializeTimeCost);

        if (currentTransferDeserializationTimes == totalTransferDeserializationTimes) {

            System.out.println("共执行" + totalTransferDeserializationTimes + "次反序列化");
            System.out.println("反序列化平均时间为："
                    + "motp: " + motpTotalTransferDeserializationTimeCost / totalTransferDeserializationTimes + "ms, "
                    + "XStream: " + XStreamTotalTransferDeserializationTimeCost / totalTransferDeserializationTimes + "ms");


            String filePath = ClassLoader.getSystemClassLoader().getResource("").getPath() + "SResult.txt";

            try {
                FileWriter fw = new FileWriter(filePath, true);
                BufferedWriter bw = new BufferedWriter(fw);

                String motpAverageDeserializationTimeCost =
                        String.valueOf(motpTotalTransferDeserializationTimeCost / totalTransferDeserializationTimes);
                String XStreamAverageDeserializationTimeCost =
                        String.valueOf(XStreamTotalTransferDeserializationTimeCost / totalTransferDeserializationTimes);

                bw.write(motpAverageDeserializationTimeCost + ",");// 往已有的文件上添加字符串
                bw.write(XStreamAverageDeserializationTimeCost + "\r\n");// 往已有的文件上添加字符串

                bw.close();
                fw.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            totalTransferDeserializationTimes = 0;
            currentTransferDeserializationTimes = 0;

            // motp
            motpTotalTransferDeserializationTimeCost = 0;

            // XStream
            XStreamTotalTransferDeserializationTimeCost = 0;
        }
    }


    /*
     *修改测试策略，生成随机类对象进行序列化/反序列化，不再添加自定义类型到TestView中
     */
    private static Object getRandomObject() {
        Object obj = null;
        Random random = new Random();

        while (obj == null) {
            int index = random.nextInt(50);
            switch (index) {
                case 0:
                    obj = RandomObjectUtil.randomObject(int.class);
                    totalTransferBasicObjectNumber++;
                    break;
                case 1:
                    obj = RandomObjectUtil.randomObject(String.class);
                    totalTransferBasicObjectNumber++;
                    break;
                case 2:
                    obj = RandomObjectUtil.randomObject(Boolean.class);
                    totalTransferBasicObjectNumber++;
                    break;
                case 3:
                    obj = RandomObjectUtil.randomObject(boolean.class);
                    totalTransferBasicObjectNumber++;
                    break;
                case 4:
                    obj = RandomObjectUtil.randomObject(Byte.class);
                    totalTransferBasicObjectNumber++;
                    break;
                case 5:
                    obj = RandomObjectUtil.randomObject(byte.class);
                    totalTransferBasicObjectNumber++;
                    break;
                case 6:
                    obj = RandomObjectUtil.randomObject(Short.class);
                    totalTransferBasicObjectNumber++;
                    break;
                case 7:
                    obj = RandomObjectUtil.randomObject(short.class);
                    totalTransferBasicObjectNumber++;
                    break;
                case 8:
                    obj = RandomObjectUtil.randomObject(Integer.class);
                    totalTransferBasicObjectNumber++;
                    break;
                case 9:
                    obj = RandomObjectUtil.randomObject(int.class);
                    totalTransferBasicObjectNumber++;
                    break;
                case 10:
                    obj = RandomObjectUtil.randomObject(Long.class);
                    totalTransferBasicObjectNumber++;
                    break;
                case 11:
                    obj = RandomObjectUtil.randomObject(long.class);
                    totalTransferBasicObjectNumber++;
                    break;
                case 12:
                    obj = RandomObjectUtil.randomObject(Float.class);
                    totalTransferBasicObjectNumber++;
                    break;
                case 13:
                    obj = RandomObjectUtil.randomObject(float.class);
                    totalTransferBasicObjectNumber++;
                    break;
                case 14:
                    obj = RandomObjectUtil.randomObject(Double.class);
                    totalTransferBasicObjectNumber++;
                    break;
                case 15:
                    obj = RandomObjectUtil.randomObject(double.class);
                    totalTransferBasicObjectNumber++;
                    break;
                case 16:
                    obj = RandomObjectUtil.randomObject(Character.class);
                    totalTransferBasicObjectNumber++;
                    break;
                case 17:
                    obj = RandomObjectUtil.randomObject(char.class);
                    totalTransferBasicObjectNumber++;
                    break;
                case 18:
                    obj = RandomObjectUtil.randomObject(int[].class);
                    totalTransferCollectionObjectNumber++;
                    break;
                case 19:
                    obj = RandomObjectUtil.randomObject(TestEnum.class);
                    totalTransferEnumObjectNumber++;
                    break;
                case 20:
                    obj = RandomObjectUtil.randomObject(TestBean.class);
                    totalTransferCompositeObjectNumber++;
                    break;
                case 21:
                    obj = RandomObjectUtil.randomObject(TestTeacher.class);
                    totalTransferCompositeObjectNumber++;
                    break;
                case 22:
                    obj = RandomObjectUtil.randomObject(TestCourse.class);
                    totalTransferCompositeObjectNumber++;
                    break;
                case 23:
                    obj = RandomObjectUtil.randomObject(TestStudent.class);
                    totalTransferCompositeObjectNumber++;
                    break;
                case 24:
                    obj = RandomObjectUtil.randomObject(TestBook.class);
                    totalTransferCompositeObjectNumber++;
                    break;
                case 25:
                    obj = RandomObjectUtil.randomObject(TestSelectCourse.class);
                    totalTransferCompositeObjectNumber++;
                    break;
                case 26:
                    obj = RandomObjectUtil.randomObject(TestOrder1.class);
                    totalTransferCompositeObjectNumber++;
                    break;
                case 27:
                    obj = RandomObjectUtil.randomObject(TestAcademicSystem.class);
                    totalTransferCompositeObjectNumber++;
                    break;
                case 28:
                    obj = RandomObjectUtil.randomObject(TestDrink.class);
                    totalTransferCompositeObjectNumber++;
                    break;
                case 29:
                    obj = RandomObjectUtil.randomObject(TestIceCream.class);
                    totalTransferCompositeObjectNumber++;
                    break;
                case 30:
                    obj = RandomObjectUtil.randomObject(TestJuice.class);
                    totalTransferCompositeObjectNumber++;
                    break;
                case 31:
                    obj = RandomObjectUtil.randomObject(TestOrder.class);
                    totalTransferCompositeObjectNumber++;
                    break;
                case 32:
                    obj = RandomObjectUtil.randomObject(TestTaste.class);
                    totalTransferCompositeObjectNumber++;
                    break;
                case 33:
                    obj = RandomObjectUtil.randomObject(TestTea.class);
                    totalTransferCompositeObjectNumber++;
                    break;
                case 34:
                    obj = RandomObjectUtil.randomObject(TestCustomerType.class);
                    totalTransferCompositeObjectNumber++;
                    break;
                case 35:
                    obj = RandomObjectUtil.randomObject(TestCustomer.class);
                    totalTransferCompositeObjectNumber++;
                    break;
                case 36:
                    obj = RandomObjectUtil.randomObject(TestTrain.class);
                    totalTransferCompositeObjectNumber++;
                    break;
                case 37:
                    obj = RandomObjectUtil.randomObject(TestTrainType.class);
                    totalTransferCompositeObjectNumber++;
                    break;
                case 38:
                    obj = RandomObjectUtil.randomObject(TestHurtType.class);
                    totalTransferCompositeObjectNumber++;
                    break;
                case 39:
                    obj = RandomObjectUtil.randomObject(TestMonsters.class);
                    totalTransferCompositeObjectNumber++;
                    break;
                case 40:
                    obj = RandomObjectUtil.randomObject(TestRole.class);
                    totalTransferCompositeObjectNumber++;
                    break;
                case 41:
                    obj = RandomObjectUtil.randomObject(TestScene.class);
                    totalTransferCompositeObjectNumber++;
                    break;
                case 42:
                    obj = RandomObjectUtil.randomObject(TestSkill.class);
                    totalTransferCompositeObjectNumber++;
                    break;
                case 43:
                    obj = RandomObjectUtil.randomObject(TestScore.class);
                    totalTransferCompositeObjectNumber++;
                    break;
                case 44:
                    obj = RandomObjectUtil.randomObject(TestLinList.class);
                    totalTransferCompositeObjectNumber++;
                    break;
                case 45:
                    obj = RandomObjectUtil.randomObject(TestQueue.class);
                    totalTransferCompositeObjectNumber++;
                    break;
                case 46:
                    obj = RandomObjectUtil.randomObject(TestStack.class);
                    totalTransferCompositeObjectNumber++;
                    break;
                case 47:
                    obj = RandomObjectUtil.randomObject(List.class);
                    totalTransferCollectionObjectNumber++;
                    break;
                case 48:
                    obj = RandomObjectUtil.randomObject(Set.class);
                    totalTransferCollectionObjectNumber++;
                    break;
                case 49:
                    obj = RandomObjectUtil.randomObject(Map.class);
                    totalTransferCollectionObjectNumber++;
                    break;
                default:
                    obj = RandomObjectUtil.randomObject(TestView.class);
            }
        }
        return obj;
    }


}


