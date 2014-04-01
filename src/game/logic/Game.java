package game.logic;

import java.io.Serializable;
import java.util.Random;
import java.util.Vector;

import game.logic.Dragon.DragonBehavior;
import game.logic.Labyrinth.Symbols;
import game.logic.LivingBeing.Type;

/**
 * 
 * Represents a Game.
 * 
 * @author Henrique Ferrolho
 * @see MazeBuilder
 * @see Labyrinth
 * @see LivingBeing
 * @see Dragon
 * @see Eagle
 * @see Hero
 * @see Sword
 * 
 */
public class Game implements Serializable {
	private static final long serialVersionUID = 1L;
	private MazeBuilder mazeBuilder = new MazeBuilder();
	private Labyrinth lab;
	private Vector<LivingBeing> livingBeings;
	private Hero hero;
	private Eagle eagle;
	private Sword sword;
	private boolean exitIsOpen = false;

	/**
	 * Constructs a game with an empty labyrinth for creation mode
	 */
	public Game() {
		// initializing variables
		lab = mazeBuilder.buildEmpty();

		initializeVariables();
	}

	/**
	 * Constructs a game with the specified number of dragons and behavior
	 * 
	 * @param dragonBehavior
	 *            dragons behavior
	 * @param numDragons
	 *            number of dragons
	 */
	public Game(DragonBehavior dragonBehavior, int numDragons) {
		// initializing variables
		lab = mazeBuilder.buildDemo();

		initializeVariables(dragonBehavior, numDragons);
	}

	/**
	 * Constructs a game with the specified dimensions, number of dragons and
	 * behavior
	 * 
	 * @param width
	 *            maze width
	 * @param height
	 *            maze height
	 * @param dragonBehavior
	 *            dragons behavior
	 * @param numDragons
	 *            number of dragons to spaw
	 */
	public Game(int width, int height, DragonBehavior dragonBehavior,
			int numDragons) {
		// initializing variables
		lab = mazeBuilder.build(width, height);

		initializeVariables(dragonBehavior, numDragons);
	}

	/**
	 * Initializes a game with no dragons. Hero and sword are initialized
	 * outside of the visible maze. This method is used when "create maze"
	 * button is pressed.
	 */
	public void initializeVariables() {
		livingBeings = new Vector<LivingBeing>();

		// creating hero
		livingBeings.add(new Hero("Hero", new Coord(-1, -1)));
		hero = (Hero) livingBeings.get(0);

		// creating eagle
		livingBeings.add(new Eagle());
		eagle = (Eagle) livingBeings.get(1);
		eagle.setPosition(new Coord(hero.getPosition()));

		// creating sword
		sword = new Sword(-1, -1);
	}

	/**
	 * Initializes a regular game.
	 * 
	 * @param dragonBehavior
	 *            a dragonBehavior
	 * @param numDragons
	 *            the number of dragons to be initialized
	 */
	public void initializeVariables(DragonBehavior dragonBehavior,
			int numDragons) {
		livingBeings = new Vector<LivingBeing>();

		// creating hero
		livingBeings.add(new Hero("Hero", lab));
		hero = (Hero) livingBeings.get(0);

		// creating eagle
		livingBeings.add(new Eagle());
		eagle = (Eagle) livingBeings.get(1);
		eagle.setPosition(new Coord(hero.getPosition()));

		// creating sword
		sword = new Sword(lab, livingBeings.get(0));

		// summoning dragons
		for (int i = 0; i < numDragons; i++)
			livingBeings.add(new Dragon(dragonBehavior, lab, livingBeings,
					sword));
	}

	/**
	 * Updates game.
	 * 
	 * @param directionToMoveHero
	 *            a Direction in which to move hero.
	 * @return <code>true</code> if game is done
	 */
	public boolean updateGame(Direction directionToMoveHero) {
		boolean done = false;

		// moving living beings
		for (LivingBeing i : livingBeings) {
			Random r = new Random();

			if (!i.isMoving()) {
				// i.setMoving(true);

				if (i.getType() == Type.HERO)
					// moving hero
					i.move(lab, directionToMoveHero);
				else {
					// move dragon randomly
					i.move(lab, Direction.values()[r.nextInt(4)]);
				}
			}
		}

		// updating eagle
		eagle.update(lab, livingBeings, hero, sword);

		// checking if player got sword
		hero.catchSwordIfPossible(sword);

		// if hero is next to a dragon
		for (LivingBeing i : livingBeings) {
			// skipping if comparing to something other than a dragon
			if (i.getType() != Type.DRAGON)
				continue;

			// if dragon is not dead nor sleeping
			if (!i.isDead() && hero.distanceTo(i) <= 1) {
				// if hero has sword
				if (hero.hasSword()) {
					// kill the dragon
					i.kill();

					// activate hero flag
					hero.killedADragon();
				} else if (!i.isSleeping()) {
					// if hero has no sword and dragon is not sleeping
					hero.kill();

					// killing eagle if it is with hero
					if (hero.hasEagle())
						eagle.kill();

					done = true;
				}
			}
		}

		// updating exit state
		if (hero.hasKilledADragon())
			exitIsOpen = true;

		// if hero is able to step on Exit, game is done
		if (lab.getLab()[hero.getPosition().getY()][hero.getPosition().getX()] == Symbols.EXIT)
			done = true;

		return done;
	}

	/**
	 * Returns the Labyrinth of this Game.
	 * 
	 * @return the Labyrinth
	 */
	public Labyrinth getLabyrinth() {
		return lab;
	}

	/**
	 * Returns the Hero of this Game.
	 * 
	 * @return the Hero
	 */
	public Hero getHero() {
		return hero;
	}

	/**
	 * Returns the LivingBeings of this Game.
	 * 
	 * @return the vector of LivingBeings
	 */
	public Vector<LivingBeing> getLivingBeings() {
		return livingBeings;
	}

	/**
	 * Returns the Sword of this Game.
	 * 
	 * @return the Sword
	 */
	public Sword getSword() {
		return sword;
	}

	/**
	 * Returns the Eagle of this Game.
	 * 
	 * @return the Eagle
	 */
	public Eagle getEagle() {
		return eagle;
	}

	/**
	 * Returns the exit state of this Game
	 * 
	 * @return <code>true</code> if exit is open
	 */
	public boolean exitIsOpen() {
		return exitIsOpen;
	}

	@Override
	public String toString() {
		return new StringBuffer("Labyrinth: ").append(lab)
				.append("Living Beings: ").append(livingBeings)
				.append("Hero: ").append(hero).append("Eagle: ").append(eagle)
				.append("Sword: ").append(sword).toString();
	}
}
