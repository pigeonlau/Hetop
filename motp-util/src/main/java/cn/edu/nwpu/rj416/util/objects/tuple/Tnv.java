package cn.edu.nwpu.rj416.util.objects.tuple;

import java.util.Objects;

/**
 * 有序号的值
 * @author MilesLiu
 *
 * 2019年10月28日 下午7:41:32
 */
public class Tnv<V> {
	private int number;
	private V value;
	
	public Tnv(int number, V value) {
		super();
		this.number = number;
		this.value = value;
	}
	
	public Tnv() {
		super();
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.number, this.value);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Tnv) {
			Tnv<?> t2 = (Tnv<?>)obj;
			return Objects.equals(this.number, t2.number)
					&& Objects.equals(this.value, t2.value);
		} else {
			return false;
		}
	}
	
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public V getValue() {
		return value;
	}
	
	public void setValue(V value) {
		this.value = value;
	}
	
	
}
