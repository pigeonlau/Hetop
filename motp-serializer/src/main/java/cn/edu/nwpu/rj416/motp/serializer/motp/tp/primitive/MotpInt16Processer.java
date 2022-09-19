package cn.edu.nwpu.rj416.motp.serializer.motp.tp.primitive;


import cn.edu.nwpu.rj416.motp.serializer.motp.MotpType;
import cn.edu.nwpu.rj416.motp.serializer.motp.tp.MotpTypeProcesser;
import cn.edu.nwpu.rj416.util.objects.MByteBuffer;

public class MotpInt16Processer implements MotpTypeProcesser {

	@Override
	public byte getMosType() {
		return MotpType.INT16;
	}
	
	@Override
	public int writeValue(MByteBuffer byteBuffer, Object o) {
		return byteBuffer.appendShort((short)o);
	}

	@Override
	public Object read(MByteBuffer byteBuffer) {
		return byteBuffer.readShort();
	}

}
