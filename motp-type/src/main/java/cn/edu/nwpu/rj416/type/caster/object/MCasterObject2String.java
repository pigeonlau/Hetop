package cn.edu.nwpu.rj416.type.caster.object;



import cn.edu.nwpu.rj416.type.astype.cast.MTypeCastException;
import cn.edu.nwpu.rj416.type.astype.cast.MTypeCaster;

import java.lang.reflect.Type;



//任意对象转字符串转换器
public class MCasterObject2String implements MTypeCaster<Object, String> {

	@Override
	public String cast(Object value, Type destType) throws MTypeCastException {
		return String.valueOf(value); //返回对象参数的字符串表示形式
	}

	
}
