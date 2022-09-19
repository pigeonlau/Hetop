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
//布尔型数组转列表转换器
public class MCasterBooleanArrayToList implements MTypeCaster<boolean[], List<Boolean>> {

	@Override
	public List<Boolean> cast(boolean[] value, Type destType) throws MTypeCastException {
		List<Boolean> list = new ArrayList<>();
		for (boolean v : value) {
			list.add(v);
		}
		return list;
	}


}
