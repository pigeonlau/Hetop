package cn.edu.nwpu.rj416.util.types;

import java.math.BigDecimal;
import java.math.BigInteger;

//字符型工具（将其他数据类型转为字符型）
public abstract class CharUtil {
	
	public static char toChar(Object o) {
		if (o == null) {
			return 0;
		}
		if (o instanceof String) {
			String v = (String)o;
			if (v.length() == 0) {
				return 0;
			} else {
				return v.charAt(0); //字符串类型返回字符串第一个字符的char值
			}
		} else if (o instanceof Long) {
			Long v = (Long)o;
			return (char)v.intValue();
		} else if (o instanceof Integer) {
			Integer v = (Integer)o;
			return (char)v.intValue();
		} else if (o instanceof Short) {
			Short v = (Short)o;
			return (char)v.intValue();
		} else if (o instanceof Byte) {
			Byte v = (Byte)o;
			return (char)v.intValue();
		} else if (o instanceof Double) {
			Double v = (Double)o;
			return (char)v.intValue();
		} else if (o instanceof Float) {
			Float v = (Float)o;
			return (char)v.intValue();
		} else if (o instanceof BigDecimal) {
			BigDecimal v = (BigDecimal)o;
			return (char)v.intValue();
		} else if (o instanceof BigInteger) {
			BigInteger v = (BigInteger)o;
			return (char)v.intValue();
		} //其他基本数据类型获取其int值后再强转为char
		
		return 0;
	}
	
	public static boolean isDigit(char c) {
		return (c >= '0' && c <= '9');
	}

	public static boolean isUpperLetter(char c) {
		return (c >= 'A' && c <= 'Z');
	}
	
	public static boolean isLowerLetter(char c) {
		return (c >= 'a' && c <= 'z');
	}
	
	public static boolean isLetter(char c) {
		return isUpperLetter(c) || isLowerLetter(c);
	}
}
