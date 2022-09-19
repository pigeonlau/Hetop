package cn.edu.nwpu.rj416.util.objects.interval;

//在一定范围内的短整型类
public class ShortInterval {
	private Short min;
	private boolean includeMin = false;//大于等于还是大于
	private Short max;
	private boolean includeMax = false;
	
	public ShortInterval(Short min, boolean includeMin, Short max, boolean includeMax) {
		super();
		this.min = min;
		this.includeMin = includeMin;
		this.max = max;
		this.includeMax = includeMax;
	}
	
	public ShortInterval() {
		super();
	}


	public boolean match(Short value) {
		if (value == null) {
			return false;
		}
		if (this.min != null) {
			if (this.isIncludeMin()) {
				if (this.min > value) {
					return false;
				}
			} else {
				if (this.min >= value) {
					return false;
				}
			}
		}

		if (this.max != null) {
			if (this.isIncludeMax()) {
				if (this.max < value) {
					return false;
				}
			} else {
				if (this.max <= value) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	public Short getMin() {
		return min;
	}

	public void setMin(Short min) {
		this.min = min;
	}

	public boolean isIncludeMin() {
		return includeMin;
	}

	public void setIncludeMin(boolean includeMin) {
		this.includeMin = includeMin;
	}

	public Short getMax() {
		return max;
	}

	public void setMax(Short max) {
		this.max = max;
	}

	public boolean isIncludeMax() {
		return includeMax;
	}

	public void setIncludeMax(boolean includeMax) {
		this.includeMax = includeMax;
	}
	
}
