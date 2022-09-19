package cn.edu.nwpu.rj416.type.random;

import java.util.Random;

//随机枚举工具
public class RandomEnumUtil {

	//获取给定枚举中的一个随机元素
	public static <T extends Enum<?>> T randomEnum(Class<T> enumClass) {
		Random r = new Random();
		T[] values = enumClass.getEnumConstants();//返回这个枚举类的元素，如果这个类对象不表示枚举类型，则返回null
		if (values.length == 0) {
			return null;
		}
		int index = r.nextInt(values.length); //获取枚举中的一个随机索引
		return values[index];
	}
}
