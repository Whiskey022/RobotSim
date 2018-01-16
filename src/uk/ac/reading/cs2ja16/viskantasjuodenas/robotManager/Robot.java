package uk.ac.reading.cs2ja16.viskantasjuodenas.robotManager;

/**
 *
 * Class for Robot object
 */
public abstract class Robot extends ArenaObject {

	protected int initialX, initialY; // To keep starting positions
	protected int oldX, oldY;
	protected static int robotsCount = 0; // Robots counter, to help calculate IDs
	protected Direction direction;
	protected int defaultCharge = 50;
	protected int charge = defaultCharge;
	protected RobotArena robotArena;
	protected boolean didMove = false;

	@Override
	public boolean tryToMove() {
		// Move only if it has charge left
		if (charge > 0) {
			// Decrease charge
			charge--;

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

	/**
	 * Function to move robot according to its direction
	 * 
	 * @return x and y coordinates
	 */
	protected int[] move() {
		int nextX, nextY;
		switch (direction) {
		case NORTH:
			nextY = y - 1;
			return new int[] { x, nextY };
		case EAST:
			nextX = x + 1;
			return new int[] { nextX, y };
		case SOUTH:
			nextY = y + 1;
			return new int[] { x, nextY };
		case WEST:
			nextX = x - 1;
			return new int[] { nextX, y };
		default:
			return new int[] { x, y };
		}
	}

	/**
	 * reset charge to robot's default
	 */
	public void resetCharge() {
		charge = defaultCharge;
	}

	/**
	 * increase charge by robot's default
	 */
	public void increaseCharge() {
		charge += defaultCharge;
	}

	@Override
	public boolean isRobot() {
		return true;
	}

	/**
	 * set charge
	 * @param charge
	 */
	public void setCharge(int charge) {
		this.charge = charge;
	}

	/**
	 * 
	 * @return previous x coordinate
	 */
	public int getOldX() {
		return oldX;
	}

	/**
	 * 
	 * @return previous y coordinate
	 */
	public int getOldY() {
		return oldY;
	}

	/**
	 * 
	 * @return direction
	 */
	public Direction getDirection() {
		return direction;
	}

	@Override
	public boolean getDidMove() {
		return didMove;
	}

	/**
	 * 
	 * @return charge level
	 */
	public int getChargeLevel() {
		return charge;
	}
}
