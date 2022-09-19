package cn.edu.nwpu.rj416.util.types;

import java.math.BigDecimal;
import java.math.BigInteger;

//长整型数工具（将其他数据类型转成长整型数类型）
public abstract class LongUtil {

	public static long toLong(Object o) {
		if (o == null) {
			return 0L;
		}
		if (o instanceof String) {
				String v = (String)o;
				try {
				return Long.parseLong(v);
				} catch (NumberFormatException e) {
					return 0;
				}
		} else if (o instanceof Long) {
			Long v = (Long)o;
			return v;
		} else if (o instanceof Integer) {
			Integer v = (Integer)o;
			return v.longValue();
		} else if (o instanceof Short) {
			Short v = (Short)o;
			return v.longValue();
		} else if (o instanceof Byte) {
			Byte v = (Byte)o;
			return v.longValue();
		} else if (o instanceof Double) {
			Double v = (Double)o;
			return v.longValue();
		} else if (o instanceof Float) {
			Float v = (Float)o;
			return v.longValue();
		} else if (o instanceof BigDecimal) {
			BigDecimal v = (BigDecimal)o;
			return v.longValue();
		} else if (o instanceof BigInteger) {
			BigInteger v = (BigInteger)o;
			return v.longValue();
		} else if (o instanceof Character) {
			Character v = (Character)o;
			return v.charValue();
		}
		
		return 0L;
	}
	
	
	public static long alignUp(long n, long inteval) {
		if (n % inteval > 0) {
			return (n / inteval + 1) / inteval;
		} else {
			return n;
		}
	}
	
	public static long alignDown(long n, long inteval) {
		return (n / inteval) * inteval;
	}
	
	public static long dividRoundUp(long a, long b) {
		if (a % b > 0) {
			return a / b + 1;
		} else {
			return a / b;
		}
	}
	
	public static long dividHalfUp(long a, long b) {
		long m = a % b;
		if (m >= (b / 2)) {
			return a / b + 1;
		} else {
			return a / b;
		}
	}
}
