package motp.serializer.test.networkplus.common;

import java.io.IOException;
import java.io.InputStream;

/**
 * TODO
 *
 * @author pigeonliu
 * @date 2022/11/12 15:06
 * @since motp-test
 */
public class Commons {
    public static final int TCP_PORT = 8080;

    public static final int SCHEMA_SERVICE_TCP_PORT = 8081;

    public static final int UDP_PORT = 9000;

    public static final int TOTAL_SERIALIZATION_TIMES = 500;

    public static final String SERVER_HOST = "127.0.0.1";

    public static final String CLIENT_HOST = "127.0.0.1";

    public static final String EMPTY_UUID = "11112222333344445555666677778888";

    public static byte[] readBytesFromInputStream(InputStream inputStream,
                                                  int length2) throws IOException {
        int readSize;
        byte[] bytes = null;
        bytes = new byte[length2];

        long temp = length2;
        long index = 0;
        while ((readSize = inputStream.read(bytes, (int) index, (int) temp)) != -1) {
            temp -= readSize;
            if (temp == 0) {
                break;
            }
            index = index + readSize;
        }
        return bytes;
    }

    public static int convertByteArrayToInt(byte[] bytes) {
        return ((bytes[0] & 0xFF) << 24) |
                ((bytes[1] & 0xFF) << 16) |
                ((bytes[2] & 0xFF) << 8) |
                ((bytes[3] & 0xFF));
    }


    public static byte[] convertIntToByteArray(int value) {
        return new byte[]{
                (byte) (value >> 24),
                (byte) (value >> 16),
                (byte) (value >> 8),
                (byte) value};
    }

    public static void main(String[] args) {

    }
}
