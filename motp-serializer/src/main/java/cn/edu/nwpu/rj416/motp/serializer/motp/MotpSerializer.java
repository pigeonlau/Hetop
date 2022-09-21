package cn.edu.nwpu.rj416.motp.serializer.motp;

import cn.edu.nwpu.rj416.motp.serializer.motp.builder.MotpBuilder;
import cn.edu.nwpu.rj416.motp.serializer.motp.loader.MotpLoader;
import cn.edu.nwpu.rj416.type.util.ObjectUtil;

import java.lang.reflect.Type;

/**
 * @author MilesLiu
 * <p>
 * 2019年10月28日 下午2:56:07
 */

public class MotpSerializer {

    private MotpSerializer() {
    }

    private static final MotpSerializer INSTANCE = new MotpSerializer();

    public static MotpSerializer getInstance() {
        return INSTANCE;
    }


    public byte[] serialize(Object o) throws MSerializeException {
        MotpBuilder builder = new MotpBuilder();
        return builder.getBytes(o);
    }


    public Object deserialize(byte[] buffer, Type type) throws MSerializeException {
        if (type == null) {
            return null;
        }
        if (buffer == null || buffer.length == 0) {
            return ObjectUtil.createObjectByType(type);
        }
        MotpLoader loader = new MotpLoader();
        Object rst = loader.loadBytes(buffer, type);
        return rst;
    }


    public Object deserialize(byte[] buffer) throws MSerializeException {
        if (buffer == null || buffer.length == 0) {
            return null;
        }
        MotpLoader loader = new MotpLoader();
        Object rst = loader.loadBytes(buffer);
        return rst;
    }

}
