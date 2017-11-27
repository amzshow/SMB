package entity;

public class Timer {

	private long elapsed;
	private long lastTime;
	private int deltaTime;
	
	public Timer() {
		lastTime = System.nanoTime();
		elapsed = 0;
	}
	
	public void reset() {
		lastTime = System.nanoTime();
		elapsed = 0;
	}
	
	public void updateTime() {
		deltaTime = (int)((System.nanoTime() - lastTime) / 1000000);
		elapsed = elapsed + deltaTime;
		lastTime = System.nanoTime();
	}
	
	public long getElapsedTime() {
		return elapsed;
	}
	
	public void setElapsedTime(long elapsed) {
		this.elapsed = elapsed;
	}
	
	public int getDeltaTime() {
		return deltaTime;
	}
	
	public void setDeltaTime(int deltaTime) {
		this.deltaTime = deltaTime;
	}
	
	
}
