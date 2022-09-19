package cn.edu.nwpu.rj416.type.caster.list;




import cn.edu.nwpu.rj416.type.Macaw;
import cn.edu.nwpu.rj416.type.astype.cast.MTypeCastException;
import cn.edu.nwpu.rj416.type.astype.cast.MTypeCaster;

import java.lang.reflect.Type;
import java.util.List;

//列表转float数组转换器
public class MCasterList2FloatArray implements MTypeCaster<List<?>, float[]> {
	@Override
	public float[] cast(List<?> value, Type destType) throws MTypeCastException {
		if (value == null) {
			return null;
		}
		if (value.isEmpty()) {
			return new float[0];
		}
		
		float[] arr = new float[value.size()];
		for (int i = 0; i < value.size(); i++) {
			arr[i] = Macaw.cast(value.get(i), float.class);
		}
		return arr;
	}


}
