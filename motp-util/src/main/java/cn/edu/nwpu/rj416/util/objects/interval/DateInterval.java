package cn.edu.nwpu.rj416.util.objects.interval;

import java.util.Date;

//在一定范围内的日期Date型类
public class DateInterval {
	private Date min;
	private boolean includeMin = false;
	private Date max;
	private boolean includeMax = false;
	
	public DateInterval(Date min, boolean includeMin, Date max, boolean includeMax) {
		super();
		this.min = min;
		this.includeMin = includeMin;
		this.max = max;
		this.includeMax = includeMax;
	}
	
	
	public DateInterval() {
		super();
	}

	public boolean match(Date value) {
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

	public Date getMin() {
		return min;
	}
	public void setMin(Date min) {
		this.min = min;
	}
	public boolean isIncludeMin() {
		return includeMin;
	}
	public void setIncludeMin(boolean includeMin) {
		this.includeMin = includeMin;
	}
	public Date getMax() {
		return max;
	}
	public void setMax(Date max) {
		this.max = max;
	}
	public boolean isIncludeMax() {
		return includeMax;
	}
	public void setIncludeMax(boolean includeMax) {
		this.includeMax = includeMax;
	}
	
	
}
