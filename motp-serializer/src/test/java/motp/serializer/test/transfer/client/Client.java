package motp.serializer.test.transfer.client;

/**
 *
 */


import cn.edu.nwpu.rj416.motp.serializer.motp.MotpSerializer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;

/**
 * @author zl
 */
public class Client {

    private static final int tcpPort = 2021; // TCP连接端口
    private static final String host = "127.0.0.1"; // 连接地址

    private static final int udpPort = 9999; // UDP端口
    private Socket socket;

    DatagramSocket dataSocket;


    public static void main(String[] args) throws IOException {
        new Client().receiveData();
    }

    public Client() throws IOException {
        socket = new Socket(host, tcpPort); // 创建客户端套接字

    }


    public <T> void receiveData() {
        String msg = null;
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println(br.readLine()); // 输出服务器返回连接成功的消息
            br.close();
            socket.close();
            dataSocket = new DatagramSocket(udpPort);

            DatagramPacket dp = new DatagramPacket(new byte[512], 512);
            dataSocket.receive(dp);
            //MMotpSerializer motpSerializer = Macaw.getBean(MacawMotpSerializer.class);
            MotpSerializer motpSerializer = MotpSerializer.getInstance();
            byte[] bytes = dp.getData();
            T loadView = (T) motpSerializer.deserialize(bytes);
            System.err.println(loadView.getClass());
            System.err.println(loadView);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            dataSocket.close();
        }

    }


}
