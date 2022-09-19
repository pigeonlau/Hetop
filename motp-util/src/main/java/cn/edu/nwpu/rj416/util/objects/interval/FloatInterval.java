package cn.edu.nwpu.rj416.util.objects.interval;

//在一定间隔范围内的浮点数类
public class FloatInterval {
	private Float min;
	private boolean includeMin = false;
	private Float max;
	private boolean includeMax = false;
	
	public FloatInterval(Float min, boolean includeMin, Float max, boolean includeMax) {
		super();
		this.min = min;
		this.includeMin = includeMin;
		this.max = max;
		this.includeMax = includeMax;
	}
	
	public FloatInterval() {
		super();
	}


	public boolean match(Float value) {
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
	
	public Float getMin() {
		return min;
	}

	public void setMin(Float min) {
		this.min = min;
	}

	public boolean isIncludeMin() {
		return includeMin;
	}

	public void setIncludeMin(boolean includeMin) {
		this.includeMin = includeMin;
	}

	public Float getMax() {
		return max;
	}

	public void setMax(Float max) {
		this.max = max;
	}

	public boolean isIncludeMax() {
		return includeMax;
	}

	public void setIncludeMax(boolean includeMax) {
		this.includeMax = includeMax;
	}
	
}
