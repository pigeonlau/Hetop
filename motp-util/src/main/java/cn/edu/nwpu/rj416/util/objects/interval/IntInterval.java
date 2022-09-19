package cn.edu.nwpu.rj416.util.objects.interval;

//在一定范围内的整型类
public class IntInterval {
	private Integer min;
	private boolean includeMin = false;
	private Integer max;
	private boolean includeMax = false;
	
	public IntInterval(Integer min, boolean includeMin, Integer max, boolean includeMax) {
		super();
		this.min = min;
		this.includeMin = includeMin;
		this.max = max;
		this.includeMax = includeMax;
	}
	
	public IntInterval() {
		super();
	}


	public boolean match(Integer value) {
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
	
	public Integer getMin() {
		return min;
	}

	public void setMin(Integer min) {
		this.min = min;
	}

	public boolean isIncludeMin() {
		return includeMin;
	}

	public void setIncludeMin(boolean includeMin) {
		this.includeMin = includeMin;
	}

	public Integer getMax() {
		return max;
	}

	public void setMax(Integer max) {
		this.max = max;
	}

	public boolean isIncludeMax() {
		return includeMax;
	}

	public void setIncludeMax(boolean includeMax) {
		this.includeMax = includeMax;
	}
	
}
