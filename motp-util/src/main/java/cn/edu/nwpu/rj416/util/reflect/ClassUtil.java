/**
 * 
 */
package cn.edu.nwpu.rj416.util.reflect;

import cn.edu.nwpu.rj416.util.basic.Assert;
import cn.edu.nwpu.rj416.util.types.CollectionUtil;
import cn.edu.nwpu.rj416.util.types.StringUtil;

import java.lang.reflect.*;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author Harry
 * @since 3.0
 *
 */
public abstract class ClassUtil {

	/**
	 * Primitive types, including byte, short, int, long, float, double, boolean, char
	 * 
	 * @since 3.0
	 */
	//基本类型
	private static final Class<?>[] PRIMITIVE_TYPES = {
			byte.class, short.class, int.class, long.class, float.class, double.class, boolean.class, char.class
	};

	/**
	 * Primitive types, including Byte, Short, Integer, Long, Float, Double, Boolean, Character
	 * 
	 * @since 3.0
	 */
	//基本类型的包装类
	private static final Class<?>[] WRAPPER_PRIMITIVE_TYPES = {
			Byte.class, Short.class, Integer.class, Long.class, Float.class, Double.class, Boolean.class, Character.class
	};
	
	//基本类型的包装类加字符串类和高精度类
	private static final Type[] IMMUTABLE_TYPES = {
			Byte.class, Short.class, Integer.class, Long.class, Float.class, Double.class, Boolean.class, Character.class, String.class, BigDecimal.class
	};

	/**
	 * The package separator character '.'
	 * 
	 * @since 3.0
	 */
	private static final char PACKAGE_SEPARATOR = '.';

	/**
	 * The path separator character '/'
	 * 
	 * @since 3.0
	 */
	private static final char PATH_SEPARATOR = '/';

	/**
	 * The int value representing the synthetic modifier
	 * 
	 * @since 3.0
	 */
	//合成修饰符
	private static final int SYNTHETIC_MODIFIER = 0x00001000;

	/**
	 * Checks if the {@code modifiers} includes the synthetic modifier.
	 * 
	 * @param modifiers a set of modifiers to check
	 * @return {@code true} if the {@code modifiers} includes the synthetic modifier, otherwise {@code false}
	 * @since 3.0
	 */
	//检查一个修饰符是否是合成修饰符
	public static boolean isSynthetic(int modifiers) {
		return (modifiers & SYNTHETIC_MODIFIER) != 0;
	}

	/**
	 * Checks if a class is primitive type.
	 * 
	 * @param clazz the class to check
	 * @return {@code true} if the class is primitive type, otherwise {@code false}
	 * @since 3.0
	 */
	//检查传入Class对象是否属于基本类型
	public static boolean isPrimitiveType(Class<?> clazz) {
		if (clazz != null) {
			for (Class<?> c : PRIMITIVE_TYPES) {
				if (c == clazz) {
					return true;
				}
			}
		}
		return false;
	}

	//检查传入Type对象是否属于不可变类型（基本类型的包装类加上字符串类和高精度类）
	public static boolean isImmutableType(Type type) {
		if (type != null) {
			for (Type c : IMMUTABLE_TYPES) {
				if (c == type) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Checks if a class is wrapper primitive type.
	 * 
	 * @param clazz the class to check
	 * @return {@code true} if the class is wrapper primitive type, otherwise {@code false}
	 * @since 3.0
	 */
	//检查传入Class对象是否属于基本类型的合成类
	public static boolean isWrapperPrimitiveType(Class<?> clazz) {
		if (clazz != null) {
			for (Class<?> c : WRAPPER_PRIMITIVE_TYPES) {
				if (c == clazz) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Checks if a class is a collection.
	 * <p>
	 * Will return false if the class is null.
	 * 
	 * @param clazz the class to check
	 * @return true if the class is a collection, otherwise false
	 * @since 3.0
	 */
	//检查传入Class对象是否是一个容器类
	public static boolean isCollection(Class<?> clazz) {
		return clazz != null && Collection.class.isAssignableFrom(clazz);
	}

	/**
	 * Checks if a class is a list.
	 * <p>
	 * Will return false if the class is null.
	 * 
	 * @param clazz the class to check
	 * @return true if the class is a list, otherwise false
	 * @since 3.0
	 */
	//检查传入Class对象是否是一个列表类
	public static boolean isList(Class<?> clazz) {
		return clazz != null && List.class.isAssignableFrom(clazz);
	}

	/**
	 * Checks if a class is a map.
	 * <p>
	 * Will return false if the class is null.
	 * 
	 * @param clazz the class to check
	 * @return true if the class is a map, otherwise false
	 * @since 3.0
	 */
	//检查传入Class对象是否是一个图类
	public static boolean isMap(Class<?> clazz) {
		return clazz != null && Map.class.isAssignableFrom(clazz);
	}

	/**
	 * Checks if the method is a interface's default method.
	 * 
	 * @param method the method
	 * @return {@code true} if the method is a default method, otherwise {@code false}
	 * @since 3.0
	 */
	//判断一个方法是否是接口的默认方法
	public static boolean isDefaultMethod(Method method) {
		return ((method.getModifiers() & (Modifier.ABSTRACT | Modifier.PUBLIC | Modifier.STATIC)) == Modifier.PUBLIC) && method.getDeclaringClass().isInterface();
	}//接口方法的修饰符为public abstract static，类声明的关键字为interface

	/**
	 * Returns an {@link ArrayList} of {@link Field} objects reflecting all the fields declared by the
	 * {@code class}.<br>
	 * This includes public, protected, default (package) access, private fields, and inherited fields, but excludes
	 * synthetic fields.
	 *
	 * The elements in the returned {@link ArrayList} are not sorted and are not in any particular order.
	 * 
	 * @param clazz the class to find
	 * @return the {@link ArrayList} of {@code Field} objects representing all the declared fields of the {@code class}
	 * @throws SecurityException If a security manager, s, is present and any of the following conditions is met:
	 *             <ul>
	 *             <li>the caller's class loader is not the same as the class loader of this class and invocation of
	 *             {@link SecurityManager#checkPermission s.checkPermission} method with
	 *             {@code RuntimePermission("accessDeclaredMembers")} denies access to the declared fields within this
	 *             class
	 *             <li>the caller's class loader is not the same as or an ancestor of the class loader for the current
	 *             class and invocation of {@link SecurityManager#checkPackageAccess s.checkPackageAccess()} denies
	 *             access to the package of this class
	 *             </ul>
	 * @since 3.0
	 */
	//获取传入Class对象的所有字段
	public static List<Field> getAllFields(Class<?> clazz) throws SecurityException {
		return getFields(clazz, 0);//指定修饰符值为0，会添加所有字段进返回的字段列表
	}

	/**
	 * Returns an {@link ArrayList} of {@link Field} objects reflecting all the fields declared by the
	 * {@code class}.<br>
	 * This includes public, protected, default (package) access, private fields, and inherited fields, <br>
	 * but excludes synthetic fields, and modifiers in the skip set.
	 *
	 * <p>
	 * The elements in the returned {@link ArrayList} are not sorted and are not in any particular order.
	 * 
	 * @param clazz the class to find
	 * @param skipModifiers a set of modifiers to skip
	 * @return the {@link ArrayList} of {@code Field} objects representing all the declared fields of the {@code class}
	 * @throws SecurityException If a security manager, s, is present and any of the following conditions is met:
	 *             <ul>
	 *             <li>the caller's class loader is not the same as the class loader of this class and invocation of
	 *             {@link SecurityManager#checkPermission s.checkPermission} method with
	 *             {@code RuntimePermission("accessDeclaredMembers")} denies access to the declared fields within this
	 *             class
	 *             <li>the caller's class loader is not the same as or an ancestor of the class loader for the current
	 *             class and invocation of {@link SecurityManager#checkPackageAccess s.checkPackageAccess()} denies
	 *             access to the package of this class
	 *             </ul>
	 * @since 3.0
	 */
	//获取Class对象被指定修饰符以及合成修饰符修饰之外的所有字段（包括其继承而来的字段）
	public static List<Field> getFields(Class<?> clazz, int skipModifiers) throws SecurityException {
		if (clazz != null && clazz != Object.class) {
			List<Field> fields = new ArrayList<Field>(); //存储字段的列表
			for (Field field : clazz.getDeclaredFields()) { //遍历该类的所有字段
				if (isSynthetic(field.getModifiers())) {
					continue; //字段修饰符是合成修饰符，不添加进字段列表
				}
				if (skipModifiers > 0 && (field.getModifiers() & skipModifiers) != 0) {
					continue; //字段修饰符是指定修饰符，不添加进字段列表
				}
				fields.add(field);
			}
			CollectionUtil.addAll(fields, getFields(clazz.getSuperclass(), skipModifiers));//将继承来的字段也加以判断并加入列表
			return fields;
		}
		return null;
	}
	
	/**
	 * 获取一个类的所有可用属性，包括从父类继承的，但不包括已被覆盖的父类属性
	 */
	public static List<Field> getEffectiveFields(Class<?> clazz, int skipModifiers) throws SecurityException {
		return getEffectiveFields(clazz, new HashSet<String>(), skipModifiers);
	}
	
	//获取一个类除被指定修饰符修饰以及属于指定字段集合之外的所有有效字段（包括从父类继承的，但不包括已被覆盖的父类属性）
	private static List<Field> getEffectiveFields(
			Class<?> clazz, Set<String> fieldNames, int skipModifiers) throws SecurityException {
		if (clazz != null && clazz != Object.class) {
			List<Field> fields = new ArrayList<Field>(); //返回的字段列表
			for (Field field : clazz.getDeclaredFields()) { //遍历传入Class对象的所有字段
				if (isSynthetic(field.getModifiers())) {
					continue; //被合成修饰符修饰的字段不添加返回列表
				}
				if (skipModifiers > 0 && (field.getModifiers() & skipModifiers) != 0) {
					continue; //被指定修饰符修饰的字段不添加返回列表
				}
				if (fieldNames.contains(field.getName())) {
					continue; //属于指定字段集合中的字段不添加返回列表
				}
				fieldNames.add(field.getName());//被覆盖的父类属性字段不再重复添加
				fields.add(field);
			}
			CollectionUtil.addAll(fields, getEffectiveFields(
					clazz.getSuperclass(), fieldNames, skipModifiers));//这里的fieldNames添加了被子类覆盖的字段，所以被覆盖的父类属性字段不再重复添加
			return fields;
		}
		return null;
	}
	
	//获取一个类被指定修饰符和合成修饰符修饰之外的方法
	public static List<Method> getMethods(Class<?> clazz, int skipModifiers) throws SecurityException {
		if (clazz != null && clazz != Object.class) {
			List<Method> methods = new ArrayList<Method>();
			for (Method method : clazz.getDeclaredMethods()) {
				if (method.isSynthetic()) {
					continue;
				}
				if (skipModifiers > 0 && (method.getModifiers() & skipModifiers) != 0) {
					continue;
				}
				methods.add(method);
			}
			return methods;
		}
		return null;
	}

	/**
	 * Returns the get method of the {@link Field}.
	 * 
	 * <p>
	 * If the {@code class} has no such method, then returns null.
	 * 
	 * @param clazz the class
	 * @param field the field
	 * @return
	 * @throws IllegalArgumentException If the {@code field} or {@code class} is null
	 * @throws SecurityException If a security manager, s, is present and the caller's class loader is not the same as
	 *             or an ancestor of the class loader for the current class and invocation of
	 *             {@link SecurityManager#checkPackageAccess s.checkPackageAccess()} denies access to the package of
	 *             this class
	 * @since 3.0
	 */
	//获取指定类属性的get方法
	public static Method getGetMethod(Class<?> clazz, Field field) throws SecurityException {
		Assert.notNull(field, "Argument field cannot be null");
		Method method = getGetMethod(clazz, field.getName());//根据属性名获取get方法
		if (method != null && method.getReturnType() == field.getType()) {
			return method; //方法返回类型是指定字段类型且不空即为该字段的get方法
		}
		return null;
	}

	/**
	 * Returns the get method of the {@link Field} {@code name}.
	 * 
	 * <p>
	 * If the {@code class} has no such method, then returns null.
	 * 
	 * @param clazz the class
	 * @param name the field name
	 * @return
	 * @throws IllegalArgumentException If the {@code name} is null or length is 0, or if the {@code class} is null
	 * @throws SecurityException If a security manager, s, is present and the caller's class loader is not the same as
	 *             or an ancestor of the class loader for the current class and invocation of
	 *             {@link SecurityManager#checkPackageAccess s.checkPackageAccess()} denies access to the package of
	 *             this class
	 * @since 3.0
	 */
	//根据传入的类和其属性名获取其get方法
	public static Method getGetMethod(Class<?> clazz, String name) throws SecurityException {
		Assert.notEmpty(name, "Argument name cannot be null or empty");
		Assert.notNull(clazz, "Argument clazz cannot be null");
		String methodName = "get" + Character.toUpperCase(name.charAt(0)) + name.substring(1);//拼接指定属性的get方法名
		try {
			return clazz.getMethod(methodName);//根据get方法名获取方法返回
		} catch (NoSuchMethodException e) {
		}
		return null;
	}

	/**
	 * Returns the set method of the {@link Field} {@code name}.
	 * 
	 * <p>
	 * If the {@code class} has no such method, then returns null.
	 * 
	 * @param clazz the class
	 * @param name the field name
	 * @return
	 * @throws IllegalArgumentException If the {@code name} is null or length is 0, or if the {@code class} is null
	 * @throws SecurityException If a security manager, s, is present and the caller's class loader is not the same as
	 *             or an ancestor of the class loader for the current class and invocation of
	 *             {@link SecurityManager#checkPackageAccess s.checkPackageAccess()} denies access to the package of
	 *             this class
	 * @since 3.0
	 */
	//根据传入的属性名和类获取其set方法
	public static Method getSetMethod(Class<?> clazz, String name) throws SecurityException {
		Assert.notEmpty(name, "Argument name cannot be null or empty");
		Assert.notNull(clazz, "Argument clazz cannot be null");
		String methodName = "set" + Character.toUpperCase(name.charAt(0)) + name.substring(1);//拼接该属性的set方法名
		try {
			return clazz.getMethod(methodName);//根据set方法名获取对应的方法
		} catch (NoSuchMethodException e) {
		}
		return null;
	}

	/**
	 * Returns the set method of the {@link Field}.
	 * 
	 * <p>
	 * If the {@code class} has no such method, then returns null.
	 * 
	 * @param clazz the class
	 * @param field the field
	 * @return
	 * @throws IllegalArgumentException If the {@code field} or {@code class} is null
	 * @throws SecurityException If a security manager, s, is present and the caller's class loader is not the same as
	 *             or an ancestor of the class loader for the current class and invocation of
	 *             {@link SecurityManager#checkPackageAccess s.checkPackageAccess()} denies access to the package of
	 *             this class
	 * @since 3.0
	 */
	//获取指定类属性的set方法
	public static Method getSetMethod(Class<?> clazz, Field field) throws SecurityException {
		Assert.notNull(field, "Argument field cannot be null");
		Method method = getSetMethod(clazz, field.getName());
		if (method != null && method.getReturnType() == field.getType()) {
			return method;
		}
		return null;
	}

	/**
	 * Returns a {@link Field} object matching with the specified name.
	 * <p>
	 * Will returns null if not found.
	 * 
	 * @param clazz the class
	 * @param name the name
	 * @return a field matching with the specified name
	 * @throws SecurityException If a security manager, s, is present and any of the following conditions is met:
	 *             <ul>
	 *             <li>the caller's class loader is not the same as the class loader of this class and invocation of
	 *             {@link SecurityManager#checkPermission s.checkPermission} method with
	 *             {@code RuntimePermission("accessDeclaredMembers")} denies access to the declared fields within this
	 *             class
	 *             <li>the caller's class loader is not the same as or an ancestor of the class loader for the current
	 *             class and invocation of {@link SecurityManager#checkPackageAccess s.checkPackageAccess()} denies
	 *             access to the package of this class
	 *             </ul>
	 * @since 3.0
	 */
	//通过属性名获取指定类的具体属性字段
	public static Field getField(Class<?> clazz, String name) throws SecurityException {
		Assert.notEmpty(name, "Argument name cannot be null or empty");
		Assert.notNull(clazz, "Argument clazz cannot be null");
		if (clazz != Object.class) {
			for (Field field : clazz.getDeclaredFields()) {
				if (isSynthetic(field.getModifiers())) {
					continue;
				}
				if (field.getName().equals(name)) {
					return field; //遍历类的所有字段，字段名与指定名称相等的即为所寻找的字段
				}
			}
			return getField(clazz.getSuperclass(), name);
		}
		return null;
	}

	/**
	 * Returns the value of field with the specified name.
	 * 
	 * <p>
	 * Trying to find the get method and invoke it, then returns the value. If has not get method, then returns the
	 * value by invoking {@link Field#get(Object) Field.get(Object)}.
	 * 
	 * @param object the object
	 * @param name the name
	 * @return
	 * @throws IllegalAccessException If the get method or {@link Field#get(Object) Field.get(Object)} is enforcing Java
	 *             language access control
	 * @throws InvocationTargetException If the get method of {@code field} throws an exception
	 * @since 3.0
	 */
	//获取指定对象指定字段名的属性值
	public static Object getValue(Object object, String name) throws IllegalAccessException, InvocationTargetException {
		Assert.notEmpty(name, "Argument name cannot be null or empty");
		if (object != null) {
			Field field = getField(object.getClass(), name);//先根据字段名获取具体字段
			if (field != null) {
				return getValue(object, field); //再根据字段获取属性值
			}
		}
		return null;
	}

	/**
	 * Returns the {@code field}'s value of {@code object}.
	 * 
	 * <p>
	 * Trying to find the get method and invoke it, then returns the value. If has not get method, then returns the
	 * value by invoking {@link Field#get(Object) Field.get(Object)}.
	 * 
	 * @param object the object
	 * @param field the field
	 * @return
	 * @throws IllegalAccessException If the get method or {@link Field#get(Object) Field.get(Object)} is enforcing Java
	 *             language access control
	 * @throws InvocationTargetException If the get method of {@code field} throws an exception
	 * @since 3.0
	 */
	//获取指定对象指定字段的属性值
	public static Object getValue(Object object, Field field) throws IllegalAccessException, InvocationTargetException {
		Assert.notNull(object, "Argument object cannot be null");
		Method method = getGetMethod(object.getClass(), field);//获取指定对象指定属性的get方法
		Object value = null;
		if (method != null) {
			method.setAccessible(true);
			value = method.invoke(object); //有get方法，从get方法中获取
		} else {
			field.setAccessible(true);
			value = field.get(object); //无get方法，通过字段获取
		}
		return value;
	}

	/**
	 * Sets the value of field with the specified name.
	 * 
	 * <p>
	 * Trying to find the set method and invoke it. If has not set method, then invokes {@link Field#set(Object, Object)
	 * Field.set(Object, Object)}.
	 * 
	 * @param object the object
	 * @param name the name
	 * @param value the value
	 * @throws IllegalAccessException If the set method or {@link Field#set(Object, Object) Field.set(Object, Object)}
	 *             is enforcing Java language access control
	 * @throws InvocationTargetException If the set method of {@code field} throws an exception
	 * @since 3.0
	 */
	//指定属性名设置对象中该属性的值
	public static void setValue(Object object, String name, Object value) throws IllegalAccessException, InvocationTargetException {
		Assert.notEmpty(name, "Argument name cannot be null or empty");
		if (object != null) {
			Field field = getField(object.getClass(), name);//先通过属性名获取具体字段
			if (field != null) {
				setValue(object, field, value);//再根据具体字段设置属性值
			}
		}
	}

	/**
	 * Sets the {@code field}'s value of {@code object}.
	 * 
	 * <p>
	 * Trying to find the set method and invoke it. If has not set method, then invokes {@link Field#set(Object, Object)
	 * Field.set(Object, Object)}.
	 * 
	 * @param object the object
	 * @param field the field
	 * @param value the value
	 * @throws IllegalAccessException If the set method or {@link Field#set(Object, Object) Field.set(Object, Object)}
	 *             is enforcing Java language access control
	 * @throws InvocationTargetException If the set method of {@code field} throws an exception
	 * @since 3.0
	 */
	//设置一个对象中指定属性的值
	public static void setValue(Object object, Field field, Object value) throws IllegalAccessException, InvocationTargetException {
		Assert.notNull(object, "Argument object cannot be null");
		Method method = getSetMethod(object.getClass(), field);//根据字段获取对象的set方法
		if (method != null) {
			method.setAccessible(true);
			method.invoke(object, value);//有set方法使用set方法设置
		} else {
			field.setAccessible(true);
			field.set(object, value); //没有set方法用字段设置
		}
	}

	/**
	 * Converts a '.' based class name to a '/' based resource path.
	 * <p>
	 * Will returns null if the class name is null.
	 * 
	 * @param className the class name
	 * @return the resource path
	 * @since 3.0
	 */
	//将类的全名转换成资源路径（"."操作符换成"/"）
	public static String convertClassNameToResourcePath(String className) {
		if (StringUtil.isEmpty(className)) {
			return className;
		}
		return className.replace(PACKAGE_SEPARATOR, PATH_SEPARATOR);//"."操作符换成"/"
	}

	/**
	 * Converts a class name to a table name.
	 * <p>
	 * Will returns null if the class name is null.
	 * <p>
	 * For example: Class name {@code ClassName} will be converted to table name {@code class_name}.
	 * 
	 * @param className the class name to convert
	 * @return the table name
	 * @since 3.0
	 */
	//将类名转化成表格名（例：ClassUtil转换之后变成class_util）
	public static String convertClassNameToTableName(String className) {
		if (StringUtil.isEmpty(className)) {
			return className;
		}
		StringBuilder sb = new StringBuilder();
		sb.append(Character.toLowerCase(className.charAt(0)));//类名首字母转成小写
		int length = className.length(); //获取类名长度
		char c;
		for (int i = 1; i < length; i++) {
			c = className.charAt(i); //遍历类名中的每个字符
			if (Character.isUpperCase(c)) {
				sb.append('_').append(Character.toLowerCase(c));//将大写字母前加下划线后再转成小写连接
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	/**
	 * Converts a camel case string to underline case string.
	 * 
	 * @param camelCaseString the camel case string
	 * @return a underline case string
	 * @since 3.0
	 */
	//"驼峰式"命名转成"下划线式"命名（例：ClassUtil转换之后变成class_util）
	public static String convertCamelCaseToUnderlineCase(String camelCaseString) {
		if (StringUtil.isEmpty(camelCaseString)) {
			return camelCaseString;
		}
		StringBuilder sb = new StringBuilder();
		sb.append(Character.toLowerCase(camelCaseString.charAt(0)));//首字母先转小写
		int length = camelCaseString.length();
		char c;
		for (int i = 1; i < length; i++) {
			c = camelCaseString.charAt(i);
			if (Character.isUpperCase(c)) {
				sb.append('_').append(Character.toLowerCase(c));//之后的每一个大写字母前加下划线并将大写转成小写
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}
	
	//判断两个Class对象是否相等
	public static boolean equals(Class<?> cls1, Class<?> cls2) {
		return cls1 == cls2;
	}
	
	//判断两个Class对象是否不等
	public static boolean notEquals(Class<?> cls1, Class<?> cls2) {
		return cls1 != cls2;
	}
}
