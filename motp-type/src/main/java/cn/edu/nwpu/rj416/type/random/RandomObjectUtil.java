package cn.edu.nwpu.rj416.type.random;



import cn.edu.nwpu.rj416.type.util.ObjectUtil;
import cn.edu.nwpu.rj416.type.util.TypeUtil;
import cn.edu.nwpu.rj416.util.reflect.ClassUtil;

import java.lang.reflect.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

//随机对象生成工具
public class RandomObjectUtil {
	//为传参对象随机赋值
	public static void setRandomValue(Object o) {
		if (o == null) {
			return;
		}
		MRandom r = new MRandom();
		setObjectValue(r, o);
	}
	
	//生成指定类型的随机对象
	public static Object randomObject(Type type) {
		if (type == null) {
			return null;
		}
		MRandom r = new MRandom(); 
 		Object obj = createValue(r, type);
		return obj;
	}
	
	//生成指定类的随机对象
	public static <T> T randomObject(Class<T> clazz) {
		if (clazz == null) {
			return null;
		}
		MRandom r = new MRandom(); 
 		T obj = createValueByClass(r, clazz);
		return obj;
	}
	
	//生成指定长度的随机数组（组件类型由Class对象指定）
	public static <E> E[] randomArray(Class<E> componentType, int len) {
		MRandom r = new MRandom();
		return createArray(r, componentType, len);
	}
	
	//生成指定长度的基本类型随机数组（组件类型由Class对象指定）
	public static Object randomPrimitiveArray(Class<?> componentType, int len) {
		MRandom r = new MRandom();
		return createPrimitiveArray(r, componentType, len);
	}
	
	//生成指定长度的随机数组列表（元素类型由Class对象指定）
	public static <E> ArrayList<E> randomArrayList(Class<E> eleType, int len) {
		MRandom r = new MRandom();
		return createArrayListOfClass(r, eleType, len);
	}
	
	//生成指定长度的随机数组（组件类型由泛型类型指定）
	public static ArrayList<Object> randomArrayList(Type eleType, int len) {
		MRandom r = new MRandom();
		return createArrayList(r, eleType, len);
	}
	
	//生成指定长度的随机HashSet（元素类型由Class对象指定）
	public static <E> HashSet<E> randomHashSet(Class<E> eleType, int len) {
		MRandom r = new MRandom();
		return createHashSetOfClass(r, eleType, len);
	}
	
	//生成指定长度的随机HashSet（元素类型由泛型类型指定）
	public static HashSet<Object> randomHashSet(Type eleType, int len) {
		MRandom r = new MRandom();
		return createHashSet(r, eleType, len);
	}

	//生成指定长度的随机HashMap（键值类型由Class对象指定）
	public static <K, V> HashMap<K, V> randomHashMap(Class<K> keyType, Class<V> valueType, int len) {
		MRandom r = new MRandom();
		return createHashMapOfClass(r, keyType, valueType, len);
	}

	//生成指定长度的随机HashMap（键值类型由泛型类型指定）
	public static  HashMap<Object, Object> randomHashMap(Type keyType, Type valueType, int size) {
		MRandom r = new MRandom();
		return createHashMap(r, keyType, valueType, size);
	}
	
	//随机设置对象属性值
	private static void setObjectValue(MRandom r, Object o) {
		if (o == null) {
			return;
		}
		Class<?> clazz = o.getClass(); //获取实例对象的Class对象
		List<Field> fields = ClassUtil.getFields(clazz, Modifier.FINAL | Modifier.STATIC); //获取除被final与static修饰的所有字段
		if (fields == null) {
			return;
		}

 		for (Field f : fields) { //遍历所有获取字段
			Object value = RandomObjectUtil.createValue(r, f.getGenericType());//生成该字段类型的随机值
			if (value == null) {
				continue;
			}
			f.setAccessible(true);
			try {
				f.set(o, value); //尝试将对象的值设为随机生成的值
			} catch (IllegalArgumentException | IllegalAccessException e) {
				
			}
		}
	}
	
	//根据Class对象生成与之对应的随机实例对象
	private static <T> T createValueByClass(MRandom r, Class<T> clazz) {
		Object value = null;
        System.out.println("反序列化；");
		if (TypeUtil.equals(clazz, Object.class)) { //传参对象为Object类
			@SuppressWarnings("unchecked")
			T objValue = (T)String.format("RandomObject%d", r.nextInt(10000));//强制向上转型一个随机字符串
            System.out.println("object:"+objValue);
			return objValue;
		} else if (clazz.isEnum()) { //传参对象为枚举类
			@SuppressWarnings("unchecked")
			Class<? extends Enum<?>> enumClass = (Class<? extends Enum<?>>)clazz;
			value = RandomEnumUtil.randomEnum(enumClass); //根据传参的枚举获取其一个随机元素
		} else if (clazz.isArray()) { //传参对象为数组类
			Class<?> componentClass = clazz.getComponentType(); //获取数组组件的Class类
			if (componentClass != null) {
				int len = r.nextInt(10); //随机长度，最大为10
				if (componentClass.isPrimitive()) {
					value = RandomObjectUtil.createPrimitiveArray(r, componentClass, len);
				} else {
					value = RandomObjectUtil.createArray(r, componentClass, len);
				}
			}
		} else if (clazz == List.class || clazz == ArrayList.class) {//传参对象为列表类
			value = new ArrayList<>(); //返回新建数组列表对象
		} else if (clazz == Set.class || clazz == HashSet.class) { //传参对象为Set类
			value = new HashSet<>(); //返回新建HashSet对象
		} else if (clazz == Map.class || clazz == HashMap.class) { //传参对象为Map类
			value = new HashMap<>(); //返回新建HashMap对象
		} else if (clazz == boolean.class || clazz == Boolean.class) { //传参对象为布尔型或布尔型的包装类
			value = r.nextBoolean(); //返回随机布尔数
		} else if (clazz == byte.class || clazz == Byte.class) { //传参对象为字节型或字节型的包装类
			value = (byte)r.nextInt(); //返回随机字节数
		} else if (clazz == short.class || clazz == Short.class) { //传参对象为短整型或短整型的包装类
			value = (short)r.nextInt(); //返回随机短整型数
		} else if (clazz == int.class || clazz == Integer.class) { //传参对象为整型或整型的包装类
			value = r.nextInt(); //返回随机整型数
		} else if (clazz == long.class || clazz == Long.class) { //传参对象为长整型或长整型的包装类
			value = r.nextLong(); //返回随机长整型数
		} else if (clazz == float.class || clazz == Float.class) { //传参对象为浮点型或浮点型的包装类
			value = r.nextFloat(); //返回随机浮点数
		} else if (clazz == double.class || clazz == Double.class) { //传参对象为双精度浮点型或双精度浮点型的包装类
			value = r.nextDouble(); //返回随机双精度浮点数
		} else if (clazz == char.class || clazz == Character.class) { //传参对象为字符型或字符型的包装类
			value = r.nextChar(); //返回随机字符
		} else if (clazz == String.class) { //传参对象为字符串类
			value = r.nextString(); //返回随机字符串
		} else if (clazz == Date.class) { //传参对象为日期类
			value = r.nextDate(); //返回随机日期
		} else if (clazz == BigDecimal.class) { //传参对象为高精度类
			value = r.nextBigDecimal(); //返回随机高精度数
		} else if (clazz == BigInteger.class) { //传参对象为大整数类
			value = r.nextBigInteger(); //返回随机大整数
		} else {
            System.out.println("序列化");
			value = ObjectUtil.createObjectByClass(clazz); //传参对象为自定义类，递归调用为其赋值
			if (value != null) {
				setObjectValue(r, value);
			}
		}
		if (value == null) {
			return null;
		}
		@SuppressWarnings("unchecked")
		T rst = (T)value;
		return rst;
	}
	
	//根据泛型类型生成与之对应的随机实例对象
	private static Object createValue(MRandom r, Type type) {
		Object value = null;
		if (type instanceof Class) {
			value = createValueByClass(r, (Class<?>)type);
		} else if (type instanceof ParameterizedType) {
			ParameterizedType paramType = (ParameterizedType)type;
			Type rawType = paramType.getRawType();
			int len = r.nextInt(5);
			if (rawType == List.class || rawType == ArrayList.class) {
				Type[] tps = paramType.getActualTypeArguments();
				if (tps.length == 1) {
					value = createArrayList(r, tps[0], len);
				} else {
					value = new ArrayList<>();
				}
			} else if (rawType == Set.class || rawType == HashSet.class) {
				Type[] tps = paramType.getActualTypeArguments();
				if (tps.length == 1) {
					value = createHashSet(r, tps[0], len);
				} else {
					value = new HashSet<>();
				}
			} else if (rawType == Map.class || rawType == HashMap.class) {
				Type[] tps = paramType.getActualTypeArguments();
				if (tps.length == 2) {
					value = createHashMap(r, tps[0], tps[1], len);
				} else {
					value = new HashMap<>();
				}
			}
		}

		return value;
	}
	
	//生成随机的基本类型数组（指定长度和元素类型）
	private static Object createPrimitiveArray(MRandom r, Class<?> componentType, int len) {

		if (componentType == boolean.class) {
			boolean[] arr = new boolean[len];
			for (int i = 0; i < len; i++) {
				arr[i] = r.nextBoolean();
			}
			return arr;
		} else if (componentType == byte.class) {
			byte[] arr = new byte[len];
			for (int i = 0; i < len; i++) {
				arr[i] = r.nextByte();
			}
			return arr;
		} else if (componentType == short.class) {
			short[] arr = new short[len];
			for (int i = 0; i < len; i++) {
				arr[i] = r.nextShort();
			}
			return arr;
		} else if (componentType == int.class) {
			int[] arr = new int[len];
			for (int i = 0; i < len; i++) {
				arr[i] = r.nextInt();
			}
			return arr;
		} else if (componentType == long.class) {
			long[] arr = new long[len];
			for (int i = 0; i < len; i++) {
				arr[i] = r.nextLong();
			}
			return arr;
		} else if (componentType == float.class) {
			float[] arr = new float[len];
			for (int i = 0; i < len; i++) {
				arr[i] = r.nextFloat();
			}
			return arr;
		} else if (componentType == double.class) {
			double[] arr = new double[len];
			for (int i = 0; i < len; i++) {
				arr[i] = r.nextDouble();
			}
			return arr;
		} else if (componentType == char.class) {
			char[] arr = new char[len];
			for (int i = 0; i < len; i++) {
				arr[i] = r.nextChar();
			}
			return arr;
		} else {
			return null;
		}
	}
	
	//生成随机的指定组件类型的数组（指定长度）
	private static <E> E[] createArray(MRandom r, Class<E> componentType, int len) {
		if (componentType == null || componentType.isPrimitive()) {
			return null;
		}
		
		@SuppressWarnings("unchecked")
		E[] array = (E[])Array.newInstance(componentType, len); //创建组件类型的数组对象再强转为指定类型数组
		for (int i = 0; i < len; i++) {
			array[i] = createValueByClass(r, componentType); //给数组中的每个元素赋随机的值
		}
		
		return array;
	}
	
	//生成随机的指定组件类型的数组列表（组件类型以泛型类型表示）
	private static ArrayList<Object> createArrayList(MRandom r, Type componentType, int len) {
		if (componentType == null) {
			return null;
		}
		
		ArrayList<Object> list = new ArrayList<>();
		for (int i = 0; i < len; i++) {
			list.add(createValue(r, componentType));
		}
		
		return list;
	}
	
	//生成随机的指定组件类型的数组列表（组件类型以Class对象表示）
	private static <E> ArrayList<E> createArrayListOfClass(MRandom r, Class<E> componentType, int len) {
		if (componentType == null) {
			return null;
		}
		
		ArrayList<E> list = new ArrayList<E>();
		for (int i = 0; i < len; i++) {
			list.add(createValueByClass(r, componentType));
		}
		
		return list;
	}
	
	//生成随机的指定组件类型的HashSet集合（组件类型以Class对象表示）
	private static <E> HashSet<E> createHashSetOfClass(MRandom r, Class<E> componentType, int len) {
		if (componentType == null) {
			return null;
		}
		
		HashSet<E> set = new HashSet<E>();
		for (int i = 0; i < len; i++) {
			set.add(createValueByClass(r, componentType));
		}
		
		return set;
	}

	//生成随机的指定组件类型的HashSet集合（组件类型以泛型类型表示）
	private static HashSet<Object> createHashSet(MRandom r, Type eleType, int len) {
		if (eleType == null) {
			return null;
		}
		
		HashSet<Object> set = new HashSet<Object>();
		for (int i = 0; i < len; i++) {
			set.add(createValue(r, eleType));
		}
		
		return set;
	}
	
	//生成随机的指定值类型的HashMap（值类型以Class对象表示）
	private static <K, V> HashMap<K, V> createHashMapOfClass(MRandom r, Class<K> keyType, Class<V> valueType, int size) {
		if (keyType == null || valueType == null) {
			return null;
		}
		
		HashMap<K, V> map = new HashMap<K, V>();
		for (int i = 0; i < size; i++) {
			K key = createValueByClass(r, keyType);
			while (map.containsKey(key)) {
				key = createValueByClass(r, keyType);
			}
			V value = createValueByClass(r, valueType);
			map.put(key, value);
		}
		
		return map;
	}
	
	//生成随机的指定值类型的HashMap（值类型以泛型类型表示）
	private static HashMap<Object, Object> createHashMap(MRandom r, Type keyType, Type valueType, int size) {
		if (keyType == null || valueType == null) {
			return null;
		}
		
		HashMap<Object, Object> map = new HashMap<>();
		for (int i = 0; i < size; i++) {
			Object key = createValue(r, keyType);
			while (map.containsKey(key)) {
				key = createValue(r, keyType);
			}
			Object value = createValue(r, valueType);
			map.put(key, value);
		}
		
		return map;
	}
}
