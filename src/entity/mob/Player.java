package entity.mob;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import entity.Sprite;
import entity.Timer;
import entity.entityID;
import gameState.GameStateManager;
import input.Keyboard;
import main.Game;

public class Player extends Mob {

	Keyboard input;
	
	// posture
	private boolean crouching;
	
	// stats
	private long lastTime = 0;
	private long elapsed = 0;
	private Timer shootTimer = new Timer();
	private Timer invincibilityTimer = new Timer();
	public static int availableLives;
	public boolean invincible;
	public static int powerLevel;
	
	public Player(Keyboard keyboard, float x, float y){
		super(x, y, entityID.Player);
		this.input = keyboard;
		init();
		if(image == null)
			image = new BufferedImage(32,32,1);
		invincibilityTimer.reset();
	}
	
	private float maxSpeed;
	private float runMaxSpeed;
	private float defaultMaxSpeed;
	private float currentSpeed;
	private float moveSpeed;
	
	BufferedImage[] t = new BufferedImage[56];
	
	@SuppressWarnings("static-access")
	void init() {
		dir = true;
		crouching = false;
		availableLives = 3;
		invincible = false;
		powerLevel = 2;
		defaultMaxSpeed = 5f;
		maxSpeed = defaultMaxSpeed;
		runMaxSpeed = defaultMaxSpeed * 1.6f;
		currentSpeed = 0;
		moveSpeed = 0.4f;
		moving = false;
		falling = true;
		
		animation = new Animation();
		BufferedImage spritesheet = null; 
		try {
			spritesheet = ImageIO.read(Player.class.getResource("/images/spritesheet.png"));
		} catch(Exception e) {
			e.printStackTrace();
		}
		for(int i = 0; i < 21; i++) {
			if(i < 14) {
				t[i] = spritesheet.getSubimage(i * 16, 0, 16, 16);
				t[i] = Sprite.imageToBufferedImage(t[i].getScaledInstance(32, 32, spritesheet.SCALE_DEFAULT));
				Sprite.replaceColor(t[i], new Color(255,0,255), Game.transparent);
			}
			t[i + 14] = spritesheet.getSubimage(i * 16, 16, 16, 32);
			t[i+14] = Sprite.imageToBufferedImage(t[i+14].getScaledInstance(32, 64, spritesheet.SCALE_DEFAULT));
			Sprite.replaceColor(t[i + 14], new Color(255,0,255), Game.transparent);
			
			t[i + 35] = spritesheet.getSubimage(i * 16, 48, 16, 32);
			t[i+35] = Sprite.imageToBufferedImage(t[i+35].getScaledInstance(32, 64, spritesheet.SCALE_DEFAULT));
			Sprite.replaceColor(t[i + 35], new Color(255,0,255), Game.transparent);
		}			
		animation.setFrames(t);
		
	}
	
	public void update() {
		
		if(invincible) {
			invincibilityTimer.updateTime();
			if(invincibilityTimer.getElapsedTime() > 1000)
				invincible = false;
		}
		
		if(powerLevel < 0) {
			y+=2;
			if(y > Game.HEIGHT) {
				powerLevel = 0;
				isAlive = true;
				if(availableLives == 0)
					Game.gsm.setState(GameStateManager.GAMEOVER);
				else
					Game.gsm.reset();
			}
			return;
		}
		
		if(powerLevel == 2)
			shootTimer.updateTime();
		
		moving = false;
		if(input.left) {
			dir = false;
			maxSpeed = -(float)Math.abs(maxSpeed);
			moveSpeed = -(float)Math.abs(moveSpeed);
			moving = true;
			moveInDirection();
		}
		if(input.right) {
			dir = true;
			maxSpeed = (float)Math.abs(maxSpeed);
			moveSpeed = (float)Math.abs(moveSpeed);
			moving = true;
			moveInDirection();
		}
		if(input.up && canJump && !falling && !jumping) {
			dy -= 7;
			powerupAnimationUpdate(5,6);
			animation.stopAnimation();
			jumping = true;
			canJump = false;
		}
		
		if(input.down)
			crouching = true;
		else
			crouching = false;
		
		if(input.shift) {
			if(!falling && !jumping)
				this.maxSpeed = runMaxSpeed;
			
			if(powerLevel > 1 && shootTimer.getElapsedTime() > 400) {
				shoot();
				shootTimer.setElapsedTime(0);
			}
		}
		else {
			this.maxSpeed = defaultMaxSpeed;
		}
		
		if(!input.left && !input.right) {	
			currentSpeed -= moveSpeed * 0.47f;
			if((dir == true && currentSpeed < 0) || (dir == false && currentSpeed > 0))
				currentSpeed = 0;
			if(!jumping) {
				powerupAnimationUpdate(0,1);
			}	
			animation.stopAnimation();
			dx = currentSpeed;
		}
		
		if(crouching) {
			animation.startAnimation();
			if(powerLevel == 0)
				animation.setStartStop(0,1);
			else if(powerLevel == 1)
				animation.setStartStop(14+6,14+7);
			else
				animation.setStartStop(35+6,35+7);
			animation.stopAnimation();
			animation.update();
			image = animation.getImage();
			dx = 0;
		}
		
		animation.update();
		image = animation.getImage();
		
		move();

	}
	
	private void shoot() {
		if(dir)
			Game.handler.addEntity(new Projectile(x + 33, y + 32));
		else
			Game.handler.addEntity(new Projectile(x - 16, y + 32));
	}

	protected void applyGravity() {
		if(falling && input.up)
			dy += gravity * 0.45f;
		else if(falling)
			dy += gravity;
		if(dy > MAX_SPEED)
			dy = MAX_SPEED;
	}
	
	void moveInDirection() {
		if(!falling) {
			powerupAnimationUpdate(1,4);
			animation.setDelay(100);
			if((dir == false && moveSpeed > 0) || (dir == true && moveSpeed < 0)) {
				powerupAnimationUpdate(4,5);
				animation.stopAnimation();
			}
			if(dir == true && moveSpeed < 0) {
				moveSpeed = (float)Math.abs(moveSpeed);
				currentSpeed += moveSpeed;
			}
			if (dir == false && moveSpeed > 0){
				moveSpeed = -(float)Math.abs(moveSpeed);
				currentSpeed += (moveSpeed * 0.01f);
			}
		}
		if(!falling && !input.up) {
			if((float)Math.abs(currentSpeed) > (float)Math.abs(maxSpeed))
				currentSpeed = maxSpeed;
			else
				currentSpeed += moveSpeed;
		} else {
			if((float)Math.abs(currentSpeed) > (float)Math.abs(maxSpeed *0.5))
				currentSpeed = maxSpeed * 0.3f;
			else
				currentSpeed += moveSpeed * 0.3;
		}
			
		dx = currentSpeed; 
	}
	
	public void draw(Graphics2D g) {	
		if(dir)
			g.drawImage(image, (int)x, (int)y, null);
		else
			g.drawImage(image, (int)x + (int)image.getWidth(), (int)y, (int)-image.getWidth(), (int)image.getHeight(), null);
		
		g.setColor(Color.red);
		g.drawRect((int)x, (int)y, (int)this.getBounds().getWidth(), (int)this.getBounds().getHeight());
		g.setColor(Game.background);
	}
	
	public void powerup() {
		powerLevel = powerLevel + 1;
		if(powerLevel > 2)
			powerLevel = 2;
		
		animation.startAnimation();
		if(powerLevel == 1) {
			animation.setStartStop(14+animation.getStart(), 14+animation.getStop());
			this.y = this.y - 32;
		} else {
			animation.setStartStop(21+animation.getStart(), 21+animation.getStop());
			shootTimer.reset();
		}
		image = animation.getImage();
	}

	public void powerupAnimationUpdate(int x, int y) {
		animation.startAnimation();
		if(powerLevel == 0)
			animation.setStartStop(x,y);
		else if(powerLevel == 1)
			animation.setStartStop(14+x,14+y);
		else
			animation.setStartStop(35+x,35+y);
		image = animation.getImage();
	}
	
	public void die() {
		
		if(powerLevel > 0) {
			invincibilityTimer.reset();
			invincible = true;
			this.y += 32;
			powerLevel = 0;
		} else if (!invincible) {
			powerLevel = -1;
			if(isAlive)
				availableLives--;
			isAlive = false;
			stopAllAnimation();
			animation.setStartStop(6, 7);
			animation.update();
			image = animation.getImage();
			dy -= 7;
			if(availableLives == 0)
				this.stopAllAnimation();
		}
	
	}
	
}
