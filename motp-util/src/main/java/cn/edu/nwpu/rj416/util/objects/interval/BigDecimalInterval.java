package cn.edu.nwpu.rj416.util.objects.interval;

import java.math.BigDecimal;

//在一定范围内的高精度类
public class BigDecimalInterval {
	private BigDecimal min; //最小值min属性
	private boolean includeMin = false;//大于等于还是大于
	private BigDecimal max; //最大值max属性）
	private boolean includeMax = false;//小于等于还是小于
	
	//有参构造函数（可自主设置最大最小值，以及是否包括最大最小值）
	public BigDecimalInterval(BigDecimal min, boolean includeMin, BigDecimal max, boolean includeMax) {
		super();
		this.min = min;
		this.includeMin = includeMin;
		this.max = max;
		this.includeMax = includeMax;
	}
	
	public BigDecimalInterval() {
		super();
	}
	
	//判断一个高精度小数是否属于一定范围（min~max）
	public boolean match(BigDecimal value) {
		if (this.min != null) {
			if (this.isIncludeMin()) {
				if (this.min.compareTo(value) > 0) {
					return false;
				}
			} else {
				if (this.min.compareTo(value) >= 0) {
					return false;
				}
			}
		}

		if (this.max != null) {
			if (this.isIncludeMax()) {
				if (this.max.compareTo(value) < 0) {
					return false;
				}
			} else {
				if (this.max.compareTo(value) <= 0) {
					return false;
				}
			}
		}
		
		return true;
	}

	public BigDecimal getMin() {
		return min;
	}

	public void setMin(BigDecimal min) {
		this.min = min;
	}

	public boolean isIncludeMin() {
		return includeMin;
	}

	public void setIncludeMin(boolean includeMin) {
		this.includeMin = includeMin;
	}

	public BigDecimal getMax() {
		return max;
	}

	public void setMax(BigDecimal max) {
		this.max = max;
	}

	public boolean isIncludeMax() {
		return includeMax;
	}

	public void setIncludeMax(boolean includeMax) {
		this.includeMax = includeMax;
	}
	
}
