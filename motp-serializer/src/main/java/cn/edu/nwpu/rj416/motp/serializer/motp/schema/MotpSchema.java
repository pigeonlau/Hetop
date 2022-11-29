package cn.edu.nwpu.rj416.motp.serializer.motp.schema;

import cn.edu.nwpu.rj416.motp.reflectasm.FieldAccess;

import cn.edu.nwpu.rj416.util.objects.MByteBuffer;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author pigeonliu
 * @date 2022/11/29 16:22
 */
public class MotpSchema {

    private int numberIndex = 1;

    // 序列化
    private Map<Class<?>, AbstractSchema> buildSchema = new HashMap<>();

    // 反序列化
    private Map<Integer, AbstractSchema> loadSchema = new HashMap<>();

    public AbstractSchema append(Class<?> clazz) {
        if (clazz.isEnum()) {
            @SuppressWarnings("unchecked")
            Class<Enum<?>> enumClass = (Class<Enum<?>>) clazz;
            return this.appendEnum(enumClass);
        } else {
            return this.appendClass(clazz);
        }
    }

    public MotpObjectSchema appendClass(Class<?> clazz) {
        MotpObjectSchema schema = new MotpObjectSchema();
        schema.setClassName(clazz.getName());

        FieldAccess access = FieldAccess.get(clazz);
        Field[] fields = access.getFields();

        int noIndex = 1;
        for (Field f : fields) {
            schema.addBuildColumn(noIndex++, f);
        }
        schema.setNumber(this.numberIndex++);
        this.buildSchema.put(clazz, schema);
        return schema;
    }


    public MotpEnumSchema appendEnum(Class<Enum<?>> clazz) {
        MotpEnumSchema schema = new MotpEnumSchema();

        Enum<?>[] enumValues = clazz.getEnumConstants();
        for (Enum<?> o : enumValues) {
            schema.addValue(o.ordinal(), o.name());
        }

        schema.setNumber(this.numberIndex++);//设置schemaid
        this.buildSchema.put(clazz, schema);
        return schema;
    }

    public AbstractSchema appendMotpSchema(Class<?> clazz, AbstractSchema schema) {

        schema.setNumber(numberIndex++);
        buildSchema.put(clazz, schema);

        return schema;
    }

    public AbstractSchema getByClass(Class<?> clazz) {
        return this.buildSchema.get(clazz);
    }

    public MotpEnumSchema getEnumSchemaByClass(Class<?> clazz) {
        AbstractSchema schema = this.buildSchema.get(clazz);
        if (schema == null) {
            return null;
        }
        if (schema.getType() != AbstractSchema.EnumSchema) {
            return null;
        }
        return (MotpEnumSchema) schema;
    }

    public MotpObjectSchema getObjectSchemaByClass(Class<?> clazz) {
        AbstractSchema schema = this.buildSchema.get(clazz);
        if (schema == null) {
            return null;
        }
        if (schema.getType() != AbstractSchema.ObjectSchema) {
            return null;
        }
        return (MotpObjectSchema) schema;
    }


    public MByteBuffer getByteBuffer() {
        MByteBuffer buffer = new MByteBuffer();
        List<AbstractSchema> list = new ArrayList<>(this.buildSchema.values());
        list.sort(Comparator.comparingInt(AbstractSchema::getNumber));
        for (AbstractSchema s : list) {
            buffer.appendMVLInt(s.getNumber());
            buffer.appendByte(s.getType());

            int offset = buffer.getOffset();
            buffer.appendInt(0);
            s.appendBytes(buffer);

            buffer.writeInt(offset, buffer.getOffset() - offset - 4);
        }

        return buffer;
    }

    public void add(int number, AbstractSchema schema) {
        loadSchema.put(number, schema);
    }

    public AbstractSchema get(int number) {
        if (loadSchema == null || loadSchema.isEmpty()) {
            loadSchema = buildSchema.values().stream().collect(Collectors.toMap(AbstractSchema::getNumber, e -> e));
        }
        return loadSchema.get(number);
    }
}
