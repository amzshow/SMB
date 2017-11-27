package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;

import javax.imageio.ImageIO;

import entity.mob.Animation;
import gameState.GameStateManager;
import main.Game;

public class HUD extends Entity{
 
	Animation animation;
	
	public HUD(float x, float y) {
		super(x, y, entityID.Other);
		try {
			BufferedImage[] sprites = 	{ 
					ImageIO.read(new FileInputStream("resources/images/coin_0.png")),
					ImageIO.read(new FileInputStream("resources/images/coin_1.png")),
					ImageIO.read(new FileInputStream("resources/images/coin_2.png")),
				};

			for(int i = 0; i < 3; i++) {
				sprites[i] = Sprite.imageToBufferedImage(sprites[i].getScaledInstance(sprites[i].getWidth(), sprites[i].getHeight(), Image.SCALE_DEFAULT));
				Sprite.replaceColor(sprites[i], new Color(255,0,255), Game.transparent);
			}
			animation = new Animation();
			animation.setFrames(sprites);
			animation.setDelay(200);
			image = sprites[0];
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	static long score = 0;
	static long coins = 0;
	static long lives = 0;
	static boolean count = false;
	public static int time = -1;
	
	private static long lastTime = 0;
	
	public static void start() {
		lastTime = System.nanoTime();
		count = true;
	}
	
	public static void stop() {
		count = false;
	}

	public void update() {
		this.x = -Camera.x;
		if(lastTime == -1)
			return;
		if(count) {
		    long deltaTime = (int) ((System.nanoTime() - lastTime) / 1000000);
		    if(deltaTime > 500) {
				lastTime = System.nanoTime();
				time--;
			}		
		}
	    animation.update();
	    image = animation.getImage();
	    if(time == 0 && count) {
	    	Game.player.die();
	    	count = false;
	    }
	}
	
	public void addScore(int score) {
		HUD.score += score;
	}
	
	public void addCoin() {
		if(++coins == 100)
			coins = 0;
	}

	public void draw(Graphics2D g) {
		g.setColor(Color.white);
		//---------------------SCORE--------------------------------------
		g.drawString("MARIO", -Camera.x + 20, 32);
		g.drawString(String.format("%06d", score), -Camera.x + 20, 50);
		//---------------------COINS--------------------------------------		
		g.drawImage(image, (int) (-Camera.x + 180), 35,null);
		g.drawString("x" + String.format("%02d", coins), -Camera.x + 200, 50);
		//---------------------WORLD--------------------------------------		
		g.drawString("WORLD", -Camera.x + 300, 32);
		g.drawString(String.valueOf(Game.gsm.getCurrentState().levelName), -Camera.x + 300, 50);
		//---------------------TIME--------------------------------------		
		g.drawString("TIME", -Camera.x + 400, 32);
		if(count)
			g.drawString(String.format("%03d", time), -Camera.x + 418, 50);
		//-----------------------------------------------------------		
		if(GameStateManager.currentState == GameStateManager.GAMEOVER)
			g.drawString("GameOver", (Game.WIDTH / 2) - 64, (Game.HEIGHT /2) - 64);
		g.setColor(Game.background);
	}

	@Override
	public void die() {
		// TODO Auto-generated method stub
		
	}
	
}
