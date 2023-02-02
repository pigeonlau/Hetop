package cn.edu.nwpu.rj416.type.caster.list;



import cn.edu.nwpu.rj416.type.TypeCaster;
import cn.edu.nwpu.rj416.type.astype.cast.MTypeCastException;
import cn.edu.nwpu.rj416.type.astype.cast.MTypeCaster;

import java.lang.reflect.Type;
import java.util.List;



//列表转整型数组转换器
public class MCasterList2IntArray implements MTypeCaster<List<?>, int[]> {

	@Override
	public int[] cast(List<?> value, Type destType) throws MTypeCastException {
		if (value == null) {
			return null;
		}
		if (value.isEmpty()) {
			return new int[0];
		}
		
		int[] arr = new int[value.size()];
		for (int i = 0; i < value.size(); i++) {
			arr[i] = TypeCaster.cast(value.get(i), int.class);
		}
		return arr;
	}


}
