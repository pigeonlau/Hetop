package motp.serializer.test.beans;


import cn.edu.nwpu.rj416.util.reflect.ClassUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;


public class TestType {
	public static void main(String[] args) {
		List<Field> fieldList = ClassUtil.getFields(
				TestBean.class, Modifier.FINAL | Modifier.STATIC);
		
		for (Field f : fieldList) {
			printType(f);
		}
	}
	
	public static void printType(Field f) {
		Type type = f.getGenericType();
		
		System.out.print(type);
		
		if (type instanceof ParameterizedType) {
			System.out.print(" ParameterizedType");
		}
		if (type instanceof Class) {
			System.out.print(" Class");
		}
		System.out.println();
	}
}
