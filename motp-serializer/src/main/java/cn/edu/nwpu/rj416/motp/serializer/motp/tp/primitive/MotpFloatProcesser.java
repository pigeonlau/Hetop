package cn.edu.nwpu.rj416.motp.serializer.motp.tp.primitive;


import cn.edu.nwpu.rj416.motp.serializer.motp.MotpType;
import cn.edu.nwpu.rj416.motp.serializer.motp.tp.MotpTypeProcesser;
import cn.edu.nwpu.rj416.util.objects.MByteBuffer;
import cn.edu.nwpu.rj416.util.objects.MLinkedBuffer;

public class MotpFloatProcesser implements MotpTypeProcesser {

    @Override
    public byte getMosType() {
        return MotpType.FLOAT;
    }

    @Override
    public void writeValue(MLinkedBuffer byteBuffer, Object o) {
        byteBuffer.appendFloat((float) o);
    }

    @Override
    public Object read(MByteBuffer byteBuffer) {
        return byteBuffer.readFloat();
    }

}
