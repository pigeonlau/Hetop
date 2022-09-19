package cn.edu.nwpu.rj416.type.caster.primitive;


import cn.edu.nwpu.rj416.type.astype.cast.MTypeCastException;
import cn.edu.nwpu.rj416.type.astype.cast.MTypeCaster;

import java.lang.reflect.Type;


//长整型转整型转换器
public class MCasterLong2Int implements MTypeCaster<Long, Integer> {

	@Override
	public Integer cast(Long value, Type destType) throws MTypeCastException {
		if (value == null) {
			return 0;
		}
		
		return value.intValue();
	}

}
