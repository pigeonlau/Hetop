package cn.edu.nwpu.rj416.motp.serializer.motp.loader;


import cn.edu.nwpu.rj416.motp.serializer.motp.schema.MotpSchema;

import java.util.HashMap;
import java.util.Map;

public class MotpLoaderSchema {

	private Map<Integer, MotpSchema> schemas = new HashMap<>();
	
	public void add(int number, MotpSchema schema) {
		this.schemas.put(number, schema);
	}
	
	public MotpSchema get(int number) {
		return this.schemas.get(number);
	}
}
