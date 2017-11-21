package gameState;

import java.awt.Graphics2D;

import main.Game;

public class MenuState extends GameState{

	private static long lastTime = 0;
	private long elapsed = 0;
	
	public void init() {
		lastTime = System.nanoTime();
	}

	public void update() {
		 long deltaTime = (int) ((System.nanoTime() - lastTime) / 1000000);
		 elapsed += deltaTime;
		 if(elapsed > 2000)
			 Game.gsm.setState(GameStateManager.LEVEL1_1);
	}

	public void draw(Graphics2D g) {
		
	}

}
