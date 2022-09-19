package cn.edu.nwpu.rj416.type.random;

import java.util.Random;

//随机字符工具
public class RandomCharUtil {
	private static final int BASE_RANDOM = 0x9fa5 - 0x4e00 + 1;

	public static char randomChar() {
		Random r = new Random();
		return (char) (0x4e00 + r.nextInt(BASE_RANDOM));
	}
}
