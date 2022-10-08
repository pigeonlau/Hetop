package cn.edu.nwpu.rj416.motp.serializer.motp.tp.primitive;


import cn.edu.nwpu.rj416.motp.serializer.motp.MotpType;
import cn.edu.nwpu.rj416.motp.serializer.motp.tp.MotpTypeProcesser;
import cn.edu.nwpu.rj416.util.objects.MByteBuffer;

public class MotpDoubleProcesser implements MotpTypeProcesser {

	@Override
	public byte getMosType() {
		return MotpType.DOUBLE;
	}
	
	@Override
	public int writeValue(MByteBuffer byteBuffer, Object o) {
		return byteBuffer.appendDouble((double)o);
	}

	@Override
	public Object read(MByteBuffer byteBuffer) {
		return byteBuffer.readDouble();
	}

}
