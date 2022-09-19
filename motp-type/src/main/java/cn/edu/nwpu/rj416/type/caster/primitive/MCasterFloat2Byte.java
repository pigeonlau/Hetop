package cn.edu.nwpu.rj416.type.caster.primitive;


import cn.edu.nwpu.rj416.type.astype.cast.MTypeCastException;
import cn.edu.nwpu.rj416.type.astype.cast.MTypeCaster;

import java.lang.reflect.Type;



//浮点型转字节型转换器
public class MCasterFloat2Byte implements MTypeCaster<Float, Byte> {

	@Override
	public Byte cast(Float value, Type destType) throws MTypeCastException {
		if (value == null) {
			return (byte)0;
		}
		
		return value.byteValue();
	}

}
