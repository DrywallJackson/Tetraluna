package nabo;

import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.swing.JFrame;

//frame
public class Frame extends JFrame
{
	private static final long serialVersionUID = 5346188262959234720L;

	public Frame()
	{
		URL url = ClassLoader.getSystemResource("nabo/textures/icon.png");
		Toolkit kit = Toolkit.getDefaultToolkit();
		Image img = kit.createImage(url);
		this.setIconImage(img);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1280, 720);
		setLocationRelativeTo(null);
		setTitle("Tetraluna");
		setResizable(false);
		Display disp = new Display();
		add(disp);
		setVisible(true);
		setCursor(this.getToolkit().createCustomCursor(
	            new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB), new Point(0, 0),
	            "null"));
		new Game(disp, this);
	}
}
