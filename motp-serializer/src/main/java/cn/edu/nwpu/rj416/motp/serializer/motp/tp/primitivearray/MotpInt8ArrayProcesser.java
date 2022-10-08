package cn.edu.nwpu.rj416.motp.serializer.motp.tp.primitivearray;


import cn.edu.nwpu.rj416.motp.serializer.motp.MotpType;
import cn.edu.nwpu.rj416.motp.serializer.motp.tp.MotpTypeProcesser;
import cn.edu.nwpu.rj416.util.objects.MByteBuffer;
import cn.edu.nwpu.rj416.util.objects.MLinkedBuffer;

public class MotpInt8ArrayProcesser implements MotpTypeProcesser {

	@Override
	public byte getMosType() {
		return MotpType.BYTE_ARR;
	}
	
	@Override
	public void writeValue(MLinkedBuffer byteBuffer, Object o) {
		byte[] bytes = (byte[])o;
		byteBuffer.appendMVLInt(bytes.length);
		byteBuffer.appendBytes(bytes);

	}

	@Override
	public Object read(MByteBuffer byteBuffer) {
		int len = (int)byteBuffer.readMVLInt().getValue();
		byte[] bytes = byteBuffer.readBytes(len);
		
		return bytes;
	}
}
