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
 * 2020年3月16日 下午1:13:29
 */
//对象数组转列表转换器
public class MCasterArrayToList implements MTypeCaster<Object[], List<Object>> {

	@Override
	public List<Object> cast(Object[] value, Type destType) throws MTypeCastException {
		List<Object> list = new ArrayList<>();
		for (Object v : value) {
			list.add(v);
		}
		return list;
	}


}
