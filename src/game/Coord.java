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
	
	int getX() { return x; }
	int getY() { return y; }
}
