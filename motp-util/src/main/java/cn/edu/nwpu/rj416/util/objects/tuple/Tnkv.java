package cn.edu.nwpu.rj416.util.objects.tuple;

import java.util.Objects;

//键值对（带序号）
public class Tnkv<K, V> {
	private int number;
	private K key;
	private V value;
	
	
	public Tnkv(int number, K key, V value) {
		super();
		this.number = number;
		this.key = key;
		this.value = value;
	}
	
	public Tnkv() {
		super();
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.number, this.key, this.value);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Tnkv) {
			Tnkv<?, ?> t2 = (Tnkv<?,?>)obj;
			return Objects.equals(this.number, t2.number) 
					&& Objects.equals(this.key, t2.key)
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
	public K getKey() {
		return key;
	}
	public void setKey(K key) {
		this.key = key;
	}
	public V getValue() {
		return value;
	}
	public void setValue(V value) {
		this.value = value;
	}
	
	public Tkv<K, V> getKv() {
		Tkv<K, V> rst = new Tkv<K, V>();
		rst.setKey(this.key);
		rst.setValue(this.value);
		return rst;
	}
}
