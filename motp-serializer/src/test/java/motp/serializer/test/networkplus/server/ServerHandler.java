package motp.serializer.test.networkplus.server;

import cn.edu.nwpu.rj416.motp.serializer.motp.builder.MotpBuilder;
import motp.serializer.test.networkplus.common.Commons;

import java.io.BufferedOutputStream;
import java.lang.reflect.Modifier;
import java.net.Socket;


import static motp.serializer.test.networkplus.common.Commons.*;
import static motp.serializer.test.networkplus.common.TestMotpSerializer.getRandomObject;

/**
 * TODO
 *
 * @author pigeonliu
 * @date 2022/11/12 15:16
 * @since motp-test
 */

public class ServerHandler implements Runnable {

    private final Socket socket;


    public ServerHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        try (BufferedOutputStream bs = new BufferedOutputStream(socket.getOutputStream())) {
            for (int i = 0; i < Commons.TOTAL_SERIALIZATION_TIMES; i++) {
                Object view = getRandomObject();
                Class<?> clazzName = view.getClass();
                while (clazzName.isInterface() || Modifier.isAbstract(clazzName.getModifiers())) {
                    view = getRandomObject();
                    clazzName = view.getClass();
                }

                System.out.println();
                System.out.println();

                System.out.println(clazzName);
                System.out.println(view);

                MotpBuilder motpBuilder = new MotpBuilder();
                if (SchemaCache.hasCache(clazzName)) {
                    System.out.println("has Cache");
                    String cacheUuid = SchemaCache.getCacheUuid(view);
                    byte[] dataBytes = motpBuilder.getDataBytesPart(view);
                    System.out.println(cacheUuid);
                    bs.write(cacheUuid.getBytes());
                    bs.write(convertIntToByteArray(dataBytes.length));
                    bs.write(dataBytes);
                    bs.flush();

                } else if (SchemaCache.canCache(view)) {
                    System.out.println("can cache");

                    byte[][] twoParts = motpBuilder.getTwoParts(view);
                    SchemaCache.addCache(view, twoParts[0]);
                    String cacheUuid = SchemaCache.getCacheUuid(view);
                    System.out.println(cacheUuid);

                    bs.write(cacheUuid.getBytes());
                    bs.write(convertIntToByteArray(twoParts[1].length));
                    bs.write(twoParts[1]);
                    bs.flush();
                } else {
                    System.out.println("can't cache");
                    byte[] bytes = motpBuilder.getBytes(view);
                    bs.write(EMPTY_UUID.getBytes());
                    bs.write(convertIntToByteArray(bytes.length));
                    bs.write(bytes);
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
