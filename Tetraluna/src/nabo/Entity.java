package nabo;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public abstract class Entity
{
	int posX, posY, lastX, lastY, velX, velY, width, height;
	BufferedImage[][] images;
	int facing;
	boolean walking;
	Dialogue dialogue;
	String name;

	public Entity(String name, int posX, int posY, String image)
	{
		this.posX = posX;
		this.posY = posY;
		this.images = ImageLoader.entityImages.get(image);
		this.width = images[0][0].getWidth()*2;
		this.height = images[0][0].getHeight();
		this.name = name;
	}
	
	public void openDialogue(Dialogue d)
	{
		this.dialogue = d;
		dialogue.readLine();
	}
	
	public void kill()
	{
		Game.instance.toKill.add(this);
	}
	
	public Rectangle getBounds()
	{
		return new Rectangle(posX, posY + this.images[0][0].getHeight() - this.height, this.width, this.height);
	}

	public final void update(Area a)
	{
		lastX = posX;
		lastY = posY;
		updateSubclass(a);
		if(posX + velX > 0 && posX + velX + 64 < a.width)
		{
			posX += velX;
			for(Block b : a.blocks)
			{
				if(b.getRect().intersects(posX, posY, posX + 64, posY + 32))
				{
					b.interact(this);
				}
			}
			for(Entity b : Game.instance.entities)
			{
				if(!b.equals(this) && b.getBounds().intersects(this.getBounds()))
				{
					posX = lastX;
				}
			}
			lastX = posX;
		}
		if(posY + velY + 32 > 0 && posY + velY + 64 < a.height)
		{
			posY += velY;
			for(Block b : a.blocks)
			{
				if(b.getRect().intersects(posX, posY, posX + 64, posY + 32))
				{
					b.interact(this);
				}
			}
			for(Entity b : Game.instance.entities)
			{
				if(!b.equals(this) && b.getBounds().intersects(this.getBounds()))
				{
					posY = lastY;
				}
			}
			lastY = posY;
		}
		if(dialogue != null && dialogue.question)
		{
			dialogue.b1.x = 608 + this.posX + 64 - Game.instance.cameraX;
			dialogue.b1.y = 328 +  this.posY - Game.instance.cameraY + 100;
			dialogue.b2.x = 608 + this.posX + 164 - Game.instance.cameraX;
			dialogue.b2.y = 328 +  this.posY - Game.instance.cameraY + 100;
		}
		if(dialogue != null && dialogue.shouldClose && Math.sqrt(Math.pow(this.posX - Game.instance.player.posX, 2) + Math.pow(this.posY - Game.instance.player.posY, 2)) > 200)
		{
			dialogue.currentDialogue = 0;
			dialogue = null;
		}
	}
	
	public abstract void updateSubclass(Area a);

	public void render(Graphics2D g2d)
	{
		int currentImage = (int) ((Game.instance.disp.renderTick / 50) % 4);
		if(currentImage == 3) currentImage = 1;
		if(walking)
		{
			switch(facing)
			{
			case 0:
				g2d.drawImage(images[currentImage][0].getScaledInstance(64, 64, 0), 608 + (posX - Game.instance.cameraX), 328 + (posY - Game.instance.cameraY), null);
				break;
			case 3:
				g2d.drawImage(images[currentImage][3].getScaledInstance(64, 64, 0), 608 + (posX - Game.instance.cameraX), 328 + (posY - Game.instance.cameraY), null);
				break;
			case 1:
				g2d.drawImage(images[currentImage][1].getScaledInstance(64, 64, 0), 608 + (posX - Game.instance.cameraX), 328 + (posY - Game.instance.cameraY), null);
				break;
			case 2:
				g2d.drawImage(images[currentImage][2].getScaledInstance(64, 64, 0), 608 + (posX - Game.instance.cameraX), 328 + (posY - Game.instance.cameraY), null);
				break;
			}
		}
		else
		{
			g2d.drawImage(images[1][facing].getScaledInstance(64, 64, 0), 608 + (posX - Game.instance.cameraX), 328 + (posY - Game.instance.cameraY), null);
		}
		if(dialogue != null)
		{
			g2d.setColor(new Color(0, 0, 0, 255));
			g2d.fillRect(608 + this.posX + 64 - Game.instance.cameraX, 288 +  this.posY - Game.instance.cameraY, 200, 40);
			g2d.setFont(Helper.getTetraluna().deriveFont(40f));
			g2d.setColor(Game.gameColor);
			g2d.drawString(name, 608 + this.posX + 64 - Game.instance.cameraX, 328 +  this.posY - Game.instance.cameraY);
			g2d.setColor(new Color(0, 0, 0, 200));
			g2d.fillRect(608 + this.posX + 64 - Game.instance.cameraX,328 +  this.posY - Game.instance.cameraY, 200, 100);
			g2d.setColor(new Color(0, 0, 0, 150));
			if(dialogue.question) g2d.fillRect(608 + this.posX + 64 - Game.instance.cameraX,428 +  this.posY - Game.instance.cameraY, 200, 50);
			Helper.renderText(dialogue.currentText, 608 + this.posX + 64 - Game.instance.cameraX, 328 + this.posY - Game.instance.cameraY, 200, 100, g2d);
		}
	}
}
