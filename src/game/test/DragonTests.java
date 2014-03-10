package game.test;

import static org.junit.Assert.*;

import game.logic.Coord;
import game.logic.Direction;
import game.logic.Dragon;
import game.logic.Dragon.DragonBehavior;
import game.logic.Labyrinth;
import game.logic.MazeBuilder;

import org.junit.Test;

public class DragonTests {
	private MazeBuilder mazeBuilder = new MazeBuilder();

	@Test
	public void validCoords() {
		Labyrinth lab = mazeBuilder.buildDemo();
		Dragon dragon = new Dragon(DragonBehavior.MOVINGANDSLEEPING, new Coord(
				1, 1));

		assertTrue("Dragon x <= 0", dragon.getPosition().getX() > 0);
		assertTrue("Dragon y <= 0", dragon.getPosition().getY() > 0);
		assertTrue("Dragon x > maze dimension",
				dragon.getPosition().getX() < lab.getWidth() - 1);
		assertTrue("Dragon y > maze dimension",
				dragon.getPosition().getY() < lab.getHeight() - 1);
	}

	@Test
	public void movement() {
		Labyrinth lab = mazeBuilder.buildDemo();
		Dragon dragon = new Dragon(DragonBehavior.MOVINGANDSLEEPING, new Coord(
				1, 1));

		// saving dragon start position
		Coord startPosition = new Coord(dragon.getPosition().getX(), dragon
				.getPosition().getY());

		// moving dragon
		dragon.move(lab, Direction.RIGHT);

		// saving dragon current position
		Coord currentPosition = new Coord(dragon.getPosition().getX(), dragon
				.getPosition().getY());

		// checking if dragon really moved
		assertTrue(!startPosition.equals(currentPosition));
	}

	@Test
	public void wallCollision() {
		Labyrinth lab = mazeBuilder.buildDemo();
		Dragon dragon = new Dragon(DragonBehavior.MOVINGANDSLEEPING, new Coord(
				1, 1));

		// saving dragon start position
		Coord startPosition = new Coord(dragon.getPosition().getX(), dragon
				.getPosition().getY());

		// moving dragon
		dragon.move(lab, Direction.UP);

		// saving dragon current position
		Coord currentPosition = new Coord(dragon.getPosition().getX(), dragon
				.getPosition().getY());

		// checking if dragon did not move
		assertEquals("Dragon did not move.", startPosition, currentPosition);
	}

	@Test
	public void sleeping() {
		Dragon dragon = new Dragon(DragonBehavior.MOVINGANDSLEEPING, new Coord(
				1, 1));

		// dragon is created awake by default
		assertFalse("Dragon was not created awake by default.",
				dragon.isSleeping());

		// testing sleeping
		dragon.sleep();
		assertTrue("Dragon did not go to sleep.", dragon.isSleeping());

		// testing waking up
		dragon.wakeUp();
		assertFalse("Dragon did not wake up.", dragon.isSleeping());
	}
}
