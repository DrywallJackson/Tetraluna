package nabo;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class Screen
{
	public List<ScreenComponent> components = new ArrayList<ScreenComponent>();
	Game game;
	Screen nextScreen;
	int exitTimer = 0;
	boolean exiting = false;
	int coverAlpha; //how transparent Cool Fade Rectangle(TM) is
	
	public Screen(Game game)
	{
		this.game = game;
		coverAlpha = 255;
	}
	
	public void render(Graphics2D g2d)
	{
		for(ScreenComponent c : components)
		{
			c.render(g2d);
		}
		if(coverAlpha > 0)
		{
			//fade the cover away
			coverAlpha-=2;
			if(coverAlpha < 0) coverAlpha = 0;
			g2d.setColor(new Color(0, 0, 0, coverAlpha));
			g2d.fillRect(0, 0, game.disp.getWidth(), game.disp.getHeight());
		}
		if(exitTimer > 0 && exiting)
		{
			exitTimer-=2;
			if(exitTimer < 0) exitTimer = 0;
			int alpha = 255 - exitTimer;
			g2d.setColor(new Color(0, 0, 0, alpha));
			g2d.fillRect(0, 0, game.disp.getWidth(), game.disp.getHeight());
		}
		else if(exiting)
		{
			g2d.setColor(new Color(0, 0, 0));
			g2d.fillRect(0, 0, game.disp.getWidth(), game.disp.getHeight());
			this.game.currentScreen = nextScreen;
		}
	}
	public void update()
	{
		for(ScreenComponent c : components)
		{
			c.update();
		}
	}
	
	public void exitTo(Screen next)
	{
		this.nextScreen = next;
		exitTimer = 255;
		exiting = true;
	}

	public void mouseClicked(MouseEvent e)
	{
		
	}

	public void mousePressed(MouseEvent e)
	{
		for(int i = 0; i < components.size(); i++)
		{
			if(!(components.get(i) instanceof TextArea)) components.get(i).onClick(e);
		}
	}

	public void mouseReleased(MouseEvent e)
	{
		
	}

	public void mouseEntered(MouseEvent e)
	{
		Display.mouseOutside = false;
	}

	public void mouseExited(MouseEvent e)
	{
		Display.mouseOutside = true;
	}

	public void keyTyped(KeyEvent e)
	{

	}

	public void keyPressed(KeyEvent e)
	{
		
	}

	public void keyReleased(KeyEvent e)
	{
			
	}
	
	public void onAction(int id)
	{
		
	}
}
