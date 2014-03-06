package game.gui;

import game.logic.Game;
import game.logic.Dragon.DragonBehavior;

public class GUI {
	private static Game game = null;

	public static void main(String[] args) {
		game = new Game(DragonBehavior.NOTMOVING, 1);
		
		GameFrame window = new GameFrame();
		window.start();
	}
}
