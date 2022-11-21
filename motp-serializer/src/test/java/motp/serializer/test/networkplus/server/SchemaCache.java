package motp.serializer.test.networkplus.server;

import cn.edu.nwpu.rj416.motp.serializer.motp.util.MotpProcesserMapping;
import cn.edu.nwpu.rj416.util.astype.AsType;

import java.io.File;
import java.util.*;

/**
 * @author pigeonliu
 * @date 2022/11/15 11:09
 */
public class SchemaCache {
    private static Map<Class<?>, String> uuidCache= new HashMap<>();

    private static Map<String, byte[]> schemaDataCache = new HashMap<>();

    /**
     * 判定一个类的对象是否 普通对象， 即此类的所有对象拥有相同的schema
     *
     * @param o
     * @return
     */
    public static boolean canCache(Object o) {
        Class<?> clazz = o.getClass();
        if (clazz == null) {
            return false;
        }

        return clazz != Object.class
                && !AsType.class.isAssignableFrom(clazz)
                && MotpProcesserMapping.getProcesser(clazz) == null
                && !clazz.isArray()
                && !clazz.isEnum()
                && !List.class.isAssignableFrom(clazz)
                && !Map.class.isAssignableFrom(clazz)
                && !Set.class.isAssignableFrom(clazz)
                && !File.class.isAssignableFrom(clazz);
    }

    public static String getCacheUuid(Object o) {
        return uuidCache.get(o.getClass());
    }

    public static byte[] getSchemaData(String uuid) {
        return schemaDataCache.get(uuid);
    }

    public static byte[] getSchemaData(Class<?> clazz) {
        return schemaDataCache.get(uuidCache.get(clazz));
    }

    public static byte[] getSchemaData(Object o) {
        return schemaDataCache.get(uuidCache.get(o.getClass()));
    }

    public static boolean hasCache(Object o) {
        return uuidCache.containsKey(o.getClass());
    }

    public static boolean hasCache(Class<?> clazz) {
        return uuidCache.containsKey(clazz);
    }

    public static boolean addCache(Object o, byte[] schemaData) {
        if (!canCache(o) || hasCache(o)) {
            return false;
        }

        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        uuidCache.put(o.getClass(), uuid);
        schemaDataCache.put(uuid, schemaData);
        return true;
    }


}
