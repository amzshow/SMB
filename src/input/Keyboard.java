package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {
	
	private boolean[] keys = new boolean [120];
	public boolean up, down, left, right, shift, shift_once;
	public boolean pause;
	public boolean buttonPressed = false;
	public int pressedCount = 0;
	long lastTime = 0;
	public int timeElapsed = 0;
	
	public void update() {
		up = keys[KeyEvent.VK_UP] || keys[KeyEvent.VK_W];
		down = keys[KeyEvent.VK_DOWN] || keys[KeyEvent.VK_S];
		left = keys[KeyEvent.VK_LEFT] || keys[KeyEvent.VK_A];
		right = keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_D];
		pause = keys[KeyEvent.VK_ESCAPE];
		shift = keys[KeyEvent.VK_SHIFT];
		if(shift && shift_once)
			shift_once = false;
		if(shift_once)
			shift_once = false;
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(lastTime == 0)
			lastTime = System.nanoTime();
		buttonPressed = true;
		long time = System.nanoTime();
	    int deltaTime = (int) ((time - lastTime) / 1000000);
	    timeElapsed += deltaTime;
	    lastTime = time;
		if (e.getKeyCode() > 0 && e.getKeyCode() < keys.length) {
			keys[e.getKeyCode()] = true;
			pressedCount++;
		}
		

	}

	@Override
	public void keyReleased(KeyEvent e) {
		buttonPressed = false;
		if (e.getKeyCode() > 0 && e.getKeyCode() < keys.length) {
			keys[e.getKeyCode()] = false;
			pressedCount = 0;
			timeElapsed = 0;
		}
	}
}