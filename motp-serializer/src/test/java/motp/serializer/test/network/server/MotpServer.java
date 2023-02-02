package motp.serializer.test.network.server;

import motp.serializer.test.network.common.Commons;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * TODO
 *
 * @author pigeonliu
 * @date 2022/11/12 15:08
 * @since motp-test
 */
public class MotpServer {


    public static void main(String[] args) {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        try (ServerSocket serverSocket = new ServerSocket(Commons.TCP_PORT)) {

            while (true) {
                Socket socket = serverSocket.accept();

                System.out.println("新连接");
                System.out.println("ip: " + socket.getInetAddress() + "  port:  " + socket.getPort());

                executor.submit(new Handler(socket));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
