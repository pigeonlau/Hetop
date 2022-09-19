package cn.edu.nwpu.rj416.util.objects.tuple;

import java.util.Objects;

/**
 * 二元组
 * @author MilesLiu
 *
 * 2019年11月25日 下午1:11:56
 */
public class T2<C1, C2> {
	private C1 v1;
	private C2 v2;
	
	public T2(C1 v1, C2 v2) {
		super();
		this.v1 = v1;
		this.v2 = v2;
	}

	public T2() {
		this(null, null);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(v1, v2);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof T2) {
			T2<?, ?> t2 = (T2<?,?>)obj;
			return Objects.equals(this.v1, t2.v1) 
					&& Objects.equals(this.v2, t2.v2);
		} else {
			return false;
		}
	}
	
	public C1 getV1() {
		return v1;
	}
	public void setV1(C1 v1) {
		this.v1 = v1;
	}
	public C2 getV2() {
		return v2;
	}
	public void setV2(C2 v2) {
		this.v2 = v2;
	}
}
