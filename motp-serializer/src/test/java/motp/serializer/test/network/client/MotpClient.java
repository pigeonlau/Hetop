package motp.serializer.test.network.client;



import motp.serializer.test.network.common.Commons;
import motp.serializer.test.network.common.TestMotpSerializer;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;

import static motp.serializer.test.network.common.Commons.*;

/**
 * TODO
 *
 * @author pigeonliu
 * @date 2022/11/12 15:58
 * @since motp-test
 */
public class MotpClient {


    public static void main(String[] args) throws IOException {
        Socket socket = new Socket(Commons.SERVER_HOST, Commons.TCP_PORT);
        TestMotpSerializer testMotpSerializer = new TestMotpSerializer();

        testMotpSerializer.transferDeserializeInitialize(Commons.TOTAL_SERIALIZATION_TIMES);

        try (BufferedInputStream bs = new BufferedInputStream(socket.getInputStream())) {

            for (int i = 0; i < Commons.TOTAL_SERIALIZATION_TIMES; i++) {
                byte[][] res = new byte[2][];

                byte[] b4 = readBytesFromInputStream(bs, 4);
                int motpLength = convertByteArrayToInt(b4);
                System.out.println(motpLength);
                res[0] = readBytesFromInputStream(bs, motpLength);

                System.out.println(Arrays.toString(res[0]));

                b4 = readBytesFromInputStream(bs, 4);
                int xStreamLength = convertByteArrayToInt(b4);
                System.out.println(xStreamLength);
                res[1] = readBytesFromInputStream(bs, xStreamLength);

                System.out.println(Arrays.toString(res[1]));


                testMotpSerializer.transferDeserialize(res);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            socket.close();
        }

    }
}
