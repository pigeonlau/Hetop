package cn.edu.nwpu.rj416.type.caster.primitive;


import cn.edu.nwpu.rj416.type.astype.cast.MTypeCastException;
import cn.edu.nwpu.rj416.type.astype.cast.MTypeCaster;

import java.lang.reflect.Type;



//整型转字节型转换器
public class MCasterInteger2Byte implements MTypeCaster<Integer, Byte> {
	private static final byte DEFAULT_VALUE = (byte)0;

	@Override
	public Byte cast(Integer value, Type destType) throws MTypeCastException {
		if (value == null) {
			return DEFAULT_VALUE;
		}
		
		return value.byteValue();
	}

}
