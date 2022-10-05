package cn.edu.nwpu.rj416.motp.serializer.motp.tp.basic;


import cn.edu.nwpu.rj416.motp.serializer.motp.MotpType;
import cn.edu.nwpu.rj416.motp.serializer.motp.tp.MotpTypeProcesser;
import cn.edu.nwpu.rj416.util.objects.MByteBuffer;
import cn.edu.nwpu.rj416.util.objects.MLinkedBuffer;

import java.util.Date;


public class MotpDateProcesser implements MotpTypeProcesser {

    @Override
    public byte getMosType() {
        return MotpType.INT64;
    }

    @Override
    public void writeValue(MLinkedBuffer byteBuffer, Object o) {
        Date d = (Date) o;
        byteBuffer.appendLong(d.getTime());
    }

    @Override
    public Object read(MByteBuffer byteBuffer) {
        long time = byteBuffer.readLong();
        return new Date(time);
    }

}
