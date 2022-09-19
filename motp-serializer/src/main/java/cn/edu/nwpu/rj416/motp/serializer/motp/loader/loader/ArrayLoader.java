package cn.edu.nwpu.rj416.motp.serializer.motp.loader.loader;



import cn.edu.nwpu.rj416.motp.serializer.motp.MotpType;
import cn.edu.nwpu.rj416.motp.serializer.motp.loader.MotpLoader;
import cn.edu.nwpu.rj416.util.objects.MByteBuffer;

import java.lang.reflect.Array;

public class ArrayLoader {
	public static Object readArrayData(
			MotpLoader loader, MByteBuffer buffer) {
		
		byte arrayComponentType = buffer.readByte();
		int arrayLen = buffer.readMVLInt().castToInteger();
		switch(arrayComponentType) {
			case MotpType.INT8:
				return ArrayLoader.readArrayOfInt8(buffer, arrayLen);
			case MotpType.INT16:
				return ArrayLoader.readArrayOfInt16(buffer, arrayLen);
			case MotpType.CHAR:
				return ArrayLoader.readArrayOfChar(buffer, arrayLen);
			case MotpType.INT32:
				return ArrayLoader.readArrayOfInt32(buffer, arrayLen);
			case MotpType.FLOAT:
				return ArrayLoader.readArrayOfFloat(buffer, arrayLen);
			case MotpType.INT64:
				return ArrayLoader.readArrayOfInt64(buffer, arrayLen);
			case MotpType.DOUBLE:
				return ArrayLoader.readArrayOfDouble(buffer, arrayLen);
			default:
				MotpDataLoader.readDataError("错误的数组元素类型:%d", arrayComponentType);
		}
		return null;
	}
	
	private static byte[] readArrayOfInt8(
			MByteBuffer buffer,
			int len) {
		byte[] arr = new byte[len];
		for (int i = 0; i < len; i++) {
			arr[i] = buffer.readByte();
		}
		
		return arr;
	}
	
	private static short[] readArrayOfInt16(
			MByteBuffer buffer, 
			int len) {
		short[] arr = new short[len];
		for (int i = 0; i < len; i++) {
			arr[i] = buffer.readShort();
		}
		
		return arr;
	}
	
	private static int[] readArrayOfInt32(
			MByteBuffer buffer, 
			int len) {
		int[] arr = new int[len];
		for (int i = 0; i < len; i++) {
			arr[i] = buffer.readInt();
		}
		
		return arr;
	}
	
	private static long[] readArrayOfInt64(
			MByteBuffer buffer, 
			int len) {
		long[] arr = new long[len];
		for (int i = 0; i < len; i++) {
			arr[i] = buffer.readLong();
		}
		
		return arr;
	}
	
	private static float[] readArrayOfFloat(
			MByteBuffer buffer, 
			int len) {
		float[] arr = new float[len];
		for (int i = 0; i < len; i++) {
			arr[i] = buffer.readFloat();
		}
		
		return arr;
	}
	
	private static double[] readArrayOfDouble(
			MByteBuffer buffer, 
			int len) {
		double[] arr = new double[len];
		for (int i = 0; i < len; i++) {
			arr[i] = buffer.readDouble();
		}
		
		return arr;
	}
	
	private static char[] readArrayOfChar(
			MByteBuffer buffer, 
			int len) {
		char[] arr = new char[len];
		for (int i = 0; i < len; i++) {
			arr[i] = buffer.readChar();
		}
		
		return arr;
	}
	
	public static Object readArrayOfCommonObject(
			MotpLoader loader,
			MByteBuffer buffer, 
			int len, 
			Class<?> componentType) {
		if (componentType == null) {
			return null;
		}
		
		if (componentType.isPrimitive()) {
			return null;
		}
		Object arr = Array.newInstance(componentType, len);
		for (int i = 0; i < len; i++) {
			Object ele = MotpDataLoader.readData(loader, buffer, componentType);
			Array.set(arr, i, ele);
		}
		
		return arr;
	}
	
}
