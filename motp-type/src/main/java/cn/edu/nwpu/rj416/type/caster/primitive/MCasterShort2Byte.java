package cn.edu.nwpu.rj416.type.caster.primitive;


import cn.edu.nwpu.rj416.type.astype.cast.MTypeCastException;
import cn.edu.nwpu.rj416.type.astype.cast.MTypeCaster;

import java.lang.reflect.Type;



//短整型转字节型转换器
public class MCasterShort2Byte implements MTypeCaster<Short, Byte> {

	@Override
	public Byte cast(Short value, Type destType) throws MTypeCastException {
		if (value == null) {
			return (byte)0;
		}
		
		return value.byteValue();
	}

}
