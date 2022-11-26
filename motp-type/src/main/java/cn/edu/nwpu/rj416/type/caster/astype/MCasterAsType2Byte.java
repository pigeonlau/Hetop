package cn.edu.nwpu.rj416.type.caster.astype;



import cn.edu.nwpu.rj416.type.TypeCaster;
import cn.edu.nwpu.rj416.type.astype.cast.MTypeCastException;
import cn.edu.nwpu.rj416.type.astype.cast.MTypeCaster;
import cn.edu.nwpu.rj416.util.astype.AsType;

import java.lang.reflect.Type;



//AsType转字节类转换器
public class MCasterAsType2Byte implements MTypeCaster<AsType<?>, Byte> {
	@Override
	public Byte cast(AsType<?> value, Type destType) throws MTypeCastException {
		Object o = value.dumpToAsType();
		return TypeCaster.cast(o, Byte.class);
	}

}
