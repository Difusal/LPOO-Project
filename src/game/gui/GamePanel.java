package game.gui;

import game.logic.Dragon.DragonBehavior;
import game.logic.Game;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

public class GamePanel extends JPanel {
	private static Game game = null;
	private Image path;
	private Image wall;
	private Image exit;
	private Image smaug;

	public GamePanel() {
		game = new Game(DragonBehavior.NOTMOVING, 1);
		setUpPanel();
	}

	private void setUpPanel() {
		setBackground(Color.GREEN);

		// loading images
		ImageIcon ii;

		// loading smaug
		ii = new ImageIcon(this.getClass().getResource("res/Smaug.jpg"));
		smaug = ii.getImage();

		// loading path
		ii = new ImageIcon(this.getClass().getResource("res/path.jpg"));
		path = ii.getImage();

		// loading wall
		ii = new ImageIcon(this.getClass().getResource("res/wall.jpg"));
		wall = ii.getImage();

		// loading exit
		ii = new ImageIcon(this.getClass().getResource("res/exit.jpg"));
		exit = ii.getImage();
	}

	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		// g2d.drawImage(smaug, 0, 0, null);
		
		// drawing maze
		char[][] maze = game.getLabyrinth().getLab();
		for (int i = 0; i < game.getLabyrinth().getDimension(); i++) {
			for (int j = 0; j < game.getLabyrinth().getDimension(); j++) {
				;
			}
		}
	}
}
