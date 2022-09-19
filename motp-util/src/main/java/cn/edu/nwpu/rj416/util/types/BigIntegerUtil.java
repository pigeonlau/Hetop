package cn.edu.nwpu.rj416.util.types;

import java.math.BigDecimal;
import java.math.BigInteger;

//大整数工具
public abstract class BigIntegerUtil {
	//将传参对象转成大整数类
	public static BigInteger toBigInteger(Object o) { 
		if (o == null) {
			return BigInteger.ZERO;
		}//对象为空，返回0的大整数
		if (o instanceof String) { //对象属于字符串类型，强转后再用字符串对象创建大整数对象
			String v = (String)o;
			return new BigInteger(v);
		} else if (o instanceof Long) {
			Long v = (Long)o;
			return BigInteger.valueOf(v);
		} else if (o instanceof Integer) {
			Integer v = (Integer)o;
			return BigInteger.valueOf(v);
		} else if (o instanceof Short) {
			Short v = (Short)o;
			return BigInteger.valueOf(v);
		} else if (o instanceof Byte) {
			Byte v = (Byte)o;
			return BigInteger.valueOf(v);
		} else if (o instanceof Double) {
			Double v = (Double)o;
			return BigInteger.valueOf(v.longValue());
		} else if (o instanceof Float) {
			Float v = (Float)o;
			return BigInteger.valueOf(v.longValue());
		} else if (o instanceof BigDecimal) {
			BigDecimal v = (BigDecimal)o;
			return v.toBigInteger();
		} else if (o instanceof BigInteger) {
			BigInteger v = (BigInteger)o;
			return v;
		}
		
		return BigInteger.ZERO;
	}
}
