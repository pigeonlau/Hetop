package cn.edu.nwpu.rj416.type.caster.primitive;



import cn.edu.nwpu.rj416.type.astype.cast.MTypeCastException;
import cn.edu.nwpu.rj416.type.astype.cast.MTypeCaster;

import java.lang.reflect.Type;
import java.math.BigDecimal;



//整型转高精度类转换器
public class MCasterInteger2BigDecimal implements MTypeCaster<Integer, BigDecimal> {

	@Override
	public BigDecimal cast(Integer value, Type destType) throws MTypeCastException {
		if (value == null) {
			return BigDecimal.ZERO;
		}
		
		return BigDecimal.valueOf(value);
	}

}
