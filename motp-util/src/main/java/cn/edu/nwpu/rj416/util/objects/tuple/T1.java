package cn.edu.nwpu.rj416.util.objects.tuple;

import java.util.Objects;

/**
 * T1, 一元组（不推荐使用）
 * @author MilesLiu
 *
 * 2019年11月25日 下午1:11:33
 */
public class T1<C1> {
	private C1 v1;

	public T1(C1 v1) {
		super();
		this.v1 = v1;
	}
	
	public T1() {
		this(null);
	}

	@Override
	public int hashCode() {
		return Objects.hash(v1);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof T1) {
			return Objects.equals(this.v1, ((T1<?>)obj).v1);
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
}
