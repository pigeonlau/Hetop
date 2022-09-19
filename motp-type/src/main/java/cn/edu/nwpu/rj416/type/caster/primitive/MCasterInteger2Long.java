package cn.edu.nwpu.rj416.type.caster.primitive;


import cn.edu.nwpu.rj416.type.astype.cast.MTypeCastException;
import cn.edu.nwpu.rj416.type.astype.cast.MTypeCaster;

import java.lang.reflect.Type;



//整型转长整型转换器
public class MCasterInteger2Long implements MTypeCaster<Integer, Long> {

	@Override
	public Long cast(Integer value, Type destType) throws MTypeCastException {
		if (value == null) {
			return 0L;
		}
		
		return value.longValue();
	}

}
