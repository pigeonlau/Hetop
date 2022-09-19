package cn.edu.nwpu.rj416.type.caster.object;



import cn.edu.nwpu.rj416.type.astype.cast.MTypeCastException;
import cn.edu.nwpu.rj416.type.astype.cast.MTypeCaster;
import cn.edu.nwpu.rj416.type.util.ObjectUtil;

import java.lang.reflect.Type;
import java.util.Map;



//任意对象转Map转换器（将对象字段名与对应字段值组成键值对存进Map）
public class MCasterObject2Map implements MTypeCaster<Object, Map<?, ?>> {

	@Override
	public Map<String, Object> cast(Object value, Type destType) throws MTypeCastException {
		return ObjectUtil.objectToMap(value);
	}

}
