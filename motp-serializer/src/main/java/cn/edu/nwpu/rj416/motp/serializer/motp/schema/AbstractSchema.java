package cn.edu.nwpu.rj416.motp.serializer.motp.schema;

import cn.edu.nwpu.rj416.util.objects.MByteBuffer;

/**
 * @author pigeonliu
 * @date 2022/11/29 16:21
 */
public abstract class AbstractSchema {

    public static final byte EnumSchema = 1;
    public static final byte ObjectSchema = 2;

    private int number;
    private byte type;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public abstract MByteBuffer getByteBuffer();

    public abstract void appendBytes(MByteBuffer buffer);
}
