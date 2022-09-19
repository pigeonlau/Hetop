package cn.edu.nwpu.rj416.util.types;

import java.math.BigDecimal;
import java.math.BigInteger;

//整型数工具
public abstract class IntUtil {
	//将其他数据类型转换成整型数据类型
	public static int toInt(Object o) {
		if (o == null) {
			return 0;
		}
		if (o instanceof String) {
				String v = (String)o;
				try {
				return Integer.parseInt(v);
				} catch (NumberFormatException e) {
					return 0;
				}
		} else if (o instanceof Long) {
			Long v = (Long)o;
			return v.intValue();
		} else if (o instanceof Integer) {
			Integer v = (Integer)o;
			return v;
		} else if (o instanceof Short) {
			Short v = (Short)o;
			return v.intValue();
		} else if (o instanceof Byte) {
			Byte v = (Byte)o;
			return v.intValue();
		} else if (o instanceof Double) {
			Double v = (Double)o;
			return v.intValue();
		} else if (o instanceof Float) {
			Float v = (Float)o;
			return v.intValue();
		} else if (o instanceof BigDecimal) {
			BigDecimal v = (BigDecimal)o;
			return v.intValue();
		} else if (o instanceof BigInteger) {
			BigInteger v = (BigInteger)o;
			return v.intValue();
		} else if (o instanceof Character) {
			Character v = (Character)o;
			return v.charValue();
		}
		
		return 0;
	}
	
	//两数相除，补足被除数的余数
	public static int alignUp(int n, int inteval) {
		if (n % inteval > 0) {
			return (n / inteval + 1) * inteval;
		} else {
			return n;
		}
	}
	
	//两数相除，舍弃被除数的余数
	public static int alignDown(int n, int inteval) {
		return (n / inteval) * inteval;
	}
	
	//两数相除，向上取整
	public static int dividRoundUp(int a, int b) {
		if (a % b > 0) {
			return a / b + 1;
		} else {
			return a / b;
		}
	}
	
	//两数相除，四舍五入
	public static int dividHalfUp(int a, int b) {
		long m = a % b;
		if (m >= (b / 2)) {
			return a / b + 1;
		} else {
			return a / b;
		}
	}
}
