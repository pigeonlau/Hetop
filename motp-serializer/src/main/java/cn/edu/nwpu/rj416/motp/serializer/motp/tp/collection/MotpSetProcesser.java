package cn.edu.nwpu.rj416.motp.serializer.motp.tp.collection;

import cn.edu.nwpu.rj416.motp.serializer.motp.MotpType;
import cn.edu.nwpu.rj416.motp.serializer.motp.tp.MotpTypeProcesser;
import cn.edu.nwpu.rj416.util.objects.MByteBuffer;
import cn.edu.nwpu.rj416.util.objects.MLinkedBuffer;
import cn.edu.nwpu.rj416.util.objects.MVLInt;

import java.util.List;


public class MotpSetProcesser implements MotpTypeProcesser {

	@Override
	public byte getMosType() {
		return MotpType.LIST;
	}
	
	@Override
	public void writeValue(MLinkedBuffer byteBuffer, Object o) {
		List<?> str = (List<?>)o;
	}

	@Override
	public Object read(MByteBuffer byteBuffer) {
		MVLInt bytesLen = byteBuffer.readMVLInt();
		byte[] bytes = byteBuffer.readBytes(bytesLen.castToInteger());
		return bytes;
	}
}
