package nabo;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;

public class Area
{
	Image image;
	public int width, height;
	List<Block> blocks = new ArrayList<Block>();
	Music music;
	String name;
	public static HashMap<String, Area> areas = new HashMap<String, Area>();
	
	public Area(String file)
	{
		ArrayList<String> lines = (ArrayList<String>) Helper.readFile(file);
		this.name = file.split("/")[file.split("/").length - 1].split("\\.")[0];
		for(String s : lines)
		{
			String type = s.split(":")[0];
			if(s.split(":").length == 1) return;
			String data = s.split(":")[1];
			switch(type)
			{
			case "image":
				try
				{
					BufferedImage orig = ImageIO.read(this.getClass().getResourceAsStream("textures/areas/" + data + ".png"));
					this.image = orig.getScaledInstance(orig.getWidth(), orig.getHeight(), 0);
					this.width = image.getWidth(null);
					this.height = image.getHeight(null);
				} catch (IOException e)
				{
					e.printStackTrace();
				}
				break;
			case "solid":
				String[] blocks = data.split(";");
				for(String b : blocks)
				{
					int minX = Integer.parseInt(b.split(",")[0]) * 2;
					int minY = Integer.parseInt(b.split(",")[1].split("-")[0]) * 2;
					int maxX = Integer.parseInt(b.split("-")[1].split(",")[0]) * 2;
					int maxY = Integer.parseInt(b.split("-")[1].split(",")[1]) * 2;
					this.blocks.add(new SolidBlock(minX, minY, maxX, maxY));
				}
				break;
			case "music":
				this.music = Music.sounds.get(data);
				break;
			}
		}
	}

	public static void loadAreas()
	{
		for(String s : Helper.listAllFiles("resources/areas"))
		{
			Area a = new Area(s);
			Area.areas.put(a.name, a);
		}
	}
}
