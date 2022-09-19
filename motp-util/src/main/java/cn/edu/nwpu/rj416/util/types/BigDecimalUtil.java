package cn.edu.nwpu.rj416.util.types;

import java.math.BigDecimal;
import java.math.BigInteger;

//高精度小数工具
public abstract class BigDecimalUtil {
	//将传参对象转成高精度小数类
	public static BigDecimal toBigDecimal(Object o) {
		if (o == null) {
			return BigDecimal.ZERO;
		} //对象为空，返回0的高精度小数
		if (o instanceof String) { //对象属于字符串类型，强转后用字符串对象创建高精度小数对象
			String v = (String)o;
			return new BigDecimal(v);
		} else if (o instanceof Long) {
			Long v = (Long)o;
			return BigDecimal.valueOf(v);
		} else if (o instanceof Integer) {
			Integer v = (Integer)o;
			return BigDecimal.valueOf(v);
		} else if (o instanceof Short) {
			Short v = (Short)o;
			return BigDecimal.valueOf(v);
		} else if (o instanceof Byte) {
			Byte v = (Byte)o;
			return BigDecimal.valueOf(v);
		} else if (o instanceof Double) {
			Double v = (Double)o;
			return BigDecimal.valueOf(v);
		} else if (o instanceof Float) {
			Float v = (Float)o;
			return BigDecimal.valueOf(v);
		} else if (o instanceof BigDecimal) {
			BigDecimal v = (BigDecimal)o;
			return v;
		} else if (o instanceof BigInteger) {
			BigInteger v = (BigInteger)o;
			return new BigDecimal(v);
		}
		
		return BigDecimal.ZERO;
	}
}
