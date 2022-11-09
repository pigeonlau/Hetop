package cn.edu.nwpu.rj416.motp.serializer.motp.builder;


import cn.edu.nwpu.rj416.motp.serializer.motp.MotpType;
import cn.edu.nwpu.rj416.motp.serializer.motp.schema.MotpSchema;
import cn.edu.nwpu.rj416.motp.serializer.motp.tp.MotpTypeProcesser;
import cn.edu.nwpu.rj416.motp.serializer.motp.util.MTempFileUtil;
import cn.edu.nwpu.rj416.motp.serializer.motp.util.MotpProcesserMapping;
import cn.edu.nwpu.rj416.util.astype.AsType;
import cn.edu.nwpu.rj416.util.objects.MByteBuffer;
import cn.edu.nwpu.rj416.util.objects.MVLInt;
import com.alibaba.fastjson.JSONObject;

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.util.*;

public class MotpBuilder {
    private static final byte[] VOID_BYTES = {0, 0};

    private MotpBuilderSchema schema;
    private MByteBuffer dataBuffer;

    private static Map<Class<?>, MotpSchema> schemaCache = new HashMap<>();

    public byte[] getBytes(Object o) {
        if (o == null) {
            return VOID_BYTES;
        }

        //初始化
        this.schema = new MotpBuilderSchema();
        this.dataBuffer = new MByteBuffer();

        //生成序列化数据
        try {
            this.appendData(this.dataBuffer, o);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        //生成结果数据
//        MByteBuffer rst = new MByteBuffer();
//        byte[] schemaBytes = this.schema.getBytes();
//        byte[] dataBytes = this.dataBuffer.getBytes();
//
//
//        rst.appendMVLInt(schemaBytes.length);
//        //实现schema与data的数据分离，以便提取到独立的schema和data
//        rst.appendBytes(schemaBytes);
//
//        rst.appendMVLInt(dataBytes.length);
//        rst.appendBytes(dataBytes);

        return combineRes(schema.getByteBuffer(), dataBuffer);
    }

    private byte[] combineRes(MByteBuffer schemaByteBuffer, MByteBuffer dataBuffer) {
        MVLInt schemaByteBufferLength = new MVLInt(schemaByteBuffer.getSize());
        MVLInt dataBufferLength = new MVLInt(dataBuffer.getSize());

        int resLength = schemaByteBufferLength.getLen() + schemaByteBuffer.getSize()
                + dataBufferLength.getLen() + dataBuffer.getSize();

        byte[] res = new byte[resLength];
        int offset = 0;
        System.arraycopy(schemaByteBufferLength.getBytes(), 0, res, offset, schemaByteBufferLength.getLen());
        offset += schemaByteBufferLength.getLen();
        System.arraycopy(schemaByteBuffer.getRawBuffer(), 0, res, offset, schemaByteBuffer.getSize());
        offset += schemaByteBuffer.getSize();
        System.arraycopy(dataBufferLength.getBytes(), 0, res, offset, dataBufferLength.getLen());
        offset += dataBufferLength.getLen();
        System.arraycopy(dataBuffer.getRawBuffer(), 0, res, offset, dataBuffer.getSize());

        return res;
    }


    /**
     * 生成对象的Schema和数据
     *
     * @param object 要序列化的对象，且不为null
     * @param clazz  序列化参照的类型，不能为null
     * @throws Exception
     */
    private void appendData(MByteBuffer dataBuffer, Object object) throws Exception {
        if (object == null) {
            dataBuffer.appendByte(MotpType.VOID);
            return;
        }
        /*
         * 首先对对象进行化简(检查是否是AsType)
         * 如果是AsType，则将值转化为AsType对应的类型
         */
        while (AsType.class.isAssignableFrom(object.getClass())) {
            Object asObject = ((AsType<?>) object).dumpToAsType();
            if (asObject == null) {
                dataBuffer.appendByte(MotpType.VOID);
                return;
            }
            object = asObject;
        }

        Class<?> objectClass = object.getClass();


        /*
         * 判断是否是直接转换类型
         */
        MotpTypeProcesser processer = MotpProcesserMapping.getProcesser(objectClass);
        if (processer != null) {
            dataBuffer.appendByte(processer.getMosType());
            processer.writeValue(dataBuffer, object);
            return;
        }

        /*
         * 判断是否为数组或基本集合
         * 为 包装类型数组  或者  普通obj数组, 否则获取到processor
         */
        if (objectClass.isArray()) {

            appendArray(dataBuffer, (Object[]) object);
            return;

        } else if (objectClass.isEnum()) {
            MotpBuilderEnumSchema enumSchema = this.schema.getEnumSchemaByClass(objectClass);
            if (enumSchema == null) {
                @SuppressWarnings("unchecked")
                Class<Enum<?>> enumClass = (Class<Enum<?>>) objectClass;
                enumSchema = this.schema.appendEnum(enumClass);
            }

            Enum<?> enumValue = (Enum<?>) object;
            MotpBuilderEnumSchemaValue ev = enumSchema.getValueByOrdinal(enumValue.ordinal());
            ev.setInUse(true);

            dataBuffer.appendByte(MotpType.ENUM);
            dataBuffer.appendMVLInt(enumSchema.getNumber());
            dataBuffer.appendMVLInt(enumValue.ordinal());
            return;
        } else if (List.class.isAssignableFrom(objectClass)) {
            List<?> list = (List<?>) object;
            this.appendList(dataBuffer, list);
            return;
        } else if (Map.class.isAssignableFrom(objectClass)) {
            Map<?, ?> map = (Map<?, ?>) object;
            this.appendMap(dataBuffer, map);
            return;
        } else if (Set.class.isAssignableFrom(objectClass)) {
            Set<?> set = (Set<?>) object;
            this.appendSet(dataBuffer, set);
            return;
        } else if (File.class.isAssignableFrom(objectClass)) {
            File file = (File) object;
            this.appendFile(dataBuffer, file);
            return;
        }

        this.buildNormalObject(dataBuffer, object);
    }

    private void appendArray(MByteBuffer dataBuffer, Object[] arr) throws Exception {
        dataBuffer.appendByte(MotpType.LIST);
        dataBuffer.appendMVLInt(arr.length);
        // 首先获取数组类型, 尝试获取数组类型的快速处理器
        Class<?> componentType = arr.getClass().getComponentType();
        MotpTypeProcesser processer = MotpProcesserMapping.getProcesser(componentType);
        if (processer != null) {
            for (Object o : arr) {
                dataBuffer.appendByte(processer.getMosType());
                processer.writeValue(dataBuffer, o);
            }
        } else if (isNormalObject(componentType)) {
            for (Object o : arr) {
                this.buildNormalObject(dataBuffer, o);
            }
        } else {
            for (Object o : arr) {
                this.appendData(dataBuffer, o);
            }
        }
    }

    /**
     * 传递来源字段Filed, 获取具体泛型类型, 以便优化
     *
     * @param dataBuffer
     * @param list
     * @param f
     */
    private void appendList(MByteBuffer dataBuffer, List<?> list, Field f) throws Exception {
        dataBuffer.appendByte(MotpType.LIST);
        dataBuffer.appendMVLInt(list.size());

        Type genericType = f.getGenericType();
        if (genericType instanceof ParameterizedType) {
            try {
                Type t = ((ParameterizedType) f.getGenericType()).getActualTypeArguments()[0];
                // 应该只有常规泛型可以转型成功, 通配符泛型不行
                Class<?> clazz = (Class<?>) t;
                MotpTypeProcesser fastProcessor = MotpProcesserMapping.getProcesser(clazz);
                if (fastProcessor != null) {
                    for (Object o : list) {
                        dataBuffer.appendByte(fastProcessor.getMosType());
                        fastProcessor.writeValue(dataBuffer, o);
                    }
                } else if (isNormalObject(clazz)) {
                    for (Object o : list) {
                        buildNormalObject(dataBuffer, o);
                    }
                } else {
                    for (Object o : list) {
                        appendData(dataBuffer, o);
                    }
                }
            } catch (Exception e) {
                // 不是常规泛型  可能是 ? super Object的类别  wildcardTypeImpl
                for (Object ele : list) {
                    this.appendData(dataBuffer, ele);
                }
            }
        } else {
            // 可能无泛型标识
            for (Object ele : list) {
                this.appendData(dataBuffer, ele);
            }
        }
    }

    private void appendList(MByteBuffer dataBuffer, List<?> list) throws Exception {
        dataBuffer.appendByte(MotpType.LIST);
        dataBuffer.appendMVLInt(list.size());
        for (Object ele : list) {
            this.appendData(dataBuffer, ele);
        }
    }

    private void appendMap(MByteBuffer dataBuffer, Map<?, ?> map, Field f) throws Exception {
        dataBuffer.appendByte(MotpType.MAP);
        dataBuffer.appendMVLInt(map.size());

        Type genericType = f.getGenericType();
        if (genericType instanceof ParameterizedType) {
            try {
                Type keyType = ((ParameterizedType) f.getGenericType()).getActualTypeArguments()[0];
                Type valueType = ((ParameterizedType) f.getGenericType()).getActualTypeArguments()[1];

                // 应该只有常规泛型可以转型成功, 通配符泛型不行
                Class<?> keyClazz = (Class<?>) keyType;
                Class<?> valueClazz = (Class<?>) valueType;

                MotpTypeProcesser keyProcessor = MotpProcesserMapping.getProcesser(keyClazz);
                boolean keyIsNormalObject = isNormalObject(keyClazz);
                MotpTypeProcesser valueProcessor = MotpProcesserMapping.getProcesser(valueClazz);
                boolean valueIsNormalObject = isNormalObject(valueClazz);


                for (Map.Entry<?, ?> entry : map.entrySet()) {
                    Object key = entry.getKey();
                    if (keyProcessor != null) {
                        dataBuffer.appendByte(keyProcessor.getMosType());
                        keyProcessor.writeValue(dataBuffer, key);
                    } else if (keyIsNormalObject) {
                        buildNormalObject(dataBuffer, key);
                    } else {
                        appendData(dataBuffer, key);
                    }

                    Object value = entry.getValue();
                    if (valueProcessor != null) {
                        dataBuffer.appendByte(valueProcessor.getMosType());
                        valueProcessor.writeValue(dataBuffer, value);
                    } else if (valueIsNormalObject) {
                        buildNormalObject(dataBuffer, value);
                    } else {
                        appendData(dataBuffer, value);
                    }

                }
            } catch (Exception e) {

                // 不是常规泛型  可能是 ? super Object的类别
                for (Map.Entry<?, ?> entry : map.entrySet()) {
                    this.appendData(dataBuffer, entry.getKey());
                    this.appendData(dataBuffer, entry.getValue());
                }
            }
        } else {
            // 可能无泛型标识
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                this.appendData(dataBuffer, entry.getKey());
                this.appendData(dataBuffer, entry.getValue());
            }
        }
    }

    private void appendMap(MByteBuffer dataBuffer, Map<?, ?> map) throws Exception {
        dataBuffer.appendByte(MotpType.MAP);
        dataBuffer.appendMVLInt(map.size());
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            this.appendData(dataBuffer, entry.getKey());
            this.appendData(dataBuffer, entry.getValue());
        }
    }

    private void appendSet(MByteBuffer dataBuffer, Set<?> set, Field f) throws Exception {
        dataBuffer.appendByte(MotpType.SET);
        dataBuffer.appendMVLInt(set.size());

        Type genericType = f.getGenericType();
        if (genericType instanceof ParameterizedType) {
            try {
                Type t = ((ParameterizedType) f.getGenericType()).getActualTypeArguments()[0];
                // 应该只有常规泛型可以转型成功, 通配符泛型不行
                Class<?> clazz = (Class<?>) t;
                MotpTypeProcesser fastProcessor = MotpProcesserMapping.getProcesser(clazz);
                if (fastProcessor != null) {
                    for (Object o : set) {
                        dataBuffer.appendByte(fastProcessor.getMosType());
                        fastProcessor.writeValue(dataBuffer, o);
                    }
                } else if (isNormalObject(clazz)) {
                    for (Object o : set) {
                        buildNormalObject(dataBuffer, o);
                    }
                } else {
                    for (Object o : set) {
                        appendData(dataBuffer, o);
                    }
                }
            } catch (Exception e) {

                // 不是常规泛型  可能是 ? super Object的类别
                for (Object ele : set) {
                    this.appendData(dataBuffer, ele);
                }
            }
        } else {
            // 可能无泛型标识
            for (Object ele : set) {
                this.appendData(dataBuffer, ele);
            }
        }
    }

    private void appendSet(MByteBuffer dataBuffer, Set<?> set) throws Exception {
        dataBuffer.appendByte(MotpType.SET);
        dataBuffer.appendMVLInt(set.size());
        for (Object ele : set) {
            this.appendData(dataBuffer, ele);
        }
    }

    private void appendFile(MByteBuffer dataBuffer, File file) throws Exception {
        dataBuffer.appendByte(MotpType.FILE);

        String fileName = file.getName();
        byte[] fileNameBytes = fileName.getBytes("UTF-8");
        dataBuffer.appendMVLInt(fileNameBytes.length);
        dataBuffer.appendBytes(fileNameBytes);

        byte[] bytes = Files.readAllBytes(file.toPath());
        dataBuffer.appendMVLInt(bytes.length);
        dataBuffer.appendBytes(bytes);

        if (MTempFileUtil.isTempFile(file)) {
            Files.deleteIfExists(file.toPath());
        }
    }

    private void buildNormalObject(MByteBuffer dataBuffer, Object o) throws Exception, IllegalAccessException {
        if (o == null) {
            dataBuffer.appendByte(MotpType.VOID);
            return;
        }
        Class<?> clazz = o.getClass();

        MotpBuilderObjectSchema objectSchema = this.schema.getObjectSchemaByClass(clazz);
        MotpBuilderObjectSchema cache = (MotpBuilderObjectSchema) schemaCache.get(clazz);

        if (cache == null && objectSchema == null) {
            objectSchema = this.schema.appendClass(clazz);
            schemaCache.put(clazz, objectSchema);
        } else if (objectSchema == null) {
            objectSchema = (MotpBuilderObjectSchema) schema.appendMotpSchema(clazz, cache);
        } else {
            schemaCache.put(clazz, objectSchema);
        }


        dataBuffer.appendByte(MotpType.OBJECT);
        dataBuffer.appendMVLInt(objectSchema.getNumber());

        // 提前占4位 int  作为 后续 buffer数据的长度
        int offset = dataBuffer.getOffset();
        dataBuffer.appendInt(0);
        for (Field f : objectSchema.getFields()) {
            f.setAccessible(true);
            Object fieldValue = f.get(o);

            if (fieldValue == null) {
                continue;
            }

            MotpBuilderObjectSchemaColumn column = objectSchema.getColumnByName(f.getName());
            if (column == null) {
                continue;
            }

            column.setInUse(true);

            dataBuffer.appendMVLInt(column.getNumber());

            // 泛型类型 field
            Class<?> type = f.getType();
            if (type.isAssignableFrom(List.class)) {
                appendList(dataBuffer, (List<?>) fieldValue, f);
            } else if (type.isAssignableFrom(Set.class)) {
                appendSet(dataBuffer, (Set<?>) fieldValue, f);
            } else if (type.isAssignableFrom(Map.class)) {
                appendMap(dataBuffer, (Map<?, ?>) fieldValue, f);
            } else {
                this.appendData(dataBuffer, fieldValue);
            }
        }

        // 重新填补后续databuffer的长度
        dataBuffer.writeInt(offset, dataBuffer.getOffset() - offset - 4);

    }

    /**
     * 判断 某个类的对象, 是否应该走 buildNormalObject
     *
     * @param obj
     * @return
     */
    private boolean isNormalObject(Class<?> clazz) {
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


    public MotpBuilderSchema getSchema() {
        return schema;
    }


    public MByteBuffer getDataBuffer() {
        return dataBuffer;
    }


}
