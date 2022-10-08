package cn.edu.nwpu.rj416.motp.serializer.motp.tp;


import cn.edu.nwpu.rj416.util.objects.MByteBuffer;
import cn.edu.nwpu.rj416.util.objects.MLinkedBuffer;

public interface MotpTypeProcesser {
	
	public byte getMosType();
	
	public void writeValue(MLinkedBuffer byteBuffer, Object o);

	public Object read(MByteBuffer byteBuffer);
}
