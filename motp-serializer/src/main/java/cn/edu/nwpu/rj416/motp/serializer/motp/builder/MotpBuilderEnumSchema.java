package cn.edu.nwpu.rj416.motp.serializer.motp.builder;



import cn.edu.nwpu.rj416.motp.serializer.motp.schema.MotpSchema;
import cn.edu.nwpu.rj416.util.objects.MByteBuffer;
import cn.edu.nwpu.rj416.util.objects.MVLInt;
import cn.edu.nwpu.rj416.util.types.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 对象结构描述</br>
 * 
 * 
 * 序列化结构：</br>
 * [enumSize:MVLInt]List<([valueOrdinal:MVLInt][valueNameLen:varInt][valueName:String])></br>
 * 
 * 
 * @author MilesLiu
 *
 * 2019年11月28日 下午1:25:39
 */
class MotpBuilderEnumSchema extends MotpSchema {
	
	private Map<Integer, MotpBuilderEnumSchemaValue> values = new HashMap<>();
	
	
	public MotpBuilderEnumSchema() {
		super();
		this.setType(MotpSchema.EnumSchema);
	}

	@Override
	public byte[] getBytes() {
		//生成SchemaContent，即枚举值列表
		MByteBuffer schemaContent = new MByteBuffer();
		
		List<MotpBuilderEnumSchemaValue> valueList = new ArrayList<>();
		
		for (MotpBuilderEnumSchemaValue v : this.values.values()) {
			if (!v.isInUse()) {
				continue;
			}
			valueList.add(v);
		}
		
		schemaContent.appendMVLInt(valueList.size());
		
		for (MotpBuilderEnumSchemaValue v : valueList) {
			schemaContent.appendMVLInt(new MVLInt(v.getOridnal()));
			byte[] nameBytes = StringUtil.getBytes(v.getName());
			schemaContent.appendMVLInt(new MVLInt(nameBytes.length));
			schemaContent.appendBytes(nameBytes);
		}
		
		return schemaContent.getBytes();
	}

	public MByteBuffer getByteBuffer(){
		MByteBuffer schemaContent = new MByteBuffer();

		List<MotpBuilderEnumSchemaValue> valueList = new ArrayList<>();

		for (MotpBuilderEnumSchemaValue v : this.values.values()) {
			if (!v.isInUse()) {
				continue;
			}
			valueList.add(v);
		}

		schemaContent.appendMVLInt(valueList.size());

		for (MotpBuilderEnumSchemaValue v : valueList) {
			schemaContent.appendMVLInt(new MVLInt(v.getOridnal()));
			byte[] nameBytes = StringUtil.getBytes(v.getName());
			schemaContent.appendMVLInt(new MVLInt(nameBytes.length));
			schemaContent.appendBytes(nameBytes);
		}

		return schemaContent;
	}

	@Override
	public void appendBytes(MByteBuffer buffer) {

		List<MotpBuilderEnumSchemaValue> valueList = new ArrayList<>();

		for (MotpBuilderEnumSchemaValue v : this.values.values()) {
			if (!v.isInUse()) {
				continue;
			}
			valueList.add(v);
		}

		buffer.appendMVLInt(valueList.size());

		for (MotpBuilderEnumSchemaValue v : valueList) {
			buffer.appendMVLInt(new MVLInt(v.getOridnal()));
			byte[] nameBytes = StringUtil.getBytes(v.getName());
			buffer.appendMVLInt(new MVLInt(nameBytes.length));
			buffer.appendBytes(nameBytes);
		}

	}

	public MotpBuilderEnumSchemaValue getValueByOrdinal(int n) {
		return this.values.get(n);
	}
	
	public void addValue(int ordinal, String name) {
		MotpBuilderEnumSchemaValue ev = new MotpBuilderEnumSchemaValue();
		ev.setOridnal(ordinal);
		ev.setName(name);
		this.values.put(ordinal, ev);
	}
}
