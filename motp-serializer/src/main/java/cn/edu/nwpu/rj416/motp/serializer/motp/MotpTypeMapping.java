package cn.edu.nwpu.rj416.motp.serializer.motp;



import cn.edu.nwpu.rj416.motp.serializer.motp.tp.MotpTypeProcesser;
import cn.edu.nwpu.rj416.motp.serializer.motp.tp.primitive.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MotpTypeMapping {
	
	private static Map<Class<?>, MotpTypeProcesser> javaTypeMap = new HashMap<>();
	private static Map<Byte, MotpTypeProcesser> mosTypeMap = new HashMap<>();
	
	private static final MotpTypeProcesser charProcesser = new MotpCharProcesser();
	private static final MotpTypeProcesser floatProcesser = new MotpFloatProcesser();
	private static final MotpTypeProcesser doubleProcesser = new MotpDoubleProcesser();
	private static final MotpTypeProcesser int8Processer = new MotpInt8Processer();
	private static final MotpTypeProcesser int16Processer = new MotpInt16Processer();
	private static final MotpTypeProcesser int32Processer = new MotpInt32Processer();
	private static final MotpTypeProcesser int64Processer = new MotpInt64Processer();
	
	static {
		javaTypeMap.put(char.class, charProcesser);
		javaTypeMap.put(Character.class, charProcesser);
		javaTypeMap.put(float.class, floatProcesser);
		javaTypeMap.put(Float.class, floatProcesser);
		javaTypeMap.put(double.class, doubleProcesser);
		javaTypeMap.put(Double.class, doubleProcesser);
		javaTypeMap.put(byte.class, int8Processer);
		javaTypeMap.put(Byte.class, int8Processer);
		javaTypeMap.put(short.class, int16Processer);
		javaTypeMap.put(Short.class, int16Processer);
		javaTypeMap.put(int.class, int32Processer);
		javaTypeMap.put(Integer.class, int32Processer);
		javaTypeMap.put(long.class, int64Processer);
		javaTypeMap.put(Long.class, int64Processer);
		
		javaTypeMap.put(Date.class, int64Processer);
		
		mosTypeMap.put(MotpType.CHAR, charProcesser);
		mosTypeMap.put(MotpType.FLOAT, floatProcesser);
		mosTypeMap.put(MotpType.DOUBLE, doubleProcesser);
		mosTypeMap.put(MotpType.INT8, int8Processer);
		mosTypeMap.put(MotpType.INT16, int16Processer);
		mosTypeMap.put(MotpType.INT32, int32Processer);
		mosTypeMap.put(MotpType.INT64, int64Processer);
	}
	
	public static MotpTypeProcesser getProcesserByClass(Class<?> clazz) {
		return javaTypeMap.get(clazz);
	}
	
	public static MotpTypeProcesser getProcesserByMosType(byte mosType) {
		return mosTypeMap.get(mosType);
	}
}
