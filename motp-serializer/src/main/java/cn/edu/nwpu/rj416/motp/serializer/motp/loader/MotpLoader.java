package cn.edu.nwpu.rj416.motp.serializer.motp.loader;

import cn.edu.nwpu.rj416.motp.serializer.motp.loader.loader.MotpDataLoader;
import cn.edu.nwpu.rj416.motp.serializer.motp.schema.MotpSchema;
import cn.edu.nwpu.rj416.util.exception.runtime.MInvalidValueException;
import cn.edu.nwpu.rj416.util.objects.MByteBuffer;
import cn.edu.nwpu.rj416.util.objects.MVLInt;
import cn.edu.nwpu.rj416.util.time.Stopwatch;
import cn.edu.nwpu.rj416.util.types.StringUtil;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * @author MilesLiu
 * <p>
 * 2020年3月13日 下午5:03:14
 */
public class MotpLoader {
    private byte[] motpBytes;
    private MVLInt schemaLen;
    private MVLInt dataLen;
    private MByteBuffer schemaBuffer;
    private MByteBuffer dataBuffer;
    private MotpLoaderSchema schemas = new MotpLoaderSchema();
    private static Map<Class<?>, MotpLoaderEnumClassCache> enumClassCache = new HashMap<>();
    private static Map<Class<?>, MotpLoaderCustomClassCache> customClassCache = new HashMap<>();

    public Object loadBytes(byte[] bytes, Type type) {
        this.motpBytes = bytes;
        MByteBuffer buffer = new MByteBuffer(this.motpBytes);

        this.schemaLen = buffer.readMVLInt();
        //反序列化的时候线读取schema部分再读取data部分

        byte[] schemaBytes = buffer.readBytes(schemaLen.castToInteger());
        this.schemaBuffer = new MByteBuffer(schemaBytes);

//        Stopwatch stopwatch = new Stopwatch();
//        stopwatch.start();
        this.loadSchema();
//        stopwatch.stop();
//        System.out.println("load schema " + stopwatch.getMicrosecond());

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

        if (obj == null) {
            return null;
        }

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

        Object obj = MotpDataLoader.readData(this, this.dataBuffer);
        if (obj == null) {
            return null;
        }

        return obj;
    }

    private void loadSchema() {
        if (this.schemaBuffer == null) {
            return;
        }
        int pos = schemaBuffer.getOffset();
        while (pos < schemaBuffer.getSize()) {
            int number = schemaBuffer.readMVLInt().castToInteger();
            byte type = schemaBuffer.readByte();
            int len = schemaBuffer.readInt();

            MotpSchema schema = null;
            if (type == MotpSchema.EnumSchema) {
                schema = this.loadEnumSchema(len);
            } else if (type == MotpSchema.ObjectSchema) {
                schema = this.loadObjectSchema(len);
            } else {
                throw new MInvalidValueException(
                        "未知的Schema类型：%d, Offset:%d", type, pos);
            }

            schema.setNumber(number);
            this.schemas.add(number, schema);

            pos = schemaBuffer.getOffset();
        }

        if (pos > this.schemaLen.getValue()) {
            throw new MInvalidValueException("SchemaLength与实际Schema定义不符");
        }
    }

    private MotpLoaderEnumSchema loadEnumSchema(int len) {
        MotpLoaderEnumSchema schema = new MotpLoaderEnumSchema();

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


    private MotpLoaderObjectSchema loadObjectSchema(int len) {
        MotpLoaderObjectSchema schema = new MotpLoaderObjectSchema();

        int pos = schemaBuffer.getOffset();
        int end = pos + len;

        MVLInt typeNameLen = schemaBuffer.readMVLInt();
        byte[] typeNameBytes = this.schemaBuffer.readBytes(typeNameLen.castToInteger());
        String typeName = StringUtil.newString(typeNameBytes);
        schema.setTypeName(typeName);

        int size = schemaBuffer.readMVLInt().castToInteger();
        for (int i = 0; i < size; i++) {

            int number = schemaBuffer.readMVLInt().castToInteger();
            MVLInt nameLen = schemaBuffer.readMVLInt();
            byte[] nameBytes = this.schemaBuffer.readBytes(nameLen.castToInteger());
            String name = StringUtil.newString(nameBytes);

            schema.addColumn(number, name);
            pos = this.schemaBuffer.getOffset();
        }
        if (pos > end) {
            throw new MInvalidValueException(
                    "Schema长度有误, Offset:%d, Len:%d", pos, len);
        }
        return schema;
    }

    public MotpLoaderSchema getSchemas() {
        return schemas;
    }


    public Map<Class<?>, MotpLoaderEnumClassCache> getEnumClassCache() {
        return enumClassCache;
    }

    public Map<Class<?>, MotpLoaderCustomClassCache> getCustomClassCache() {
        return customClassCache;
    }
}
