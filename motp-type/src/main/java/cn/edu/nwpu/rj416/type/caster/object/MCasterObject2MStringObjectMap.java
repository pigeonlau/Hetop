package cn.edu.nwpu.rj416.type.caster.object;



import cn.edu.nwpu.rj416.type.astype.cast.MTypeCastException;
import cn.edu.nwpu.rj416.type.astype.cast.MTypeCaster;
import cn.edu.nwpu.rj416.type.util.MStringObjectMap;
import cn.edu.nwpu.rj416.type.util.ObjectUtil;

import java.lang.reflect.Type;



//任意对象转字符串对象Map转换器
public class MCasterObject2MStringObjectMap implements MTypeCaster<Object, MStringObjectMap> {

	@Override
	public MStringObjectMap cast(Object value, Type destType) throws MTypeCastException {
		return ObjectUtil.objectToStringObjectMap(value);
	}

}
