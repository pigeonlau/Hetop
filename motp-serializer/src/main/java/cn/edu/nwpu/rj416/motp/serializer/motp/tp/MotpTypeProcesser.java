package cn.edu.nwpu.rj416.motp.serializer.motp.tp;


import cn.edu.nwpu.rj416.util.objects.MByteBuffer;

public interface MotpTypeProcesser {
	
	public byte getMosType();
	
	public int writeValue(MByteBuffer byteBuffer, Object o);

	public Object read(MByteBuffer byteBuffer);
}
