package nabo;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class TextArea extends ScreenComponent
{
	String text;
	String[] words;
	int size;
	ArrayList<String> lines = new ArrayList<String>();
	float realFontSize;
	Font f;
	Color color;

	public TextArea(int x, int y, int width, int height, int id, String text, Color c, Screen screen)
	{
		super(x, y, width, height, id, screen);
		this.text = text;
		this.color = c;
		words = text.split(" ");
	}

	@Override
	public void render(Graphics2D g2d)
	{
		if(realFontSize != 0)
		{
			f = Helper.tetraluna.deriveFont((float)(realFontSize));
		}
		else
		{
			f = Helper.tetraluna.deriveFont((float)1000);
		}
		FontMetrics fm = g2d.getFontMetrics(f);
		if(lines.size() == 0)
		{
			int numLines = 0;
			do
			{
				lines.clear();
				numLines = 1;
				realFontSize = f.getSize2D();
				int i = 0;
				String currentLineString = "";
				do
				{
					String currentWord = words[i];
					while(fm.stringWidth(currentWord) > this.width)
					{
						f = f.deriveFont((float)f.getSize2D() - 1);
						fm = g2d.getFontMetrics(f);
					}
					realFontSize = f.getSize2D();
					if(fm.stringWidth(currentLineString + currentWord) <= this.width)
					{
						currentLineString += currentWord;
						currentLineString += " ";
						i++;
					}
					else
					{
						lines.add(currentLineString);
						currentLineString = "";
						numLines++;
					}
					if(i == words.length)
					{
						lines.add(currentLineString);
						currentLineString = "";
					}
				}
				while(i < words.length);
				if(numLines * fm.getHeight() > this.height)
				{
					f = f.deriveFont((float)f.getSize2D() - 1);
					fm = g2d.getFontMetrics(f);
				}
			} while(numLines * fm.getHeight() > this.height);
			realFontSize = f.getSize2D();
		}
		g2d.setColor(color);
		g2d.setFont(f);
		for(int i = 0; i < lines.size(); i++)
		{
			g2d.drawString(lines.get(i), x, y+(fm.getHeight()*8/10)*(i+1));
		}
	}

	@Override
	public void update()
	{

	}

	public void setText(String text)
	{
		this.text = text;
		words = text.split(" ");
		realFontSize = 0;
		this.lines.clear();
	}
}
