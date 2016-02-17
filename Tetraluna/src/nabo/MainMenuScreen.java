package nabo;


//main menu
public class MainMenuScreen extends Screen
{
//	Image i; //cover image

	
	public MainMenuScreen(Game game)
	{
		super(game);
		//add buttons
		this.components.add(new Button("Start",531, 300, 100, 0, this));
		this.components.add(new Button("Options",488, 400, 100, 1, this));
		this.components.add(new Button("Exit", 20, 630, 50, 2, this));
		this.components.add(new TextArea(0, 0, game.disp.getWidth(), 325, 4, "Tetraluna", Game.gameColor, this));
		//draw cover image
//		i = new ImageIcon(this.getClass().getResource("textures/main_menu.png")).getImage();
//		i = i.getScaledInstance(1280, 720, 0);
	}

	public void onAction(int id)
	{
		switch(id)
		{
		//Start
		case 0:
			exitTo(new StartGameScreen(game));
			break;
		//Options
		case 1:
			exitTo(new OptionScreen(game));
			break;
		//Exit
		case 2:
			//Clear sounds and exit
			game.running = false;
			System.exit(0);
		default:
			break;
		}
	}
}
