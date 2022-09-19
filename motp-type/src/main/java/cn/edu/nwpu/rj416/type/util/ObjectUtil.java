/**
 * 
 */
package cn.edu.nwpu.rj416.type.util;


import cn.edu.nwpu.rj416.type.Macaw;
import cn.edu.nwpu.rj416.util.reflect.ClassUtil;
import cn.edu.nwpu.rj416.util.types.CollectionUtil;
import cn.edu.nwpu.rj416.util.types.StringUtil;

import java.lang.reflect.*;
import java.util.*;

/**
 * @author Harry
 * @since 3.0
 *
 */
public final class ObjectUtil {

	/**
	 * Checks if an object is equal to the other object.
	 * <p>
	 * Will returns true if two objects both are null.
	 * 
	 * @param object1 the object
	 * @param object2 the other object
	 * @return true if two objects are equal, otherwise false
	 * @since 3.0
	 */
	public static boolean equal(Object object1, Object object2) {
		return object1 == null ? object2 == null : object1.equals(object2);
	}

	/**
	 * Checks if the object is a list.
	 * <p>
	 * Will return false if the object is null.
	 * 
	 * @param object the object to check
	 * @return true if the object is a list, otherwise false
	 * @since 3.0
	 */
	public static boolean isList(Object object) {
		return object != null && object instanceof List;
	}

	/**
	 * Checks if the object is a map.
	 * <p>
	 * Will return false if the object is null.
	 * 
	 * @param object the object to check
	 * @return true if the object is a map, otherwise false
	 * @since 3.0
	 */
	public static boolean isMap(Object object) {
		return object != null && object instanceof Map;
	}

	/**
	 * Checks if the object isn't a map.
	 * <p>
	 * Will return true if the object is null.
	 * 
	 * @param object the object
	 * @return true if the object isn't a map, otherwise false
	 * @since 3.0
	 */
	public static boolean isNotMap(Object object) {
		return !isMap(object);
	}
	
	public static Map<String, Object> objectToMap(Object object){
		Map<String, Object> map = new HashMap<>();
		if (object == null){
			return map;
		}
		List<Field> fields = ClassUtil.getFields(object.getClass(), Modifier.FINAL | Modifier.STATIC);
		for (Field f : fields){
			try {
				Object value = ClassUtil.getValue(object, f.getName());
				if (value == null){
					continue;
				}
				map.put(f.getName(), value);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return map;
	}
	
	public static MStringObjectMap objectToStringObjectMap(Object object){
		MStringObjectMap map = new MStringObjectMap();
		if (object == null){
			return map;
		}
		List<Field> fields = ClassUtil.getFields(object.getClass(), Modifier.FINAL | Modifier.STATIC);
		for (Field f : fields){
			try {
				Object value = ClassUtil.getValue(object, f.getName());
				if (value == null){
					continue;
				}
				map.put(f.getName(), value);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return map;
	}
	
	public static void mapToObject(Object object, Map<String, Object> map){
		if (CollectionUtil.isEmpty(map)) {
			return;
		}
		List<Field> fields = ClassUtil.getFields(object.getClass(), Modifier.FINAL | Modifier.STATIC);
		for (Field f : fields){
			try {
				Object value = map.get(f.getName());
				if (value == null){
					continue;
				}
				if (value.getClass() != f.getType()) {
					value = Macaw.cast(value, f.getGenericType());
				}
				ClassUtil.setValue(object, f, value);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static <T> T createObjectByClass(Class<T> clazz) {
		if (clazz.isPrimitive()) {
			Object actuallyValue = null;
			if (clazz == boolean.class) {
				actuallyValue = Boolean.FALSE;
			} else if (clazz == byte.class) {
				Byte v = (byte)0;
				actuallyValue = v;
			} else if (clazz == short.class) {
				Short v = (short)0;
				actuallyValue = v;
			} else if (clazz == int.class) {
				Integer v = 0;
				actuallyValue = v;
			} else if (clazz == long.class) {
				Long v = 0L;
				actuallyValue = v;
			} else if (clazz == float.class) {
				Float v = (float)0.0;
				actuallyValue = v;
			} else if (clazz == double.class) {
				Double v = 0.0;
				actuallyValue = v;
			} else if (clazz == char.class) {
				Character v = '\u0000';
				actuallyValue = v;
			}
			@SuppressWarnings("unchecked")
			T rst = (T)actuallyValue;
			return rst;
		} else {
			Constructor<T> constructor;
			try {
	 			constructor = clazz.getDeclaredConstructor();
				constructor.setAccessible(true);
				T rst = constructor.newInstance();
				return rst;
			} catch (Exception e) {
				return null;
			}
		}
		
	}
	
	public static Object createObjectByType(Type type) {
		Object rst = null;
		if (type instanceof Class) {
			Class<?> clazz = (Class<?>)type;
			if (clazz.isPrimitive()) {
				if (clazz == boolean.class) {
					return false;
				} else if (clazz == byte.class) {
					return (byte)0;
				} else if (clazz == short.class) {
					return (short)0;
				} else if (clazz == int.class) {
					return 0;
				} else if (clazz == long.class) {
					return 0L;
				} else if (clazz == float.class) {
					return (float)0.0;
				} else if (clazz == double.class) {
					return 0.0;
				} else if (clazz == char.class) {
					return '\u0000';
				}
			} else {//构造器
				Constructor<?> constructor;
				try {
		 			constructor = clazz.getDeclaredConstructor();
					constructor.setAccessible(true);
					rst = constructor.newInstance();
				} catch (Exception e) {
				}
			}
		} else if (type instanceof ParameterizedType) {//参数化类型
			ParameterizedType pt = (ParameterizedType)type;
			Type rawType = pt.getRawType();
			if (rawType == List.class) {
				rst = new ArrayList<>();
			} else if (rawType == Set.class) {
				rst = new HashSet<>();
			} else if (rawType == Map.class) {
				rst = new HashMap<>();
			} else {
				rst = createObjectByType(rawType);
			}
		} else {
			rst = null;
		}
		
		return rst;
	}
	

	public static void override(Object dest, Object source) {
		if (dest == null || source == null) {
			return;
		}
		
		List<Field> fields = ClassUtil.getFields(dest.getClass(), Modifier.FINAL | Modifier.STATIC);
		for (Field f : fields){
			try {
				Object value = ClassUtil.getValue(source, f.getName());
				Object destvalue = Macaw.cast(value, f.getType());
				f.setAccessible(true);
				f.set(dest, destvalue);
			} catch (Exception e) {
			}
		}
	}
	
	public static void merge(Object dest, Object source) {
		if (dest == null || source == null) {
			return;
		}
		
		List<Field> fields = ClassUtil.getFields(dest.getClass(), Modifier.FINAL | Modifier.STATIC);
		for (Field f : fields){
			try {
				Object existValue = f.get(dest);
				if (existValue != null) {
					continue;
				}
				Object value = ClassUtil.getValue(source, f.getName());
				Object destvalue = Macaw.cast(value, f.getType());
				f.setAccessible(true);
				f.set(dest, destvalue);
			} catch (Exception e) {
			}
		}
	}
	
	/**
	 * 判断两个对象严格相等</br>
	 * 对于基本类型和枚举类型，判断值相等</br>
	 * 对于一般对象，判断每个属性是否严格相等</br>
	 * 
	 * @author MilesLiu
	 * @since 1.0.0
	 * 
	 * @param o1
	 * @param o2
	 * @return
	 */
	public static boolean strictEqual(Object o1, Object o2) {
		if (o1 == null) {
			return o2 == null;
		}
		if (o2 == null) {
			return false;
		}
		
		if (StringUtil.notEqual(o1.getClass().getName(), o2.getClass().getName())) {
			return false;
		}
		
		Class<?> clazz = o1.getClass();
		
		if (clazz.isPrimitive()) {
			return o1.equals(o2);
		}
		
		if (clazz.isEnum()) {
			return o1.equals(o2);
		}
		
		if (TypeUtil.isPrimitiveWrapper(clazz)) {
			return o1.equals(o2);
		}
		
		if (clazz.isArray()) {
			int len = Array.getLength(o1);
			if (Array.getLength(o2) != len) {
				return false;
			}
			for (int i = 0; i < len; i++) {
				Object v1 = Array.get(o1, i);
				Object v2 = Array.get(o2, i);
				if (!strictEqual(v1, v2)) {
					return false;
				}
			}
			return true;
		}
		
		if (o1 instanceof List) {
			List<?> list1 = (List<?>)o1;
			List<?> list2 = (List<?>)o2;
			if (list1.size() != list2.size()) {
				return false;
			}
			for (int i = 0; i < list1.size(); i++) {
				if (!strictEqual(list1.get(i), list2.get(i))) {
					return false;
				}
			}
			return true;
		}
		
		if (o1 instanceof Map ) {
			
		}
		
		if (o1 instanceof Set) {
			Set<?> list1 = (Set<?>)o1;
			Set<?> list2 = (Set<?>)o2;
			if (list1.size() != list2.size()) {
				return false;
			}
			return true;
		}
		
		
		List<Field> fields = ClassUtil.getFields(clazz, Modifier.FINAL | Modifier.STATIC);
		for (Field f : fields){
			try {
				Object v1 = f.get(o1);
				Object v2 = f.get(o2);
				if (!ObjectUtil.strictEqual(v1, v2)) {
					return false;
				}
			} catch (Exception e) {
			}
		}
		
		return true;
	}
	
	public static <T> T castToClass(Object o, Class<T> clazz) {
		T dest = ObjectUtil.createObjectByClass(clazz);
		ObjectUtil.override(dest, o);
		
		return dest;
	}
	
	public static Object castToType(Object o, Type type) {
		Object dest = ObjectUtil.createObjectByType(type);
		ObjectUtil.override(dest, o);
		
		return dest;
	}
	
	public static <T> T deepCopy(T object) {
		if (object == null) {
			return null;
		}
		
		Object copy = ObjectUtil.createObjectByClass(object.getClass());
		ObjectUtil.override(copy, object);
		
		@SuppressWarnings("unchecked")
		T rst = (T)copy;
		return rst;
	}
}
