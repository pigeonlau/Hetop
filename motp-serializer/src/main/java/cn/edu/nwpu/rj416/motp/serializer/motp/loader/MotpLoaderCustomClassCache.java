package cn.edu.nwpu.rj416.motp.serializer.motp.loader;


import cn.edu.nwpu.rj416.motp.reflectasm.FieldAccess;
import cn.edu.nwpu.rj416.util.reflect.ClassUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MotpLoaderCustomClassCache {
	private Map<String, Field> fieldMap = new HashMap<>();
	
	public void buildClass(Class<?> clazz) {
		this.fieldMap.clear();
//		List<Field> fields = ClassUtil.getEffectiveFields(
//				clazz, Modifier.FINAL | Modifier.STATIC);

		Field[] fields = FieldAccess.get(clazz).getFields();

		for (Field f : fields) {
//			Type ft = f.getGenericType();

			f.setAccessible(true);
			fieldMap.put(f.getName(), f);
		}
	}
	
	public Field getFieldByName(String name) {
		return this.fieldMap.get(name);
	}

	
}
