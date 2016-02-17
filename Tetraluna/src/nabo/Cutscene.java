package nabo;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

public class Cutscene
{
	public static HashMap<String, Cutscene> scenes = new HashMap<String, Cutscene>();
	ArrayList<String> commands = new ArrayList<String>();
	ArrayList<Integer> delays = new ArrayList<Integer>();
	ArrayList<String[]> args = new ArrayList<String[]>();
	int currentCommand;
	int currentDelay;
	int delayX, delayY;
	String name;
	Dialogue currentDialogue;
	Entity dialogueEntity;

	public Cutscene(String file)
	{
		ArrayList<String> lines = Helper.readFile(file);
		this.name = file.split("/")[file.split("/").length - 1].split("\\.")[0];
		for(String s : lines)
		{
			String command = s.split("~")[0];
			commands.add(command);
			if(s.contains("~"))
			{
				delays.add(Integer.parseInt(s.split("~")[1]));
			}
			else
			{
				delays.add(0);
			}
			int argNum = command.split(",").length;
			String[] a = new String[argNum];
			if(command.contains("("))
			{
				for(int i = 0; i < a.length; i++)
				{
					a[i] = command.split("\\(")[1].split("\\)")[0].split(",")[i];
				}
			}
			args.add(a);
		}
	}

	public static void loadCutscenes()
	{

		for(String s : Helper.listAllFiles("resources/cutscenes"))
		{
			Cutscene c = new Cutscene(s);
			scenes.put(c.name, c);
		}

	}

	public void update()
	{
		if(currentDialogue != null) return;
		String command = commands.get(currentCommand);
		String[] arguments = args.get(currentCommand);
		if(currentDelay <= 0) currentDelay = delays.get(currentCommand);
		String method = command.split("\\(")[0];
		try
		{
			Method m = this.getClass().getMethod(method, String[].class);
			m.setAccessible(true);
			m.invoke(this, new Object[]{arguments});
		} catch (Exception e1)
		{
			e1.printStackTrace();
		}
		currentDelay--;
		if(currentDelay <= 0)
		{
			currentCommand++;
			delayX = 0;
			delayY = 0;
			if(currentCommand == commands.size())
			{
				((GameScreen)Game.instance.currentScreen).currentCutscene = null;
			}
		}
	}
	
	public void setCamera(String[] arguments)
	{
		int x = Integer.parseInt(arguments[0]);
		int y = Integer.parseInt(arguments[1]);
		Game.instance.cameraX = x;
		Game.instance.cameraY = y;
	}

	public void moveCamera(String[] arguments)
	{
		int x = Integer.parseInt(arguments[0]);
		int y = Integer.parseInt(arguments[1]);
		int cameraX = Game.instance.cameraX;
		int cameraY = Game.instance.cameraY;
		if(delayY == 0)
		{
			delayY = cameraX != x ? (int) Math.round(Math.abs((double)currentDelay/(cameraX-x))) : currentDelay+1;
		}
		if(delayX == 0)
		{
			delayX = cameraY != y ? (int) Math.round(Math.abs((double)currentDelay/(cameraY-y))) : currentDelay+1;
		}
		if(currentDelay % delayX == 0) Game.instance.cameraY += (y > cameraY) ? 1 : -1;
		if(currentDelay % delayY == 0) Game.instance.cameraX += (x > cameraX) ? 1 : -1;
	}
	
	public void delay(String[] arguments){}
	
	public void spawnEntity(String[] arguments)
	{
		String name = arguments[0];
		int x = Integer.parseInt(arguments[1]);
		int y = Integer.parseInt(arguments[2]);
		String image = arguments[3];
		Entity e = new Entity(name, x, y, image)
		{
			@Override
			public void updateSubclass(Area a)
			{
				
			}
		};
		Game.instance.addEntity(e);
	}
	
	public void removeEntity(String[] arguments)
	{
		String name = arguments[0];
		Game.instance.removeEntity(name);
	}
	
	public void openDialogue(String[] arguments)
	{
		String name = arguments[0];
		String entity = arguments[1];
		Entity e = Game.instance.getEntity(entity);
		Dialogue d = Dialogue.dialogues.get(name);
		this.currentDialogue = d;
		this.dialogueEntity = e;
		e.openDialogue(d);
	}
	
	public void moveDialogue(String[] arguments)
	{
		String entity = arguments[0];
		Entity e = Game.instance.getEntity(entity);
		dialogueEntity.dialogue = null;
		dialogueEntity = e;
		e.openDialogue(currentDialogue);
	}
}
