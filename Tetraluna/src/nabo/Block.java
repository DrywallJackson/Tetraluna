package nabo;

import java.awt.Rectangle;


public abstract class Block
{
	int minX, minY, maxX, maxY;
	
	public Block(int minX, int minY, int maxX, int maxY)
	{
		this.minX = minX;
		this.minY = minY;
		this.maxX = maxX;
		this.maxY = maxY;
	}
	
	public boolean contains(int x, int y)
	{
		Rectangle r = new Rectangle(minX, minY, maxX, maxY);
		return r.contains(x, y);
	}
	
	public Rectangle getRect()
	{
		return new Rectangle(minX, minY, maxX, maxY);
	}
	
	public abstract void interact(Entity e);
}
