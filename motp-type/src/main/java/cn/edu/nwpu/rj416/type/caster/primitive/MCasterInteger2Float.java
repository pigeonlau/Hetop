package cn.edu.nwpu.rj416.type.caster.primitive;



import cn.edu.nwpu.rj416.type.astype.cast.MTypeCastException;
import cn.edu.nwpu.rj416.type.astype.cast.MTypeCaster;

import java.lang.reflect.Type;


//整型转浮点型转换器
public class MCasterInteger2Float implements MTypeCaster<Integer, Float> {

	@Override
	public Float cast(Integer value, Type destType) throws MTypeCastException {
		if (value == null) {
			return new Float(0);
		}
		
		return value.floatValue();
	}

}
