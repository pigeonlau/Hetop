package cn.edu.nwpu.rj416.util.objects;

//有序对象（实现Comparable自然排序接口）
public class MOrderingObject<T> implements Comparable<MOrderingObject<T>> {
	private int order; //维持一个私有属性表示顺序
	private T object;  

	public MOrderingObject(int order, T object) { //指定顺序和类对象构造成有序类对象
		super();
		this.order = order;
		this.object = object;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	public T getObject() {
		return object;
	}
	public void setObject(T object) {
		this.object = object;
	}

	@Override
	//比较两个有序对象
	public int compareTo(MOrderingObject<T> o) {
		return this.order - o.getOrder();
	}//返回顺序之差
}
