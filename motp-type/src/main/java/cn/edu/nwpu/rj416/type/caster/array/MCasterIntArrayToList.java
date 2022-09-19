package cn.edu.nwpu.rj416.type.caster.array;

import cn.ducis.macaw.core.type.astype.cast.MTypeCastException;
import cn.ducis.macaw.core.type.astype.cast.MTypeCaster;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


/**
 * 
 * @author MilesLiu
 *
 * 2020年3月16日 下午1:14:04
 */
//整型数组转列表类转换器
public class MCasterIntArrayToList implements MTypeCaster<int[], List<Integer>> {

	@Override
	public List<Integer> cast(int[] value, Type destType) throws MTypeCastException {
		List<Integer> list = new ArrayList<>();
		for (int v : value) {
			list.add(v);
		}
		return list;
	}


}
