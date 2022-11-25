package cn.edu.nwpu.rj416.motp.serializer.motp.loader.loader;



import cn.edu.nwpu.rj416.motp.serializer.motp.loader.MotpLoader;
import cn.edu.nwpu.rj416.type.util.TypeUtil;
import cn.edu.nwpu.rj416.util.objects.MByteBuffer;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ListLoader {
	/**
	 * 
	 * @param buffer
	 * @param size
	 * @param destType 读取结果类型，可能包含以下情况: </br>
	 * (1) Set&lt;E&gt; / HashSet&lt;E&gt 返回HashSet&lt;E&gt</br>
	 * (2) List&lt;E&gt / ArrayList&lt;E&gt 返回ArrayList&lt;E&gt</br>
	 * (4) byte[] MOTP二进制原始数据</br>
	 * (3) E[] 返回数组</br>
	 * @return
	 */
	public static Object readListData(
			MotpLoader loader,
			MByteBuffer buffer,
			Type destType) {
		int size = buffer.readMVLInt().castToInteger();
		if (destType instanceof ParameterizedType) {
			/*
			 * 如果destType是泛型类型，那么只可能是以下情况：
			 * (1) Set<E> / HashSet<E>
			 * (2) List<E> / ArrayList<E>
			 * 所以先判断泛型参数数量，并获取泛型参数类型
			 */
			ParameterizedType pt = (ParameterizedType)destType;
			do {
				Type[] typeArgs = pt.getActualTypeArguments();
				if (typeArgs.length != 1) {
					break;
				}
				
				if (pt.getRawType() == Set.class 
						|| pt.getRawType() == HashSet.class) {
					return ListLoader.readListDataAsHashSet(
							loader, buffer, size, typeArgs[0]);
				}
				
				if (pt.getRawType() == List.class 
						|| pt.getRawType() == ArrayList.class) {
					return ListLoader.readListDataAsArrayList(
							loader, buffer, size, typeArgs[0]);
				}
			} while (false);
			
			SetLoader.skipSetData(loader, buffer, size);
			return null;
		} else if (destType instanceof Class){
			/*
			 * 如果destType是Class类型，那么只可能是数组：
			 * (3) byte[] 
			 */
			Class<?> clazz = (Class<?>)destType;
			if (clazz.isArray()) {
				Class<?> componentType = clazz.getComponentType();
				if (componentType == byte.class) {
					int currentPos = buffer.getOffset();
					SetLoader.skipSetData(loader, buffer, size);
					byte[] bytes = buffer.readBytes(
							currentPos, 
							buffer.getOffset() - currentPos);
					return bytes;
				}
				
				return ListLoader.readListDataAsArray(
						loader, 
						buffer, size, clazz.getComponentType());
			} else {
				if (clazz == Set.class 
						|| clazz == HashSet.class) {
					return ListLoader.readListDataAsHashSet(
							loader, buffer, size);
				}
				
				if (clazz == List.class 
						|| clazz == ArrayList.class) {
					return ListLoader.readListDataAsArrayList(
							loader, buffer, size);
				}
				SetLoader.skipSetData(loader, buffer, size);
				return null;
			}
		} else if (destType instanceof GenericArrayType) {
			GenericArrayType gat = (GenericArrayType)destType;
			Class<?> componentClass = TypeUtil.getRawType(gat.getGenericComponentType());
			return ListLoader.readListDataAsArray(loader, buffer, size, componentClass);
			
		} else {
			/*
			 * 目前应该不会出现这种情况
			 */
			SetLoader.skipSetData(loader, buffer, size);
			return null;
		}
	}
	
	private static HashSet<Object> readListDataAsHashSet(
			MotpLoader loader,
			MByteBuffer buffer,
			int size,
			Type eleType) {
		HashSet<Object> set = new HashSet<>();

		for (int i = 0; i < size; i++) {
			Object ele = MotpDataLoader.readData(loader, buffer, eleType);
			set.add(ele);
		}
		return set;
	}
	private static HashSet<Object> readListDataAsHashSet(
			MotpLoader loader,
			MByteBuffer buffer, 
			int size) {
		HashSet<Object> set = new HashSet<>();

		for (int i = 0; i < size; i++) {
			Object ele = MotpDataLoader.readData(loader, buffer);
			set.add(ele);
		}
		return set;
	}
	
	private static ArrayList<Object> readListDataAsArrayList(
			MotpLoader loader,
			MByteBuffer buffer, 
			int size,
			Type eleType) {
		ArrayList<Object> list = new ArrayList<>();

		for (int i = 0; i < size; i++) {
			Object ele = MotpDataLoader.readData(loader, buffer, eleType);
			list.add(ele);
		}
		return list;
	}
	
	private static ArrayList<Object> readListDataAsArrayList(
			MotpLoader loader,
			MByteBuffer buffer, 
			int size) {
		ArrayList<Object> list = new ArrayList<>(size);

		for (int i = 0; i < size; i++) {
			Object ele = MotpDataLoader.readData(loader, buffer);
			list.add(ele);
		}
		return list;
	}
	
	private static Object readListDataAsArray(
			MotpLoader loader,
			MByteBuffer buffer, 
			int size,
			Class<?> componentType) {

		Object arr = Array.newInstance(componentType, size);
//        ConstructorAccess constructorAccess=ConstructorAccess.get(Array.class);
//        constructorAccess.newInstance()
		for (int i = 0; i < size; i++) {
			Object ele = MotpDataLoader.readData(loader, buffer, componentType);
			Array.set(arr, i, ele);
		}
		return arr;
	}
	
	public static  void skipListData(
			MotpLoader loader,
			MByteBuffer buffer, 
			int size) {
		for (int i = 0; i < size; i++) {
			MotpDataLoader.readData(loader, buffer);
		}
	}
}
