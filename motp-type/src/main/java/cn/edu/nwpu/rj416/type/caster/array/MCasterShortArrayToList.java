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
//短整型数组转列表转换器
public class MCasterShortArrayToList implements MTypeCaster<short[], List<Short>> {

	@Override
	public List<Short> cast(short[] value, Type destType) throws MTypeCastException {
		List<Short> list = new ArrayList<>();
		for (short v : value) {
			list.add(v);
		}
		return list;
	}


}
