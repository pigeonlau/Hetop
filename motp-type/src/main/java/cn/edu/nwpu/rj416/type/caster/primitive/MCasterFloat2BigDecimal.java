package cn.edu.nwpu.rj416.type.caster.primitive;



import cn.edu.nwpu.rj416.type.astype.cast.MTypeCastException;
import cn.edu.nwpu.rj416.type.astype.cast.MTypeCaster;

import java.lang.reflect.Type;
import java.math.BigDecimal;



//浮点型转高精度类转换器
public class MCasterFloat2BigDecimal implements MTypeCaster<Float, BigDecimal> {

	@Override
	public BigDecimal cast(Float value, Type destType) throws MTypeCastException {
		if (value == null) {
			return null;
		}
		
		return BigDecimal.valueOf(value);
	}

}
