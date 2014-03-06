package game.gui;

import game.logic.Dragon.DragonBehavior;
import game.logic.Game;
import game.logic.Labyrinth.Symbols;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

public class GamePanel extends JPanel {
	private static Game game = null;
	private Image pathWithShadows;
	private Image pathWithLeftShadows;
	private Image pathWithRightShadows;
	private Image pathWithNoShadows;
	private Image wall;

	public GamePanel() {
		game = new Game(11, DragonBehavior.NOTMOVING, 1);
		setUpPanel();
	}

	private void setUpPanel() {
		setBackground(Color.GREEN);

		// loading images
		ImageIcon ii;

		// loading path
		ii = new ImageIcon(this.getClass().getResource(
				"res/pathWithShadows.png"));
		pathWithShadows = ii.getImage();
		ii = new ImageIcon(this.getClass().getResource(
				"res/pathWithLeftShadows.png"));
		pathWithLeftShadows = ii.getImage();
		ii = new ImageIcon(this.getClass().getResource(
				"res/pathWithRightShadows.png"));
		pathWithRightShadows = ii.getImage();
		ii = new ImageIcon(this.getClass().getResource(
				"res/pathWithNoShadows.png"));
		pathWithNoShadows = ii.getImage();

		// loading wall
		ii = new ImageIcon(this.getClass().getResource("res/wall.png"));
		wall = ii.getImage();
	}

	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		// drawing maze
		Symbols[][] maze = game.getLabyrinth().getLab();
		for (int i = 0; i < game.getLabyrinth().getDimension(); i++) {
			for (int j = 0; j < game.getLabyrinth().getDimension(); j++) {
				if (maze[i][j] == Symbols.WALL)
					g2d.drawImage(wall, j * wall.getWidth(null),
							i * wall.getHeight(null) - 10 - 50 * i, null);
				else {
					// display shadow on both sides
					if (j - 1 >= 0 && j + 1 < maze.length
							&& maze[i][j - 1] == Symbols.WALL
							&& maze[i][j + 1] == Symbols.WALL)
						g2d.drawImage(pathWithShadows,
								j * pathWithShadows.getWidth(null), i
										* pathWithShadows.getHeight(null) + 24
										- 50 * i, null);
					// display shadow on left side
					else if (j - 1 >= 0 && maze[i][j - 1] == Symbols.WALL)
						g2d.drawImage(pathWithLeftShadows, j
								* pathWithLeftShadows.getWidth(null), i
								* pathWithLeftShadows.getHeight(null) + 24 - 50
								* i, null);
					// display shadow on right side
					else if (j + 1 < maze.length
							&& maze[i][j + 1] == Symbols.WALL)
						g2d.drawImage(pathWithRightShadows, j
								* pathWithRightShadows.getWidth(null), i
								* pathWithRightShadows.getHeight(null) + 24
								- 50 * i, null);
					// display path with no shadows
					else
						g2d.drawImage(pathWithNoShadows,
								j * pathWithNoShadows.getWidth(null), i
										* pathWithNoShadows.getHeight(null)
										+ 24 - 50 * i, null);
				}
			}
		}
	}
}
