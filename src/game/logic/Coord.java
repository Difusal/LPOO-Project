package game.logic;

public class Coord {
	private int x, y;

	public Coord() {
		x = 0;
		y = 0;
	}

	public Coord(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Coord(Coord position) {
		x = position.getX();
		y = position.getY();
	}

	public boolean equals(Object other) {
		if (!(other instanceof Coord))
			return false;

		Coord that = (Coord) other;

		// Custom equality check here.
		return this.x == that.x && this.y == that.y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
}
