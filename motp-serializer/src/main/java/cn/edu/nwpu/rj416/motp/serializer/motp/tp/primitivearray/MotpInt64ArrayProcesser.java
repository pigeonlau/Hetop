package cn.edu.nwpu.rj416.motp.serializer.motp.tp.primitivearray;


import cn.edu.nwpu.rj416.motp.serializer.motp.MotpType;
import cn.edu.nwpu.rj416.motp.serializer.motp.tp.MotpTypeProcesser;
import cn.edu.nwpu.rj416.util.objects.MByteBuffer;
import cn.edu.nwpu.rj416.util.objects.MLinkedBuffer;
import cn.edu.nwpu.rj416.util.types.BytesUtil;

public class MotpInt64ArrayProcesser implements MotpTypeProcesser {

	@Override
	public byte getMosType() {
		return MotpType.INT64_ARR;
	}
	
	@Override
	public void writeValue(MLinkedBuffer byteBuffer, Object o) {
		long[] arr = (long[])o;
		byte[] bytes = BytesUtil.toBytes(arr);
		byteBuffer.appendMVLInt(arr.length);
		byteBuffer.appendBytes(bytes);

	}

	@Override
	public Object read(MByteBuffer byteBuffer) {
		int len = (int)byteBuffer.readMVLInt().getValue();
		byte[] bytes = byteBuffer.readBytes(len * 8);
		long[] arr = BytesUtil.getLongArr(bytes, 0, len);
		
		return arr;
	}
}
