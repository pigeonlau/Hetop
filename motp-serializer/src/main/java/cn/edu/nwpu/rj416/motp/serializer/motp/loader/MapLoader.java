package cn.edu.nwpu.rj416.motp.serializer.motp.loader;


import cn.edu.nwpu.rj416.motp.reflectasm.ConstructorAccess;
import cn.edu.nwpu.rj416.type.util.MStringObjectMap;
import cn.edu.nwpu.rj416.util.objects.MByteBuffer;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class MapLoader {
    /**
     * @param buffer
     * @param size
     * @param destType 读取结果类型，可能包含以下情况: </br>
     *                 (1) Map&lt;K,V&gt; / HashMap&lt;K,V&gt 返回HashMap&lt;K,V&gt</br>
     *                 (2) MStringObjectMap 返回MStringObjectMap</br>
     *                 (4) byte[] MOTP二进制原始数据</br>
     *                 (3) CustomObject 返回对象</br>
     * @return
     */
    public static Object readMapData(
            MotpLoader loader,
            MByteBuffer buffer,
            Type destType) {

        int size = buffer.readMVLInt().castToInteger();

        if (destType instanceof ParameterizedType) {
            /*
             * 如果destType是泛型类型，那么只可能是以下情况：
             * (1) Map<K,V> / HashMap<K,V>
             * 所以先判断泛型参数数量，并获取泛型参数类型
             */
            ParameterizedType pt = (ParameterizedType) destType;
            do {
                Type[] typeArgs = pt.getActualTypeArguments();
                if (typeArgs.length != 2) {
                    break;
                }

                if (pt.getRawType() == Map.class
                        || pt.getRawType() == HashMap.class) {
                    return MapLoader.readMapDataAsHashMap(
                            loader,
                            buffer, size, typeArgs[0], typeArgs[1]);
                }
            } while (false);

            MapLoader.skipMapData(loader, buffer, size);
            return null;
        } else if (destType instanceof Class) {
            /*
             * 如果destType是Class类型，那么可能是以下情况：
             * (1) Map / HashMap
             * (2) MStringObjectMap
             * (3) byte[]
             * (4) CustomObject
             */
            Class<?> clazz = (Class<?>) destType;
            if (clazz.isArray()) {
                // (3) byte[]
                Class<?> componentType = clazz.getComponentType();
                if (componentType == byte.class) {
                    int currentPos = buffer.getOffset();
                    MapLoader.skipMapData(loader, buffer, size);
                    byte[] bytes = buffer.readBytes(
                            currentPos,
                            buffer.getOffset() - currentPos);
                    return bytes;
                } else {
                    MapLoader.skipMapData(loader, buffer, size);
                    return null;
                }
            } else if (clazz == Map.class
                    || clazz == HashMap.class) {
                return MapLoader.readMapDataAsHashMap(
                        loader,
                        buffer, size);
            } else if (clazz == MStringObjectMap.class) {
                // (2) MStringObjectMap
                return MapLoader.readMapDataAsMStringObjectMap(loader, buffer, size);
            } else {
                // (4) CustomObject
                return MapLoader.readMapDataAsObject(loader, buffer, size, clazz);
            }
        } else {
            /*
             * 目前应该不会出现这种情况
             */
            MapLoader.skipMapData(loader, buffer, size);
            return null;
        }
    }

    private static HashMap<Object, Object> readMapDataAsHashMap(
            MotpLoader loader,
            MByteBuffer buffer,
            int size,
            Type keyType,
            Type valueType) {
        HashMap<Object, Object> map = new HashMap<>();
        for (int i = 0; i < size; i++) {
            Object key = MotpDataLoader.readData(loader, buffer, keyType);
            Object value = MotpDataLoader.readData(loader, buffer, valueType);
            map.put(key, value);
        }
        return map;
    }

    private static HashMap<Object, Object> readMapDataAsHashMap(
            MotpLoader loader,
            MByteBuffer buffer,
            int size) {
        HashMap<Object, Object> map = new HashMap<>();
        for (int i = 0; i < size; i++) {
            Object key = MotpDataLoader.readData(loader, buffer);
            Object value = MotpDataLoader.readData(loader, buffer);
            map.put(key, value);
        }
        return map;
    }

    private static MStringObjectMap readMapDataAsMStringObjectMap(
            MotpLoader loader,
            MByteBuffer buffer,
            int size) {
        MStringObjectMap map = new MStringObjectMap();
        for (int i = 0; i < size; i++) {
            Object key = MotpDataLoader.readData(loader, buffer);
            Object value = MotpDataLoader.readData(loader, buffer);
            if (key instanceof String) {
                map.put((String) key, value);
            }
        }
        return map;
    }

    private static <T> T readMapDataAsObject(
            MotpLoader loader,
            MByteBuffer buffer,
            int size,
            Class<T> clazz) {
        // asm
        T obj = ConstructorAccess.get(clazz).newInstance();


        MotpLoaderCustomClassCache ccc = loader.getCustomClassCache().get(clazz);
        if (ccc == null) {
            ccc = new MotpLoaderCustomClassCache();
            ccc.buildClass(clazz);
            loader.getCustomClassCache().put(clazz, ccc);
        }

        for (int i = 0; i < size; i++) {
            Object key = MotpDataLoader.readData(loader, buffer);
            if (!(key instanceof String)) {
                MotpDataLoader.skipData(loader, buffer);
                continue;
            }
            String name = (String) key;

            Field field = ccc.getFieldByName(name);
            if (field == null) {
                MotpDataLoader.skipData(loader, buffer);
                continue;
            }

            Object fieldValue = MotpDataLoader.readData(loader, buffer, field.getGenericType());
            if (fieldValue == null) {
                continue;
            }

            try {
                field.set(obj, fieldValue);
            } catch (IllegalArgumentException | IllegalAccessException e) {

            }
        }
        return obj;
    }

    public static void skipMapData(
            MotpLoader loader,
            MByteBuffer buffer,
            int size) {
        for (int i = 0; i < size; i++) {
            MotpDataLoader.readData(loader, buffer);
            MotpDataLoader.readData(loader, buffer);
        }
    }

}
