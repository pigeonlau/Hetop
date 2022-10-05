package cn.edu.nwpu.rj416.motp.serializer.motp.tp.basic;


import cn.edu.nwpu.rj416.motp.serializer.motp.MotpType;
import cn.edu.nwpu.rj416.motp.serializer.motp.tp.MotpTypeProcesser;
import cn.edu.nwpu.rj416.util.exception.runtime.MacawRuntimeException;
import cn.edu.nwpu.rj416.util.objects.MByteBuffer;
import cn.edu.nwpu.rj416.util.objects.MLinkedBuffer;
import cn.edu.nwpu.rj416.util.objects.MVLInt;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;


public class MotpBigIntegerProcesser implements MotpTypeProcesser {

	@Override
	public byte getMosType() {
		return MotpType.STRING;
	}
	
	@Override
	public void writeValue(MLinkedBuffer byteBuffer, Object o) {
		BigInteger bd = (BigInteger)o;
		String str = bd.toString();
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
		String str;
		try {
			str = new String(bytes, "UTF-8");
			return new BigInteger(str);
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}

}
