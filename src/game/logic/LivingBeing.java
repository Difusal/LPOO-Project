package game.logic;

public abstract class LivingBeing {
	protected String name;
	protected Type type;
	protected Coord position = new Coord();
	protected int life = 100;
	protected boolean isSleeping = false;

	public abstract void move(Labyrinth lab);

	public abstract void draw();

	public enum Type {
		HERO, EAGLE, DRAGON
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public boolean isOn(int x, int y) {
		return (x == getPosition().getX()) && (y == getPosition().getY());
	}

	public Coord getPosition() {
		return position;
	}

	public void setPosition(Coord position) {
		this.position = position;
	}

	public boolean isDead() {
		return life == 0;
	}

	public void setLife(int life) {
		this.life = life;
	}

	public boolean isSleeping() {
		return isSleeping;
	}
}