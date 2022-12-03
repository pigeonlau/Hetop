package cn.edu.nwpu.rj416.motp.serializer.motp.loader;

import cn.edu.nwpu.rj416.motp.serializer.motp.schema.AbstractSchema;
import cn.edu.nwpu.rj416.motp.serializer.motp.schema.EnumSchema;
import cn.edu.nwpu.rj416.motp.serializer.motp.schema.ObjectSchema;
import cn.edu.nwpu.rj416.motp.serializer.motp.schema.MotpSchema;
import cn.edu.nwpu.rj416.util.exception.runtime.MInvalidValueException;
import cn.edu.nwpu.rj416.util.objects.MByteBuffer;
import cn.edu.nwpu.rj416.util.objects.MVLInt;
import cn.edu.nwpu.rj416.util.types.StringUtil;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * @author pigeonliu
 * @date 2022/11/29 19:15
 */
public class MotpLoader {

    private byte[] motpBytes;
    private MVLInt schemaLen;
    private MVLInt dataLen;
    private MByteBuffer schemaBuffer;
    private MByteBuffer dataBuffer;
    private MotpSchema schema;
    private static Map<Class<?>, MotpLoaderEnumClassCache> enumClassCache = new HashMap<>();
    private static Map<Class<?>, MotpLoaderCustomClassCache> customClassCache = new HashMap<>();

    public Object loadDataBytes(MotpSchema schema, byte[] bytes, Type type) {

        this.schema = schema;
        dataBuffer = new MByteBuffer(bytes);

        return MotpDataLoader.readData(this, this.dataBuffer, type);

    }

    public Object loadBytes(byte[] bytes, Type type) {
        this.motpBytes = bytes;
        MByteBuffer buffer = new MByteBuffer(this.motpBytes);

        this.schemaLen = buffer.readMVLInt();
        //反序列化的时候线读取schema部分再读取data部分

        byte[] schemaBytes = buffer.readBytes(schemaLen.castToInteger());
        this.schemaBuffer = new MByteBuffer(schemaBytes);


        this.loadSchema();


        this.dataLen = buffer.readMVLInt();
        if (this.dataLen.getValue() == 0) {
            return null;
        }


        byte[] dataBytes = buffer.readBytes(dataLen.castToInteger());
        this.dataBuffer = new MByteBuffer(dataBytes);

//        stopwatch.start();
        Object obj = MotpDataLoader.readData(this, this.dataBuffer, type);
//        stopwatch.stop();
//        System.out.println("read data " + stopwatch.getMicrosecond());

        return obj;
    }

    public Object loadBytes(byte[] bytes) {
        this.motpBytes = bytes;
        MByteBuffer buffer = new MByteBuffer(this.motpBytes);

        this.schemaLen = buffer.readMVLInt();

        byte[] schemaBytes = buffer.readBytes(schemaLen.castToInteger());
        this.schemaBuffer = new MByteBuffer(schemaBytes);
        this.loadSchema();
        //参考上面没有带目标类型的loadBytes实现schema和data的数据分离

        this.dataLen = buffer.readMVLInt();

        if (this.dataLen.getValue() == 0) {
            return null;
        }
        byte[] dataBytes = buffer.readBytes(dataLen.castToInteger());
        this.dataBuffer = new MByteBuffer(dataBytes);

        return MotpDataLoader.readData(this, this.dataBuffer);
    }

    private void loadSchema() {
        this.schema = new MotpSchema();
        if (this.schemaBuffer == null) {
            return;
        }
        int pos = schemaBuffer.getOffset();
        while (pos < schemaBuffer.getSize()) {
            int number = schemaBuffer.readMVLInt().castToInteger();
            byte type = schemaBuffer.readByte();
            int len = schemaBuffer.readInt();

            AbstractSchema schema = null;
            if (type == AbstractSchema.EnumSchema) {
                schema = this.loadEnumSchema(len);
            } else if (type == AbstractSchema.ObjectSchema) {
                schema = this.loadObjectSchema(len);
            } else {
                throw new MInvalidValueException(
                        "未知的Schema类型：%d, Offset:%d", type, pos);
            }

            schema.setNumber(number);
            this.schema.add(number, schema);

            pos = schemaBuffer.getOffset();
        }

        if (pos > this.schemaLen.getValue()) {
            throw new MInvalidValueException("SchemaLength与实际Schema定义不符");
        }
    }

    private EnumSchema loadEnumSchema(int len) {
        EnumSchema schema = new EnumSchema();

        int pos = schemaBuffer.getOffset();
        int end = pos + len;
        int size = schemaBuffer.readMVLInt().castToInteger();
        for (int i = 0; i < size; i++) {

            MVLInt ordinal = schemaBuffer.readMVLInt();
            MVLInt nameLen = schemaBuffer.readMVLInt();
            byte[] nameBytes = this.schemaBuffer.readBytes(nameLen.castToInteger());
            String name = StringUtil.newString(nameBytes);

            schema.addValue(ordinal.castToInteger(), name);
            pos = this.schemaBuffer.getOffset();
        }
        if (pos > end) {
            throw new MInvalidValueException(
                    "Schema长度有误, Offset:%d, Len:%d", pos, len);
        }
        return schema;
    }


    private ObjectSchema loadObjectSchema(int len) {
        ObjectSchema schema = new ObjectSchema();

        int pos = schemaBuffer.getOffset();
        int end = pos + len;

        MVLInt typeNameLen = schemaBuffer.readMVLInt();
        byte[] typeNameBytes = this.schemaBuffer.readBytes(typeNameLen.castToInteger());
        String typeName = StringUtil.newString(typeNameBytes);
        schema.setClassName(typeName);

        int size = schemaBuffer.readMVLInt().castToInteger();
        for (int i = 0; i < size; i++) {

            int number = schemaBuffer.readMVLInt().castToInteger();
            MVLInt nameLen = schemaBuffer.readMVLInt();
            byte[] nameBytes = this.schemaBuffer.readBytes(nameLen.castToInteger());
            String name = StringUtil.newString(nameBytes);

            schema.addLoadColumn(number, name);
            pos = this.schemaBuffer.getOffset();
        }
        if (pos > end) {
            throw new MInvalidValueException(
                    "Schema长度有误, Offset:%d, Len:%d", pos, len);
        }
        return schema;
    }

    public MotpSchema getSchema() {
        return schema;
    }


    public Map<Class<?>, MotpLoaderEnumClassCache> getEnumClassCache() {
        return enumClassCache;
    }

    public Map<Class<?>, MotpLoaderCustomClassCache> getCustomClassCache() {
        return customClassCache;
    }
}
