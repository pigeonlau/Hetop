package cn.edu.nwpu.rj416.type.caster.string;

import cn.edu.nwpu.rj416.type.astype.cast.MTypeCaster;
import cn.edu.nwpu.rj416.util.types.StringUtil;

import java.lang.reflect.Type;

//字符串转枚举类转换器
public class MCasterString2Enum implements MTypeCaster<String, Enum<?>> {

	@Override
	public Enum<?> cast(String value, Type destType) {
		if (StringUtil.isEmpty(value)) {
			return null;
		}
		
		if (!(destType instanceof Class)) {
			return null;
		}
		
		Class<?> enumClass = (Class<?>)destType; //获取转换类型的Class对象
		if (!enumClass.isEnum()) {
			return null; //不是枚举类返回null
		}
		
		Enum<?>[] enumValues = (Enum<?>[]) enumClass.getEnumConstants();//获取要转换的枚举类型的元素，并强转为枚举数组
		if (enumValues == null) {
			return null;
		}
		
		for (Enum<?> ev : enumValues) {
			if (value.equals(ev.name())) {
				return ev; //返回和字符串内容相同的元素（强转为枚举类返回）
			}
		}
		
		return null;
	}

}
