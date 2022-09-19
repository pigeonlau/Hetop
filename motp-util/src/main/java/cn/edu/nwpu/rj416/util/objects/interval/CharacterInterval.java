package cn.edu.nwpu.rj416.util.objects.interval;

//在一定范围内的字符型类
public class CharacterInterval {
	private Character min;
	private boolean includeMin = false;
	private Character max;
	private boolean includeMax = false;
	
	public CharacterInterval(Character min, boolean includeMin, Character max, boolean includeMax) {
		super();
		this.min = min;
		this.includeMin = includeMin;
		this.max = max;
		this.includeMax = includeMax;
	}
	
	public CharacterInterval() {
		super();
	}


	public boolean match(Character value) {
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
	
	public Character getMin() {
		return min;
	}

	public void setMin(Character min) {
		this.min = min;
	}

	public boolean isIncludeMin() {
		return includeMin;
	}

	public void setIncludeMin(boolean includeMin) {
		this.includeMin = includeMin;
	}

	public Character getMax() {
		return max;
	}

	public void setMax(Character max) {
		this.max = max;
	}

	public boolean isIncludeMax() {
		return includeMax;
	}

	public void setIncludeMax(boolean includeMax) {
		this.includeMax = includeMax;
	}
	
}
