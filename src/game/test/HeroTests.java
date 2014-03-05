package game.test;

import static org.junit.Assert.*;
import game.logic.Coord;
import game.logic.Direction;
import game.logic.Eagle;
import game.logic.Hero;
import game.logic.Labyrinth;
import game.logic.MazeBuilder;
import game.logic.Sword;

import java.util.Random;

import org.junit.Test;

public class HeroTests {
	private MazeBuilder mazeBuilder = new MazeBuilder();
	private Labyrinth lab;
	private Hero hero;
	private Sword sword;

	@Test
	public void testValidCoords() {
		lab = mazeBuilder.Build();
		hero = new Hero("Hero", lab);
		assertTrue("Hero x <= 0", hero.getPosition().getX() > 0);
		assertTrue("Hero y <= 0", hero.getPosition().getY() > 0);
		assertTrue("Hero x > maze dimension",
				hero.getPosition().getX() < lab.getDimension() - 1);
		assertTrue("Hero y > maze dimension",
				hero.getPosition().getY() < lab.getDimension() - 1);
	}

	@Test
	public void testMovement() {
		lab = mazeBuilder.Build();
		hero = new Hero("Hero", lab);

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
		assertTrue("Hero did not move.", !startPosition.equals(currentPosition));
	}

	@Test
	public void testWallCollision() {
		lab = mazeBuilder.Build();
		hero = new Hero("Hero", lab);

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
		assertTrue("Hero did move.", startPosition.equals(currentPosition));
	}

	@Test
	public void testCatchingSword() {
		lab = mazeBuilder.Build();
		hero = new Hero("Hero", lab);

		// placing sword
		sword = new Sword(hero.getPosition());

		// testing that hero does not have the sword yet
		assertTrue("Hero unexpectedly has sword.", !hero.hasSword());

		// trying to catch sword
		assertTrue("Hero did not catch the sword.",
				hero.catchSwordIfPossible(sword));

		// testing hasSword value
		assertTrue("Hero hasSword value is not true.", hero.hasSword());
	}
}
