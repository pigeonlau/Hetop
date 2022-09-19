package cn.edu.nwpu.rj416.util.objects.tuple;

import java.util.Objects;

/**
 * 三元组
 * @author MilesLiu
 *
 * 2019年11月25日 下午1:12:15
 */
public class T3<C1, C2, C3> {
	private C1 v1;
	private C2 v2;
	private C3 v3;
	
	
	public T3(C1 v1, C2 v2, C3 v3) {
		super();
		this.v1 = v1;
		this.v2 = v2;
		this.v3 = v3;
	}

	@Override
	public int hashCode() {
		return Objects.hash(v1, v2, v3);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof T3) {
			T3<?, ?, ?> t3 = (T3<?,?,?>)obj;
			return Objects.equals(this.v1, t3.v1) 
					&& Objects.equals(this.v2, t3.v2)
					&& Objects.equals(this.v3, t3.v3);
		} else {
			return false;
		}
	}
	
	public T3() {
		this(null, null, null);
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
	public C3 getV3() {
		return v3;
	}
	public void setV3(C3 v3) {
		this.v3 = v3;
	}
}
