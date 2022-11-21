package motp.serializer.test.networkplus.client;

import cn.edu.nwpu.rj416.motp.serializer.motp.MotpSerializer;
import motp.serializer.test.networkplus.common.Commons;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import static motp.serializer.test.networkplus.common.Commons.*;

/**
 * TODO
 *
 * @author pigeonliu
 * @date 2022/11/15 12:31
 * @since motp-test
 */
public class MotpClient {

    private static Map<String, byte[]> schemaCache;


    public static void main(String[] args) throws IOException {
        schemaCache = new HashMap<>();
        MotpSerializer motpSerializer = MotpSerializer.getInstance();

        try (Socket serverSocket = new Socket(Commons.SERVER_HOST, Commons.TCP_PORT);
             Socket schemaServerSocket = new Socket(Commons.SERVER_HOST, Commons.SCHEMA_SERVICE_TCP_PORT);
             BufferedInputStream bs = new BufferedInputStream(serverSocket.getInputStream());
             BufferedOutputStream query = new BufferedOutputStream(schemaServerSocket.getOutputStream());
             BufferedInputStream resp = new BufferedInputStream(schemaServerSocket.getInputStream())) {

            for (int i = 0; i < Commons.TOTAL_SERIALIZATION_TIMES; i++) {
                System.out.println();
                System.out.println();
                byte[] b32 = readBytesFromInputStream(bs, 32);
                String uuid = new String(b32);

                System.out.println("uuid: " + uuid);
                byte[] schemaBytes;

                if (EMPTY_UUID.equals(uuid)) {
                    System.out.println("empty uuid");
                    schemaBytes = null;
                } else if (schemaCache.containsKey(uuid)) {
                    System.out.println("contains  cache");
                    schemaBytes = schemaCache.get(uuid);

                } else {
                    System.out.println("query schema");
                    query.write(b32);
                    query.flush();
                    byte[] b4 = readBytesFromInputStream(resp, 4);
                    int schemaDataLength = Commons.convertByteArrayToInt(b4);
                    schemaBytes = readBytesFromInputStream(resp, schemaDataLength);
                }


                byte[] b4 = readBytesFromInputStream(bs, 4);
                int motpLength = convertByteArrayToInt(b4);
                byte[] dataBytes = readBytesFromInputStream(bs, motpLength);

                byte[] bytes = mergeBytes(schemaBytes, dataBytes);
                Object o = motpSerializer.deserialize(bytes);
                System.out.println(o.getClass());
                System.out.println(o);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static byte[] mergeBytes(byte[] b1, byte[] b2) {
        if (b1 == null) {
            return b2;
        }
        int l1 = b1.length;
        int l2 = b2.length;
        byte[] b3 = new byte[l1 + l2];
        System.arraycopy(b1, 0, b3, 0, l1);

        System.arraycopy(b2, 0, b3, l1, l2);

        return b3;
    }


}
