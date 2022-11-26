package cn.edu.nwpu.rj416.type.caster.list;



import cn.edu.nwpu.rj416.type.TypeCaster;
import cn.edu.nwpu.rj416.type.astype.cast.MTypeCastException;
import cn.edu.nwpu.rj416.type.astype.cast.MTypeCaster;

import java.lang.reflect.Type;
import java.util.List;



//列表转布尔型数组转换器
public class MCasterList2BooleanArray implements MTypeCaster<List<?>, boolean[]> {

	@Override
	public boolean[] cast(List<?> value, Type destType) throws MTypeCastException {
		if (value == null) {
			return null;
		}
		if (value.isEmpty()) {
			return new boolean[0];
		}
		
		boolean[] arr = new boolean[value.size()];//数组大小为列表长度
		for (int i = 0; i < value.size(); i++) {
			arr[i] = TypeCaster.cast(value.get(i), boolean.class);//将列表中的每个元素转成布尔值，不能转则抛出异常
		}
		return arr;
	}


}
