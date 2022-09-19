package motp.serializer.test.beans;


import cn.edu.nwpu.rj416.motp.serializer.motp.MacawMotpSerializer;
import cn.edu.nwpu.rj416.motp.serializer.motp.builder.MotpBuilder;
import cn.edu.nwpu.rj416.motp.serializer.motp.loader.MotpLoader;
import cn.edu.nwpu.rj416.type.FormatUtil;
import cn.edu.nwpu.rj416.type.random.RandomObjectUtil;

public class TestMotpBuilder {
	public static void main(String[] args) {
		TestView view = RandomObjectUtil.randomObject(TestView.class);
		MacawMotpSerializer motpSerializer = MacawMotpSerializer.getInstance();

		MotpBuilder builder = new MotpBuilder();
		byte[] bytes = builder.getBytes(view);

		//FormatUtil.print(builder.getSchema());
		FormatUtil.print(view);
		
		MotpLoader loader = new MotpLoader();
		TestView loadView = (TestView)loader.loadBytes(bytes, TestView.class);
		FormatUtil.print(loadView);

//		MotpExplorer explorer = new MotpExplorer(bytes);
//		FormatUtil.print(explorer);
		
	}
}
