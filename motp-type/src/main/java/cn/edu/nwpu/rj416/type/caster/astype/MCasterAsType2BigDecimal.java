package cn.edu.nwpu.rj416.type.caster.astype;



import cn.edu.nwpu.rj416.type.TypeCaster;
import cn.edu.nwpu.rj416.type.astype.cast.MTypeCastException;
import cn.edu.nwpu.rj416.type.astype.cast.MTypeCaster;
import cn.edu.nwpu.rj416.util.astype.AsType;

import java.lang.reflect.Type;
import java.math.BigDecimal;



//AsType转高精度类转换器
public class MCasterAsType2BigDecimal implements MTypeCaster<AsType<?>, BigDecimal> {
	@Override
	public BigDecimal cast(AsType<?> value, Type destType) throws MTypeCastException {
		Object o = value.dumpToAsType();
		return TypeCaster.cast(o, BigDecimal.class);
	}

}
