package entity.block;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import entity.Entity;
import entity.entityID;

public abstract class Block extends Entity {
	
	protected BufferedImage image;
	protected boolean solid;
	protected boolean breakable;
	protected boolean interactable;
	
	
	protected Block(float x, float y, boolean solid, boolean breakable, boolean interactable) {
		super(x,y,entityID.Block);
		this.solid = solid;
		this.breakable = breakable;
		this.interactable = interactable;
	}
	
	public BufferedImage getImage() { return image; }

	public boolean isSolid() { return solid; }
	public boolean isBreakable() { return breakable; }
	public boolean isInteractable() { return interactable; }
	
	public void setSolid(boolean solid) { this.solid = solid; }
	public void setBreakable(boolean breakable) { this.breakable = breakable; }
	public void setInteractable(boolean interactable) { this.interactable = interactable; }

	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, image.getWidth(), image.getHeight());
	}

	public Rectangle getTop() {
		return new Rectangle((int)x, (int)y, image.getWidth(), image.getHeight());
	}

	public Rectangle getBottom() {
		return new Rectangle((int)x, (int)y, image.getWidth(), image.getHeight());
	}

	public Rectangle getLeft() {
		return new Rectangle((int)x, (int)y, image.getWidth(), image.getHeight());
	}

	public Rectangle getRight() {
		return new Rectangle((int)x, (int)y, image.getWidth(), image.getHeight());
	}

}
