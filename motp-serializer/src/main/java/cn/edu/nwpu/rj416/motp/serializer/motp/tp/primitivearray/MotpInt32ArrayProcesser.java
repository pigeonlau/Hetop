package cn.edu.nwpu.rj416.motp.serializer.motp.tp.primitivearray;


import cn.edu.nwpu.rj416.motp.serializer.motp.MotpType;
import cn.edu.nwpu.rj416.motp.serializer.motp.tp.MotpTypeProcesser;
import cn.edu.nwpu.rj416.util.objects.MByteBuffer;
import cn.edu.nwpu.rj416.util.types.BytesUtil;

public class MotpInt32ArrayProcesser implements MotpTypeProcesser {

	@Override
	public byte getMosType() {
		return MotpType.INT32_ARR;
	}
	
	@Override
	public int writeValue(MByteBuffer byteBuffer, Object o) {
		int[] arr = (int[])o;
		byte[] bytes = BytesUtil.toBytes(arr);
		int len = byteBuffer.appendMVLInt(arr.length);
		len += byteBuffer.appendBytes(bytes);
		
		return len;
	}

	@Override
	public Object read(MByteBuffer byteBuffer) {
		int len = (int)byteBuffer.readMVLInt().getValue();
		byte[] bytes = byteBuffer.readBytes(len * 4);
		int[] arr = BytesUtil.getIntArr(bytes, 0, len);
		
		return arr;
	}
}
