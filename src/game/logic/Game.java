package game.logic;

import java.util.Vector;

import game.logic.Dragon.DragonBehavior;
import game.logic.LivingBeing.Type;

public class Game {
	private Labyrinth lab;
	private Vector<LivingBeing> livingBeings;
	private Hero hero;
	private Eagle eagle;
	private Sword sword;

	public Game(DragonBehavior dragonBehavior, int numDragons) {
		// initializing variables
		lab = MazeBuilder.Build();

		initializeVariables(dragonBehavior, numDragons);
	}

	public Game(int dimension, DragonBehavior dragonBehavior, int numDragons) {
		// initializing variables
		lab = MazeBuilder.Build(dimension);

		initializeVariables(dragonBehavior, numDragons);
	}

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

	public void startGame() {
		boolean done = false;

		// print labyrinth for the first time
		lab.draw(livingBeings, sword, eagle);

		while (!done) {
			// moving living beings
			for (LivingBeing i : livingBeings)
				i.move(lab);

			// updating eagle
			eagle.update(lab, livingBeings, hero, sword);

			// checking if player got sword
			if (sword.isVisible()
					&& sword.getPosition().getX() == hero.getPosition().getX()
					&& sword.getPosition().getY() == hero.getPosition().getY()) {
				// hide sword from labyrinth
				sword.hide();

				// arm hero
				hero.arm();
			}

			// if hero is next to a dragon
			for (LivingBeing i : livingBeings) {
				// skipping if comparing to something other than a dragon
				if (i.getType() != Type.DRAGON)
					continue;

				// if dragon is not dead nor sleeping
				if (!i.isDead()) {
					if (hero.distanceTo(i) <= 1) {
						// if hero has sword
						if (hero.hasSword()) {
							// kill the dragon
							i.setLife(0);
							// active hero flag
							hero.killedADragon();
						} else if (!hero.isSleeping()) {
							// if hero has no sword and dragon is not sleeping,
							// kill hero
							hero.setLife(0);
							done = true;
						}
					}
				}
			}

			// if hero is able to step on Exit, game is done
			if (lab.getLab()[hero.getPosition().getY()][hero.getPosition()
					.getX()] == 'S')
				done = true;

			// printing labyrinth
			lab.draw(livingBeings, sword, eagle);
		}

		// displaying notification message
		System.out.println();
		if (hero.isDead())
			System.out.println("GAME OVER! You lost.");
		else
			System.out.println("CONGRATULATIONS! You won the game.");
	}
}
