package nabo;

import java.awt.image.BufferedImage;
import java.util.HashMap;

public class ImageLoader
{
	public static HashMap<String, BufferedImage[][]> entityImages = new HashMap<String, BufferedImage[][]>();
	
	public static void loadImages()
	{
		for(String s : Helper.listAllFiles("textures/character/"))
		{
			String fileName = s.split("/")[s.split("/").length - 1];
			String key = fileName.split("-")[0];
			int x = Integer.parseInt(fileName.split("-")[1]);
			int y = Integer.parseInt(fileName.split("-")[2].split("\\.")[0]);
			BufferedImage[][] image = new BufferedImage[x][y];
			for(int i = 0; i < x; i++)
			{
				for(int j = 0; j < y; j++)
				{
					image[i][j] = Helper.getImagePart("textures/character/" + fileName, i, j);
				}
			}
			entityImages.put(key, image);
		}
	}
}
