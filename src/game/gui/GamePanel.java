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
		game = new Game(25, 17, DragonBehavior.NOTMOVING, 1);
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

		for (int i = 0; i < game.getLabyrinth().getHeight(); i++) {
			for (int j = 0; j < game.getLabyrinth().getWidth(); j++) {
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
		int imageWidth = 50;
		int imageHeight = (int) (imageWidth * (131.0 / 101.0));

		int dstX = x * imageWidth;

		int dstY, yCorrection;
		if (tile == wall)
			yCorrection = (int) (-10.0 * imageHeight / 131.0);
		else
			yCorrection = (int) (24.0 * imageHeight / 131.0);

		dstY = y * imageHeight + yCorrection;

		yCorrection = (int) (50.0 * imageHeight / 131.0);
		dstY -= yCorrection * y;

		dstX += 20;
		dstY += 20;

		g2d.drawImage(tile, dstX, dstY, dstX + imageWidth, dstY + imageHeight,
				0, 0, tile.getWidth(null), tile.getHeight(null), null);
	}
}
