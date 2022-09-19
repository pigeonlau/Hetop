package cn.edu.nwpu.rj416.type.caster.primitive;



import cn.edu.nwpu.rj416.type.astype.cast.MTypeCastException;
import cn.edu.nwpu.rj416.type.astype.cast.MTypeCaster;

import java.lang.reflect.Type;



//长整型转字节型转换器
public class MCasterLong2Byte implements MTypeCaster<Long, Byte> {

	@Override
	public Byte cast(Long value, Type destType) throws MTypeCastException {
		if (value == null) {
			return (byte)0;
		}
		
		return value.byteValue();
	}

}
