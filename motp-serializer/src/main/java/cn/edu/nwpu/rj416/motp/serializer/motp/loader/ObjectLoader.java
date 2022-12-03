package cn.edu.nwpu.rj416.motp.serializer.motp.loader;


import cn.edu.nwpu.rj416.motp.reflectasm.ConstructorAccess;
import cn.edu.nwpu.rj416.motp.serializer.motp.schema.AbstractSchema;
import cn.edu.nwpu.rj416.motp.serializer.motp.schema.ObjectSchema;

import cn.edu.nwpu.rj416.type.util.FieldTypeUtil;
import cn.edu.nwpu.rj416.type.util.MStringObjectMap;
import cn.edu.nwpu.rj416.util.objects.MByteBuffer;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class ObjectLoader {
    /**
     * 从MByteBuffer中读取一个Object的值
     *
     * @param buffer   MByteBuffer
     * @param destType 想要转换成的对象类型
     * @return 读取的对象，可能包含以下情况：</br>
     * <p>
     * Map&lt;String, Object&gt;
     * HashMap&lt;String, Object&gt;
     * MStringObjectMap
     * CustomObject
     * byte[]  对象的原始Content
     */
    public static Object readObjectData(
            MotpLoader loader,
            MByteBuffer buffer,
            Type destType) {

        int schemaNumber = buffer.readMVLInt().castToInteger();

        // 对象的datalen  使用定长4字节
        int dataLen = buffer.readInt();

        AbstractSchema schema = loader.getSchema().get(schemaNumber);
        if (!(schema instanceof ObjectSchema)) {
            MotpDataLoader.readDataError("错误的SchemaNumber:%d", schemaNumber);
        }
        ObjectSchema objectSchema = (ObjectSchema) schema;
//        Class<?> oriType = null;
//        if (StringUtil.isNotEmpty(objectSchema.getTypeName())) {
//            try {
//                oriType = Class.forName(objectSchema.getTypeName());
//            } catch (ClassNotFoundException e) {
//
//            }
//        }
//        if (oriType != null) {
//            Object rst = ObjectLoader.readObjectDataAsObject(
//                    loader,
//                    buffer, objectSchema, dataLen, oriType);
//
//            // 快速判断
//            if (Objects.equals(oriType, destType)) {
//                return rst;
//            }
//
//            return TypeCaster.cast(rst, destType);
//        }

        if (destType instanceof ParameterizedType) {
            /*
             * 包含:
             * Map<?, ?>
             * HashMap<?, ?>
             */
            ParameterizedType pt = (ParameterizedType) destType;
            if (pt.getRawType() != Map.class && pt.getRawType() != HashMap.class) {
                buffer.skip(dataLen);
                return null;
            }
            Type[] typeArgs = pt.getActualTypeArguments();
            if (typeArgs.length != 2) {
                buffer.skip(dataLen);
                return null;
            }

            return ObjectLoader.readObjectDataAsHashMap(loader, buffer, objectSchema, dataLen);
        } else if (destType instanceof Class) {
            /*
             * 包含：
             * MStringObjectMap
             * byte[]
             * 自定义对象
             */
            Class<?> clazz = (Class<?>) destType;
            if (clazz.isArray()) {
                if (clazz.getComponentType() != byte.class) {
                    buffer.skip(dataLen);
                    return null;
                }
                return buffer.readBytes(dataLen);
            }

            if (clazz == MStringObjectMap.class) {
                return ObjectLoader.readObjectDataAsMStringObjectMap(
                        loader, buffer, objectSchema, dataLen);
            }

            return ObjectLoader.readObjectDataAsObject(
                    loader,
                    buffer, objectSchema, dataLen, clazz);

        } else {
            buffer.skip(dataLen);
            return null;
        }
    }

    public static Object readObjectDataAsMStringObjectMap(
            MotpLoader loader,
            MByteBuffer buffer,
            ObjectSchema objectSchema,
            int dataLen) {

        int pos = buffer.getOffset();
        int end = pos + dataLen;

        MStringObjectMap map = new MStringObjectMap();
        while (buffer.getOffset() < end) {
            int fieldNumber = buffer.readMVLInt().castToInteger();
            String name = objectSchema.getColumnByNumber(fieldNumber);
            Object fieldValue = MotpDataLoader.readData(loader, buffer);
            map.put(name, fieldValue);
        }
        if (buffer.getOffset() > end) {
            MotpDataLoader.readDataError("读取OBJECT时长度越界");
        }
        return map;
    }

    public static Object readObjectDataAsHashMap(
            MotpLoader loader,
            MByteBuffer buffer,
            ObjectSchema objectSchema,
            int dataLen) {

        int pos = buffer.getOffset();
        int end = pos + dataLen;

        HashMap<String, Object> map = new HashMap<>();
        while (buffer.getOffset() < end) {
            int fieldNumber = buffer.readMVLInt().castToInteger();
            String name = objectSchema.getColumnByNumber(fieldNumber);
            Object fieldValue = MotpDataLoader.readData(loader, buffer);
            map.put(name, fieldValue);
        }
        if (buffer.getOffset() > end) {
            MotpDataLoader.readDataError("读取OBJECT时长度越界");
        }
        return map;
    }

    public static <T> T readObjectDataAsObject(
            MotpLoader loader,
            MByteBuffer buffer,
            ObjectSchema objectSchema,
            int dataLen,
            Class<T> clazz) {

        int pos = buffer.getOffset();
        int end = pos + dataLen;

        //T obj = ObjectUtil.createObjectByClass(clazz);-
        T obj = ConstructorAccess.get(clazz).newInstance();


//        MotpLoaderCustomClassCache ccc = loader.getCustomClassCache().get(clazz);
//        if (ccc == null) {
//            ccc = new MotpLoaderCustomClassCache();
//            ccc.buildClass(clazz);
//            loader.getCustomClassCache().put(clazz, ccc);
//        }
//
//        while (buffer.getOffset() < end) {
//            int fieldNumber = buffer.readMVLInt().castToInteger();
//            String name = objectSchema.getColumnByNumber(fieldNumber);
//            Field field = ccc.getFieldByName(name);
//            if (field == null) {
//                MotpDataLoader.readData(loader, buffer);
//            } else {
//                Type fType = FieldTypeUtil.getFieldType(clazz, field);
//                Object fieldValue = MotpDataLoader.readData(loader, buffer, fType);
//                try {
////					field.setAccessible(true);
//                    field.set(obj, fieldValue);//根据属性名赋值
//                } catch (IllegalArgumentException | IllegalAccessException e) {
//
//                }
//            }
//
//        }
//        if (buffer.getOffset() > end) {
//            MotpDataLoader.readDataError("读取OBJECT时长度越界");
//        }
        return obj;
    }
}
