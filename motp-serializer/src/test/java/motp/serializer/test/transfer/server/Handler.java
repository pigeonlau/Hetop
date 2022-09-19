package motp.serializer.test.transfer.server;


import cn.edu.nwpu.rj416.motp.serializer.motp.MacawMotpSerializer;
import cn.edu.nwpu.rj416.type.random.RandomObjectUtil;
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

import java.io.IOException;
import java.lang.reflect.Modifier;
import java.net.*;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * @program: macaw-v3-zl
 * @description:
 * @author: zlei
 * @create: 2022-04-07 16:48
 **/
public class Handler implements Runnable {

    private DatagramSocket datagramSocket;
    private final int udp_PORT = 9999;
    private final String host = "127.0.0.1";
    private byte[] bytes;


    public Handler() {
    }


    @Override
    public void run() { // 执行的内容

        //使用udp传输序列化后的文件
        try {
            datagramSocket = new DatagramSocket();
            SocketAddress socketAddress = new InetSocketAddress(host, udp_PORT);
            DatagramPacket dp = new DatagramPacket(new byte[512], 512, socketAddress);
            this.bytes=this.ser();
            dp.setData(bytes);
            datagramSocket.send(dp);
            System.err.println("发送成功");
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            datagramSocket.close();
        }
    }
    private<T> byte[] ser(){
        T view = (T) getRandomObject();
        Class<T> clazzName = (Class<T>) view.getClass();
        while (clazzName.isInterface() || Modifier.isAbstract(clazzName.getModifiers())) {
            view = (T) getRandomObject();
            clazzName = (Class<T>) view.getClass();
        }
        System.err.println(view.getClass());
        System.err.println(view);
       // MMotpSerializer motpSerializer = Macaw.getBean(MacawMotpSerializer.class);
        MacawMotpSerializer motpSerializer=MacawMotpSerializer.getInstance();
        byte[] motpBytes = motpSerializer.serialize(view);
        return motpBytes;
    }
    private static Object getRandomObject() {
        Object obj = null;
        Random random = new Random();

        while (obj == null) {
            int index = random.nextInt(50);
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
                    obj = RandomObjectUtil.randomObject(List.class);
                    break;
                case 48:
                    obj = RandomObjectUtil.randomObject(Set.class);
                    break;
                case 49:
                    obj = RandomObjectUtil.randomObject(Map.class);
                    break;
                default:
                    obj = RandomObjectUtil.randomObject(TestView.class);
            }
        }
        return obj;
    }


}

