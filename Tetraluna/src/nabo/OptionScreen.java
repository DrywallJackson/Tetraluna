package nabo;

import java.awt.Graphics2D;

public class OptionScreen extends Screen
{
	int coverAlpha = 255; //how transparent Cool Fade Rectangle(TM) is
	int exitTimer = 0;
	boolean exiting = false;

	public OptionScreen(Game game)
	{
		super(game);
		this.components.add(new Button("Back", 10, 10, 50, 0, this));
	}
	
	public void onAction(int id)
	{
		switch(id)
		{
		case 0:
			exitTo(new MainMenuScreen(game));
			break;
		default:
			break;
		}
	}
	
	public void render(Graphics2D g2d)
	{
		super.render(g2d);
	}
}
