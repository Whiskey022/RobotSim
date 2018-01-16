package uk.ac.reading.cs2ja16.viskantasjuodenas.robotManager;

import java.util.Random;

/**
 * 
 *	Enum for robot directions
 */
public enum Direction {

	NORTH, EAST, SOUTH, WEST;

	/**
	 * 
	 * @return random direction
	 */
	public static Direction getRandomDirection() {
		Random random = new Random();
		return values()[random.nextInt(values().length)];
	}

	/**
	 * 
	 * @return next direction 
	 */
	public Direction getNextDirection() {
		return values()[(this.ordinal() + 1) % values().length];
	}

}
