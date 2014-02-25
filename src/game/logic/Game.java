package game.logic;

import java.util.Vector;

import game.logic.Dragon.DragonBehavior;
import game.logic.LivingBeing.Type;

public class Game {
	public static void startGame(int dimension, DragonBehavior dragonBehavior,
			int numDragons) {
		// initializing variables
		Labyrinth lab = MazeBuilder.Build(dimension);
		Vector<LivingBeing> livingBeings = new Vector<LivingBeing>();

		// creating hero
		livingBeings.add(new Hero("Hero", lab));
		Hero hero = (Hero) livingBeings.get(0);

		// creating eagle
		livingBeings.add(new Eagle());
		Eagle eagle = (Eagle) livingBeings.get(1);
		eagle.setPosition(new Coord(hero.getPosition()));

		// creating sword
		Sword sword = new Sword(lab, livingBeings.get(0));

		// summoning dragons
		for (int i = 0; i < numDragons; i++)
			livingBeings.add(new Dragon(dragonBehavior, lab, livingBeings,
					sword));

		// print labyrinth for the first time
		lab.draw(livingBeings, sword, eagle);

		boolean done = false;
		while (!done) {
			// moving living beings
			for (LivingBeing i : livingBeings)
				i.move(lab);

			// if eagle got the sword
			if (!eagle.isDead() && sword.isVisible() && eagle.hasSword())
				// hide sword from labyrinth
				sword.hide();

			// if dragon nearby eagle while catching sword or waiting for hero,
			// die and drop sword
			if (!eagle.isDead()
					&& ((!eagle.isFlying() && !eagle.isWithHero()) || eagle
							.isCatchingSword())) {
				for (LivingBeing i : livingBeings) {
					// skipping if comparing to something other than a dragon
					if (i.getType() != Type.DRAGON)
						continue;

					if (!i.isDead() && !i.isSleeping) {
						if ((Math.abs(eagle.getPosition().getX()
								- i.getPosition().getX()) <= 1)
								&& (Math.abs(eagle.getPosition().getY()
										- i.getPosition().getY()) <= 1)) {
							// sword is dropped
							sword.setPosition(new Coord(eagle.getPosition()));
							sword.show();

							// eagle dies
							eagle.setLife(0);
						}
					}
				}
			}

			// updating eagle position if it is on hero's shoulder
			if (hero.hasEagle())
				eagle.setPosition(new Coord(hero.getPosition()));

			// if hero sent eagle
			if (hero.hasJustSentEagle())
				eagle.startFlight(lab, sword);

			// checking if hero caught eagle
			if (!eagle.isFlying() && !eagle.isDead()
					&& hero.getPosition().getX() == eagle.getPosition().getX()
					&& hero.getPosition().getY() == eagle.getPosition().getY())
				hero.catchEagle(eagle);

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

				if (!i.isDead()) {
					if ((Math.abs(hero.getPosition().getX()
							- i.getPosition().getX()) <= 1)
							&& (Math.abs(hero.getPosition().getY()
									- i.getPosition().getY()) <= 1)) {
						// if hero has sword
						if (hero.hasSword()) {
							// kill the dragon
							i.setLife(0);
							hero.killedTheDragon();
						} else if (!i.isSleeping()) {
							// else kill hero
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
