package motp.serializer.test.network.server;



import motp.serializer.test.network.common.Commons;
import motp.serializer.test.network.common.TestMotpSerializer;

import java.io.BufferedOutputStream;
import java.net.Socket;
import java.util.Arrays;

import static motp.serializer.test.network.common.Commons.*;

/**
 * TODO
 *
 * @author pigeonliu
 * @date 2022/11/12 15:16
 * @since motp-test
 */

public class Handler implements Runnable {

    private final Socket socket;


    public Handler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        TestMotpSerializer testMotpSerializer = new TestMotpSerializer();

        // 设置序列化总次数
        testMotpSerializer.transferSerializeInitialize(Commons.TOTAL_SERIALIZATION_TIMES);

        try (BufferedOutputStream bs = new BufferedOutputStream(socket.getOutputStream())) {
            for (int i = 0; i < Commons.TOTAL_SERIALIZATION_TIMES; i++) {
                byte[][] bytes = testMotpSerializer.transferSerialize();

                // 首先发送motp序列化结果
                byte[] motpBytes = bytes[0];


                bs.write(convertIntToByteArray(motpBytes.length));
                bs.write(motpBytes);

                System.out.println(motpBytes.length);
                System.out.println(Arrays.toString(motpBytes));


//                Thread.sleep(1000L);

                byte[] xStreamBytes = bytes[1];
                bs.write(convertIntToByteArray(xStreamBytes.length));
                bs.write(xStreamBytes);
                System.out.println(xStreamBytes.length);
                System.out.println(Arrays.toString(xStreamBytes));

//                Thread.sleep(1000L);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
