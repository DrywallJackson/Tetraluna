package nabo;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class GameScreen extends Screen
{
	Area area;
	Cutscene currentCutscene;

	public GameScreen(Game game, Area a)
	{
		super(game);
		this.area = a;
		Music.sounds.get("start_game").fadeIn(3, true);
		currentCutscene = Cutscene.scenes.get("Test");
	}

	public void update()
	{
		for(Entity e : game.entities)
		{
			e.update(area);
		}
		if(currentCutscene != null)
		{
			currentCutscene.update();
		}
		else
		{
			game.cameraX = game.player.posX;
			game.cameraY = game.player.posY;
		}
	}

	public void onAction(int id)
	{
		for(Entity e : game.entities)
		{
			if(e.dialogue != null && e.dialogue.question)
			{
				e.dialogue.currentDialogue = id - 2;
				e.dialogue.readLine();
			}
		}
	}

	public void render(Graphics2D g2d)
	{
		g2d.drawImage(area.image, 608 - game.cameraX, 328 - game.cameraY, null);
		for(Entity e: game.entities)
		{
			e.render(g2d);
		}
		super.render(g2d);
	}

	public void mousePressed(MouseEvent e)
	{
		for(Entity d : game.entities)
		{
			if(d.dialogue != null && !d.dialogue.question)
			{
				if(d.dialogue.currentDialogue == d.dialogue.dialogue.size())
				{
					d.dialogue.currentDialogue = 0;
					d.dialogue = null;
					if(this.currentCutscene != null)
					{
						currentCutscene.currentDialogue = null;
					}
				}
				else
				{
					d.dialogue.readLine();
				}
			}
		}
		super.mousePressed(e);
	}

	//Handle controls
	public void keyPressed(KeyEvent e)
	{
		if(currentCutscene != null) return;
		switch(e.getKeyCode())
		{
		case KeyEvent.VK_D:
			game.player.facing = 2;
			game.player.walking = true;
			game.right = true;
			break;
		case KeyEvent.VK_S:
			game.player.facing = 0;
			game.player.walking = true;
			game.down = true;
			break;
		case KeyEvent.VK_W:
			game.player.facing = 3;
			game.player.walking = true;
			game.up = true;
			break;
		case KeyEvent.VK_A:
			game.player.facing = 1;
			game.player.walking = true;
			game.left = true;
			break;
		default:
			break;
		}
	}

	public void keyReleased(KeyEvent e)
	{
		if(currentCutscene != null) return;
		switch(e.getKeyCode())
		{
		case KeyEvent.VK_D:
			game.right = false;
			if(!game.left) game.player.velX = 0; else game.player.velX = -1;
			if(!game.left && !game.up && !game.down)
			{
				game.player.walking = false;
			}
			break;
		case KeyEvent.VK_S:
			game.down = false;
			if(!game.up) game.player.velY = 0; else game.player.velY = -1;
			if(!game.left && !game.up && !game.right)
			{
				game.player.walking = false;
			}
			break;
		case KeyEvent.VK_W:
			game.up = false;
			if(!game.down) game.player.velY = 0; else game.player.velY = 1;
			if(!game.left && !game.right && !game.down)
			{
				game.player.walking = false;
			}
			break;
		case KeyEvent.VK_A:
			game.left = false;
			if(!game.right) game.player.velX = 0; else game.player.velX = 1;
			if(!game.right && !game.up && !game.down)
			{
				game.player.walking = false;
			}
			break;
		case KeyEvent.VK_SPACE:
			for(Entity entity : game.entities)
			{
				if(!entity.equals(game.player) && game.currentScreen.equals(this) && Math.sqrt(Math.pow(entity.posX - game.player.posX, 2) + Math.pow(entity.posY - game.player.posY, 2)) < 100)
				{
					entity.openDialogue(Dialogue.dialogues.get("Test"));
				}
			}
			break;
		default:
			break;
		}	
	}
}
