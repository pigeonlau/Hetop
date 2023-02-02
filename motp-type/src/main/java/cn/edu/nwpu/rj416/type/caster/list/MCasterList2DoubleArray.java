package cn.edu.nwpu.rj416.type.caster.list;




import cn.edu.nwpu.rj416.type.TypeCaster;
import cn.edu.nwpu.rj416.type.astype.cast.MTypeCastException;
import cn.edu.nwpu.rj416.type.astype.cast.MTypeCaster;

import java.lang.reflect.Type;
import java.util.List;

//列表转double数组转换器
public class MCasterList2DoubleArray implements MTypeCaster<List<?>, double[]> {
	@Override
	public double[] cast(List<?> value, Type destType) throws MTypeCastException {
		if (value == null) {
			return null;
		}
		if (value.isEmpty()) {
			return new double[0];
		}
		
		double[] arr = new double[value.size()];
		for (int i = 0; i < value.size(); i++) {
			arr[i] = TypeCaster.cast(value.get(i), double.class);
		}
		return arr;
	}


}
