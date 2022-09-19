package cn.edu.nwpu.rj416.type.caster.string;


import cn.edu.nwpu.rj416.type.astype.cast.MTypeCaster;
import cn.edu.nwpu.rj416.util.types.StringUtil;

import java.lang.reflect.Type;

//字符串转整型转换器
public class MCasterString2Int implements MTypeCaster<String, Integer> {

	@Override
	public Integer cast(String value, Type destType) {
		if (StringUtil.isEmpty(value)) {
			return null;
		}
		
		return Integer.parseInt(value);
	}

}
