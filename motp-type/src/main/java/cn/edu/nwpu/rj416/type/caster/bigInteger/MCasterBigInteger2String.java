package cn.edu.nwpu.rj416.type.caster.bigInteger;



import cn.edu.nwpu.rj416.type.astype.cast.MTypeCastException;
import cn.edu.nwpu.rj416.type.astype.cast.MTypeCaster;

import java.lang.reflect.Type;
import java.math.BigInteger;



//大整数类转字符串类转换器
public class MCasterBigInteger2String implements MTypeCaster<BigInteger, String> {

	@Override
	public String cast(BigInteger value, Type destType) throws MTypeCastException {
		if (value == null) {
			return "0";
		}
		return value.toString();
	}

	
}
