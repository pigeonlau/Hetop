package cn.edu.nwpu.rj416.motp.serializer.motp.util;


import cn.edu.nwpu.rj416.motp.serializer.motp.MotpType;
import cn.edu.nwpu.rj416.motp.serializer.motp.tp.MotpTypeProcesser;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MotpTypeUtil {
	public static byte getType(Class<?> clazz) {
		MotpTypeProcesser processer = MotpProcesserMapping.getProcesser(clazz);
		if (processer != null) {
			return processer.getMosType();
		}

		if (clazz.isEnum()) {
			return MotpType.ENUM;
		}
		
		if (clazz.isArray()) {
			//数组
			return MotpType.LIST;
		}
		
		//一般对象
		if (clazz.equals(Boolean.class)) {
			return MotpType.INT8;
		} else if (clazz.equals(Long.class)) {
			return MotpType.INT64;
		} else if (clazz.equals(Double.class)) {
			return MotpType.DOUBLE;
		} else if (clazz.equals(Integer.class)) {
			return MotpType.INT32;
		} else if (clazz.equals(Short.class)) {
			return MotpType.INT32;
		} else if (clazz.equals(Byte.class)) {
			return MotpType.INT32;
		} else if (clazz.equals(Float.class)) {
			return MotpType.INT32;
		} else if (clazz.equals(Character.class)) {
			return MotpType.INT32;
		} else if (clazz.equals(Date.class)) {
			return MotpType.INT32;
		} else if (clazz.equals(BigDecimal.class)) {
			return MotpType.DOUBLE;
		} else if (clazz.equals(Set.class)) {
			return MotpType.INT32;
		}
		
		/*
		 * 集合
		 */
		if (List.class.isAssignableFrom(clazz)) {
			return MotpType.LIST;
		} else if (Map.class.isAssignableFrom(clazz)) {
			return MotpType.MAP;
		} else if (Set.class.isAssignableFrom(clazz)) {
			return MotpType.SET;
		}
		
		return MotpType.OBJECT;
	}
}
