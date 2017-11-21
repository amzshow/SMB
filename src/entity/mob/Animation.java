package entity.mob;

import java.awt.image.BufferedImage;

public class Animation {
	
	private BufferedImage[] frames;
	private int currentFrame;
	private int startFrame;
	private int stopFrame;
	private boolean play;
	
	private long startTime;
	private long delay;
	
	public void setFrames(BufferedImage[] frames) {
		this.frames = frames;
		startFrame = 0;
		stopFrame = frames.length;
		currentFrame = startFrame;
		startTime = System.nanoTime();
		play = true;
		delay = -1;
	}
	
	public void setDelay(long millisecond) { this.delay = millisecond; }
	public void setCurrentFrame(int currentFrame) { this.currentFrame = currentFrame; }
	
	public void update() {
		if(!play)
			return;
		
		if(delay < 0)
			delay = 0;
		
		long elapsed = (System.nanoTime() - startTime) / 1000000;
		
		if(elapsed > delay) {
			currentFrame++;
			startTime = System.nanoTime();
		}
		if(currentFrame == stopFrame || currentFrame == frames.length || currentFrame < startFrame)
			currentFrame = startFrame;
	}
	
	public int getCurrentFrame() { return currentFrame; }
	public BufferedImage getImage() { return frames[currentFrame]; }

	public void setStartStop(int startFrame, int stopFrame) {
		if(stopFrame > frames.length || startFrame >= stopFrame || startFrame >= frames.length)
			return;
		if(startFrame == this.startFrame && stopFrame == this.stopFrame)
			return;
		this.startFrame = startFrame;
		this.currentFrame = startFrame;
		this.stopFrame = stopFrame;
	}
	
	public void startAnimation() { play = true; }
	public void stopAnimation() { play = false; }
	public void toggleAnimation() { play = !play; }
	public int getStart() { return startFrame; }
	public int getStop() { return startFrame; }

}
