package cn.edu.nwpu.rj416.type.caster.primitive;



import cn.edu.nwpu.rj416.type.astype.cast.MTypeCastException;
import cn.edu.nwpu.rj416.type.astype.cast.MTypeCaster;

import java.lang.reflect.Type;



//短整型转整型转换器
public class MCasterShort2Int implements MTypeCaster<Short, Integer> {

	@Override
	public Integer cast(Short value, Type destType) throws MTypeCastException {
		if (value == null) {
			return 0;
		}
		
		return value.intValue();
	}

}
