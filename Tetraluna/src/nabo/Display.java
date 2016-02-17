package nabo;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JPanel;

//Display the jazz
public class Display extends JPanel
{
	private static final long serialVersionUID = 4859873746452811099L;//idk what this is for
	private Game game;//the game
	public static Dimension defaultSize;//the size
	public static int mouseX, mouseY, lastMouseX, lastMouseY;//mouse positions
	public static boolean mouseOutside;//is the mouse outside the window
	Point mouseTemp;//temporary mouse position?
	public long renderTick;
	public boolean loading;
	public String loadingString;
	float loadingProgress;

	public Display()
	{
		setBackground(Color.black);
		setDoubleBuffered(true);//buffer dat shiz
		setFocusable(true);//FOCUS
	}

	//this is the game
	public void setGame(Game g)
	{
		this.game = g;
	}

	//draw it
	public void paint(Graphics g)
	{
		super.paint(g);

		Graphics2D g2d = (Graphics2D)g;//get that g
		if(!loading)
		{
			renderCurrentGameScreen(g2d);//render the current screen
		}
		else
		{
			g2d.setColor(Game.gameColor);
			Font f = Helper.getTetraluna().deriveFont((float)48);
			FontMetrics fm = g2d.getFontMetrics(f);
			g2d.setFont(f);
			int x = (int) (this.getWidth()/2d - fm.stringWidth(loadingString)/2d);
			int y = (int) (this.getHeight()/2d + 48/2d);
			g2d.drawString(loadingString, x, y);
			g2d.fillRect((int)(this.getWidth()/2d - 200), (int)(this.getHeight()/2d + 40), (int)(400*loadingProgress), 20);
		}

		Toolkit.getDefaultToolkit().sync();//sink it
		g.dispose();//dispose it
	}

	private void renderCurrentGameScreen(Graphics2D g2d)
	{

		if(game != null && game.currentScreen != null)
		{
			game.currentScreen.render(g2d);//render tehe screen
			//use maths to render the mouse cursor
			g2d.setColor(Color.green);
			int dot1X = (int) (12*Math.cos(Math.toRadians(this.renderTick)));
			int dot1Y = (int) (12*Math.sin(Math.toRadians(this.renderTick)));
			g2d.fillOval(dot1X + Display.mouseX, dot1Y + Display.mouseY, 5, 5);
			g2d.setColor(Color.red);
			int dot2X = (int) (15*Math.cos(Math.toRadians(-this.renderTick) + Math.PI/2));
			int dot2Y = (int) (15*Math.sin(Math.toRadians(-this.renderTick) + Math.PI/2));
			g2d.fillOval(dot2X + Display.mouseX, dot2Y + Display.mouseY, 5, 5);
			g2d.setColor(Color.yellow);
			int dot3X = (int) (25*Math.cos(Math.toRadians(this.renderTick) + Math.PI));
			int dot3Y = (int) (25*Math.sin(Math.toRadians(this.renderTick) + Math.PI));
			g2d.fillOval(dot3X + Display.mouseX, dot3Y + Display.mouseY, 5, 5);
			g2d.setColor(Color.ORANGE);
			int dot4X = (int) (20*Math.cos(Math.toRadians(this.renderTick) + Math.PI * 3/2));
			int dot4Y = (int) (10*Math.sin(Math.toRadians(this.renderTick) + Math.PI * 3/2));
			g2d.fillOval(dot4X + Display.mouseX, dot4Y + Display.mouseY, 5, 5);
			g2d.setColor(Game.gameColor);
			g2d.fillOval(Display.mouseX, Display.mouseY, 10, 10);;
		}
	}

	public void render()
	{		
		lastMouseX = mouseX; //update mouse positions
		lastMouseY = mouseY;
		repaint();//redraw
		if(this.getMousePosition() != null) mouseTemp = this.getMousePosition(); //temporary mouse position
		if(mouseTemp == null)
		{
			//put it back
			mouseTemp = new Point((int)(lastMouseX), (int)(lastMouseY));
		}
		mouseX = (int)(mouseTemp.x); //set the resized mouse positions
		mouseY = (int)(mouseTemp.y);
		renderTick++;
		if(renderTick > 9223372036854775806L)
		{
			renderTick = 0;
		}
	}

	//handle key/mouse inputs and pass them to the game
	public class Input implements MouseListener, KeyListener, WindowListener
	{

		@Override
		public void keyTyped(KeyEvent e)
		{
			game.keyTyped(e);
		}

		@Override
		public void keyPressed(KeyEvent e)
		{
			game.keyPressed(e);
		}

		@Override
		public void keyReleased(KeyEvent e)
		{
			game.keyReleased(e);
		}

		@Override
		public void mouseClicked(MouseEvent e)
		{
			game.mouseClicked(e);
		}

		@Override
		public void mousePressed(MouseEvent e)
		{
			game.mousePressed(e);
		}

		@Override
		public void mouseReleased(MouseEvent e)
		{
			game.mouseReleased(e);
		}

		@Override
		public void mouseEntered(MouseEvent e)
		{
			game.mouseEntered(e);
		}

		@Override
		public void mouseExited(MouseEvent e)
		{
			game.mouseExited(e);
		}

		@Override
		public void windowOpened(WindowEvent e)
		{
			
		}

		@Override
		public void windowClosing(WindowEvent e)
		{
			game.running = false;
		}

		@Override
		public void windowClosed(WindowEvent e)
		{
			System.exit(0);
		}

		@Override
		public void windowIconified(WindowEvent e)
		{
			
		}

		@Override
		public void windowDeiconified(WindowEvent e)
		{
			
		}

		@Override
		public void windowActivated(WindowEvent e)
		{
			
		}

		@Override
		public void windowDeactivated(WindowEvent e)
		{
			
		}

	}

	public void renderLoadingScreen(String loadingString, float progress)
	{
		loading = true;
		this.loadingString = loadingString;
		this.loadingProgress = progress;
		repaint();
	}
}
