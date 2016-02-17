package nabo;

public class StartGameScreen extends Screen
{

	public StartGameScreen(Game game)
	{
		super(game);
		this.components.add(new TextArea(450, 100, 450, 300, 0, "AAAAAAAA! AAA! AAAAAAAAAAA!", Game.gameColor, this));
		this.components.add(new Button("Okay", 400, 400, 72, 1, this));
		this.components.add(new Button("Cool", 700, 400, 72, 2, this));
	}

	public void onAction(int id)
	{
		switch(id)
		{
		case 0:
			break;
		case 1:
		case 2:
			exitTo(new GameScreen(game, Area.areas.get("Test")));
			Music.sounds.get("main_menu").fadeOut(3);
			break;
		default:
			break;
		}
	}
}
