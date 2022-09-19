package motp.serializer.test.util;



import cn.edu.nwpu.rj416.type.util.TypeUtil;
import cn.edu.nwpu.rj416.util.astype.AsType;

import java.util.ArrayList;
import java.util.List;


public class TestMotpSchemaUtil {
	public static void main(String[] args) {
//		MotpSchemaBuilder builder = new MotpSchemaBuilder();
//		
//		MotpSchemaUtil.appendClass(builder, ViewClass.class);
		AsIntObject intObj = new AsIntObject();
		TypeUtil.parseTypeAs(AsIntObject.class, AsType.class);
		TypeUtil.parseTypeAs(new ArrayList<Integer>(), List.class);
	}
}
