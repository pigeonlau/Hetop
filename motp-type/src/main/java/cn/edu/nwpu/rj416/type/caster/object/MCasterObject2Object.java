package cn.edu.nwpu.rj416.type.caster.object;



import cn.edu.nwpu.rj416.type.Macaw;
import cn.edu.nwpu.rj416.type.astype.cast.MTypeCastException;
import cn.edu.nwpu.rj416.type.astype.cast.MTypeCaster;
import cn.edu.nwpu.rj416.type.util.ObjectUtil;
import cn.edu.nwpu.rj416.util.reflect.ClassUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.List;

//源对象转目标对象转换器（要有相同的字段名）
public class MCasterObject2Object implements MTypeCaster<Object, Object> {


	@Override
	public Object cast(Object value, Type destType) throws MTypeCastException {
		if (value == null) {
			return null;
		}
		Object dest = ObjectUtil.createObjectByType(destType); //根据目标对象类型构造其默认值的对象
		this.override(dest, value); //将源对象的字段值转换为目标对象的字段值后覆盖原字段的值以达成转换
		return dest;
	}

	//将源对象的字段值转换为目标对象的字段值后覆盖原字段的值以达成转换
	private void override(Object dest, Object source) {
  		if (dest == null || source == null) {
			return;
		}
		
		List<Field> fields = ClassUtil.getFields(dest.getClass(), Modifier.FINAL | Modifier.STATIC);
		for (Field f : fields){
			try {
				Object value = ClassUtil.getValue(source, f.getName()); //根据字段名从源对象中获取对应的字段值
				Object destvalue = Macaw.cast(value, f.getType());
				f.setAccessible(true);
				f.set(dest, destvalue);
			} catch (Exception e) {

			}
		}
	}
}
