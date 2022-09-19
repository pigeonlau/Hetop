package cn.edu.nwpu.rj416.util.types;

import java.math.BigDecimal;
import java.math.BigInteger;

//浮点数工具（将其他数据类型转成浮点数类型）
public abstract class FloatUtil {

	public static float toFloat(Object o) {
		if (o == null) {
			return 0;
		}
		if (o instanceof String) {
			String v = (String)o;
			try {
				return Float.parseFloat(v); //字符串类型要调用解析方法
			} catch (NumberFormatException e) {
				return 0;
			}
		} else if (o instanceof Long) {
			Long v = (Long)o;
			return v.floatValue();
		} else if (o instanceof Integer) {
			Integer v = (Integer)o;
			return v.floatValue();
		} else if (o instanceof Short) {
			Short v = (Short)o;
			return v.floatValue();
		} else if (o instanceof Byte) {
			Byte v = (Byte)o;
			return v.floatValue();
		} else if (o instanceof Double) {
			Double v = (Double)o;
			return v.floatValue();
		} else if (o instanceof Float) {
			Float v = (Float)o;
			return v;
		} else if (o instanceof BigDecimal) {
			BigDecimal v = (BigDecimal)o;
			return v.floatValue();
		} else if (o instanceof BigInteger) {
			BigInteger v = (BigInteger)o;
			return v.floatValue();
		} else if (o instanceof Character) {
			Character v = (Character)o;
			return v.charValue();
		}//基本数据类型获取其浮点数值返回
		
		return 0;
	}
}
