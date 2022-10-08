package cn.edu.nwpu.rj416.motp.serializer.motp.tp.basic;



import cn.edu.nwpu.rj416.motp.serializer.motp.MotpType;
import cn.edu.nwpu.rj416.motp.serializer.motp.tp.MotpTypeProcesser;
import cn.edu.nwpu.rj416.util.objects.MByteBuffer;

import java.util.Date;


public class MotpDateProcesser implements MotpTypeProcesser {

	@Override
	public byte getMosType() {
		return MotpType.INT64;
	}
	
	@Override
	public int writeValue(MByteBuffer byteBuffer, Object o) {
		Date d = (Date)o;
		return byteBuffer.appendLong(d.getTime());
	}

	@Override
	public Object read(MByteBuffer byteBuffer) {
		long time = byteBuffer.readLong();
		return new Date(time);
	}

}
