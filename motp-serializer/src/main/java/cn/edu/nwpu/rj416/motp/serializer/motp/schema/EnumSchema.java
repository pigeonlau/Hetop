package cn.edu.nwpu.rj416.motp.serializer.motp.schema;

import cn.edu.nwpu.rj416.util.objects.MByteBuffer;
import cn.edu.nwpu.rj416.util.objects.MVLInt;
import cn.edu.nwpu.rj416.util.types.StringUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author pigeonliu
 * @date 2022/11/29 17:18
 */
public class EnumSchema extends AbstractSchema {

    // ordinary - name
    private Map<Integer, String> values = new HashMap<>(16);


    public EnumSchema() {
        super();
        this.setType(AbstractSchema.EnumSchema);
    }

    public byte[] getBytes() {
        //生成SchemaContent，即枚举值列表
        MByteBuffer schemaContent = new MByteBuffer();



        schemaContent.appendMVLInt(values.size());

        for (Map.Entry<Integer, String> v : values.entrySet()) {
            schemaContent.appendMVLInt(new MVLInt(v.getKey()));
            byte[] nameBytes = StringUtil.getBytes(v.getValue());
            schemaContent.appendMVLInt(new MVLInt(nameBytes.length));
            schemaContent.appendBytes(nameBytes);
        }

        return schemaContent.getBytes();
    }

    public MByteBuffer getByteBuffer() {
        MByteBuffer schemaContent = new MByteBuffer();


        schemaContent.appendMVLInt(values.size());

        for (Map.Entry<Integer, String> v : values.entrySet()) {
            schemaContent.appendMVLInt(new MVLInt(v.getKey()));
            byte[] nameBytes = StringUtil.getBytes(v.getValue());
            schemaContent.appendMVLInt(new MVLInt(nameBytes.length));
            schemaContent.appendBytes(nameBytes);
        }

        return schemaContent;
    }

    @Override
    public void appendBytes(MByteBuffer buffer) {

        buffer.appendMVLInt(values.size());

        for (Map.Entry<Integer, String> v : values.entrySet()) {
            buffer.appendMVLInt(new MVLInt(v.getKey()));
            byte[] nameBytes = StringUtil.getBytes(v.getValue());
            buffer.appendMVLInt(new MVLInt(nameBytes.length));
            buffer.appendBytes(nameBytes);
        }

    }

    public String getValueByOrdinal(int n) {
        return this.values.get(n);
    }

    public void addValue(int ordinal, String name) {

        this.values.put(ordinal, name);
    }
}
