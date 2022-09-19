package cn.edu.nwpu.rj416.util.objects.tuple;

import java.util.Objects;

/**
 * 键值对
 * @author MilesLiu
 *
 * 2019年10月28日 下午6:11:15
 */
public class Tkv<K, V> {
	private K key;
	private V value;
	
	public Tkv(K key, V value) {
		super();
		this.key = key;
		this.value = value;
	}
	
	public Tkv() {
		super();
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.key, this.value);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Tkv) {
			Tkv<?, ?> t2 = (Tkv<?,?>)obj;
			return Objects.equals(this.key, t2.key) 
					&& Objects.equals(this.value, t2.value);
		} else {
			return false;
		}
	}

	public K getKey() {
		return key;
	}

	public void setKey(K key) {
		this.key = key;
	}
	
	public void setValue(V value) {
		this.value = value;
	}

	public V getValue() {
		return value;
	}
	
	
}
