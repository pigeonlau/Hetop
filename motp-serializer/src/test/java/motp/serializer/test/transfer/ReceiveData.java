package motp.serializer.test.transfer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * @program: macaw-v3-zl
 * @description:
 * @author: zlei
 * @create: 2022-04-08 18:01
 **/
public class ReceiveData {
    private static final int udpPort = 9999; // UDP端口

    static DatagramSocket dataSocket;

    public static byte[] receive() {
        byte[] bytes = null;
        try {
//            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            System.out.println(br.readLine()); // 输出服务器返回连接成功的消息
//            br.close();
//            socket.close();
            dataSocket = new DatagramSocket(udpPort);

            DatagramPacket dp = new DatagramPacket(new byte[512], 512);
            dataSocket.receive(dp);

            bytes = dp.getData();
            System.out.println("接收成功");


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            dataSocket.close();
        }

        return bytes;
    }

    public static void main(String[] args) {
        System.out.println(ReceiveData.receive().toString());
    }
}
