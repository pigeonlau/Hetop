package cn.edu.nwpu.rj416.type.caster.string;


import cn.edu.nwpu.rj416.type.astype.cast.MTypeCaster;

import java.lang.reflect.Type;


//字符串转布尔型转换器
public class MCasterString2Boolean implements MTypeCaster<String, Boolean> {

	@Override
	public Boolean cast(String value, Type destType) {
		if (value == null) {
			return false;
		}
		return Boolean.parseBoolean(value); //字符串为"True"时（无关大小写），返回布尔值true
	}

}
