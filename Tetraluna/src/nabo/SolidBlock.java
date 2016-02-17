package nabo;

public class SolidBlock extends Block
{

	public SolidBlock(int minX, int minY, int maxX, int maxY)
	{
		super(minX, minY, maxX, maxY);
	}

	@Override
	public void interact(Entity e)
	{
		e.posX = e.lastX;
		e.posY = e.lastY;
	}
}
