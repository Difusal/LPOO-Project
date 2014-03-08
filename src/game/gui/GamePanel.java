package game.gui;

import game.logic.Dragon.DragonBehavior;
import game.logic.Game;
import game.logic.LivingBeing;
import game.logic.Labyrinth.Symbols;
import game.logic.LivingBeing.Type;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.Random;

public class GamePanel extends JPanel {
	private static Game game = null;
	private Image pathWithShadows;
	private Image pathWithLeftShadows;
	private Image pathWithRightShadows;
	private Image pathWithNoShadows;
	private Image wall;
	private Image dragon;
	private int tileWidth, tileHeight;

	public GamePanel() {
		game = new Game(11, 11, DragonBehavior.NOTMOVING, 5);
		setUpPanel();
	}

	private void setUpPanel() {
		setBackground(Color.GREEN);
		loadImages();
	}

	private void loadImages() {
		ImageIcon ii;

		// path
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

		// wall
		ii = new ImageIcon(this.getClass().getResource("res/wall.png"));
		wall = ii.getImage();

		// dragon sprite
		ii = new ImageIcon(this.getClass().getResource("res/dragon.png"));
		dragon = ii.getImage();
	}

	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		drawGame(g2d);
	}

	private void drawGame(Graphics2D g2d) {

		for (int i = 0; i < game.getLabyrinth().getHeight(); i++) {
			for (int j = 0; j < game.getLabyrinth().getWidth(); j++) {
				// drawing maze
				drawMaze(g2d, j, i);

				// drawing dragons
				for (LivingBeing k : game.getLivingBeings())
					if (!k.isDead() && k.getType() == Type.DRAGON
							&& k.isOn(j, i))
						drawDragon(g2d, k, j, i);
			}
		}
	}

	private void drawMaze(Graphics2D g2d, int j, int i) {
		Symbols[][] maze = game.getLabyrinth().getLab();

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
			else if (j + 1 < maze.length && maze[i][j + 1] == Symbols.WALL)
				drawTile(g2d, pathWithRightShadows, j, i);
			// display path with no shadows
			else
				drawTile(g2d, pathWithNoShadows, j, i);
		}
	}

	private void drawTile(Graphics2D g2d, Image tile, int x, int y) {
		// tileWidth = 101;
		tileWidth = this.getWidth() / game.getLabyrinth().getWidth();
		tileHeight = (int) (tileWidth * (131.0 / 101.0));

		int dstX = x * tileWidth;

		int dstY, yCorrection;
		if (tile == wall)
			yCorrection = (int) (-11.0 * tileHeight / 131.0);
		else
			yCorrection = (int) (23.0 * tileHeight / 131.0);

		dstY = y * tileHeight + yCorrection;

		yCorrection = (int) (50.0 * tileHeight / 131.0);
		dstY -= y * yCorrection;

		g2d.drawImage(tile, dstX, dstY, dstX + tileWidth, dstY + tileHeight, 0,
				0, tile.getWidth(null), tile.getHeight(null), null);
	}

	private void drawDragon(Graphics2D g2d, LivingBeing k, int x, int y) {
		int dstX = x * tileWidth;
		int dstY = (int) (y * tileHeight - (12 * tileHeight / 131.0));
		int yCorrection = (int) (-50.0 * tileHeight / 131.0);
		dstY += y * yCorrection;

		g2d.drawImage(dragon, dstX, dstY, dstX + tileWidth, dstY + tileHeight,
				k.getCurrentFrame() * dragon.getWidth(null) / k.getFrames(), 0,
				k.getCurrentFrame() * dragon.getWidth(null) / k.getFrames()
						+ dragon.getWidth(null) / k.getFrames(),
				dragon.getHeight(null) / 4, null);

		k.nextFrame();
	}
}
