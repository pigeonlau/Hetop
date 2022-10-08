package cn.edu.nwpu.rj416.motp.serializer.motp.tp.primitive;


import cn.edu.nwpu.rj416.motp.serializer.motp.MotpType;
import cn.edu.nwpu.rj416.motp.serializer.motp.tp.MotpTypeProcesser;
import cn.edu.nwpu.rj416.util.objects.MByteBuffer;

public class MotpBooleanProcesser implements MotpTypeProcesser {

	@Override
	public byte getMosType() {
		return MotpType.BOOLEAN;
	}
	
	@Override
	public int writeValue(MByteBuffer byteBuffer, Object o) {
		Boolean b = (Boolean)o;
		byte v = 0;
		if (b != null && b) {
			v = 1;
		}
		return byteBuffer.appendByte(v);
	}

	@Override
	public Object read(MByteBuffer byteBuffer) {
		return byteBuffer.readByte() != 0;
	}
}
