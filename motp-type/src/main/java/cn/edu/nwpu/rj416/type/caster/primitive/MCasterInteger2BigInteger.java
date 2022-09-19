package cn.edu.nwpu.rj416.type.caster.primitive;



import cn.edu.nwpu.rj416.type.astype.cast.MTypeCastException;
import cn.edu.nwpu.rj416.type.astype.cast.MTypeCaster;

import java.lang.reflect.Type;
import java.math.BigInteger;


//整型转大整数类转换器
public class MCasterInteger2BigInteger implements MTypeCaster<Integer, BigInteger> {

	@Override
	public BigInteger cast(Integer value, Type destType) throws MTypeCastException {
		if (value == null) {
			return BigInteger.ZERO;
		}
		
		return BigInteger.valueOf(value);
	}

}
