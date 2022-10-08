package cn.edu.nwpu.rj416.motp.serializer.motp.tp.primitive;


import cn.edu.nwpu.rj416.motp.serializer.motp.MotpType;
import cn.edu.nwpu.rj416.motp.serializer.motp.tp.MotpTypeProcesser;
import cn.edu.nwpu.rj416.util.objects.MByteBuffer;

public class MotpFloatProcesser implements MotpTypeProcesser {

	@Override
	public byte getMosType() {	
		return MotpType.FLOAT;
	}
	
	@Override
	public int writeValue(MByteBuffer byteBuffer, Object o) {
		return byteBuffer.appendFloat((float)o);
	}

	@Override
	public Object read(MByteBuffer byteBuffer) {
		return byteBuffer.readFloat();
	}

}
