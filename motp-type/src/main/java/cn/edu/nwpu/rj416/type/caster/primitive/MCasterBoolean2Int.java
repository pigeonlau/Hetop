package cn.edu.nwpu.rj416.type.caster.primitive;



import cn.edu.nwpu.rj416.type.astype.cast.MTypeCastException;
import cn.edu.nwpu.rj416.type.astype.cast.MTypeCaster;

import java.lang.reflect.Type;


//布尔型转整型转换器
public class MCasterBoolean2Int implements MTypeCaster<Boolean, Integer> {

	@Override
	public Integer cast(Boolean value, Type destType) throws MTypeCastException {
		if (value == null) {
			return null;
		}
		
		if (value.booleanValue()) {
			return 1;
		} else {
			return 0;
		}
	}

}
