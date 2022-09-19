package cn.edu.nwpu.rj416.type.caster.primitive;


import cn.edu.nwpu.rj416.type.astype.cast.MTypeCastException;
import cn.edu.nwpu.rj416.type.astype.cast.MTypeCaster;

import java.lang.reflect.Type;
import java.math.BigDecimal;


//布尔型转高精度类转换器
public class MCasterBoolean2BigDecimal implements MTypeCaster<Boolean, BigDecimal> {

	@Override
	public BigDecimal cast(Boolean value, Type destType) throws MTypeCastException {
		if (value == null) {
			return null;
		}
		
		if (value.booleanValue()) {
			return BigDecimal.ONE;
		} else {
			return BigDecimal.ZERO;
		}
	}

}
