package game.logic;

public abstract class LivingBeing {
	protected String name;
	protected Type type;
	protected Coord position = new Coord();
	protected int life = 100;
	protected boolean isSleeping = false;
	protected Direction facingDir = Direction.DOWN;
	protected int frames, currentFrame;

	public enum Type {
		HERO, EAGLE, DRAGON
	}

	public abstract void move(Labyrinth lab, Direction direction);

	public abstract String drawToString();

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

	public int distanceTo(LivingBeing livingBeing) {
		int dx = Math.abs(getPosition().getX()
				- livingBeing.getPosition().getX());
		int dy = Math.abs(getPosition().getY()
				- livingBeing.getPosition().getY());

		return (int) Math.round(Math.sqrt(dx * dx + dy * dy));
	}

	public boolean isDead() {
		return life == 0;
	}

	public void kill() {
		this.life = 0;
	}

	public void setLife(int life) {
		this.life = life;
	}

	public boolean isSleeping() {
		return isSleeping;
	}

	public void sleep() {
		isSleeping = true;
	}

	public void wakeUp() {
		isSleeping = false;
	}

	public int getFrames() {
		return frames;
	}

	public void setFrames(int frames) {
		this.frames = frames;
	}

	public int getCurrentFrame() {
		return currentFrame;
	}

	public void setCurrentFrame(int currentFrame) {
		this.currentFrame = currentFrame;
	}

	public void nextFrame() {
		this.currentFrame++;

		if (this.currentFrame >= this.frames)
			this.currentFrame = 0;
	}

	public Direction getFacingDir() {
		return facingDir;
	}

	public void setFacingDir(Direction facingDir) {
		this.facingDir = facingDir;
	}
}