package cn.edu.nwpu.rj416.motp.serializer.motp.tp.primitivearray;


import cn.edu.nwpu.rj416.motp.serializer.motp.MotpType;
import cn.edu.nwpu.rj416.motp.serializer.motp.tp.MotpTypeProcesser;
import cn.edu.nwpu.rj416.util.objects.MByteBuffer;
import cn.edu.nwpu.rj416.util.types.ByteUtil;

public class MotpBooleanArrayProcesser implements MotpTypeProcesser {

	@Override
	public byte getMosType() {
		return MotpType.BYTE_ARR;
	}
	
	@Override
	public int writeValue(MByteBuffer byteBuffer, Object o) {
		boolean[] arr = (boolean[])o;
		byte[] bytes = new byte[arr.length];
		for (int i = 0; i < arr.length; i++) {
			bytes[i] = arr[i] ? ByteUtil.BYTE_1 : ByteUtil.BYTE_0;
		}
		int len = byteBuffer.appendMVLInt(arr.length);
		len += byteBuffer.appendBytes(bytes);
		return len;
	}

	@Override
	public Object read(MByteBuffer byteBuffer) {
		int len = (int)byteBuffer.readMVLInt().getValue();
		byte[] bytes = byteBuffer.readBytes(len);
		boolean[] arr = new boolean[len]; 
		
		for (int i = 0; i < arr.length; i++) {
			arr[i] = (bytes[i] > 0);
		}
		
		return arr;
	}
}
