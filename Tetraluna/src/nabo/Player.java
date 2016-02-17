package nabo;


public class Player extends Entity
{

	public Player(int posX, int posY, String image)
	{
		super("Player", posX, posY, image);
		Game.instance.cameraX = this.posX;
		Game.instance.cameraY = this.posY;
	}

	@Override
	public void updateSubclass(Area a)
	{
		if(Game.instance.down)
		{
			if(!Game.instance.up) velY = 1; else velY = 0;
			facing = 0;
			walking = true;
		}
		if(Game.instance.up)
		{
			if(!Game.instance.down) velY = -1; else velY = 0;
			facing = 3;
			walking = true;
		}
		if(Game.instance.left)
		{
			if(!Game.instance.right) velX = -1; else velX = 0;
			facing = 1;
			walking = true;
		}
		if(Game.instance.right)
		{
			if(!Game.instance.left) velX = 1; else velX = 0;
			facing = 2;
			walking = true;
		}
		if(!Game.instance.down && !Game.instance.up && !Game.instance.left && !Game.instance.right)
		{
			walking = false;
		}
	}
}
