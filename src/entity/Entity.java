package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public abstract class Entity {

	protected float x;
	protected float y;
	protected float dx;
	protected float dy;
	public boolean isAlive = true;
	
	protected entityID id;
	protected BufferedImage image;
	
	protected boolean moving;
	protected boolean falling;
	
	protected Entity(float x, float y, entityID id) {
		this.x = x;
		this.y = y;
		this.id = id;
	}
	
	public abstract void update();
	
	public abstract void draw(Graphics2D g);
	
	public entityID getID() {
		return id;
	}
	
	public Rectangle getBounds() { if(image == null) return new Rectangle((int)(x), (int)(y), 32, 32); return new Rectangle((int)(x), (int)(y), image.getWidth(), image.getHeight()); }
	public Rectangle getTop() { if(image == null) return new Rectangle((int)(x), (int)(y), 32, 32); return new Rectangle((int)(x) + 3, (int)(y - 3), image.getWidth() - 6, 3); }
	public Rectangle getLeft() { if(image == null) return new Rectangle((int)(x), (int)(y), 32, 32); return new Rectangle((int)x - 3, (int)(y + 3), 3, image.getHeight() - 6); }
	public Rectangle getRight() { if(image == null) return new Rectangle((int)(x), (int)(y), 32, 32); return new Rectangle((int)(x) + image.getWidth(), (int)(y + 3), 3, image.getHeight() - 6); }
	public Rectangle getBottom() { if(image == null) return new Rectangle((int)(x), (int)(y), 32, 32); return new Rectangle((int)(x) + 3, (int)y + image.getHeight(), image.getWidth() - 6, 3); }

	
	public float getX() { return x; }
	public float getY() { return y; }
	
	public void setX(float x) { this.x = x; }
	public void setY(float y) { this.y = y; }
	public void setDx(float dx) { this.dx = dx; }
	public void setDy(float dy) { this.dy = dy; }
	
	public abstract void die();
}
