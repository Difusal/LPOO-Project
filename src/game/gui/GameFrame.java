package game.gui;

import javax.swing.JFrame;

public class GameFrame extends JFrame {
	private GamePanel currentPanel;

	public GameFrame() {
		currentPanel = new GamePanel();
		setUpFrame();
	}

	private void setUpFrame() {
		this.setTitle("Dungeon Explorer");
		this.setContentPane(currentPanel);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public void start() {
		this.setSize(720, 480);
		this.setVisible(true);
	}
}
