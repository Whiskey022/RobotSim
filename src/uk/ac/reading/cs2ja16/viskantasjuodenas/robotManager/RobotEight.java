package uk.ac.reading.cs2ja16.viskantasjuodenas.robotManager;

public class RobotEight extends Robot {

	private int detectedLightX = -1;
	private int detectedLightY = -1;
	private int[][] northView;
	private int[][] eastView;
	private int[][] southView;
	private int[][] westView;
	
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
	 * @param imageIndex
	 *            robot's image index, stored to have a consistent image
	 */
	public RobotEight(int x, int y, Direction direction, RobotArena robotArena) {
		this.initialX = x;
		this.initialY = y;
		this.x = x;
		this.y = y;
		this.direction = direction;
		this.robotArena = robotArena;
		this.image = new ArenaImage().getRobotImage(7);
		countId();
	}

	// Function to move
	@Override
	public boolean tryToMove() {
		if (charge > 0) {
			setViews();
			int lightDetectedIndex = lightDetected();
			if (lightDetectedIndex != -1) {
				direction = moveTowardsLight(lightDetectedIndex);
			}
			for (int i = 0; i < 4; i++) {
				
				// Set next coordinates
				int[] nextCoord = move();
				int nextX = nextCoord[0], nextY = nextCoord[1];
				
				// If robot can move there, set next coordinates to current
				if (robotArena.canMoveHere(nextX, nextY)) {
					charge--;
					oldX = x;
					oldY = y;
					x = nextX;
					y = nextY;
					didMove = true;
					return true;
				}
				direction = direction.getNextDirection();
			}
		}
		charge--;
		didMove = false;
		return false;
	}

	private int lightDetected() {
		int index;
		switch (direction) {
		case NORTH:
			index = 0;
			for (int[] viewToCheck : northView) {
				if (lightIsHere(viewToCheck[0], viewToCheck[1])) {
					return index;
				}
				index++;
			}
		case EAST:
			index = 0;
			for (int[] viewToCheck : eastView) {
				if (lightIsHere(viewToCheck[0], viewToCheck[1])) {
					return index;
				}
				index++;
			}
		case SOUTH:
			index = 0;
			for (int[] viewToCheck : southView) {
				if (lightIsHere(viewToCheck[0], viewToCheck[1])) {
					return index;
				}
				index++;
			}
		case WEST:
			index = 0;
			for (int[] viewToCheck : westView) {
				if (lightIsHere(viewToCheck[0], viewToCheck[1])) {
					return index;
				}
				index++;
			}
		default:
			return -1;
		}
		
	}

	private boolean lightIsHere(int x, int y) {
		for (ArenaObject object : robotArena.getObjects()) {
			if (object.isLight() && object.isHere(x, y)) {
				detectedLightX = x;
				detectedLightY = y;
				return true;
			}
		}
		return false;
	}
	
	private Direction moveTowardsLight(int index) {
		if (index < 3) {
			if (index == 0)
				return direction.getNextDirection().getNextDirection().getNextDirection();
			if (index == 1)
				return direction;
			if (index == 2)
				return direction.getNextDirection();
		}
		if (index < 6) {
			
		}
		return direction;
	}
	
	private void setViews() {
		northView = new int[][] {
			{x-1, y},
			{x, y-1},
			{x+1, y},
			{x-1, y-1},
			{x, y-2},
			{x+1, y-1},
			{x-1, y-2},
			{x+1, y-2}};
		eastView = new int[][] {
			{x, y-1},
			{x+1, y},
			{x, y+1},
			{x+1, y-1},
			{x+2, y},
			{x+1, y+1},
			{x+2, y-1},
			{x+2, y+1}};
		southView = new int[][] {
			{x+1, y},
			{x, y+1},
			{x-1, y},
			{x+1, y+1},
			{x, y+2},
			{x-1, y+1},
			{x+1, y+2},
			{x-1, y+2}};
		westView = new int[][] {
			{x, y+1},
			{x-1, y},
			{x, y-1},
			{x-1, y+1},
			{x-2, y},
			{x-1, y-1},
			{x-2, y+1},
			{x-2, y-1}};
	}
}
