package cn.edu.nwpu.rj416.util.types;

import java.math.BigDecimal;
import java.math.BigInteger;

//字节码工具（将其他数据类型转成字节码）
public abstract class ByteUtil {
	public static final byte BYTE_0 = 0;
	public static final byte BYTE_1 = 1;
	
	public static byte toByte(Object o) {
		if (o == null) {
			return BYTE_0;
		}
		if (o instanceof String) {
				String v = (String)o;
				try {
				return Byte.parseByte(v); //字符串类型要调用解析方法
				} catch (NumberFormatException e) {
					return 0;
				}
		} else if (o instanceof Long) {
			Long v = (Long)o;
			return v.byteValue();
		} else if (o instanceof Integer) {
			Integer v = (Integer)o;
			return v.byteValue();
		} else if (o instanceof Short) {
			Short v = (Short)o;
			return v.byteValue();
		} else if (o instanceof Byte) {
			Byte v = (Byte)o;
			return v.byteValue();
		} else if (o instanceof Double) {
			Double v = (Double)o;
			return v.byteValue();
		} else if (o instanceof Float) {
			Float v = (Float)o;
			return v.byteValue();
		} else if (o instanceof BigDecimal) {
			BigDecimal v = (BigDecimal)o;
			return v.byteValue();
		} else if (o instanceof BigInteger) {
			BigInteger v = (BigInteger)o;
			return v.byteValue();
		} else if (o instanceof Character) {
			Character v = (Character)o;
			return (byte)v.charValue();
		} //其他基本数据类型获取其字节码值即可
		
		return 0;
	}
}
