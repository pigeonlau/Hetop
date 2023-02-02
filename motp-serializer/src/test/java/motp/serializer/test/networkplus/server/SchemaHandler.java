package motp.serializer.test.networkplus.server;

import motp.serializer.test.networkplus.common.Commons;
import motp.serializer.test.networkplus.common.TestMotpSerializer;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.net.Socket;

import static motp.serializer.test.networkplus.common.Commons.*;

/**
 * @author pigeonliu
 * @date 2022/11/15 11:00
 */
public class SchemaHandler implements Runnable {

    private final Socket socket;


    public SchemaHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        TestMotpSerializer testMotpSerializer = new TestMotpSerializer();

        // 设置序列化总次数

        try (BufferedOutputStream bs = new BufferedOutputStream(socket.getOutputStream());
             BufferedInputStream bi = new BufferedInputStream(socket.getInputStream())) {

            while (socket.isConnected()) {
                byte[] uuidBytes = readBytesFromInputStream(bi, 32);
                String uuid = new String(uuidBytes);
                System.out.println("schema:   请求： " + uuid);
                byte[] schemaData = SchemaCache.getSchemaData(uuid);

                bs.write(convertIntToByteArray(schemaData.length));
                bs.write(schemaData);
                bs.flush();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
