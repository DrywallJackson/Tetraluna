package nabo;

import java.util.ArrayList;
import java.util.HashMap;

public class Dialogue
{
	ArrayList<String> dialogue = new ArrayList<String>();
	int currentDialogue;
	int target = -1;
	String name;
	String file;
	boolean question;
	boolean shouldClose;
	public static HashMap<String, Dialogue> dialogues = new HashMap<String, Dialogue>();
	Button b1, b2;
	String currentText;


	public Dialogue(String file)
	{
		this.file = file;
		this.name = file.split("/")[file.split("/").length - 1].split("\\.")[0];
		dialogue = Helper.readFile(file);
		shouldClose = Boolean.parseBoolean(dialogue.get(0).split("=")[1]);
		dialogue.remove(0);
	}

	public static void loadDialogues()
	{
		for(String s : Helper.listAllFiles("resources/dialogue"))
		{
			Dialogue d = new Dialogue(s);
			dialogues.put(d.name, d);
		}
	}

	public void readLine()
	{
		Game.instance.currentScreen.components.remove(b1);
		Game.instance.currentScreen.components.remove(b2);
		target = -1;
		String d = dialogue.get(currentDialogue);
		if(d.startsWith("-"))
		{
			d = d.replaceFirst("-", "");
			question = true;
			String answer1 = d.split("~")[1].split("-")[0];
			int target1 = Integer.parseInt(d.split("~")[1].split("-")[1].split("/")[0]);
			String answer2 = d.split("~")[1].split("/")[1].split("-")[0];
			int target2 = Integer.parseInt(d.split("~")[1].split("/")[1].split("-")[1]);
			d = d.split("~")[0];
			b1 = new Button(answer1, 0, 0, 50, target1, Game.instance.currentScreen);
			b2 = new Button(answer2, 0, 0, 50, target2, Game.instance.currentScreen);
			Game.instance.currentScreen.components.add(b1);
			Game.instance.currentScreen.components.add(b2);
		}
		else
		{
			question = false;
			if(d.contains("~"))
			{
				String t = d.split("~")[1];
				if(t.equals("END"))
				{
					currentDialogue = this.dialogue.size() - 1;
				}
				else
				{
					currentDialogue = Integer.parseInt(t) - 2;
				}
				d = d.split("~")[0];
			}
		}
		currentText = d;
		currentDialogue++;
	}
}
