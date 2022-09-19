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
//长整型数组转列表转换器
public class MCasterLongArrayToList implements MTypeCaster<long[], List<Long>> {

	@Override
	public List<Long> cast(long[] value, Type destType) throws MTypeCastException {
		List<Long> list = new ArrayList<>();
		for (long v : value) {
			list.add(v);
		}
		return list;
	}


}
