package cn.edu.nwpu.rj416.util.types;

import cn.edu.nwpu.rj416.util.basic.Assert;

import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Harry
 * @since Harame3.0
 *
 */
//容器类工具
public abstract class CollectionUtil {

	/**
	 * Returns an empty array list.
	 * 
	 * @return an empty array list
	 * @since 3.0
	 */
	public static <T> ArrayList<T> newArrayList() { //生成一个空的ArrayList对象
		return new ArrayList<T>();
	}

	/**
	 * Returns an empty array list with the specified initial capacity
	 * 
	 * @param initialCapacity the initial capacity of the list
	 * @return an empty array list with the specified initial capacity
	 * @throws IllegalArgumentException If the specified initial capacity is negative
	 * @since 3.0
	 */
	public static <T> ArrayList<T> newArrayList(int initialCapacity) { //生成一个指定大小的ArrayList对象
		Assert.notNegativeNumber(initialCapacity, "Argument initialCapacity cannot be negative");
		return new ArrayList<T>(initialCapacity);
	}

	/**
	 * Returns an array list containing the elements of the specified collection.<br>
	 * In the order they are returned by the collection's iterator.
	 *
	 * @param c the collection whose elements are to be placed into this array list
	 * @since 3.0
	 */
	public static <T> ArrayList<T> newArrayList(Collection<T> c) {
		return isEmpty(c) ? new ArrayList<T>() : new ArrayList<T>(c);
	} //传参容器对象不为空返回容纳传参元素的ArrayList对象，为空返回空的ArrayList对象

	/**
	 * Returns an empty linked list.
	 * 
	 * @return an empty linked list
	 * @since 3.0
	 */
	public static <T> LinkedList<T> newLinkedList() { //生成一个空的LinkedList（链表）对象
		return new LinkedList<T>();
	}

	/**
	 * Returns an empty hash map.
	 * 
	 * @return an empty hash map
	 * @since 3.0
	 */
	public static <K, V> HashMap<K, V> newHashMap() { //生成一个空的HashMap（哈希表）对象
		return new HashMap<K, V>();
	}

	/**
	 * Returns a hash map with the key and value.
	 * 
	 * @param key the key
	 * @param value the value
	 * @return a hash map with the key and value
	 * @since 3.0
	 */
	public static <K, V> HashMap<K, V> newHashMap(K key, V value) { //将指定键值对添加到一个空的哈希表中并返回
		HashMap<K, V> map = new HashMap<K, V>();
		map.put(key, value);
		return map;
	}

	/**
	 * Returns an empty concurrent hash map.
	 * 
	 * @return an empty concurrent hash map.
	 * @since 3.0
	 */
	public static <K, V> ConcurrentHashMap<K, V> newConcurrentHashMap() {
		return new ConcurrentHashMap<K, V>();
	} //创建一个空的并发哈希表对象

	/**
	 * Checks if the collection is null or length is 0.
	 * 
	 * @param collection the collection to check
	 * @return true if the collection is null or length is 0, otherwise false
	 * @since 3.0
	 */
	public static boolean isEmpty(Collection<?> collection) {
		return collection == null || collection.isEmpty();
	} //检查集合对象是否为空（内容为空或对象指向为空均返回true）

	/**
	 * Checks if the collection is not null and length is more than 0.
	 * 
	 * @param collection the collection to check
	 * @return true if the collection is not null and length is more than 0, otherwise false
	 * @since 3.0
	 */
	public static boolean isNotEmpty(Collection<?> collection) {
		return collection != null && !collection.isEmpty();
	} //集合非空返回true

	/**
	 * Checks if the map is null or length is 0.
	 * 
	 * @param map the map to check
	 * @return true if the map is null or length is 0, otherwise false
	 * @since 3.0
	 */
	public static boolean isEmpty(Map<?, ?> map) {
		return map == null || map.isEmpty();
	}

	/**
	 * Checks if the map is not null and length is more than 0.
	 * 
	 * @param map the map to check
	 * @return true if the map is not null and length is more than 0, otherwise false
	 * @since 3.0
	 */
	public static boolean isNotEmpty(Map<?, ?> map) {
		return map != null && !map.isEmpty();
	}

	/**
	 * Gets the value to which the specified key is mapped, or null if this map contains no mapping for the key.
	 * <p>
	 * Will returns null if the map is null.
	 * 
	 * @param map the map
	 * @param key the key
	 * @return the value to which the specified key is mapped
	 * @since 3.0
	 */
	public static <K, V> V get(Map<K, V> map, K key) { //通过Map的键获取其对应的值
		return isEmpty(map) ? null : map.get(key);
	}

	/**
	 * Returns the number of elements in the collection.<br>
	 * If this collection contains more than Integer.MAX_VALUE elements, returns Integer.MAX_VALUE.
	 * <p>
	 * Will returns 0 if the collection is null.
	 * 
	 * @param collection the collection
	 * @return the number of elements in the collection
	 * @since 3.0
	 */
	public static int size(Collection<?> collection) { //获取集合的大小
		return collection == null ? 0 : collection.size();
	}

	/**
	 * Appends all of the elements in the specified collection to the end of the collection.<br>
	 * Will news an {@link ArrayList}, if the collection is null.
	 * 
	 * @param collection the collection to add
	 * @param c the collection whose elements to be added
	 * @return true if all collection's elements be added, otherwise false
	 * @since 3.0
	 */
	//将集合c中的所有内容添加到一个新集合中返回是否添加成功
	public static <T> boolean addAll(Collection<T> collection, Collection<T> c) {
		if (collection == null) {
			collection = new ArrayList<T>();
		}
		if (c == null) {
			return true;
		}
		return collection.addAll(c);
	}

	/**
	 * Appends all of the elements in the bytes array to the end of the bytes collection.<br>
	 * Will news an {@link ArrayList}, if the collection is null.
	 * 
	 * @param collection the collection to add
	 * @param array the array whose elements to be added
	 * @return true if all array's elements be added, otherwise false
	 * @since 3.0
	 */
	//将字节码数组中的所有内容添加到一个字节类集合中返回是否添加成功
	public static boolean addAllBytes(Collection<Byte> collection, byte[] array) {
		if (collection == null) {
			collection = new ArrayList<Byte>();
		}
		if (ArrayUtil.isNotEmpty(array)) {
			for (byte b : array) {
				collection.add(b);
			}
		}
		return true;
	}

	/**
	 * Appends all of the elements in the specified array to the end of the collection.<br>
	 * Will news an {@link ArrayList}, if the collection is null.
	 * 
	 * @param collection the collection to add
	 * @param array the array whose elements to be added
	 * @return true if all array's elements be added, otherwise false
	 * @since 3.0
	 */
	//将一个泛型数组中的内容添加到一个集合中并返回是否添加成功
	public static <T> boolean addAll(Collection<T> collection, T[] array) {
		if (collection == null) {
			collection = new ArrayList<T>();
		}
		if (ArrayUtil.isEmpty(array)) {
			return true;
		}
		return collection.addAll(Arrays.asList(array));
	}

	/**
	 * Returns an array containing all of the elements in the collection.
	 * 
	 * @param <T> the runtime type of the array
	 * @param collection the collection
	 * @param array the array
	 * @return an array containing all of the elements in the {@code collection}
	 * @throws ArrayStoreException if the runtime type of the specified array is not a super type of the runtime type of
	 *             every element in this list
	 * @since 3.0
	 */
	//将集合中存放的内容转成数组存放并返回
	public static <T> T[] toArray(Collection<T> collection, T[] array) {
		if (collection == null) {
			return null;
		}
		return (T[]) collection.toArray(array);
	}

	/**
	 * Returns an array containing all of the elements in the list.
	 * 
	 * @param <T> the runtime type of the array
	 * @param list the list
	 * @return an array containing all of the elements in the {@code list}
	 * @throws ArrayStoreException if the runtime type of the specified array is not a super type of the runtime type of
	 *             every element in this list
	 * @since 3.0
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[] toArray(List<T> list) {
		T[] array = null;
		if (list != null) {
			array = (T[]) Array.newInstance(list.get(0).getClass(), list.size()); //创建Array实例（反射）
		}
		return toArray(list, array); //将List对象中存放的内容转成数组存放
	}
}
