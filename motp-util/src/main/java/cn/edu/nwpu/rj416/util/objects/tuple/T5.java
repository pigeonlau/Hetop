package cn.edu.nwpu.rj416.util.objects.tuple;

import java.util.Objects;

/**
 * 五元组
 * @author MilesLiu
 *
 * 2019年11月25日 下午1:12:33
 */
public class T5<C1, C2, C3, C4, C5> {

	private C1 v1;
	private C2 v2;
	private C3 v3;
	private C4 v4;
	private C5 v5;
	public T5(C1 v1, C2 v2, C3 v3, C4 v4, C5 v5) {
		super();
		this.v1 = v1;
		this.v2 = v2;
		this.v3 = v3;
		this.v4 = v4;
		this.v5 = v5;
	}

	public T5() {
		this(null, null, null, null, null);
	}

	@Override
	public int hashCode() {
		return Objects.hash(v1, v2, v3, v4, v5);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof T5) {
			T5<?, ?, ?, ?, ?> t5 = (T5<?, ?, ?, ?, ?>)obj;
			return Objects.equals(this.v1, t5.v1) 
					&& Objects.equals(this.v2, t5.v2)
					&& Objects.equals(this.v3, t5.v3)
					&& Objects.equals(this.v4, t5.v4)
					&& Objects.equals(this.v4, t5.v4)
					&& Objects.equals(this.v5, t5.v5);
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

	public C3 getV3() {
		return v3;
	}

	public void setV3(C3 v3) {
		this.v3 = v3;
	}

	public C4 getV4() {
		return v4;
	}

	public void setV4(C4 v4) {
		this.v4 = v4;
	}

	public C5 getV5() {
		return v5;
	}

	public void setV5(C5 v5) {
		this.v5 = v5;
	}
	
}
