package game;

public class Hero extends LivingBeing {
	private boolean hasSword = false;

	public Hero(String name) {
		this.name = name;
	}

	public Hero(String name, int x, int y) {
		this.name = name;
		this.position = new Coord(x, y);
	}

	public void print() {
		if (hasSword)
			System.out.print("A ");
		else
			System.out.print("H ");
	}

	public boolean hasSword() {
		return hasSword;
	}

	public void arm() {
		hasSword = true;
		System.out.println("Hero caught the sword.");
	}

	public boolean isOn(int x, int y) {
		return (x == position.getX()) && (y == position.getY());
	}

	public void move(String dir, Labyrinth lab) {
		switch (dir) {
		case "w":
			if (lab.heroCanWalkTo(position.getX(), position.getY() - 1, this))
				position.setY(position.getY() - 1);
			else
				System.out.println("Jogada Inválida.");
			break;
		case "s":
			if (lab.heroCanWalkTo(position.getX(), position.getY() + 1, this))
				position.setY(position.getY() + 1);
			else
				System.out.println("Jogada Inválida.");
			break;
		case "a":
			if (lab.heroCanWalkTo(position.getX() - 1, position.getY(), this))
				position.setX(position.getX() - 1);
			else
				System.out.println("Jogada Inválida.");
			break;
		case "d":
			if (lab.heroCanWalkTo(position.getX() + 1, position.getY(), this))
				position.setX(position.getX() + 1);
			else
				System.out.println("Jogada Inválida.");
			break;
		}
	}
}
