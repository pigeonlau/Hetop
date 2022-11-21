package motp.serializer.test.networkplus;


import motp.serializer.test.networkplus.client.MotpClient;
import motp.serializer.test.networkplus.server.MotpSchemaServer;
import motp.serializer.test.networkplus.server.MotpServer;

/**
 * @author pigeonliu
 * @date 2022/11/21 21:27
 */
public class TestStarter {

    public static void main(String[] args) throws Exception {
        new Thread(
                () -> MotpSchemaServer.main(null)
        ).start();

        new Thread(
                () -> MotpServer.main(null)
        ).start();

        Thread.sleep(1000L);

        MotpClient.main(null);
    }

}
