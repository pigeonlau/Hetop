package cn.edu.nwpu.rj416.motp.serializer.motp.schema;

import cn.edu.nwpu.rj416.util.objects.MByteBuffer;
import cn.edu.nwpu.rj416.util.objects.MVLInt;
import cn.edu.nwpu.rj416.util.types.StringUtil;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author pigeonliu
 * @date 2022/11/29 16:24
 */
public class MotpObjectSchema extends AbstractSchema {

    private String className;

    private Map<Integer, Field> buildColumns = new HashMap<>();

    private Map<Integer, String> loadColumns = new HashMap<>();


    public MotpObjectSchema() {
        super();
        this.setType(ObjectSchema);
    }


    public MByteBuffer getByteBuffer() {
        MByteBuffer buffer = new MByteBuffer();

        byte[] classNameBytes = StringUtil.getBytes(this.getClassName());
        buffer.appendMVLInt(classNameBytes.length);
        buffer.appendBytes(classNameBytes);

        buffer.appendMVLInt(new MVLInt(buildColumns.size()));

        for (Map.Entry<Integer, Field> c : buildColumns.entrySet()) {
            buffer.appendMVLInt(new MVLInt(c.getKey()));
            byte[] nameBytes = StringUtil.getBytes(c.getValue().getName());
            buffer.appendMVLInt(new MVLInt(nameBytes.length));
            buffer.appendBytes(nameBytes);
        }

        return buffer;
    }

    @Override
    public void appendBytes(MByteBuffer buffer) {
//
        byte[] classNameBytes = StringUtil.getBytes(this.getClassName());
        buffer.appendMVLInt(classNameBytes.length);
        buffer.appendBytes(classNameBytes);

        buffer.appendMVLInt(new MVLInt(buildColumns.size()));

        for (Map.Entry<Integer, Field> c : buildColumns.entrySet()) {
            buffer.appendMVLInt(new MVLInt(c.getKey()));
            byte[] nameBytes = StringUtil.getBytes(c.getValue().getName());
            buffer.appendMVLInt(new MVLInt(nameBytes.length));
            buffer.appendBytes(nameBytes);
        }

    }


    public void addBuildColumn(int number, Field field) {
        this.buildColumns.put(number, field);
    }

    public void addLoadColumn(int number, String name) {
        this.loadColumns.put(number, name);
    }

    public String getColumnByNumber(int number) {
        return loadColumns.get(number);
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }


    public Map<Integer, Field> getBuildColumns() {
        return buildColumns;
    }
}
