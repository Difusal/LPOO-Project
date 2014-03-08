package game.logic;

import java.util.Random;
import java.util.Vector;

public class Eagle extends LivingBeing {
	private boolean flying = false;
	private boolean withHero = true;
	private boolean hasSword = false;
	private FlightState state;
	private int pathStep;
	Vector<Coord> path = new Vector<Coord>();

	public enum FlightState {
		GOING, RETURNING
	}

	// change order according to sprite sheet
	public enum SpriteDirection {
		DOWN, LEFT, RIGHT, UP
	}

	public Eagle() {
		type = Type.EAGLE;
		prepareSpriteData();
	}

	private void prepareSpriteData() {
		// change this according to sprite sheet
		frames = 4;
		Random r = new Random();
		currentFrame = r.nextInt(frames);
	}

	public void startFlight(Labyrinth lab, Sword sword) {
		// preparing variables
		flying = true;
		withHero = false;
		state = FlightState.GOING;
		pathStep = 0;

		// calculating path
		path.clear();

		// if dx > dy
		if (Math.abs(sword.position.getX() - position.getX()) > Math
				.abs(sword.position.getY() - position.getY())) {
			// calculating m
			double m;
			if (sword.position.getX() == position.getX())
				m = 0;
			else
				m = 1.0 * (sword.position.getY() - position.getY())
						/ (sword.position.getX() - position.getX());

			// calculating b
			double b = position.getY() - m * position.getX();

			// if eagleX < swordX
			if (position.getX() < sword.position.getX()) {
				for (int i = position.getX(); i <= sword.position.getX(); i++) {
					path.add(new Coord(i, (int) Math.round(m * i + b)));
				}
			}
			// if eagleX >= swordX
			else {
				for (int i = position.getX(); i >= sword.position.getX(); i--) {
					path.add(new Coord(i, (int) Math.round(m * i + b)));
				}
			}
		}
		// if dy >= dx
		else {
			// calculating m
			double m;
			if (sword.position.getY() == position.getY())
				m = 0;
			else
				m = 1.0 * (sword.position.getX() - position.getX())
						/ (sword.position.getY() - position.getY());

			// calculating b
			double b = position.getX() - m * position.getY();

			// if eagleY < swordY
			if (position.getY() < sword.position.getY()) {
				for (int i = position.getY(); i <= sword.position.getY(); i++) {
					path.add(new Coord((int) Math.round(m * i + b), i));
				}
			}
			// if eagleY >= swordY
			else {
				for (int i = position.getY(); i >= sword.position.getY(); i--) {
					path.add(new Coord((int) Math.round(m * i + b), i));
				}
			}
		}

		// moving eagle
		move(lab, Direction.NONE);
	}

	public void move(Labyrinth lab, Direction direction) {
		// if eagle moving
		if (flying) {
			// updating pathStep
			if (state == FlightState.GOING)
				pathStep++;
			else
				pathStep--;

			// sword reached
			if (pathStep >= path.size() - 1) {
				// inverting flight direction
				state = FlightState.RETURNING;

				// catching sword
				hasSword = true;
			}

			// if origin reached
			if (pathStep == 0 && state == FlightState.RETURNING)
				flying = false;

			// updating eagle position
			getPosition().setX(path.get(pathStep).getX());
			getPosition().setY(path.get(pathStep).getY());
		}
	}

	public void update(Labyrinth lab, Vector<LivingBeing> livingBeings,
			Hero hero, Sword sword) {
		// if eagle got the sword
		if (!isDead() && sword.isVisible() && hasSword())
			// hide sword from labyrinth
			sword.hide();

		// if dragon nearby when waiting for hero or catching sword
		if (!isDead() && ((!isFlying() && !isWithHero()) || isCatchingSword())) {
			for (LivingBeing i : livingBeings) {
				// skipping if comparing to something other than a dragon
				if (i.getType() != Type.DRAGON)
					continue;

				// if dragon is not dead nor sleeping
				if (!i.isDead() && !i.isSleeping) {
					if (distanceTo(i) <= 1) {
						// sword is dropped
						sword.setPosition(new Coord(getPosition()));
						sword.show();

						// eagle dies
						setLife(0);
					}
				}
			}
		}

		// updating eagle position if it is on hero's shoulder
		if (hero.hasEagle())
			setPosition(new Coord(hero.getPosition()));

		// if hero sent eagle
		if (hero.hasJustSentEagle()) {
			startFlight(lab, sword);
			hero.resetHasJustSentEagleValue();
		}

		// checking if hero caught eagle
		if (!hero.hasEagle() && !isFlying() && !isDead()
				&& hero.getPosition().getX() == getPosition().getX()
				&& hero.getPosition().getY() == getPosition().getY())
			hero.catchEagle(this);
	}

	public String drawToString() {
		if (flying || !withHero) {
			if (hasSword)
				return "BE";
			else
				return "B ";
		}

		return "";
	}

	public boolean isFlying() {
		return flying;
	}

	public boolean isWithHero() {
		return withHero;
	}

	public void setWithHero(boolean withHero) {
		this.withHero = withHero;
	}

	public boolean hasSword() {
		return hasSword;
	}

	public void setHasSword(boolean hasSword) {
		this.hasSword = hasSword;
	}

	public boolean isCatchingSword() {
		return pathStep == path.size() - 1;
	}
}
