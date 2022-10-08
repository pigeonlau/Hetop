package cn.edu.nwpu.rj416.motp.serializer.motp.tp.basic;


import cn.edu.nwpu.rj416.motp.serializer.motp.MotpType;
import cn.edu.nwpu.rj416.motp.serializer.motp.tp.MotpTypeProcesser;
import cn.edu.nwpu.rj416.util.objects.MByteBuffer;
import cn.edu.nwpu.rj416.util.objects.MVLInt;

public class MotpMVLIntProcesser implements MotpTypeProcesser {

	@Override
	public byte getMosType() {
		return MotpType.INT_VL;
	}
	
	@Override
	public int writeValue(MByteBuffer byteBuffer, Object o) {
		MVLInt vli = (MVLInt)o;
		return byteBuffer.appendMVLInt(vli);
	}

	@Override
	public Object read(MByteBuffer byteBuffer) {
		return byteBuffer.readMVLInt();
	}

}
