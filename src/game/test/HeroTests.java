package game.test;

import static org.junit.Assert.*;
import game.logic.Coord;
import game.logic.Direction;
import game.logic.Dragon;
import game.logic.Hero;
import game.logic.Labyrinth;
import game.logic.MazeBuilder;
import game.logic.Sword;
import game.logic.Dragon.DragonBehavior;

import java.util.Random;
import java.util.Vector;

import org.junit.Test;

public class HeroTests {
	private MazeBuilder mazeBuilder = new MazeBuilder();

	@Test
	public void validCoords() {
		Labyrinth lab = mazeBuilder.buildDemo();
		Hero hero = new Hero("Hero", lab);

		assertTrue("Hero x <= 0", hero.getPosition().getX() > 0);
		assertTrue("Hero y <= 0", hero.getPosition().getY() > 0);
		assertTrue("Hero x > maze dimension",
				hero.getPosition().getX() < lab.getDimension() - 1);
		assertTrue("Hero y > maze dimension",
				hero.getPosition().getY() < lab.getDimension() - 1);
	}

	@Test
	public void movement() {
		Labyrinth lab = mazeBuilder.buildDemo();
		Hero hero = new Hero("Hero", lab);

		// saving hero start position
		Coord startPosition = new Coord(hero.getPosition().getX(), hero
				.getPosition().getY());

		Random r = new Random();
		Direction direction;

		// generating random direction in which hero can move
		do {
			direction = Direction.values()[r.nextInt(4)];
		} while (!lab.heroCanWalk(direction, hero));

		// moving hero
		hero.move(lab, direction);

		// saving hero current position
		Coord currentPosition = new Coord(hero.getPosition().getX(), hero
				.getPosition().getY());

		// checking if hero really moved
		assertNotEquals("Hero did not move.", startPosition, currentPosition);
	}

	@Test
	public void wallCollision() {
		Labyrinth lab = mazeBuilder.buildDemo();
		Hero hero = new Hero("Hero", lab);

		// saving hero start position
		Coord startPosition = new Coord(hero.getPosition().getX(), hero
				.getPosition().getY());

		Random r = new Random();
		Direction direction;

		// generating random direction in which hero can move
		do {
			direction = Direction.values()[r.nextInt(4)];
		} while (lab.heroCanWalk(direction, hero));

		// moving hero
		hero.move(lab, direction);

		// saving hero current position
		Coord currentPosition = new Coord(hero.getPosition().getX(), hero
				.getPosition().getY());

		// checking if hero really moved
		assertEquals("Hero did move.", startPosition, currentPosition);
	}

	@Test
	public void catchingSword() {
		Labyrinth lab = mazeBuilder.buildDemo();
		Hero hero = new Hero("Hero", lab);

		// placing sword
		Sword sword = new Sword(hero.getPosition());

		// testing that hero does not have the sword yet
		assertTrue("Hero unexpectedly has sword.", !hero.hasSword());

		// trying to catch sword
		assertTrue("Hero did not catch the sword.",
				hero.catchSwordIfPossible(sword));

		// testing hasSword value
		assertTrue("Hero hasSword value is not true.", hero.hasSword());
	}

	@Test
	public void gettingKilledByDragon() {
		Labyrinth lab = mazeBuilder.buildDemo();
		Hero hero = new Hero("Hero", lab);
		Dragon dragon = new Dragon(DragonBehavior.NOTMOVING, hero.getPosition());

		// making sure hero and dragon are alive
		assertFalse(hero.isDead());
		assertFalse(dragon.isDead());

		if (hero.distanceTo(dragon) <= 1) {
			// if hero has sword
			if (hero.hasSword()) {
				// kill the dragon
				dragon.kill();
			} else if (!hero.isSleeping()) {
				// if hero has no sword and dragon is not sleeping
				hero.kill();
			}
		}

		assertTrue(hero.isDead());
		assertFalse(dragon.isDead());
	}

	@Test
	public void killDragon() {
		Labyrinth lab = mazeBuilder.buildDemo();
		Hero hero = new Hero("Hero", lab);
		hero.arm();
		Dragon dragon = new Dragon(DragonBehavior.NOTMOVING, hero.getPosition());

		// making sure hero and dragon are alive
		assertFalse(hero.isDead());
		assertFalse(dragon.isDead());

		// making sure hero is armed
		assertTrue(hero.hasSword());

		if (hero.distanceTo(dragon) <= 1) {
			// if hero has sword
			if (hero.hasSword()) {
				// kill the dragon
				dragon.kill();
			} else if (!hero.isSleeping()) {
				// if hero has no sword and dragon is not sleeping
				hero.kill();
			}
		}

		assertFalse(hero.isDead());
		assertTrue(dragon.isDead());
	}

	@Test
	public void getSwordKillDragonAndExit() {
		Labyrinth lab = mazeBuilder.buildDemo();
		Hero hero = new Hero("Hero", new Coord(1, 1));
		Sword sword = new Sword(new Coord(1, 8));
		Dragon dragon = new Dragon(DragonBehavior.NOTMOVING, new Coord(1, 3));
		boolean done = false;
		Vector<Direction> path = mazeBuilder.getDemoSolution();

		// initial assertions
		assertFalse(hero.isDead());
		assertFalse(dragon.isDead());
		assertFalse(hero.hasSword());

		for (int i = 0; i < path.size(); i++) {
			// moving hero
			hero.move(lab, path.elementAt(i));
			hero.catchSwordIfPossible(sword);

			if (!dragon.isDead() && hero.distanceTo(dragon) <= 1) {
				// if hero has sword
				if (hero.hasSword()) {
					// kill the dragon
					dragon.kill();

					// activate hero flag
					hero.killedADragon();
				} else if (!dragon.isSleeping()) {
					// if hero has no sword and dragon is not sleeping
					hero.kill();
				}
			}

			// if hero is able to step on Exit, game is done
			if (lab.getLab()[hero.getPosition().getY()][hero.getPosition()
					.getX()] == 'S')
				done = true;
		}

		assertFalse(hero.isDead());
		assertTrue(dragon.isDead());
		assertTrue(done);
	}

	@Test
	public void tryToExitWithoutSwordAndWithoutKillingDragon() {
		Labyrinth lab = mazeBuilder.buildDemo();
		Hero hero = new Hero("Hero", new Coord(1, 1));
		Sword sword = new Sword(new Coord(1, 8));
		Dragon dragon = new Dragon(DragonBehavior.NOTMOVING, new Coord(1, 3));
		boolean done = false;
		Vector<Direction> path = new Vector<Direction>();
		for (int i = 0; i < 7; i++)
			path.add(Direction.RIGHT);
		for (int i = 0; i < 4; i++)
			path.add(Direction.DOWN);
		path.add(Direction.RIGHT);

		// initial assertions
		assertFalse(hero.isDead());
		assertFalse(dragon.isDead());
		assertFalse(hero.hasSword());

		for (int i = 0; i < path.size(); i++) {
			// moving hero
			hero.move(lab, path.elementAt(i));
			hero.catchSwordIfPossible(sword);

			if (!dragon.isDead() && hero.distanceTo(dragon) <= 1) {
				// if hero has sword
				if (hero.hasSword()) {
					// kill the dragon
					dragon.kill();

					// activate hero flag
					hero.killedADragon();
				} else if (!dragon.isSleeping()) {
					// if hero has no sword and dragon is not sleeping
					hero.kill();
				}
			}

			// if hero is able to step on Exit, game is done
			if (lab.getLab()[hero.getPosition().getY()][hero.getPosition()
					.getX()] == 'S')
				done = true;
		}

		assertFalse(hero.isDead());
		assertFalse(hero.hasSword());
		assertFalse(dragon.isDead());
		assertFalse(done);
	}
}
