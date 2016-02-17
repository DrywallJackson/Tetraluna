package nabo;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.imageio.ImageIO;

//help with stuff
public class Helper
{
	public static Font tetraluna = getTetraluna();
	//load the font
	public static Font getTetraluna()
	{
		try
		{
			InputStream i = Helper.class.getResourceAsStream("resources/Tetraluna.ttf");
			Font f = Font.createFont(Font.TRUETYPE_FONT, i);
			return f.deriveFont(12);
		} 
		catch (Exception e)
		{
			//it broke
			e.printStackTrace();
		}
		return null;
	}

	public static int moonPhase(Calendar now)
	{
		int lp = 2551443; 
		Calendar new_moon = Calendar.getInstance();
		new_moon.clear();
		new_moon.set(1970, 0, 7, 20, 35, 0);
		float phase = ((now.getTimeInMillis() - new_moon.getTimeInMillis())/1000) % lp;
		return (int) (Math.floor(phase /(24*3600)) + 1);
	}

	public static void renderText(String s, int x, int y, int width, int height, Graphics2D g2d)
	{
		int numLines = 0;
		String[] words = s.split(" ");
		List<String> lines = new ArrayList<String>();
		Font f = Helper.getTetraluna().deriveFont((float)1000);
		FontMetrics fm = g2d.getFontMetrics(f);
		do
		{
			lines.clear();
			numLines = 1;
			int i = 0;
			String currentLineString = "";
			do
			{
				String currentWord = words[i];
				while(fm.stringWidth(currentWord) > width)
				{
					f = f.deriveFont((float)f.getSize2D() - 1);
					fm = g2d.getFontMetrics(f);
				}
				if(fm.stringWidth(currentLineString + currentWord) <= width)
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
			if(numLines * fm.getHeight() > height)
			{
				f = f.deriveFont((float)f.getSize2D() - 1);
				fm = g2d.getFontMetrics(f);
			}
		} while(numLines * fm.getHeight() > height);
		g2d.setColor(Game.gameColor);
		g2d.setFont(f);
		for(int i = 0; i < lines.size(); i++)
		{
			g2d.drawString(lines.get(i), x, y+(fm.getHeight()*8/10)*(i+1));
		}
	}

	//draw text
	public static void drawText(String text, Graphics2D g2d, float x, float y, float size)
	{
		Font f = getTetraluna().deriveFont(size);
		g2d.setFont(f);
		g2d.drawString(text, x, y);
	}

	public static BufferedImage getImagePart(String path, int sectX, int sectY)
	{
		int numX = Integer.parseInt(path.split("/")[path.split("/").length-1].split("\\.")[0].split("-")[1]);
		int numY = Integer.parseInt(path.split("/")[path.split("/").length-1].split("\\.")[0].split("-")[2]);

		try
		{
			BufferedImage i = ImageIO.read(Helper.class.getResourceAsStream(path));
			int imgX = i.getWidth() / numX * sectX;
			int imgY = i.getHeight() / numY * sectY;
			i = i.getSubimage(imgX, imgY, i.getWidth() / numX, i.getHeight() / numY);
			return i;
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static ArrayList<String> readFile(String file)
	{
		try
		{
			ArrayList<String> lines = new ArrayList<String>();
			InputStream in = Helper.class.getResourceAsStream(file.split("nabo/")[1]);
			BufferedReader input = new BufferedReader(new InputStreamReader(in));
			String s;
			do
			{
				s = input.readLine();
				if(s != null)
				{
					lines.add(s);
				}
			} while(s != null);
			return lines;
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static ArrayList<String> listAllFiles(String path)
	{
		ArrayList<String> list = new ArrayList<String>();
		CodeSource src = Helper.class.getProtectionDomain().getCodeSource();
		if(src != null)
		{
			if(src.getLocation().getPath().contains(".jar"))
			{
				try
				{
					URL jar = src.getLocation();
					ZipInputStream zip = new ZipInputStream(jar.openStream());
					while(true)
					{
						ZipEntry e = zip.getNextEntry();
						if(e == null)
						{
							break;
						}
						String name = e.getName();
						if(name.startsWith("nabo/" + path) && !name.equals("nabo/" + path + "/") && !name.equals("nabo/" + path))
						{
							System.out.println(path + " " + name);
							list.add(name);
						}
					}
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
			else
			{
				File folder = new File(Helper.class.getResource(path).getFile());
				File[] files = folder.listFiles();
				for(File f : files)
				{
					list.add(f.getAbsolutePath());
				}
			}
		}
		return list;
	}
}
