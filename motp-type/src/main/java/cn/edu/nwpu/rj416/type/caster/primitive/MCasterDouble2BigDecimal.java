package cn.edu.nwpu.rj416.type.caster.primitive;



import cn.edu.nwpu.rj416.type.astype.cast.MTypeCastException;
import cn.edu.nwpu.rj416.type.astype.cast.MTypeCaster;

import java.lang.reflect.Type;
import java.math.BigDecimal;



//双精度浮点型转高精度类转换器
public class MCasterDouble2BigDecimal implements MTypeCaster<Double, BigDecimal> {

	@Override
	public BigDecimal cast(Double value, Type destType) throws MTypeCastException {
		if (value == null) {
			return null;
		}
		
		return BigDecimal.valueOf(value);
	}

}
