package cn.edu.nwpu.rj416.motp.serializer.motp.loader;



import cn.edu.nwpu.rj416.motp.serializer.motp.MotpType;
import cn.edu.nwpu.rj416.motp.serializer.motp.schema.MotpSchema;
import cn.edu.nwpu.rj416.util.objects.MByteBuffer;
import cn.edu.nwpu.rj416.util.objects.MVLInt;
import cn.edu.nwpu.rj416.util.text.format.MFmtLine;
import cn.edu.nwpu.rj416.util.text.format.MFormatable;
import cn.edu.nwpu.rj416.util.types.StringUtil;

import java.util.ArrayList;
import java.util.List;


public class MotpExplorer implements MFormatable {
	
	private byte[] motpBytes;
	
	public MotpExplorer(byte[] motpBytes) {
		super();
		this.motpBytes = motpBytes;
	}

	@Override
	public List<MFmtLine> format(int level) {
		List<MFmtLine> lines = new ArrayList<>();
		MFmtLine line = new MFmtLine(level);
		line.appendToken("MOTP");
		lines.add(line);

		MByteBuffer buffer = new MByteBuffer(this.motpBytes);
		int pos;
		
		//读取SchemaLength
		pos = buffer.getOffset();
		MVLInt schemaLen = buffer.readMVLInt();
		lines.addAll(this.formatBytes(buffer.readBytes(pos, buffer.getOffset() - pos), level + 1));
		lines.add(MFmtLine.create(level + 2)
				.appendToken(String.format("SchemaLength : %d(%d)", 
						schemaLen.getValue(), schemaLen.getLen())));

		//读取DataLength
		pos = buffer.getOffset();
		MVLInt dataLen = buffer.readMVLInt();
		lines.addAll(this.formatBytes(buffer.readBytes(pos, buffer.getOffset() - pos), level + 1));
		lines.add(MFmtLine.create(level + 2)
				.appendToken(String.format("DataLength : %d(%d)", 
						dataLen.getValue(), dataLen.getLen())));
		lines.add(MFmtLine.create(level)
				.appendToken("---- Schema List ----"));
		
		byte[] schemaBytes = buffer.readBytes(schemaLen.castToInteger());
		lines.addAll(this.formatSchema(schemaBytes, level + 1));
		

		lines.add(MFmtLine.create(level)
				.appendToken("---- Data ----"));
		byte[] dataBytes = buffer.readBytes(dataLen.castToInteger());
		lines.addAll(this.formatData(dataBytes, level + 1));
		return lines;
	}
	
	private List<MFmtLine> formatBytes(byte[] bytes, int level) {
		List<MFmtLine> lines = new ArrayList<>();
		MFmtLine current = new MFmtLine(level);
		lines.add(current);
		for (int i = 0; i < bytes.length; i++) {
			if (i > 0 && i % 20 == 0) {
				current = new MFmtLine(level);
				lines.add(current);
			}
			current.appendToken(String.format("%02X ", bytes[i]));
		}
		return lines;
	}
	
	private List<MFmtLine> formatSchema(byte[] bytes, int level) {
		List<MFmtLine> lines = new ArrayList<>();
		MByteBuffer schemaBuffer = new MByteBuffer(bytes);
		int pos = schemaBuffer.getOffset();
		while (pos < schemaBuffer.getSize()) {
			MVLInt number = schemaBuffer.readMVLInt();
			byte type = schemaBuffer.readByte();
			MVLInt len = schemaBuffer.readMVLInt();
			byte[] schemaHeadBytes = schemaBuffer.readBytes(pos, schemaBuffer.getOffset() - pos);
			lines.add(MFmtLine.create(level).appendToken(String.format(
					"---- Schema %d ----", number.getValue())));
			lines.addAll(this.formatBytes(schemaHeadBytes, level));
			lines.add(MFmtLine.create(level + 1)
					.appendToken(String.format(
							"Number : %d, Type : %d, Length : %d", 
							number.castToInteger(), type, len.castToInteger())));
			if (type == MotpSchema.EnumSchema) {
				lines.addAll(this.formatEnumSchema(schemaBuffer, len.castToInteger(), level));
			} else if (type == MotpSchema.ObjectSchema) {
				lines.addAll(this.formatObjectSchema(schemaBuffer, len.castToInteger(), level));
			} else {
				byte[] content = schemaBuffer.readBytes(len.castToInteger());
				lines.addAll(this.formatBytes(content, level));
				lines.add(MFmtLine.create(level + 1)
						.appendToken(String.format("未知的Schema类型：%d", type)));
			}
			
			pos = schemaBuffer.getOffset();
		}
		
		return lines;
	}
	
	private List<MFmtLine> formatEnumSchema(MByteBuffer buffer, int len, int level) {
		List<MFmtLine> lines = new ArrayList<>();
		int pos = buffer.getOffset();
		int end = pos + len;
		
		int size = buffer.readMVLInt().castToInteger();
		byte[] sizeBytes = buffer.readBytes(pos, buffer.getOffset() - pos);
		lines.addAll(this.formatBytes(sizeBytes, level));
		lines.add(MFmtLine.create(level + 1)
				.appendToken(String.format(
						"EnumConstraitCount : %d", size)));
		for (int i = 0; i < size; i++) {
			MVLInt ordinal = buffer.readMVLInt();
			lines.addAll(this.formatBytes(buffer.readBytes(pos, buffer.getOffset() - pos), level));
			lines.add(MFmtLine.create(level + 1).appendToken(String.format(
					"Ordinal:%d", ordinal.getValue())));
			
			pos = buffer.getOffset();
			MVLInt nameLen = buffer.readMVLInt();
			lines.addAll(this.formatBytes(buffer.readBytes(pos, buffer.getOffset() - pos), level));
			lines.add(MFmtLine.create(level + 1).appendToken(String.format(
					"NameLength:%d", nameLen.getValue())));
			
			byte[] nameBytes = buffer.readBytes(nameLen.castToInteger());
			lines.addAll(this.formatBytes(nameBytes, level));
			String name = StringUtil.newString(nameBytes);
			lines.add(MFmtLine.create(level + 1).appendToken(String.format(
					"Name:%s", name)));
			pos = buffer.getOffset();
		}
		if (pos > end) {
			lines.add(MFmtLine.create(level).appendToken(String.format(
					"Schema长度有误, Offset:%d, Len:%d", pos, len)));
		}
		
		return lines;
	}
	
	private List<MFmtLine> formatObjectSchema(MByteBuffer buffer, int len, int level) {
		List<MFmtLine> lines = new ArrayList<>();
		int pos = buffer.getOffset();
		int end = pos + len;
		
		int size = buffer.readMVLInt().castToInteger();
		byte[] sizeBytes = buffer.readBytes(pos, buffer.getOffset() - pos);
		lines.addAll(this.formatBytes(sizeBytes, level));
		lines.add(MFmtLine.create(level + 1)
				.appendToken(String.format(
						"ObjectFieldCount : %d", size)));
		for (int i = 0; i < size; i++) {
			pos = buffer.getOffset();
			MVLInt ordinal = buffer.readMVLInt();
			lines.addAll(this.formatBytes(buffer.readBytes(pos, buffer.getOffset() - pos), level));
			lines.add(MFmtLine.create(level + 1).appendToken(String.format(
					"FieldNumber:%d", ordinal.getValue())));
			
			pos = buffer.getOffset();
			MVLInt nameLen = buffer.readMVLInt();
			lines.addAll(this.formatBytes(buffer.readBytes(pos, buffer.getOffset() - pos), level));
			lines.add(MFmtLine.create(level + 1).appendToken(String.format(
					"NameLength:%d", nameLen.getValue())));
			
			byte[] nameBytes = buffer.readBytes(nameLen.castToInteger());
			lines.addAll(this.formatBytes(nameBytes, level));
			String name = StringUtil.newString(nameBytes);
			lines.add(MFmtLine.create(level + 1).appendToken(String.format(
					"Name:%s", name)));
			pos = buffer.getOffset();
		}
		if (pos > end) {
			lines.add(MFmtLine.create(level).appendToken(String.format(
					"Schema长度有误, Offset:%d, Len:%d", pos, len)));
		}
		return lines;
	}

	private List<MFmtLine> formatData(byte[] bytes, int level) {
		List<MFmtLine> lines = new ArrayList<>();
		MByteBuffer dataBuffer = new MByteBuffer(bytes);
		while (dataBuffer.getOffset() < dataBuffer.getSize()) {
			lines.addAll(this.formatData(dataBuffer, level));
		}
		
		return lines;
	}

	private List<MFmtLine> formatData(MByteBuffer dataBuffer, int level) {
		List<MFmtLine> lines = new ArrayList<>();
		byte type = dataBuffer.readByte();
		switch (type) {
		case MotpType.VOID:
			lines.add(MFmtLine.create(level)
					.appendToken(String.format("VOID(0x%02X)[0]", type)));
			break;
		case MotpType.INT8:
			byte byteValue = dataBuffer.readByte();
			lines.add(MFmtLine.create(level)
					.appendToken(String.format("INT8(0x%02X)[1] %d", type, byteValue)));
			break;
		case MotpType.INT16:
			short shortValue = dataBuffer.readShort();
			lines.add(MFmtLine.create(level)
					.appendToken(String.format("INT16(0x%02X)[2] %d", type, shortValue)));
			break;
		case MotpType.INT32:
			int intValue = dataBuffer.readInt();
			lines.add(MFmtLine.create(level)
					.appendToken(String.format("INT32(0x%02X)[4] %d", type, intValue)));
			break;
		case MotpType.INT64:
			long longValue = dataBuffer.readLong();
			lines.add(MFmtLine.create(level)
					.appendToken(String.format("INT64(0x%02X)[8] %d", type, longValue)));
			break;
		case MotpType.FLOAT:
			float floatValue = dataBuffer.readFloat();
			lines.add(MFmtLine.create(level)
					.appendToken(String.format("Float(0x%02X)[4] %f", type, floatValue)));
			break;
		case MotpType.DOUBLE:
			double doubleValue = dataBuffer.readDouble();
			lines.add(MFmtLine.create(level)
					.appendToken(String.format("Double(0x%02X)[8] %f", type, doubleValue)));
			break;
		case MotpType.CHAR:
			char charValue = dataBuffer.readChar();
			lines.add(MFmtLine.create(level)
					.appendToken(String.format("CHAR(0x%02X)[2] %c", type, charValue)));
			break;
		case MotpType.INT_VL:
			MVLInt vlIntValue = dataBuffer.readMVLInt();
			lines.add(MFmtLine.create(level)
					.appendToken(String.format(
							"INT_VL(0x%02X)[%d] %d", 
							type, vlIntValue.getLen(), 
							vlIntValue.getValue())));
			break;
		case MotpType.STRING:
			MVLInt strLen = dataBuffer.readMVLInt();
			byte[] strBytes = dataBuffer.readBytes(strLen.castToInteger());
			String strValue = StringUtil.newString(strBytes);
			lines.add(MFmtLine.create(level)
					.appendToken(String.format(
							"STRING(0x%02X)[%d] %s", 
							type, strLen.getValue(), 
							strValue)));
			break;
		case MotpType.ENUM:
			MVLInt schemaNumber = dataBuffer.readMVLInt();
			MVLInt ordinal = dataBuffer.readMVLInt();

			lines.add(MFmtLine.create(level)
					.appendToken(String.format(
							"Enum(0x%02X) %d.%d", 
							type, schemaNumber.getValue(), 
							ordinal.getValue())));
			break;
		case MotpType.BYTE_ARR:
			MVLInt bytesLen = dataBuffer.readMVLInt();
			byte[] bytesValue = dataBuffer.readBytes(bytesLen.castToInteger());

			lines.add(MFmtLine.create(level)
					.appendToken(String.format(
							"Bytes(0x%02X) [%d]", 
							type, bytesLen.getValue())));
			lines.addAll(this.formatBytes(bytesValue, level + 1));
			break;
		case MotpType.OBJECT:
			MVLInt objectSchemaNumber = dataBuffer.readMVLInt();
			MVLInt objectDataLen = dataBuffer.readMVLInt();
			byte[] objectData = dataBuffer.readBytes(objectDataLen.castToInteger());

			lines.add(MFmtLine.create(level)
					.appendToken(String.format(
							"Object(0x%02X) [%d] SchemaNumber : %d", 
							type, objectDataLen.getValue(), objectSchemaNumber.getValue())));
			//lines.addAll(this.formatBytes(objectData, level + 1));
			lines.addAll(this.formatObjectData(objectData, level + 1));
			break;
		case MotpType.LIST:
			int listSize = dataBuffer.readMVLInt().castToInteger();
			lines.add(MFmtLine.create(level)
					.appendToken(String.format(
							"LIST(0x%02X) [%d]", 
							type, listSize)));
			lines.addAll(this.formatListData(dataBuffer, listSize, level + 1));
			break;
		case MotpType.MAP:
			int mapSize = dataBuffer.readMVLInt().castToInteger();
			lines.add(MFmtLine.create(level)
					.appendToken(String.format(
							"MAP(0x%02X) [%d]", 
							type, mapSize)));
			lines.addAll(this.formatMapData(dataBuffer, mapSize, level + 1));
			break;
		case MotpType.SET:
			int setSize = dataBuffer.readMVLInt().castToInteger();
			lines.add(MFmtLine.create(level)
					.appendToken(String.format(
							"SET(%02X) [%d]", 
							type, setSize)));
			lines.addAll(this.formatSetData(dataBuffer, setSize, level + 1));
			break;
		default:
			lines.add(MFmtLine.create(level)
					.appendToken(String.format(
							"无法识别的类型(%02X)", 
							type)));
			return lines;
		}
		
		return lines;
	}
	

	private List<MFmtLine> formatObjectData(byte[] bytes, int level) {
		List<MFmtLine> lines = new ArrayList<>();
		MByteBuffer buffer = new MByteBuffer(bytes);
		while (buffer.getOffset() < buffer.getSize()) {
			int fieldNumber = buffer.readMVLInt().castToInteger();
			MFmtLine fn = MFmtLine.create(level)
					.appendToken(String.format(
							"FieldNumber:%d", 
							fieldNumber));
			lines.add(fn);
			//FormatUtil.print(fn);
			
			List<MFmtLine> dLines = this.formatData(buffer, level + 1); 
			lines.addAll(dLines);
			//FormatUtil.print(dLines);
		}
		return lines;
	}
	
	private List<MFmtLine> formatListData(MByteBuffer buffer, int size, int level) {
		List<MFmtLine> lines = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			List<MFmtLine> dLines = this.formatData(buffer, level);
			lines.addAll(dLines);
		}
		return lines;
	}
	
	private List<MFmtLine> formatSetData(MByteBuffer buffer, int size, int level) {
		List<MFmtLine> lines = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			List<MFmtLine> dLines = this.formatData(buffer, level);
			lines.addAll(dLines);
		}
		return lines;
	}
	
	private List<MFmtLine> formatMapData(MByteBuffer buffer, int size, int level) {
		List<MFmtLine> lines = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			lines.add(MFmtLine.create(level + 1).appendToken("[Key]"));
			List<MFmtLine> key = this.formatData(buffer, level + 1);
			lines.addAll(key);
			lines.add(MFmtLine.create(level + 1).appendToken("[Value]"));
			List<MFmtLine> value = this.formatData(buffer, level + 1);
			lines.addAll(value);
		}
		return lines;
	}
}
