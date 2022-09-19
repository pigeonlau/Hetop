package cn.edu.nwpu.rj416.util.types;

//布尔工具（将其他类型的对象转换成布尔值）
public abstract class BooleanUtil {
	public static boolean toBoolean(Object o) {
		if (o == null) {
			return false;
		}
		
		if (o instanceof Boolean) {
			return (Boolean)o;
		} else if (o instanceof String) {
			String v = (String)o;
			return Boolean.parseBoolean(v); //字符串类型要用解析方法
		} else if (o instanceof Long) {
			Long v = (Long)o;
			return v > 0;
		} else if (o instanceof Integer) {
			Integer v = (Integer)o;
			return v > 0;
		} else if (o instanceof Short) {
			Short v = (Short)o;
			return v > 0;
		} else if (o instanceof Byte) {
			Byte v = (Byte)o;
			return v > 0;
		} //其他数值类，大于零为真，小于等于零为假
		
		return false;
	}
}
