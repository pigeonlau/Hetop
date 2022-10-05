package cn.edu.nwpu.rj416.motp.serializer.motp.tp.primitivearray;


import cn.edu.nwpu.rj416.motp.serializer.motp.MotpType;
import cn.edu.nwpu.rj416.motp.serializer.motp.tp.MotpTypeProcesser;
import cn.edu.nwpu.rj416.util.objects.MByteBuffer;
import cn.edu.nwpu.rj416.util.objects.MLinkedBuffer;
import cn.edu.nwpu.rj416.util.types.BytesUtil;

public class MotpFloatArrayProcesser implements MotpTypeProcesser {

	@Override
	public byte getMosType() {	
		return MotpType.FLOAT_ARR;
	}
	
	@Override
	public void writeValue(MLinkedBuffer byteBuffer, Object o) {
		float[] arr = (float[])o;
		byte[] bytes = BytesUtil.toBytes(arr);
		byteBuffer.appendMVLInt(arr.length);
		byteBuffer.appendBytes(bytes);
	}

	@Override
	public Object read(MByteBuffer byteBuffer) {
		int len = (int)byteBuffer.readMVLInt().getValue();
		byte[] bytes = byteBuffer.readBytes(len * 4);
		float[] arr = BytesUtil.getFloatArr(bytes, 0, len);
		
		return arr;
	}
}
