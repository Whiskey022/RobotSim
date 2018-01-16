package uk.ac.reading.cs2ja16.viskantasjuodenas.robotManager;

import java.util.Random;

/**
 * RobotFour class, occasionally changes direction at a random time
 *
 */
public class RobotFour extends Robot {

	/**
	 * Robot constructor, sets up his location, direction, robotArena, Id, and
	 * imageIndex
	 * 
	 * @param x
	 *            x coordinate
	 * @param y
	 *            y coordinate
	 * @param direction
	 *            robot's initial direction
	 * @param robotArena
	 *            robotArena the robot belongs to
	 */
	public RobotFour(int x, int y, Direction direction, RobotArena robotArena) {
		this.initialX = x;
		this.initialY = y;
		this.x = x;
		this.y = y;
		this.direction = direction;
		this.robotArena = robotArena;
		this.image = new ArenaImage().getRobotImage(3);
		countId();
		type = "RobotFour";
	}

	// Function to move
	@Override
	public boolean tryToMove() {
		//If below charge don't move
		if (charge > 0) {
			charge--;

			// Sometimes randomly change movement direction
			if (new Random().nextInt(5) == 1) {
				direction = Direction.getRandomDirection();
			}

			// Set next coordinates
			int[] nextCoord = move();
			int nextX = nextCoord[0], nextY = nextCoord[1];

			// If robot can move there, set next coordinates to current
			if (robotArena.canMoveHere(nextX, nextY)) {
				oldX = x;
				oldY = y;
				x = nextX;
				y = nextY;
				didMove = true;
				return true;
			} else { // Otherwise, set next direction
				direction = direction.getNextDirection();
			}
		}
		didMove = false;
		return false;
	}
}
