package cn.edu.nwpu.rj416.util.types;

import java.math.BigDecimal;
import java.math.BigInteger;

//短整型数工具（将其他数据类型转成短整型数类型）
public abstract class ShortUtil {
	public static short toShort(Object o) {
		if (o == null) {
			return 0;
		}
		if (o instanceof String) {
				String v = (String)o;
				try {
				return Short.parseShort(v);
				} catch (NumberFormatException e) {
					return 0;
				}
		} else if (o instanceof Long) {
			Long v = (Long)o;
			return v.shortValue();
		} else if (o instanceof Integer) {
			Integer v = (Integer)o;
			return v.shortValue();
		} else if (o instanceof Short) {
			Short v = (Short)o;
			return v;
		} else if (o instanceof Byte) {
			Byte v = (Byte)o;
			return v;
		} else if (o instanceof Character) {
			Character v = (Character)o;
			return (short)v.charValue();
		} else if (o instanceof Double) {
			Double v = (Double)o;
			return v.shortValue();
		} else if (o instanceof Float) {
			Float v = (Float)o;
			return v.shortValue();
		} else if (o instanceof BigDecimal) {
			BigDecimal v = (BigDecimal)o;
			return v.shortValue();
		} else if (o instanceof BigInteger) {
			BigInteger v = (BigInteger)o;
			return v.shortValue();
		}
		
		return 0;
	}
}
