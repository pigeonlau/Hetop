package cn.edu.nwpu.rj416.motp.serializer.motp.builder;


import cn.edu.nwpu.rj416.motp.serializer.motp.schema.MotpSchema;
import cn.edu.nwpu.rj416.util.objects.MByteBuffer;
import cn.edu.nwpu.rj416.util.objects.MLinkedBuffer;
import cn.edu.nwpu.rj416.util.objects.MVLInt;
import cn.edu.nwpu.rj416.util.types.StringUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 对象结构描述</br>
 * <p>
 * <p>
 * 序列化结构：</br>
 * [columnSize:varInt]List<([columnType:byte][columnNameLen:varInt][columnName:String])></br>
 *
 * @author MilesLiu
 * <p>
 * 2019年11月28日 下午1:25:39
 */
class MotpBuilderObjectSchema extends MotpSchema {
    private String className;
    private Map<String, MotpBuilderObjectSchemaColumn> columns = new HashMap<>();
    private List<Field> fields;


    public MotpBuilderObjectSchema() {
        super();
        this.setType(MotpSchema.ObjectSchema);
    }

    @Override
    public byte[] getBytes() {
        MByteBuffer buffer = new MByteBuffer();
//		buffer.appendMVLInt(new MVLInt(this.getNumber()));
//		buffer.appendByte(this.getType());
        /*
         * 1. 写入序列化对象的类型名称
         */
        byte[] classNameBytes = StringUtil.getBytes(this.getClassName());
        buffer.appendMVLInt(classNameBytes.length);
        buffer.appendBytes(classNameBytes);

        List<MotpBuilderObjectSchemaColumn> list = new ArrayList<>();
        for (MotpBuilderObjectSchemaColumn c : this.columns.values()) {
            if (!c.isInUse()) {
                continue;
            }
            list.add(c);
        }

        buffer.appendMVLInt(new MVLInt(list.size()));

        for (MotpBuilderObjectSchemaColumn c : list) {
            buffer.appendMVLInt(new MVLInt(c.getNumber()));
            byte[] nameBytes = StringUtil.getBytes(c.getName());
            buffer.appendMVLInt(new MVLInt(nameBytes.length));
            buffer.appendBytes(nameBytes);
        }

        return buffer.getBytes();
    }

    /**
     * 使用链表式 buffer 返回
     *
     * @return
     */
    public MLinkedBuffer getByteBuffer() {
        MLinkedBuffer buffer = new MLinkedBuffer();

        byte[] classNameBytes = StringUtil.getBytes(this.getClassName());
        buffer.appendMVLInt(classNameBytes.length);
        buffer.appendBytes(classNameBytes);

        List<MotpBuilderObjectSchemaColumn> list = new ArrayList<>();
        for (MotpBuilderObjectSchemaColumn c : this.columns.values()) {
            if (!c.isInUse()) {
                continue;
            }
            list.add(c);
        }

        buffer.appendMVLInt(new MVLInt(list.size()));

        for (MotpBuilderObjectSchemaColumn c : list) {
            buffer.appendMVLInt(new MVLInt(c.getNumber()));
            byte[] nameBytes = StringUtil.getBytes(c.getName());
            buffer.appendMVLInt(new MVLInt(nameBytes.length));
            buffer.appendBytes(nameBytes);
        }

        return buffer;
    }

    public MotpBuilderObjectSchemaColumn getColumnByName(String name) {
        return this.columns.get(name);
    }

    public MotpBuilderObjectSchemaColumn addColumn(int number, String name) {
        MotpBuilderObjectSchemaColumn sc = new MotpBuilderObjectSchemaColumn();
        sc.setName(name);
        sc.setNumber(number);
        this.columns.put(name, sc);
        return sc;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }


}
