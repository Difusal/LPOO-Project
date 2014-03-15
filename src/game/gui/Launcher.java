package game.gui;

import java.io.IOException;

/**
 * Launcher is the base class to start the GUI - Graphics User Interface.
 * 
 * @author Henrique Ferrolho
 * @version 1.0
 */
public class Launcher {
	public static void main(String[] args) throws IOException {
		GameFrame window = new GameFrame();
		window.start();
	}
}
