package cn.edu.nwpu.rj416.type.caster.list;


import cn.edu.nwpu.rj416.type.astype.cast.MTypeCastException;
import cn.edu.nwpu.rj416.type.astype.cast.MTypeCaster;

import java.lang.reflect.Type;
import java.util.List;



//列表转对象数组转换器
public class MCasterList2ObjectArray implements MTypeCaster<List<?>, Object[]> {
	@Override
	public Object[] cast(List<?> value, Type destType) throws MTypeCastException {
		if (value == null) {
			return null;
		}
		if (value.isEmpty()) {
			return new String[0];
		}
		
		Object[] arr = new Object[value.size()];
		for (int i = 0; i < value.size(); i++) {
			arr[i] = value.get(i); //直接将列表中的对象添加进数组即可
		}
		return arr;
	}
}
