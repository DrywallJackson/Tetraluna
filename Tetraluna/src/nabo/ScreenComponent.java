package nabo;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

public abstract class ScreenComponent
{
	int x, y, width, height, id;
	Screen screen;
	public ScreenComponent(int x, int y, int width, int height, int id, Screen screen)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.id = id;
		this.screen = screen;
	}
	public abstract void render(Graphics2D g2d);
	public abstract void update();
	public boolean isSelected()
	{
		return (Display.mouseX > this.x) && (Display.mouseY > this.y) && (Display.mouseX < this.x + this.width) && (Display.mouseY < this.y + this.height);
	}
	
	public void onClick(MouseEvent e)
	{
		if(isSelected())
		{
			screen.onAction(id);
		}
	}
}
