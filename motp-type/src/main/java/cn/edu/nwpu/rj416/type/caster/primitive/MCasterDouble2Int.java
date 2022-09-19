package cn.edu.nwpu.rj416.type.caster.primitive;



import cn.edu.nwpu.rj416.type.astype.cast.MTypeCastException;
import cn.edu.nwpu.rj416.type.astype.cast.MTypeCaster;

import java.lang.reflect.Type;


//双精度浮点型转整型转换器
public class MCasterDouble2Int implements MTypeCaster<Double, Integer> {

	@Override
	public Integer cast(Double value, Type destType) throws MTypeCastException {
		if (value == null) {
			return null;
		}
		
		return value.intValue();
	}

}
