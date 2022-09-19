package cn.edu.nwpu.rj416.type.caster.primitive;



import cn.edu.nwpu.rj416.type.astype.cast.MTypeCastException;
import cn.edu.nwpu.rj416.type.astype.cast.MTypeCaster;

import java.lang.reflect.Type;



//布尔型转字节码转换器
public class MCasterBoolean2Byte implements MTypeCaster<Boolean, Byte> {
	private static final byte DEFAULT_VALUE = (byte)0;

	@Override
	public Byte cast(Boolean value, Type destType) throws MTypeCastException {
		if (value == null) {
			return DEFAULT_VALUE;
		}
		
		if (value.booleanValue()) {
			return (byte)1;
		} else {
			return (byte)0;
		}
	}

}
