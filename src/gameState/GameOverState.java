package gameState;

import java.awt.Graphics2D;

import entity.Camera;
import entity.HUD;
import main.Game;

public class GameOverState extends GameState {

	public GameOverState() {
		levelName = "GameOver";
		startX = 96;
		startY = 96;
	}
	
	public void init() {
		
		Game.player.setX(startX);
		Game.player.setY(startY);
		Game.player.setDx(0);
		Game.player.setDy(0);
		
		Game.background = Game.overWorldBG;
		
		m.loadMap(levelName);
		
		m.addItemFromMap();
		Camera.x = 0;
		Camera.move = false;
		HUD.time = 0;
		HUD.stop();
	}

	public void update() {
		Game.handler.update();
	}

	public void draw(Graphics2D g) {
		Game.handler.draw(g);
	}
	
}
