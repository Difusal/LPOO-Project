package game;

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

	int getX() {
		return x;
	}

	int getY() {
		return y;
	}

	void setX(int x) {
		this.x = x;
	}

	void setY(int y) {
		this.y = y;
	}
}
