package cn.edu.nwpu.rj416.motp.serializer.motp.util;



import cn.edu.nwpu.rj416.motp.serializer.motp.MotpType;
import cn.edu.nwpu.rj416.motp.serializer.motp.tp.MotpTypeProcesser;
import cn.edu.nwpu.rj416.motp.serializer.motp.tp.basic.*;
import cn.edu.nwpu.rj416.motp.serializer.motp.tp.primitive.*;
import cn.edu.nwpu.rj416.motp.serializer.motp.tp.primitivearray.*;
import cn.edu.nwpu.rj416.util.objects.MVLInt;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 类型映射
 * @author MilesLiu
 *
 * 2019-11-29 18:24
 */
public abstract class MotpProcesserMapping {
	private static Map<String, MotpTypeProcesser> javaTypeMap = new HashMap<>();
	private static Map<Byte, MotpTypeProcesser> motpTypeMap = new HashMap<>();
	
	static {
		/*------------------------------------------------------------------------------
		 * MOTP类型的默认处理器
		 *------------------------------------------------------------------------------*/
		registerForMotpType(MotpType.INT8, new MotpInt8Processer());
		registerForMotpType(MotpType.INT16, new MotpInt16Processer());
		registerForMotpType(MotpType.INT32, new MotpInt32Processer());
		registerForMotpType(MotpType.INT64, new MotpInt64Processer());
		registerForMotpType(MotpType.FLOAT, new MotpFloatProcesser());
		registerForMotpType(MotpType.DOUBLE, new MotpDoubleProcesser());
		registerForMotpType(MotpType.CHAR, new MotpCharProcesser());
		
		registerForMotpType(MotpType.INT_VL, new MotpMVLIntProcesser());
		registerForMotpType(MotpType.STRING, new MotpStringProcesser());
		registerForMotpType(MotpType.BIG_DECIMAL, new MotpBigDecimalProcesser());
		registerForMotpType(MotpType.BOOLEAN, new MotpBooleanProcesser());
		
		registerForMotpType(MotpType.BYTE_ARR, new MotpInt8ArrayProcesser());
		registerForMotpType(MotpType.INT16_ARR, new MotpInt16ArrayProcesser());
		registerForMotpType(MotpType.INT32_ARR, new MotpInt32ArrayProcesser());
		registerForMotpType(MotpType.INT64_ARR, new MotpInt64ArrayProcesser());
		registerForMotpType(MotpType.FLOAT_ARR, new MotpFloatArrayProcesser());
		registerForMotpType(MotpType.DOUBLE_ARR, new MotpDoubleArrayProcesser());
		registerForMotpType(MotpType.CHAR_ARR, new MotpCharArrayProcesser());

		/*------------------------------------------------------------------------------
		 * JAVA类型的默认处理器
		 *------------------------------------------------------------------------------*/
		/*
		 * Primitive
		 * 原生类型 
		 */
		registerForJavaType(Boolean.class, new MotpBooleanProcesser());
		registerForJavaType(boolean.class, new MotpBooleanProcesser());
		registerForJavaType(Byte.class, new MotpInt8Processer());
		registerForJavaType(byte.class, new MotpInt8Processer());
		registerForJavaType(Short.class, new MotpInt16Processer());
		registerForJavaType(short.class, new MotpInt16Processer());
		registerForJavaType(Integer.class, new MotpInt32Processer());
		registerForJavaType(int.class, new MotpInt32Processer());
		registerForJavaType(Long.class, new MotpInt64Processer());
		registerForJavaType(long.class, new MotpInt64Processer());
		registerForJavaType(Float.class, new MotpFloatProcesser());
		registerForJavaType(float.class, new MotpFloatProcesser());
		registerForJavaType(Double.class, new MotpDoubleProcesser());
		registerForJavaType(double.class, new MotpDoubleProcesser());
		registerForJavaType(Character.class, new MotpCharProcesser());
		registerForJavaType(char.class, new MotpCharProcesser());

		/*
		 * 常用基本类型
		 */
		registerForJavaType(String.class, new MotpStringProcesser());
		registerForJavaType(MVLInt.class, new MotpMVLIntProcesser());
		registerForJavaType(Date.class, new MotpDateProcesser());
		registerForJavaType(BigDecimal.class, new MotpBigDecimalProcesser());
		registerForJavaType(BigInteger.class, new MotpBigIntegerProcesser());
		
		/*
		 * 基本类型数组
		 */
		
		registerForJavaType(boolean[].class, new MotpBooleanArrayProcesser());
		registerForJavaType(byte[].class, new MotpInt8ArrayProcesser());
		registerForJavaType(short[].class, new MotpInt16ArrayProcesser());
		registerForJavaType(int[].class, new MotpInt32ArrayProcesser());
		registerForJavaType(long[].class, new MotpInt64ArrayProcesser());
		registerForJavaType(float[].class, new MotpFloatArrayProcesser());
		registerForJavaType(double[].class, new MotpDoubleArrayProcesser());
		registerForJavaType(char[].class, new MotpCharArrayProcesser());
		
//		register(Boolean[].class, new MotpBooleanArrayProcesser());
//		register(Byte[].class, new MotpInt8ArrayProcesser());
//		register(Short[].class, new MotpInt16ArrayProcesser());
//		register(Integer[].class, new MotpInt32ArrayProcesser());
//		register(Long[].class, new MotpInt64ArrayProcesser());
//		register(Float[].class, new MotpFloatArrayProcesser());
//		register(Double[].class, new MotpDoubleArrayProcesser());
//		register(Character[].class, new MotpCharArrayProcesser());
		
//		/*
//		 * 枚举
//		 */
//		register(Enum.class, new MotpEnumProcesser());
//		
//		/*
//		 * 集合
//		 */
//		register(List.class, new MotpListProcesser());
//		register(Map.class, new MotpMapProcesser());
//		register(Set.class, new MotpSetProcesser());
		
	}
	
	private static void registerForJavaType(Type javaType, MotpTypeProcesser tp) {
		javaTypeMap.put(javaType.getTypeName(), tp);
	}
	
	private static void registerForMotpType(byte motpType, MotpTypeProcesser tp) {
		motpTypeMap.put(motpType, tp);
	}
	
	public static MotpTypeProcesser getProcesser(Type type) {
		return javaTypeMap.get(type.getTypeName());
	}
	
	public static MotpTypeProcesser getProcesser(Type type, byte motpType) {
		MotpTypeProcesser processer =  javaTypeMap.get(type.getTypeName());
		if (processer == null) {
			return null;
		}
		if (processer.getMosType() != motpType) {
			return null;
		}
		return processer;
	}
	
	public static MotpTypeProcesser getProcesser(byte motpType) {
		return motpTypeMap.get(motpType);
	}
}
