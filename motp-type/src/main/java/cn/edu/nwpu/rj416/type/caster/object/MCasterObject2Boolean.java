package cn.edu.nwpu.rj416.type.caster.object;


import cn.edu.nwpu.rj416.type.astype.cast.MTypeCastException;
import cn.edu.nwpu.rj416.type.astype.cast.MTypeCaster;

import java.lang.reflect.Type;



/*
 * 任意对象转布尔值转换器
 * 待转换对象是基本类型的包装类，则根据其字节码值是否大于零转成布尔值
 * 若不是基本类型的包装类，则返回null
 */
public class MCasterObject2Boolean implements MTypeCaster<Object, Boolean> {

	@Override
	public Boolean cast(Object value, Type destType) throws MTypeCastException {
		if (value == null) {
			return false;
		}
		
		if (value instanceof Boolean) {
			return (Boolean)value;
		}
		
		Boolean booleanValue = null;
		if (value instanceof Byte) {
			booleanValue = ((Byte)value).byteValue() > 0;
		} else if (value instanceof Short) {
			booleanValue = ((Short)value).byteValue() > 0;
		} else if (value instanceof Integer) {
			booleanValue = ((Integer)value).byteValue() > 0;
		} else if (value instanceof Long) {
			booleanValue = ((Long)value).byteValue() > 0;
		} else if (value instanceof Float) {
			booleanValue = ((Float)value).byteValue() > 0;
		} else if (value instanceof Double) {
			booleanValue = ((Double)value).byteValue() > 0;
		} else {
			booleanValue = true;
		}
		return booleanValue;
	}

}
