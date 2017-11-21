package gameState;

import java.awt.Graphics2D;

import entity.Camera;
import entity.HUD;
import main.Game;
//import tile.TileMap;
import map.Map;

public class Level1_2State extends GameState {
	
	public Level1_2State() {		
		levelName = "1-2";
		startX = 128 - 32;
		startY = Game.HEIGHT - 256;
	}
	
	public void init() {
		
		Game.player.setX(startX);
		Game.player.setY(startY);
		
		Game.background = Game.overWorldBG;
		
		Map m = new Map();
		
		m.loadMap(levelName);
		
		m.addItemFromMap();
		
		HUD.time = 400;
		HUD.start();
		Camera.x = 0;
	}

	public void update() {
		Game.handler.update();
	}

	public void draw(Graphics2D g) {
		Game.handler.draw(g);
	}
	
}
