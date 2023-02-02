package motp.serializer.test.networkplus.server;

import motp.serializer.test.networkplus.common.Commons;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author pigeonliu
 * @date 2022/11/15 10:59
 */
public class MotpSchemaServer {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        try (ServerSocket serverSocket = new ServerSocket(Commons.SCHEMA_SERVICE_TCP_PORT)) {

            while (true) {
                Socket socket = serverSocket.accept();

                System.out.println("schema : 新连接");
                System.out.println("schema: ip: " + socket.getInetAddress() + "  port:  " + socket.getPort());

                executor.submit(new SchemaHandler(socket));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
