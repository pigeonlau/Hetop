package motp.serializer.test.transfer;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;

/**
 * @program: macaw-v3-zl
 * @description:
 * @author: zlei
 * @create: 2022-04-08 18:01
 **/
public class SendData {



    private static int udp_PORT = 9999;
    private static String host = "127.0.0.1";

    public static void send(byte[] bytes){

        DatagramSocket datagramSocket = null;
        try {
            datagramSocket = new DatagramSocket();
            SocketAddress socketAddress = new InetSocketAddress(host, udp_PORT);
            DatagramPacket dp = new DatagramPacket(new byte[512], 512, socketAddress);

            dp.setData(bytes);
            datagramSocket.send(dp);
            System.err.println("发送成功");
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            datagramSocket.close();
        }


    }

    public static void main(String[] args) {
        SendData.send("你好赵磊".getBytes(StandardCharsets.UTF_8));
        System.out.println(ReceiveData.receive().toString());
    }

}
