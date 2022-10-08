package cn.edu.nwpu.rj416.motp.serializer.motp.tp.collection;



import cn.edu.nwpu.rj416.motp.serializer.motp.MotpType;
import cn.edu.nwpu.rj416.motp.serializer.motp.tp.MotpTypeProcesser;
import cn.edu.nwpu.rj416.util.exception.runtime.MacawRuntimeException;
import cn.edu.nwpu.rj416.util.objects.MByteBuffer;
import cn.edu.nwpu.rj416.util.objects.MLinkedBuffer;
import cn.edu.nwpu.rj416.util.objects.MVLInt;

import java.io.UnsupportedEncodingException;


public class MotpMapProcesser implements MotpTypeProcesser {

	@Override
	public byte getMosType() {
		return MotpType.MAP;
	}
	
	@Override
	public void writeValue(MLinkedBuffer byteBuffer, Object o) {
		String str = (String)o;
		byte[] bytes;
		try {
			bytes = str.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new MacawRuntimeException("无法将String转为UTF-8");
		}
		byteBuffer.appendMVLInt(new MVLInt(bytes.length));
		byteBuffer.appendBytes(bytes);

	}

	@Override
	public Object read(MByteBuffer byteBuffer) {
		MVLInt bytesLen = byteBuffer.readMVLInt();
		byte[] bytes = byteBuffer.readBytes(bytesLen.castToInteger());
		return bytes;
	}

}
