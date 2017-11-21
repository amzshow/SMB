package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.io.InputStream;

import entity.Camera;
import entity.Handler;
import entity.entityID;
import entity.HUD;
import entity.Sprite;
import entity.mob.Player;
import gameState.GameState;
import gameState.GameState.Type;
import gameState.GameStateManager;
import input.Keyboard;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 6224761727844878306L;
	public static final int WIDTH = 32 * 16, HEIGHT = 32 * 16, SCALER = 1;
	
	private String title = "SuperMarioBros";
	private Window window;
	private Thread thread;
	private boolean running = false;
	
	private Keyboard keyboard = new Keyboard();
	public static GameStateManager gsm;
	public static Handler handler;
	public static Handler blockHandler;
	public static Camera camera;
	private static Font font;
	public static Color overWorldBG = new Color(93, 148, 251, 255);
	public static Color underWorldBG = new Color(0, 0, 0, 255);
	public static Color transparent = new Color(0, 0, 0, 0);
	public static Color background = overWorldBG;
	public static Player player;
	public static Sprite sprite;
	public static HUD hud = new HUD(0,0);
	
	public Game() {
		window = new Window(WIDTH, HEIGHT, SCALER, title, this);
	}
	
	private void init() {
		this.addKeyListener(keyboard);
		handler = new Handler();
		sprite = new Sprite();
		camera = new Camera(0,0);
		handler.addEntity(player = new  Player(keyboard, 128 - 32, HEIGHT - 256));
		handler.addEntity(hud);
		gsm = new GameStateManager();
		gsm.setState(0);
		gsm.getCurrentState().init();
		InputStream is = Game.class.getResourceAsStream("/font.ttf");
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, is);
			font = new Font(font.getName(), Font.PLAIN, 16);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized void start() {
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	
	public synchronized void stop() {
		try{
			thread.join();
			running = false;
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void run() {
		this.requestFocus();
		init();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer =  System.currentTimeMillis();
		int frames = 0;
		while(running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1) {
				update();
				delta--;
			}
			if(running)
				draw();
			frames++;
			
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				window.setTitle(title + "   |   FPS: " + frames);
				frames = 0;
			}
		}
		stop();
	}
	
	private void update() {
		keyboard.update();
		gsm.update();
		for(int i  = 0; i < handler.getList().size(); i++) {
			if(handler.getList().get(i).getID() == entityID.Player)
			camera.update(handler.getList().get(i));
		}
	}
	
	private void draw() {
		BufferStrategy bs =  this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics gg = bs.getDrawGraphics();
		
		Graphics2D g = (Graphics2D) gg; 
		
		if(font.getFontName() != "Press Start")
			g.setFont(font);
		
		if(GameState.getWorldType() == Type.OverWorld)
			g.setColor(overWorldBG);
		else
			g.setColor(underWorldBG);
		
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		g.translate(camera.getX(), camera.getY());
		gsm.draw(g);
		g.translate(-camera.getX(), -camera.getY());
		
		g.dispose();
		bs.show();
			
	}
	
	public static void main(String[] args) {
		new Game();
	}

}

