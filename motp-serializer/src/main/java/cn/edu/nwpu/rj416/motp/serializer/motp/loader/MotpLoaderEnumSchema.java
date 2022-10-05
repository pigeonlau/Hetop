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
public class MotpLoaderEnumSchema extends MotpSchema {
	
	private Map<Integer, String> values = new HashMap<>();
	
	
	public MotpLoaderEnumSchema() {
		super();
		this.setType(MotpSchema.EnumSchema);
	}

	@Override
	public byte[] getBytes() {
		//生成SchemaContent，即枚举值列表
		MByteBuffer schemaContent = new MByteBuffer();
		for (Map.Entry<Integer, String> v : this.values.entrySet()) {
			schemaContent.appendMVLInt(v.getKey());
			byte[] nameBytes = StringUtil.getBytes(v.getValue());
			schemaContent.appendMVLInt(new MVLInt(nameBytes.length));
			schemaContent.appendBytes(nameBytes);
		}
		
		
		MByteBuffer buffer = new MByteBuffer();
		//写入SchemaNumber
		buffer.appendMVLInt(new MVLInt(this.getNumber()));
		//写入SchemaType
		buffer.appendByte(this.getType());
		//写入SchemaContent的长度
		buffer.appendMVLInt(new MVLInt(schemaContent.getSize()));
		//写入SchemaContent的内容
		if (schemaContent.getSize() > 0) {
			buffer.appendBytes(schemaContent.getBytes());
		}
		
		return buffer.getBytes();
	}

	@Override
	public MByteBuffer getByteBuffer() {
		return null;
	}

	public String getValueByOrdinal(int n) {
		return this.values.get(n);
	}
	
	public void addValue(int ordinal, String name) {
		this.values.put(ordinal, name);
	}
}
