package cn.edu.nwpu.rj416.type.caster.primitive;

import cn.edu.nwpu.rj416.type.astype.cast.MTypeCastException;
import cn.edu.nwpu.rj416.type.astype.cast.MTypeCaster;

import java.lang.reflect.Type;



//整型转双精度浮点型转换器
public class MCasterInteger2Double implements MTypeCaster<Integer, Double> {

	@Override
	public Double cast(Integer value, Type destType) throws MTypeCastException {
		if (value == null) {
			return 0.0;
		}
		
		return value.doubleValue();
	}

}
