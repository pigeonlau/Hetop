package cn.edu.nwpu.rj416.motp.serializer.motp.builder;


import cn.edu.nwpu.rj416.motp.reflectasm.FieldAccess;
import cn.edu.nwpu.rj416.motp.serializer.motp.schema.MotpSchema;
import cn.edu.nwpu.rj416.util.objects.MLinkedBuffer;

import java.lang.reflect.Field;
import java.util.*;


public class MotpBuilderSchema {

    private int numberIndex = 1;
    Map<Class<?>, MotpSchema> schemas = new HashMap<>();

    public MotpSchema append(Class<?> clazz) {
        if (clazz.isEnum()) {
            @SuppressWarnings("unchecked")
            Class<Enum<?>> enumClass = (Class<Enum<?>>) clazz;
            return this.appendEnum(enumClass);
        } else {
            return this.appendClass(clazz);
        }
    }

    public MotpBuilderObjectSchema appendClass(Class<?> clazz) {
        MotpBuilderObjectSchema schema = new MotpBuilderObjectSchema();
        schema.setClassName(clazz.getName());

        //List<Field> fields = ClassUtil.getEffectiveFields(clazz, Modifier.FINAL | Modifier.STATIC);

        //使用reflectAsm库
        //FieldAccess access=FieldAccess.get(clazz);
        //调用macaw-reflectasm模块改写的reflectasm库
        FieldAccess access = FieldAccess.get(clazz);
        List<Field> fields = Arrays.asList(access.getFields());
        schema.setFields(fields);

        int noIndex = 1;
        for (Field f : fields) {
            schema.addColumn(noIndex++, f.getName());
        }
        schema.setNumber(this.numberIndex++);
        this.schemas.put(clazz, schema);
        return schema;
    }


    public MotpBuilderEnumSchema appendEnum(Class<Enum<?>> clazz) {
        MotpBuilderEnumSchema schema = new MotpBuilderEnumSchema();

        Enum<?>[] enumValues = clazz.getEnumConstants();
        for (Enum<?> o : enumValues) {
            schema.addValue(o.ordinal(), o.name());
        }

        schema.setNumber(this.numberIndex++);//设置schemaid
        this.schemas.put(clazz, schema);
        return schema;
    }

    public MotpSchema getByClass(Class<?> clazz) {
        return this.schemas.get(clazz);
    }

    public MotpBuilderEnumSchema getEnumSchemaByClass(Class<?> clazz) {
        MotpSchema schema = this.schemas.get(clazz);
        if (schema == null) {
            return null;
        }
        if (schema.getType() != MotpSchema.EnumSchema) {
            return null;
        }
        return (MotpBuilderEnumSchema) schema;
    }

    public MotpBuilderObjectSchema getObjectSchemaByClass(Class<?> clazz) {
        MotpSchema schema = this.schemas.get(clazz);
        if (schema == null) {
            return null;
        }
        if (schema.getType() != MotpSchema.ObjectSchema) {
            return null;
        }
        return (MotpBuilderObjectSchema) schema;
    }

    public boolean exists(Class<?> clazz) {
        return this.schemas.containsKey(clazz);
    }

    public byte[] getBytes() {
        MLinkedBuffer buffer = new MLinkedBuffer();
        List<MotpSchema> list = new ArrayList<>();
        list.addAll(this.schemas.values());
        list.sort(new Comparator<MotpSchema>() {

            @Override
            public int compare(MotpSchema arg0, MotpSchema arg1) {
                return arg0.getNumber() - arg1.getNumber();
            }

        });
        for (MotpSchema s : list) {
            buffer.appendMVLInt(s.getNumber());
            buffer.appendByte(s.getType());
            byte[] contents = s.getBytes();
            buffer.appendMVLInt(contents.length);
            buffer.appendBytes(contents);
        }

        return buffer.getBytes();
    }

    public MLinkedBuffer getByteBuffer() {
        MLinkedBuffer buffer = new MLinkedBuffer();
        List<MotpSchema> list = new ArrayList<>();
        list.addAll(this.schemas.values());
        list.sort(new Comparator<MotpSchema>() {

            @Override
            public int compare(MotpSchema arg0, MotpSchema arg1) {
                return arg0.getNumber() - arg1.getNumber();
            }

        });
        for (MotpSchema s : list) {
            buffer.appendMVLInt(s.getNumber());
            buffer.appendByte(s.getType());

            MLinkedBuffer contents = s.getByteBuffer();
            buffer.appendMVLInt(contents.getSize());
            buffer.appendMLinkedBuffer(contents);
        }

        return buffer;
    }
}
