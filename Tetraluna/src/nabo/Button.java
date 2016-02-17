package nabo;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

//Buttons
public class Button extends ScreenComponent
{
	String text; //what it says
	float size;
	int originalWidth = 0;
	int originalHeight = 0;
	boolean selected = false;
	
	public Button(String text, int x, int y, float size, int id, Screen screen)
	{
		super(x, y, 0, 0, id, screen);
		this.size = size;
		this.text = text;
	}

	@Override
	public void render(Graphics2D g2d)
	{
		if(isSelected()) //is the mouse on it
		{
			g2d.setColor(Color.yellow);//why yellow there
			if(selected == false)
			{
				//screen.game.musics.get("Button").play();
				selected = true;
			}
		}
		else
		{
			g2d.setColor(new Color(0x4AC3BF)); //feelin blue
			selected = false;
		}
		//get and set the font
		Font f = Helper.tetraluna.deriveFont((float)(size));
		FontMetrics fm = g2d.getFontMetrics(f);
		int textWidth = fm.stringWidth(text);//width of the text
		this.width = textWidth;
		this.height = fm.getHeight();
		g2d.setFont(f);
		g2d.drawString(text, x, (float)((y+(this.height * 9/10))));//draw the text
	}

	@Override
	public void update() //update it maybe
	{
		
	}
}
