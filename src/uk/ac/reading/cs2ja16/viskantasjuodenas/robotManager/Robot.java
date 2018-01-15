package uk.ac.reading.cs2ja16.viskantasjuodenas.robotManager;

public abstract class Robot extends ArenaObject {

	protected int initialX, initialY; // To keep starting positions
	protected int oldX, oldY;
	protected static int robotsCount = 0; // Robots counter, to help calculate IDs
	protected Direction direction;
	protected int defaultCharge = 50;
	protected int charge = defaultCharge;
	protected RobotArena robotArena;
	protected boolean didMove = false;

	// Function to move
	@Override
	public boolean tryToMove() {
		if (charge > 0) {
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
	
	protected int[] move() {
		int nextX, nextY;
		switch (direction) {
		case NORTH:
			nextY = y - 1;
			return new int[] {x, nextY};
		case EAST:
			nextX = x + 1;
			return new int[] {nextX, y};
		case SOUTH:
			nextY = y + 1;
			return new int[] {x, nextY};
		case WEST:
			nextX = x - 1;
			return new int[] {nextX, y};
		default:
			return new int[] {x, y};
		}
	}
	
	public void resetCharge() {
		charge = defaultCharge;
	}
	
	public void increaseCharge() {
		charge += defaultCharge;
	}

	@Override
	public boolean isRobot() {
		return true;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setId(int robotId) {
		this.id = robotId;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	
	public void setCharge(int charge) {
		this.charge = charge;
	}

	public void setRobotArena(RobotArena robotArena) {
		this.robotArena = robotArena;
	}

	public static void setRobotsCount(int count) {
		robotsCount = count;
	}

	public int getOldX() {
		return oldX;
	}

	public int getOldY() {
		return oldY;
	}

	public Direction getDirection() {
		return direction;
	}

	public RobotArena getRobotArena() {
		return robotArena;
	}

	public boolean getDidMove() {
		return didMove;
	}
	
	public int getChargeLevel() {
		return charge;
	}
	
	public boolean isRobotEight() {
		return false;
	}
}
