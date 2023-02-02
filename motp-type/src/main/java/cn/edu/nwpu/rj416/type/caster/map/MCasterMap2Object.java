package cn.edu.nwpu.rj416.type.caster.map;



import cn.edu.nwpu.rj416.type.TypeCaster;
import cn.edu.nwpu.rj416.type.astype.cast.MTypeCastException;
import cn.edu.nwpu.rj416.type.astype.cast.MTypeCaster;
import cn.edu.nwpu.rj416.type.util.ObjectUtil;
import cn.edu.nwpu.rj416.util.reflect.ClassUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

//Map转指定类型对象转换器（Map中存放着转换类型对象的字段属性）
public class MCasterMap2Object implements MTypeCaster<Map<?, ?>, Object> {


	@Override
	public Object cast(Map<?, ?> value, Type destType) throws MTypeCastException {
		Object dest = ObjectUtil.createObjectByType(destType); //根据要转换的类型创建相应的默认值的对象
		if (dest == null) {
			return null;
		}
		
		Class<?> clazz = dest.getClass(); //获取要转换类型的Class对象
		
		List<Field> fields = ClassUtil.getFields(clazz, Modifier.FINAL | Modifier.STATIC); //获取其除非final与static修饰的所有字段
		for (Field f : fields){
			try {
				Object v = value.get(f.getName());//根据字段名（键）从Map中获取对应的字段值
				Object destvalue = TypeCaster.cast(v, f.getType());//把存放的对象值转换成字段对应类型
				f.setAccessible(true);
				f.set(dest, destvalue);//将dest中匹配的字段值设置成图中存放的值
			} catch (Exception e) {

			}
		}
		
		return dest;
	}

}
