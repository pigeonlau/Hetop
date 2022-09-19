package cn.edu.nwpu.rj416.util.astype;

public interface AsType<T> {
	/**
	 * 将当前对象转为基本类型
	 * @return
	 */
	T dumpToAsType();
	
	/**
	 * 通过基本类型向当前对象赋值
	 * @param v
	 */
	void restoreFromAsType(T v);
}
