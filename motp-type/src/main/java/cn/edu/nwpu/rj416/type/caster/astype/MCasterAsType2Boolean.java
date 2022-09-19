package cn.edu.nwpu.rj416.type.caster.astype;



import cn.edu.nwpu.rj416.type.Macaw;
import cn.edu.nwpu.rj416.type.astype.cast.MTypeCastException;
import cn.edu.nwpu.rj416.type.astype.cast.MTypeCaster;
import cn.edu.nwpu.rj416.util.astype.AsType;

import java.lang.reflect.Type;



//AsType转布尔型转换器
public class MCasterAsType2Boolean implements MTypeCaster<AsType<?>, Boolean> {
	@Override
	public Boolean cast(AsType<?> value, Type destType) throws MTypeCastException {
		Object o = value.dumpToAsType();
		return Macaw.cast(o, Boolean.class);
	}

}
