package nabo;

import java.util.HashMap;
import java.util.Map;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

//holy shit i hate adding comments
public class Music extends Thread
{
	public static Map<String, Music> sounds = new HashMap<String, Music>();
	//actual file path
	String file;
	//sound data
	Clip c;
	AudioInputStream ais;
	//should it loop
	final int DELAY = 5;
	float fadeTimer;
	float fadeFactor;
	boolean in;

	public Music(String file)
	{
		this.file = file;
		sounds.put(file, this);
		try
		{
			ais = AudioSystem.getAudioInputStream(this.getClass().getResource("resources/music/" + file + ".wav"));
			c = AudioSystem.getClip();
			c.open(ais);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void loadMusic()
	{
		for(String s : Helper.listAllFiles("resources/music"))
		{
			String fileName = s.split("/")[s.split("/").length - 1].split("\\.wav")[0];
			new Music(fileName);
		}
	}

	public void play(boolean loop)
	{
		//start from beginning
		c.setFramePosition(0);
		if(loop)
		{
			//loop
			c.loop(Clip.LOOP_CONTINUOUSLY);
			this.start();
		}
		else
		{
			//start playing
			c.start();
			this.start();
		}
	}

	public void run()
	{
		long beforeTime, timeDiff, sleep;

		beforeTime = System.currentTimeMillis();

		while(c.isOpen())
		{
			update(); //update

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

	public void update()
	{
		if(fadeTimer > 0)
		{
			fadeTimer--;
			if(!in)
			{
				setVolume(getVolume()-fadeFactor);
				if(fadeTimer == 0)
				{
					stopMusic();
				}
			}
			else
			{
				setVolume(getVolume()+fadeFactor);
			}
		}
	}

	public void fadeOut(float seconds)
	{
		in = false;
		fadeTimer = seconds*1000/5;
		fadeFactor = getVolume()/fadeTimer;
	}

	public void fadeIn(float seconds, boolean loop)
	{
		in = true;
		fadeTimer = seconds*1000/5;
		fadeFactor = Game.instance.DEFAULT_VOLUME/fadeTimer;
		setVolume(0);
		this.play(loop);
	}

	public void stopMusic()
	{
		c.close();
	}

	public void setVolume(float f)
	{
		float val = (float) (3*f-40);
		FloatControl control = (FloatControl) c.getControl(FloatControl.Type.MASTER_GAIN);
		control.setValue(val);
	}

	public float getVolume()
	{
		FloatControl control = (FloatControl) c.getControl(FloatControl.Type.MASTER_GAIN);
		float val = control.getValue();
		return (float) ((val+40)/3);
	}
}
