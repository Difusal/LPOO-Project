package game.gui;

import game.logic.Direction;
import game.logic.Dragon;
import game.logic.Dragon.DragonBehavior;
import game.logic.Game;
import game.logic.GameConfig;
import game.logic.Hero;
import game.logic.Labyrinth;
import game.logic.LivingBeing;
import game.logic.Labyrinth.Symbols;
import game.logic.LivingBeing.Type;
import game.logic.Sword;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * 
 * Game Panel is the main game panel. It is where the game board and characters
 * will be drawn.
 * 
 * @author Henrique Ferrolho
 * 
 */
public class GamePanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private boolean showBackground = true;
	private Timer timer;
	private Image background;
	private Image pathWithShadows;
	private Image pathWithLeftShadows;
	private Image pathWithRightShadows;
	private Image pathWithNoShadows;
	private Image wall;
	private Image exit;
	private Image heroWithoutSword;
	private Image heroWithSword;
	private Image sword;
	private Image dragon;
	private Image dragonSleeping;
	private Image eagle;
	private int tileWidth, tileHeight;
	private Game game = null;
	private int upKey = KeyEvent.VK_W;
	private int leftKey = KeyEvent.VK_A;
	private int rightKey = KeyEvent.VK_D;
	private int downKey = KeyEvent.VK_S;
	private int sendEagleKey = KeyEvent.VK_B;

	/**
	 * Class constructor.
	 */
	public GamePanel() {
		addKeyListener(new TAdapter());
		setFocusable(true);
		setDoubleBuffered(true);

		loadImages();

		timer = new Timer(150, (ActionListener) this);
	}

	/**
	 * Starts a new Demo game. The Demo labyrinth is 10x10. There is 1 dragon
	 * and it does not move.
	 */
	public void startNewDemoGame() {
		game = new Game(DragonBehavior.NOTMOVING, 1);
		initGame();
	}

	/**
	 * Starts a new game with the information specified. The labyrinth width and
	 * height should both be an odd number.
	 * 
	 * @param width
	 *            Labyrinth width
	 * @param height
	 *            Labyrinth height
	 * @param behavior
	 *            Dragons behavior
	 * @param numDragons
	 *            Number of dragons to spawn
	 * 
	 * @see {@link Labyrinth}, {@link Dragon}, {@link DragonBehavior}
	 */
	public void startNewGame(int width, int height, DragonBehavior behavior,
			int numDragons) {
		game = new Game(width, height, behavior, numDragons);
		initGame();
	}

	/**
	 * Starts a new game from a previous game configuration.
	 * 
	 * @param gameConfig
	 *            The GameConfig with the new game configuration
	 * 
	 * @see {@link GameConfig}
	 */
	public void startNewGame(GameConfig gameConfig) {
		game = new Game(gameConfig.getWidth(), gameConfig.getHeight(),
				gameConfig.getDragonBehavior(), gameConfig.getNumDragons());

		// reassigning movement keys
		upKey = gameConfig.getUpKeyAssignment();
		leftKey = gameConfig.getLeftKeyAssignment();
		rightKey = gameConfig.getRightKeyAssignment();
		downKey = gameConfig.getDownKeyAssignment();

		initGame();
	}

	public Game getGame() {
		return game;
	}

	public void loadGame(Game game) {
		this.game = game;
		initGame();
	}

	/**
	 * Initiates the game.
	 */
	private void initGame() {
		showBackground = false;
		timer.start();
		requestFocus();
	}

	private class TAdapter extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			if (showBackground)
				return;

			int key = e.getKeyCode();

			Direction dir = Direction.NONE;
			if (key == KeyEvent.VK_RIGHT || key == rightKey)
				dir = Direction.RIGHT;
			else if (key == KeyEvent.VK_DOWN || key == downKey)
				dir = Direction.DOWN;
			else if (key == KeyEvent.VK_LEFT || key == leftKey)
				dir = Direction.LEFT;
			else if (key == KeyEvent.VK_UP || key == upKey)
				dir = Direction.UP;
			else if (key == sendEagleKey)
				if (game.getHero().hasEagle() && !game.getHero().hasSword())
					game.getHero().sendEagle();

			if (game.updateGame(dir)) {
				// Game Over
				if (game.getHero().isDead()) {
					String msg = "Game Over!";
					JOptionPane.showMessageDialog(getRootPane(), msg);
				} else {
					String msg = "You win!";
					JOptionPane.showMessageDialog(getRootPane(), msg);
				}

				timer.stop();
				showBackground = true;
			}
		}
	}

	public void actionPerformed(ActionEvent e) {
		repaint();
	}

	/**
	 * Loads game images.
	 */
	private void loadImages() {
		ImageIcon ii;

		// background
		ii = new ImageIcon(this.getClass().getResource("res/background.png"));
		background = ii.getImage();

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

		// exit
		ii = new ImageIcon(this.getClass().getResource("res/closedExit.png"));
		exit = ii.getImage();

		// hero sprite
		ii = new ImageIcon(this.getClass().getResource(
				"res/heroWithoutSword.png"));
		heroWithoutSword = ii.getImage();
		ii = new ImageIcon(this.getClass().getResource("res/heroWithSword.png"));
		heroWithSword = ii.getImage();

		// sword
		ii = new ImageIcon(this.getClass().getResource("res/sword.png"));
		sword = ii.getImage();

		// dragon sprite
		ii = new ImageIcon(this.getClass().getResource("res/dragon.png"));
		dragon = ii.getImage();
		ii = new ImageIcon(this.getClass()
				.getResource("res/dragonSleeping.png"));
		dragonSleeping = ii.getImage();

		// eagle sprite
		ii = new ImageIcon(this.getClass().getResource("res/eagle.png"));
		eagle = ii.getImage();
	}

	/**
	 * Draws the game state.
	 */
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		if (showBackground)
			g2d.drawImage(background, 0, 0, this.getWidth(), this.getHeight(),
					0, 0, background.getWidth(null),
					background.getHeight(null), null);
		else
			drawGame(g2d);

		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}

	/**
	 * Draws the game board and characters.
	 * 
	 * @param g2d
	 */
	private void drawGame(Graphics2D g2d) {
		for (int i = 0; i < game.getLabyrinth().getHeight(); i++) {
			for (int j = 0; j < game.getLabyrinth().getWidth(); j++) {
				// drawing maze
				drawMaze(g2d, j, i);

				// drawing sword
				if (game.getSword().isOn(j, i) && game.getSword().isVisible())
					drawSword(g2d, game.getSword(), j, i);

				// drawing dragons and hero and
				// eagle (if catching sword or waiting for hero)
				for (LivingBeing k : game.getLivingBeings()) {
					if (!k.isDead() && k.isOn(j, i)) {
						if (k.getType() == Type.DRAGON)
							drawDragon(g2d, k, j, i);
						else if (k.getType() == Type.HERO)
							drawHero(g2d, k, j, i);
						else if (k.getType() == Type.EAGLE
								&& (game.getEagle().isCatchingSword() || (!game
										.getEagle().isFlying() && !game
										.getEagle().isWithHero())))
							drawEagle(g2d, k, j, i);
					}
				}
			}
		}

		// drawing eagle when not catching sword nor waiting for hero
		if (!game.getEagle().isDead()
				&& !(game.getEagle().isCatchingSword() || (!game.getEagle()
						.isFlying() && !game.getEagle().isWithHero())))
			drawEagle(g2d, game.getEagle(), game.getEagle().getPosition()
					.getX(), game.getEagle().getPosition().getY());
	}

	/**
	 * Chooses correct maze tile to be drawn.
	 * 
	 * @param g2d
	 * @param j
	 * @param i
	 */
	private void drawMaze(Graphics2D g2d, int j, int i) {
		Symbols[][] maze = game.getLabyrinth().getLab();

		if (maze[i][j] == Symbols.WALL)
			drawTile(g2d, wall, j, i);
		else if (maze[i][j] == Symbols.EXIT)
			drawTile(g2d, exit, j, i);
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

	/**
	 * Draws a maze tile.
	 * 
	 * @param g2d
	 * @param tile
	 * @param x
	 * @param y
	 */
	private void drawTile(Graphics2D g2d, Image tile, int x, int y) {
		// scaling tiles
		tileWidth = this.getWidth() / game.getLabyrinth().getWidth();
		tileHeight = (int) (tileWidth * (131.0 / 101.0));

		// correcting scaling
		int temp = (int) (81.0 * tileHeight / 131.0);
		if (this.getHeight() < temp * game.getLabyrinth().getHeight()) {
			tileHeight = this.getHeight() / game.getLabyrinth().getHeight();
			tileHeight += 81.0 * tileHeight / 131.0;
			tileWidth = (int) (tileHeight * 101.0 / 131.0);
		}

		int dstX = x * tileWidth;

		int dstY, yCorrection;
		if (tile == wall || (tile == exit && !game.exitIsOpen()))
			yCorrection = (int) (-11.0 * tileHeight / 131.0);
		else
			yCorrection = (int) (23.0 * tileHeight / 131.0);

		dstY = y * tileHeight + yCorrection;

		yCorrection = (int) (-50.0 * tileHeight / 131.0);
		dstY += y * yCorrection;

		g2d.drawImage(tile, dstX, dstY, dstX + tileWidth, dstY + tileHeight, 0,
				0, tile.getWidth(null), tile.getHeight(null), null);
	}

	/**
	 * Draws hero.
	 * 
	 * @param g2d
	 * @param hero
	 * @param x
	 * @param y
	 */
	private void drawHero(Graphics2D g2d, LivingBeing hero, int x, int y) {
		int dstX = x * tileWidth;
		int dstY = (int) (y * tileHeight - (15 * tileHeight / 131.0));
		int yCorrection = (int) (-50.0 * tileHeight / 131.0);
		dstY += y * yCorrection;

		dstX += tileWidth / 6.0;
		dstY += tileHeight / 6.0;

		/*
		 * if (hero.isMoving()) { if (hero.getFacingDir() == Direction.RIGHT)
		 * dstX += hero.getCurrentFrame() * tileWidth / hero.getFrames() -
		 * tileWidth; else if (hero.getFacingDir() == Direction.LEFT) dstX -=
		 * hero.getCurrentFrame() * tileWidth / hero.getFrames() - tileWidth;
		 * else if (hero.getFacingDir() == Direction.DOWN) dstY +=
		 * hero.getCurrentFrame() * tileHeight / hero.getFrames() - tileHeight;
		 * else if (hero.getFacingDir() == Direction.UP) dstY -=
		 * hero.getCurrentFrame() * tileHeight / hero.getFrames() - tileHeight;
		 * }
		 */

		Image sprite;
		if (((Hero) hero).hasSword())
			sprite = heroWithSword;
		else
			sprite = heroWithoutSword;

		g2d.drawImage(
				sprite,
				dstX,
				dstY,
				(int) (dstX + 2.0 * tileWidth / 3.0),
				(int) (dstY + 2.0 * tileHeight / 3.0),
				hero.getCurrentFrame() * sprite.getWidth(null)
						/ hero.getFrames(),
				hero.getFacingDir().getValue() * sprite.getHeight(null) / 4,
				hero.getCurrentFrame() * sprite.getWidth(null)
						/ hero.getFrames() + sprite.getWidth(null)
						/ hero.getFrames(), hero.getFacingDir().getValue()
						* sprite.getHeight(null) / 4 + sprite.getHeight(null)
						/ 4, null);

		/*
		 * if (hero.isMoving()) hero.nextFrame();
		 */
	}

	/**
	 * Draws sword.
	 * 
	 * @param g2d
	 * @param k
	 * @param x
	 * @param y
	 */
	private void drawSword(Graphics2D g2d, Sword k, int x, int y) {
		int dstX = x * tileWidth;
		int dstY = (int) (y * tileHeight + (24 * tileHeight / 131.0));
		int yCorrection = (int) (-50.0 * tileHeight / 131.0);
		dstY += y * yCorrection;

		int border = (int) (tileWidth / 5.0);

		g2d.drawImage(sword, dstX + border, dstY + border, dstX + tileWidth
				- border, dstY + tileWidth - border, 0, 0,
				sword.getWidth(null), sword.getHeight(null), null);
	}

	/**
	 * Draws dragon.
	 * 
	 * @param g2d
	 * @param k
	 * @param x
	 * @param y
	 */
	private void drawDragon(Graphics2D g2d, LivingBeing k, int x, int y) {
		int dstX = x * tileWidth;
		int dstY = (int) (y * tileHeight - (12 * tileHeight / 131.0));
		int yCorrection = (int) (-50.0 * tileHeight / 131.0);
		dstY += y * yCorrection;

		if (k.isSleeping())
			g2d.drawImage(dragonSleeping, dstX, dstY, dstX + tileWidth, dstY
					+ tileHeight, 0, 0, dragonSleeping.getWidth(null),
					dragonSleeping.getHeight(null), null);
		else
			g2d.drawImage(dragon, dstX, dstY, dstX + tileWidth, dstY
					+ tileHeight, k.getCurrentFrame() * dragon.getWidth(null)
					/ k.getFrames(),
					k.getFacingDir().getValue() * dragon.getHeight(null) / 4,
					k.getCurrentFrame() * dragon.getWidth(null) / k.getFrames()
							+ dragon.getWidth(null) / k.getFrames(), k
							.getFacingDir().getValue()
							* dragon.getHeight(null)
							/ 4 + dragon.getHeight(null) / 4, null);

		k.nextFrame();
	}

	/**
	 * Draws eagle.
	 * 
	 * @param g2d
	 * @param k
	 * @param x
	 * @param y
	 */
	private void drawEagle(Graphics2D g2d, LivingBeing k, int x, int y) {
		Image img = eagle;

		if (game.getEagle().hasSword())
			drawSword(g2d, game.getSword(), x, y);

		int dstX = x * tileWidth;
		int dstY = (int) (y * tileHeight - (12 * tileHeight / 131.0));
		int yCorrection = (int) (-50.0 * tileHeight / 131.0);
		dstY += y * yCorrection;

		int dstWidth = tileWidth;
		int dstHeight = tileHeight;

		if (game.getEagle().isWithHero()
				|| game.getEagle().isCatchingSword()
				|| (!game.getEagle().isWithHero() && !game.getEagle()
						.isFlying())) {
			// resizing
			dstWidth = (int) (tileWidth / 1.5);
			dstHeight = (int) (dstWidth * 131.0 / 101.0);

			// replacing
			if (game.getEagle().isCatchingSword()
					|| (!game.getEagle().isWithHero() && !game.getEagle()
							.isFlying())) {
				dstX += (tileWidth - dstWidth) / 2.0;
				dstY += (tileHeight - dstHeight) / 2.0;
			} else {
				dstX -= 0.15 * tileWidth;
				dstY -= 0.15 * tileHeight;
			}

			if (!game.getEagle().isWithHero() && !game.getEagle().isFlying())
				game.getEagle().setCurrentFrame(1);
		}

		g2d.drawImage(
				img,
				dstX,
				dstY,
				dstX + dstWidth,
				dstY + dstHeight,
				k.getCurrentFrame() * img.getWidth(null) / k.getFrames(),
				k.getFacingDir().getValue() * img.getHeight(null) / 4,
				k.getCurrentFrame() * img.getWidth(null) / k.getFrames()
						+ img.getWidth(null) / k.getFrames(),
				k.getFacingDir().getValue() * img.getHeight(null) / 4
						+ img.getHeight(null) / 4, null);

		if (game.getEagle().isFlying() || game.getEagle().isWithHero())
			k.nextFrame();
	}

	/**
	 * Gets the key assigned to move hero up.
	 * 
	 * @return key assigned to move hero up.
	 */
	public int getUpKey() {
		return upKey;
	}

	/**
	 * Sets key to move user up.
	 * 
	 * @param upKey
	 *            key to move user up
	 */
	public void setUpKey(int upKey) {
		this.upKey = upKey;
	}

	public int getLeftKey() {
		return leftKey;
	}

	public void setLeftKey(int leftKey) {
		this.leftKey = leftKey;
	}

	public int getRightKey() {
		return rightKey;
	}

	public void setRightKey(int rightKey) {
		this.rightKey = rightKey;
	}

	public int getDownKey() {
		return downKey;
	}

	public void setDownKey(int downKey) {
		this.downKey = downKey;
	}

	public void setSendEagleKey(int sendEagleKey) {
		this.sendEagleKey = sendEagleKey;
	}

	public boolean hasAnActiveGame() {
		return !showBackground;
	}
}
