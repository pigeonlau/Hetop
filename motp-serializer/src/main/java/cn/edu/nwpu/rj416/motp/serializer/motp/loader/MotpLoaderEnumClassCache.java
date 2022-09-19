package cn.edu.nwpu.rj416.motp.serializer.motp.loader;

import java.util.HashMap;
import java.util.Map;

public class MotpLoaderEnumClassCache {
	private Map<String, Enum<?>> enumValues = new HashMap<>();
	
	public void buildEnum(Class<Enum<?>> enumClass) {
		this.enumValues.clear();
		Enum<?>[] constrants = enumClass.getEnumConstants();
		for (Enum<?> c : constrants) {
			enumValues.put(c.name(), c);
		}
	}
	
	public Enum<?> getConstantByName(String name) {
		return this.enumValues.get(name);
	}
	
	
}
