package cn.edu.nwpu.rj416.type.caster.string;


import cn.edu.nwpu.rj416.type.astype.cast.MTypeCaster;

import java.lang.reflect.Type;
import java.math.BigInteger;



//字符串转大整数类转换器
public class MCasterString2BigInteger implements MTypeCaster<String, BigInteger> {

	@Override
	public BigInteger cast(String value, Type destType) {
		if (value == null) {
			return null;
		}
		return new BigInteger(value);
	}

}
