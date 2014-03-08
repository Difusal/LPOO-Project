package game.logic;

public enum Direction {
	RIGHT(2), DOWN(0), LEFT(1), UP(3), NONE(4);
	
	private final int value;
	
	private Direction(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
