package cn.edu.nwpu.rj416.util.astype;

public interface AsVoid extends AsType<Void> {
	/**
	 * 将当前对象转为基本类型
	 * @return
	 */
	default Void dumpToAsType() {
		return null;
	}
	
	/**
	 * 通过基本类型向当前对象赋值
	 * @param v
	 */
	default void restoreFromAsType(Void v) {
		
	}
}
