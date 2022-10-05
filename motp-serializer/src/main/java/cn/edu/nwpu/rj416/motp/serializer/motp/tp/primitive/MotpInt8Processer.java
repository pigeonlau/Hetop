package cn.edu.nwpu.rj416.motp.serializer.motp.tp.primitive;


import cn.edu.nwpu.rj416.motp.serializer.motp.MotpType;
import cn.edu.nwpu.rj416.motp.serializer.motp.tp.MotpTypeProcesser;
import cn.edu.nwpu.rj416.util.objects.MByteBuffer;
import cn.edu.nwpu.rj416.util.objects.MLinkedBuffer;

public class MotpInt8Processer implements MotpTypeProcesser {

    @Override
    public byte getMosType() {
        return MotpType.INT8;
    }

    @Override
    public void writeValue(MLinkedBuffer byteBuffer, Object o) {
        byteBuffer.appendByte((byte) o);
    }

    @Override
    public Object read(MByteBuffer byteBuffer) {
        return byteBuffer.readByte();
    }
}
