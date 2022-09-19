package cn.edu.nwpu.rj416.type.caster.string;


import cn.edu.nwpu.rj416.type.astype.cast.MTypeCaster;

import java.lang.reflect.Type;
import java.math.BigDecimal;



//字符串转高精度类转换器
public class MCasterString2BigDecimal implements MTypeCaster<String, BigDecimal> {

	@Override
	public BigDecimal cast(String value, Type destType) {
		if (value == null) {
			return null;
		}
		return new BigDecimal(value);
	}

}
