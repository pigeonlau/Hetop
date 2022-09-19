package motp.serializer.test.transfer.server;

/**
 *
 */

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * @author zl
 *
 */
public class Server {
    BufferedWriter bw;
    PrintWriter pw;
    ServerSocket serverSocket;
    DatagramSocket datagramSocket;
    Socket socket;
    private final int tcp_PORT = 2021;

    private Server() throws IOException {
        serverSocket = new ServerSocket(tcp_PORT);
        System.out.println("服务器启动");

    }

    private void initialize() {
        try {
            bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            pw = new PrintWriter(bw, true);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static void main(String[] args) throws IOException {
        new Server().service();
    }

    /**
     * service implements
     */
    public void service() {

        try {
            this.socket = serverSocket.accept();

            initialize();
            String s = "新连接，新地址：" + socket.getInetAddress() + "," + socket.getPort();
            System.out.println(s);
            pw.println(s);
            Thread work = new Thread(new Handler());
           // System.out.println("线程成功创建");
            // 为客户连接创建工作线程
            work.start();
            //System.out.println("线程运行");
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
