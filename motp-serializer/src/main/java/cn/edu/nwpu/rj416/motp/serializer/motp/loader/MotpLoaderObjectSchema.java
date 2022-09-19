package cn.edu.nwpu.rj416.motp.serializer.motp.loader;



import cn.edu.nwpu.rj416.motp.serializer.motp.schema.MotpSchema;
import cn.edu.nwpu.rj416.util.objects.MByteBuffer;
import cn.edu.nwpu.rj416.util.objects.MVLInt;
import cn.edu.nwpu.rj416.util.types.StringUtil;

import java.util.HashMap;
import java.util.Map;


/**
 * 对象结构描述</br>
 * 
 * @author MilesLiu
 *
 * 2019年11月28日 下午1:25:39
 */
public class MotpLoaderObjectSchema extends MotpSchema {
	private String typeName;
	private Map<Integer, String> columns = new HashMap<>();
	public MotpLoaderObjectSchema() {
		super();
		this.setType(MotpSchema.ObjectSchema);
	}

	@Override
	public byte[] getBytes() {
		MByteBuffer buffer = new MByteBuffer();
		buffer.appendMVLInt(new MVLInt(this.getNumber()));
		buffer.appendByte(this.getType());

		buffer.appendMVLInt(this.columns.size());
		
		for (Map.Entry<Integer, String> c : this.columns.entrySet()) {
			buffer.appendMVLInt(c.getKey());
			byte[] nameBytes = StringUtil.getBytes(c.getValue());
			buffer.appendMVLInt(nameBytes.length);
			buffer.appendBytes(nameBytes);
		}
		
		
		return buffer.getBytes();
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getColumnByNumber(int number) {
		return this.columns.get(number);
	}
	
	public void addColumn(int number, String name) {
		this.columns.put(number, name);
	}
}
