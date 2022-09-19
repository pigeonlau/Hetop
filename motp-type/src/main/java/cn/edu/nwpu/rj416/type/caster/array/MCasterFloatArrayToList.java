package cn.edu.nwpu.rj416.type.caster.array;



import cn.edu.nwpu.rj416.type.astype.cast.MTypeCastException;
import cn.edu.nwpu.rj416.type.astype.cast.MTypeCaster;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;



/**
 * 
 * @author MilesLiu
 *
 * 2020年3月16日 下午1:14:04
 */
//float数组转列表转换器
public class MCasterFloatArrayToList implements MTypeCaster<float[], List<Float>> {

	@Override
	public List<Float> cast(float[] value, Type destType) throws MTypeCastException {
		List<Float> list = new ArrayList<>();
		for (float v : value) {
			list.add(v);
		}
		return list;
	}


}
