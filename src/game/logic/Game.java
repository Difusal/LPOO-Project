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

		// creating sword
		Sword sword = new Sword(lab, livingBeings.get(0));

		// summoning dragons
		for (int i = 0; i < numDragons; i++)
			livingBeings.add(new Dragon(dragonBehavior, lab, livingBeings,
					sword));

		boolean done = false;
		while (!done) {
			// printing labyrinth
			lab.draw(livingBeings, sword);

			// moving living beings
			for (LivingBeing i : livingBeings)
				i.move(lab);

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
				// skipping verification if comparing to hero
				if (i.getType() == Type.HERO)
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
		}
		// print labyrinth for the last time
		lab.draw(livingBeings, sword);

		// displaying notification message
		System.out.println();
		if (hero.isDead())
			System.out.println("GAME OVER! You lost.");
		else
			System.out.println("CONGRATULATIONS! You won the game.");
	}
}
