package cn.edu.nwpu.rj416.motp.serializer.motp.loader;


import cn.edu.nwpu.rj416.motp.serializer.motp.schema.AbstractSchema;
import cn.edu.nwpu.rj416.motp.serializer.motp.schema.MotpEnumSchema;
import cn.edu.nwpu.rj416.util.objects.MByteBuffer;
import cn.edu.nwpu.rj416.util.types.StringUtil;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


public class EnumLoader {

	
	/**
	 * 从MByteBuffer中读取一个枚举类型的值
	 * @param buffer MByteBuffer
	 * @param destType
	 * @return
	 * 正确的返回值类型包括：</br>
	 * Enum&lt;EnumType&gt; 	通用定义的枚举类型</br>
	 * String 					枚举类型的名称字符串</br>
	 * int						枚举类型的ordinal</br>
	 * Integer					枚举类型的ordinal</br>
	 * EnumType 				具体枚举类型</br>
	 */
	public static Object readEnumData(
			MotpLoader loader,
			MByteBuffer buffer, Type destType) {
		int schemaNumber = buffer.readMVLInt().castToInteger();
		int ordinal = buffer.readMVLInt().castToInteger();
		
		if (destType instanceof Class) {
			//如果需要返回int或Integer，则无需做处理，直接返回ordinal
			if (destType == int.class) {
				return ordinal;
			} else if (destType == Integer.class) {
				return Integer.valueOf(ordinal);
			}
		} else {
			/*
			 * 如果返回类型不是class，那么只有一种情况，就是枚举类型的通用定义
			 * Enum<EnumType>
			 * 此时的destType类型只能是ParameterizedType
			 */
			if (!(destType instanceof ParameterizedType)) {
				return null;
			}
			/*
			 * 将Enum<EnumType>转换为等价的EnumType
			 */
			ParameterizedType pt = (ParameterizedType)destType;
			if (pt.getRawType() != Enum.class) {
				return null;
			}
			
			Type[] typeArgs = pt.getActualTypeArguments();
			if (typeArgs.length != 1) {
				return null;
			}
			
			if (!(typeArgs[0] instanceof Class)) {
				return null;
			}
			
			destType = typeArgs[0];
		}

		
		/*
		 * 此时，只剩下返回EnumType和String两种情况未处理
		 * 这两种情况都需要先根据EnumSchema获取枚举值的字符串名称
		 */
		
		/*
		 * 获取MOTP中枚举类型对应的名称 
		 */
		AbstractSchema enumSchema = loader.getSchemas().get(schemaNumber);
		if (enumSchema == null) {
			return null;
		}
		if (!(enumSchema instanceof MotpEnumSchema)) {
			return null;
		}
		String name = ((MotpEnumSchema)enumSchema).getValueByOrdinal(ordinal);
		if (StringUtil.isEmpty(name)) {
			return null;
		}

		/*
		 * 如果目标返回类型是String，直接返回枚举值的名称
		 */
		if (destType == String.class) {
			return name;
		}
		
		/*
		 * 那么现在，只剩下一种情况，就是需要返回具体的枚举类型
		 */
		Class<?> destClass = (Class<?>)destType;
		if (!destClass.isEnum()) {
			return null;
		}
		
		@SuppressWarnings("unchecked") 
		Class<Enum<?>> enumClass = (Class<Enum<?>>)destClass;
		
		MotpLoaderEnumClassCache ecc = loader.getEnumClassCache().get(enumClass);
		if (ecc == null) {
			ecc = new MotpLoaderEnumClassCache();
			ecc.buildEnum(enumClass);
			loader.getEnumClassCache().put(enumClass, ecc);
		}
		
		Enum<?> enumValue = ecc.getConstantByName(name);
		return enumValue;
	}
}
