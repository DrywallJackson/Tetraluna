package nabo;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Game implements Runnable
{
	Display disp; //the display
	boolean running = false; //is the game even running
	Thread gameThread; //game thread
	Screen currentScreen; //what to render
	public boolean right, left, up, down; //what direction keys are pressed
	Frame frame;
	public static Color gameColor = new Color(0x4AC3BF);
	public long currentTick = 0;
	public Player player;
	public List<Entity> entities = new ArrayList<Entity>();
	public List<Entity> toKill = new ArrayList<Entity>();
	public static Game instance;
	public int cameraX, cameraY;
	final float DEFAULT_VOLUME = 8.695652f;

	private final int DELAY = 5; //delay for something idk

	public Game(Display disp, Frame frame)
	{
		this.disp = disp; //what u see
		this.frame = frame;
		disp.setGame(this); //this is the game
		instance = this;
		this.startGame(); //start it up
	}

	//Start dis biz
	public void startGame()
	{
		running = true; //we goin
		gameThread = new Thread(this);
		gameThread.start(); //begin
	}

	@Override
	public void run()
	{
		long beforeTime, timeDiff, sleep;

		beforeTime = System.currentTimeMillis();
		
		disp.renderLoadingScreen("Loading...", 0);
		
		currentScreen = new MainMenuScreen(this);
		loadResources();
		disp.addMouseListener(disp.new Input());//listen for the mouse *squeak*
		disp.addKeyListener(disp.new Input());//listen for the keys to success
		player = new Player(500, 500, "main");
		entities.add(player);
		System.out.println(Helper.moonPhase(Calendar.getInstance()) + " " + MoonPhase.getCurrentPhase());
//		entities.add(new Entity("Test", 100,100,"test")
//		{
//			@Override
//			public void updateSubclass(Area a)
//			{
////				facing = (int) ((Game.instance.currentTick / 100) % 4);
////				walking = true;
////				switch(facing)
////				{
////				case 0:
////					velY=1;
////					velX=0;
////					break;
////				case 1:
////					velX=-1;
////					velY=0;
////					break;
////				case 2:
////					velX=1;
////					velY=0;
////					break;
////				case 3:
////					velY=-1;
////					velX=0;
////					break;
////				}
//			}
//		});

		Music.sounds.get("main_menu").play(true);

		disp.loading = false;
		while(running)
		{
			tick(); //update

			timeDiff = System.currentTimeMillis() - beforeTime;
			sleep = DELAY - timeDiff;

			//Delay the game
			if(sleep > 0)
			{
				try
				{
					Thread.sleep(sleep);
				}
				catch(InterruptedException e)
				{
					//oops
					System.out.println("Interrupted");
				}
			}

			beforeTime = System.currentTimeMillis();
		}
	}

	private void loadResources()
	{		
		disp.renderLoadingScreen("Loading Images...", 0);
		ImageLoader.loadImages();
		disp.renderLoadingScreen("Loading Sounds...", 0.2f);
		Music.loadMusic();
		disp.renderLoadingScreen("Loading Areas...", 0.4f);
		Area.loadAreas();
		disp.renderLoadingScreen("Loading Dialogue...", 0.6f);
		Dialogue.loadDialogues();
		disp.renderLoadingScreen("Loading Cutscenes...", 0.8f);
		Cutscene.loadCutscenes();
		disp.renderLoadingScreen("Loading...", 1);
	}
	
	public boolean addEntity(Entity e)
	{
		for(Entity e2 : entities)
		{
			if(e2.name.equals(e.name))
			{
				return false;
			}
		}
		entities.add(e);
		return true;
	}
	
	public boolean removeEntity(String name)
	{
		for(Entity e : entities)
		{
			if(e.name.equals(name))
			{
				toKill.add(e);
				return true;
			}
		}
		return false;
	}
	
	public Entity getEntity(String name)
	{
		for(Entity e : entities)
		{
			if(e.name.equals(name))
			{
				return e;
			}
		}
		return null;
	}

	//update and render
	private void tick()
	{
		entities.removeAll(toKill);
		Collections.sort(entities, new Comparator<Entity>()
		{
			@Override
			public int compare(Entity o1, Entity o2)
			{
				return (o1.posY + o1.height) - (o2.posY + o2.height);
			}
			
		});
		currentScreen.update();
		disp.render();
		currentTick++;
		if(currentTick > 9223372036854775806L)
		{
			currentTick = 0;
		}
	}

	//Pass events to the current screen to be handled
	public void mouseClicked(MouseEvent e)
	{
		currentScreen.mouseClicked(e);
	}

	public void mousePressed(MouseEvent e)
	{
		currentScreen.mousePressed(e);
	}

	public void mouseReleased(MouseEvent e)
	{
		currentScreen.mouseReleased(e);
	}

	public void mouseEntered(MouseEvent e)
	{
		currentScreen.mouseEntered(e);
	}

	public void mouseExited(MouseEvent e)
	{
		currentScreen.mouseExited(e);
	}

	public void keyTyped(KeyEvent e)
	{
		currentScreen.keyTyped(e);
	}

	//Handle controls
	public void keyPressed(KeyEvent e)
	{
		currentScreen.keyPressed(e);
	}

	public void keyReleased(KeyEvent e)
	{
		currentScreen.keyReleased(e);	
	}
}
