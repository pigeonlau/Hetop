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
//字符数组转列表转换器
public class MCasterCharArrayToList implements MTypeCaster<char[], List<Character>> {

	@Override
	public List<Character> cast(char[] value, Type destType) throws MTypeCastException {
		List<Character> list = new ArrayList<>();
		for (char v : value) {
			list.add(v);
		}
		return list;
	}


}
