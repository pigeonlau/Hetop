package cn.edu.nwpu.rj416.motp.serializer.motp.tp.primitive;


import cn.edu.nwpu.rj416.motp.serializer.motp.MotpType;
import cn.edu.nwpu.rj416.motp.serializer.motp.tp.MotpTypeProcesser;
import cn.edu.nwpu.rj416.util.objects.MByteBuffer;

public class MotpInt32Processer implements MotpTypeProcesser {

	@Override
	public byte getMosType() {
		return MotpType.INT32;
	}
	
	@Override
	public int writeValue(MByteBuffer byteBuffer, Object o) {
		return byteBuffer.appendInt((int)o);
	}

	@Override
	public Object read(MByteBuffer byteBuffer) {
		return byteBuffer.readInt();
	}
}
