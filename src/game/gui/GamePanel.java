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
		game = new Game(5, DragonBehavior.NOTMOVING, 1);
		setUpPanel();
	}

	private void setUpPanel() {
		setBackground(Color.GREEN);

		loadImages();
	}

	private void loadImages() {
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

		drawMaze(g2d);
	}

	private void drawMaze(Graphics2D g2d) {
		Symbols[][] maze = game.getLabyrinth().getLab();

		for (int i = 0; i < game.getLabyrinth().getDimension(); i++) {
			for (int j = 0; j < game.getLabyrinth().getDimension(); j++) {
				if (maze[i][j] == Symbols.WALL)
					drawTile(g2d, wall, j, i);
				else {
					// display shadow on both sides
					if (j - 1 >= 0 && j + 1 < maze.length
							&& maze[i][j - 1] == Symbols.WALL
							&& maze[i][j + 1] == Symbols.WALL)
						drawTile(g2d, pathWithShadows, j, i);
					// display shadow on left side
					else if (j - 1 >= 0 && maze[i][j - 1] == Symbols.WALL)
						drawTile(g2d, pathWithLeftShadows, j, i);
					// display shadow on right side
					else if (j + 1 < maze.length
							&& maze[i][j + 1] == Symbols.WALL)
						drawTile(g2d, pathWithRightShadows, j, i);
					// display path with no shadows
					else
						drawTile(g2d, pathWithNoShadows, j, i);
				}
			}
		}
	}

	private void drawTile(Graphics2D g2d, Image tile, int x, int y) {
		if (tile == wall)
			g2d.drawImage(tile, x * tile.getWidth(null),
					y * tile.getHeight(null) - 10 - 50 * y, null);
		else
			g2d.drawImage(tile, x * tile.getWidth(null),
					y * tile.getHeight(null) + 24 - 50 * y, null);
	}
}
