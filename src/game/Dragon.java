package game;

public class Dragon extends LivingBeing {
	public Dragon() {
		this.position = new Coord(1, 3);
	}

	public Dragon(int x, int y) {
		this.position = new Coord(x, y);
	}
	
	public void print() {
		System.out.print("D ");
	}
}
