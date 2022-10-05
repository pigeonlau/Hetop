package cn.edu.nwpu.rj416.motp.serializer.motp.builder;


import cn.edu.nwpu.rj416.motp.serializer.motp.MotpType;
import cn.edu.nwpu.rj416.motp.serializer.motp.tp.MotpTypeProcesser;
import cn.edu.nwpu.rj416.motp.serializer.motp.util.MTempFileUtil;
import cn.edu.nwpu.rj416.motp.serializer.motp.util.MotpProcesserMapping;
import cn.edu.nwpu.rj416.util.astype.AsType;
import cn.edu.nwpu.rj416.util.objects.MLinkedBuffer;
import cn.edu.nwpu.rj416.util.objects.MVLInt;

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MotpBuilder {
    private static final byte[] VOID_BYTES = {0, 0};

    private MotpBuilderSchema schema;
    private MLinkedBuffer dataBuffer;

    public byte[] getBytes(Object o) {
        if (o == null) {
            return VOID_BYTES;
        }

        //初始化
        this.schema = new MotpBuilderSchema();
        this.dataBuffer = new MLinkedBuffer();

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


//        return combineRes(schema.getByteBuffer(), dataBuffer);

        return schema.getByteBuffer().appendMLinkedBuffer(dataBuffer).getBytes();

    }

//    private byte[] combineRes(MByteBuffer schemaByteBuffer, MByteBuffer dataBuffer) {
//        MVLInt schemaByteBufferLength = new MVLInt(schemaByteBuffer.getSize());
//        MVLInt dataBufferLength = new MVLInt(dataBuffer.getSize());
//
//        int resLength = schemaByteBufferLength.getLen() + schemaByteBuffer.getSize()
//                + dataBufferLength.getLen() + dataBuffer.getSize();
//
//        byte[] res = new byte[resLength];
//        int offset = 0;
//        System.arraycopy(schemaByteBufferLength.getBytes(), 0, res, offset, schemaByteBufferLength.getLen());
//        offset += schemaByteBufferLength.getLen();
//        System.arraycopy(schemaByteBuffer.getRawBuffer(), 0, res, offset, schemaByteBuffer.getSize());
//        offset += schemaByteBuffer.getSize();
//        System.arraycopy(dataBufferLength.getBytes(), 0, res, offset, dataBufferLength.getLen());
//        offset += dataBufferLength.getLen();
//        System.arraycopy(dataBuffer.getRawBuffer(), 0, res, offset, dataBuffer.getSize());
//
//        return res;
//    }


    /**
     * 生成对象的Schema和数据
     *
     * @param object 要序列化的对象，且不为null
     * @param clazz  序列化参照的类型，不能为null
     * @throws Exception
     */
    private void appendData(MLinkedBuffer dataBuffer, Object object) throws Exception {
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
        //应用了反射机制
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
         */
        if (objectClass.isArray()) {

            int arrLen = Array.getLength(object);
            List<Object> list = new ArrayList<>();
            for (int i = 0; i < arrLen; i++) {
                list.add(Array.get(object, i));
            }
            this.appendList(dataBuffer, list);

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

    private void appendList(MLinkedBuffer dataBuffer, List<?> list) throws Exception {
        dataBuffer.appendByte(MotpType.LIST);
        dataBuffer.appendMVLInt(list.size());
        for (Object ele : list) {
            this.appendData(dataBuffer, ele);
        }
    }

    private void appendMap(MLinkedBuffer dataBuffer, Map<?, ?> map) throws Exception {
        dataBuffer.appendByte(MotpType.MAP);
        dataBuffer.appendMVLInt(map.size());
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            this.appendData(dataBuffer, entry.getKey());
            this.appendData(dataBuffer, entry.getValue());
        }
    }

    private void appendSet(MLinkedBuffer dataBuffer, Set<?> set) throws Exception {
        dataBuffer.appendByte(MotpType.SET);
        dataBuffer.appendMVLInt(set.size());
        for (Object ele : set) {
            this.appendData(dataBuffer, ele);
        }
    }

    private void appendFile(MLinkedBuffer dataBuffer, File file) throws Exception {
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

    private void buildNormalObject(MLinkedBuffer dataBuffer, Object o) throws Exception, IllegalAccessException {
        Class<?> clazz = o.getClass();

        MotpBuilderObjectSchema objectSchema = this.schema.getObjectSchemaByClass(clazz);
        if (objectSchema == null) {
            objectSchema = this.schema.appendClass(clazz);
        }
        //i do
        dataBuffer.appendByte(MotpType.OBJECT);
        dataBuffer.appendMVLInt(objectSchema.getNumber());

        MLinkedBuffer objectContent = new MLinkedBuffer();
        for (Field f : objectSchema.getFields()) {//反射
            f.setAccessible(true);//暴力反射，
            Object fieldValue = f.get(o);

            if (fieldValue == null) {
                continue;
            }

            MotpBuilderObjectSchemaColumn column = objectSchema.getColumnByName(f.getName());
            if (column == null) {
                //这个是不可能发生的，但是谁知道呢
                continue;
            }

            column.setInUse(true);
//			dataBuffer.appendMVLInt(column.getNumber());
//			this.appendData(dataBuffer,fieldValue);
            objectContent.appendMVLInt(column.getNumber());
            this.appendData(objectContent, fieldValue);
        }

        dataBuffer.appendMVLInt(objectContent.getSize());
        dataBuffer.appendBytes(objectContent.getBytes());

    }


    public MotpBuilderSchema getSchema() {
        return schema;
    }


    public MLinkedBuffer getDataBuffer() {
        return dataBuffer;
    }


}
