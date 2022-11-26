package cn.edu.nwpu.rj416.type.caster.list;



import cn.edu.nwpu.rj416.type.TypeCaster;
import cn.edu.nwpu.rj416.type.astype.cast.MTypeCastException;
import cn.edu.nwpu.rj416.type.astype.cast.MTypeCaster;

import java.lang.reflect.Type;
import java.util.List;


//列表转字符串数组转换器
public class MCasterList2StringArray implements MTypeCaster<List<?>, String[]> {
	@Override
	public String[] cast(List<?> value, Type destType) throws MTypeCastException {
		if (value == null) {
			return null;
		}
		if (value.isEmpty()) {
			return new String[0];
		}
		
		String[] arr = new String[value.size()];
		for (int i = 0; i < value.size(); i++) {
			arr[i] = TypeCaster.cast(value.get(i), String.class);
		}
		return arr;
	}


}
