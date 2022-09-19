package cn.edu.nwpu.rj416.util.time;

//码表工具
public class Stopwatch {
	private long startTime; //记录开始时间
	private long total; //总计时
	private boolean started = false; //开始标志
	
	//开始计时
	public long start() {
		this.startTime = System.nanoTime(); //返回系统计时器的当前值，精确到纳秒
		this.total = 0;
		this.started = true;
		return this.startTime;
	}
	
	//停止计时
	public Stopwatch stop() {
		if (!this.started) {
			return this;
		}
		this.total += (System.nanoTime() - this.startTime); //总计时等于当前系统计时器值减去记录的开始时间
		return this; //返回这个码表对象
	}
	
	//重新开始计时
	public long restart() {
		if (!this.started) { //若从未开始计时，先开始计时（主要是要将总计时设为0）
			return this.start();
		}
		this.startTime = System.nanoTime();
		this.started = true;
		return this.startTime;
	}
	
	//获取纳秒单位的计时结果
	public long getNanosecond() {
		return this.total;
	}
	
	//获取微秒单位的计时结果
	public double getMicrosecond() {
		return this.total / 1000.0;
	}
	
	//获取毫秒单位的计时结果
	public double getMillisecond() {
		return this.total / 1000000.0;
	}
}
