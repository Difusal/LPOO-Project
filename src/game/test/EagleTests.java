package game.test;

import static org.junit.Assert.*;

import java.util.Vector;

import game.logic.Eagle;
import game.logic.Hero;
import game.logic.Labyrinth;
import game.logic.LivingBeing;
import game.logic.MazeBuilder;
import game.logic.Sword;

import org.junit.Test;

public class EagleTests {
	private MazeBuilder mazeBuilder = new MazeBuilder();

	@Test
	public void creation() {
		Eagle eagle = new Eagle();

		assertFalse(eagle.isFlying());
		assertFalse(eagle.isDead());
		assertFalse(eagle.hasSword());
	}

	@Test
	public void flying() {
		Labyrinth lab = mazeBuilder.buildDemo();
		Hero hero = new Hero("Hero", lab);
		Vector<LivingBeing> livingBeings = new Vector<LivingBeing>();
		Sword sword = new Sword(lab, hero);
		Eagle eagle = new Eagle();

		assertFalse(eagle.isFlying());

		eagle.update(lab, livingBeings, hero, sword);
		eagle.startFlight(lab, sword);

		assertTrue(eagle.isFlying());
	}
}
