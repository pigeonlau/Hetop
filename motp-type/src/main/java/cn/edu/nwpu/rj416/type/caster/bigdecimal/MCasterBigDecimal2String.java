package cn.edu.nwpu.rj416.type.caster.bigdecimal;



import cn.edu.nwpu.rj416.type.astype.cast.MTypeCastException;
import cn.edu.nwpu.rj416.type.astype.cast.MTypeCaster;

import java.lang.reflect.Type;
import java.math.BigDecimal;



//高精度类转字符串类转换器
public class MCasterBigDecimal2String implements MTypeCaster<BigDecimal, String> {

	@Override
	public String cast(BigDecimal value, Type destType) throws MTypeCastException {
		if (value == null) {
			return "0";
		}
		return value.toString();
	}

	
}
