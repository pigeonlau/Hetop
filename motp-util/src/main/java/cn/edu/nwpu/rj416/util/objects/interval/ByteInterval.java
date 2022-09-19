package cn.edu.nwpu.rj416.util.objects.interval;

//在一定范围内的字节码型类
public class ByteInterval {
	private Byte min;
	private boolean includeMin = false;
	private Byte max;
	private boolean includeMax = false;
	
	public ByteInterval(Byte min, boolean includeMin, Byte max, boolean includeMax) {
		super();
		this.min = min;
		this.includeMin = includeMin;
		this.max = max;
		this.includeMax = includeMax;
	}
	
	public ByteInterval() {
		super();
	}


	public boolean match(Byte value) {
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
	
	public Byte getMin() {
		return min;
	}

	public void setMin(Byte min) {
		this.min = min;
	}

	public boolean isIncludeMin() {
		return includeMin;
	}

	public void setIncludeMin(boolean includeMin) {
		this.includeMin = includeMin;
	}

	public Byte getMax() {
		return max;
	}

	public void setMax(Byte max) {
		this.max = max;
	}

	public boolean isIncludeMax() {
		return includeMax;
	}

	public void setIncludeMax(boolean includeMax) {
		this.includeMax = includeMax;
	}
	
}
