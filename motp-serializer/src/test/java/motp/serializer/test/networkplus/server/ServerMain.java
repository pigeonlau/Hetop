package motp.serializer.test.networkplus.server;

/**
 * @author pigeonliu
 * @date 2022/11/15 15:31
 */
public class ServerMain {

    public static void main(String[] args) {
        new Thread(
                () -> MotpSchemaServer.main(null)
        ).start();

        new Thread(
                () -> MotpServer.main(null)
        ).start();
    }
}
