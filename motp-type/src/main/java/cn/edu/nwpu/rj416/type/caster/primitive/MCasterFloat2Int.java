package cn.edu.nwpu.rj416.type.caster.primitive;



import cn.edu.nwpu.rj416.type.astype.cast.MTypeCastException;
import cn.edu.nwpu.rj416.type.astype.cast.MTypeCaster;

import java.lang.reflect.Type;



//浮点型转整型转换器
public class MCasterFloat2Int implements MTypeCaster<Float, Integer> {

	@Override
	public Integer cast(Float value, Type destType) throws MTypeCastException {
		if (value == null) {
			return null;
		}
		
		return value.intValue();
	}

}
