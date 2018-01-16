package uk.ac.reading.cs2ja16.viskantasjuodenas.robotManager;

/**
 *	RobotSeven, moves towards light if detected
 *
 */
public class RobotSeven extends Robot {

	//Coordinates for which robot can monitor for light
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
	 */
	public RobotSeven(int x, int y, Direction direction, RobotArena robotArena) {
		this.initialX = x;
		this.initialY = y;
		this.x = x;
		this.y = y;
		this.direction = direction;
		this.robotArena = robotArena;
		this.image = new ArenaImage().getRobotImage(6);
		countId();
		type = "RobotEight";
	}

	// Function to move
	@Override
	public boolean tryToMove() {
		//If below charge, don't move
		if (charge > 0) {
			//Set coordinates to check for light
			setViews();
			
			//Try to detect light
			int lightDetectedIndex = lightDetected();
			//If light detected
			if (lightDetectedIndex != -1) {
				//Try to steer towards light
				direction = directionTowardsLight(lightDetectedIndex);
			}
			
			//Attempt 4 times to move/change direction
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
				//else change direction
				direction = direction.getNextDirection();
			}
		}
		charge--;
		didMove = false;
		return false;
	}

	/**
	 * Check if light is detected
	 * @return	index of which view it got detected under
	 */
	private int lightDetected() {
		int index = 0;
		for (int[] viewToCheck : getView()) {
			if (lightIsHere(viewToCheck[0], viewToCheck[1])) {
				return index;
			}
			index++;
		}
		return -1;		
	}

	/**
	 * Check if light is on location
	 * @param x
	 * @param y
	 * @return	true if light found
	 */
	private boolean lightIsHere(int x, int y) {
		for (ArenaObject object : robotArena.getObjects()) {
			if (object.isLight() && object.isHere(x, y)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Try to steer towards light
	 * @param index
	 * @return	direction of robot's next move
	 */
	private Direction directionTowardsLight(int index) {
		//If light one move away, move onto it
		if (index < 3) {
			if (index == 0)
				return direction.getNextDirection().getNextDirection().getNextDirection();
			if (index == 1)
				return direction;
			if (index == 2)
				return direction.getNextDirection();
		}
		//If light is 2 moves away on its left and not blocked by anything
		else if (index == 3 && !viewBlocked(3)){
			return direction.getNextDirection().getNextDirection().getNextDirection();
		}
		//If light is 2 moves away on its right and not blocked by anything
		else if (index == 4 && !viewBlocked(4)) {
			return direction.getNextDirection();
		}
		return direction;
	}
	
	/**
	 * Check if view is blocked by an object
	 * @param index
	 * @return true if view is blocked
	 */
	private boolean viewBlocked(int index) {
		//Which view to check for light
		int viewIndexToCheck;
		//If light found on the left 
		if (index == 3) {
			viewIndexToCheck = 0;
		}
		//Else if light found on the right
		else {
			viewIndexToCheck = 2;
		}
		//Check if that location is blocked
		for (ArenaObject object : robotArena.getObjects()) {
			if ((object.isRobot() || object.isWall()) && 
					object.isHere(getView()[viewIndexToCheck][0], getView()[viewIndexToCheck][1])) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Set views the robot can monitor for light objects
	 */
	private void setViews() {
		northView = new int[][] {
			{x-1, y},
			{x, y-1},
			{x+1, y},
			{x-2, y},
			{x+2, y}};
		eastView = new int[][] {
			{x, y-1},
			{x+1, y},
			{x, y+1},
			{x, y-2},
			{x, y+2}};
		southView = new int[][] {
			{x+1, y},
			{x, y+1},
			{x-1, y},
			{x+2, y},
			{x-2, y}};
		westView = new int[][] {
			{x, y+1},
			{x-1, y},
			{x, y-1},
			{x, y+2},
			{x, y-2}};
	}
	
	/**
	 * get current view being monitored
	 * @return view robot is monitoring
	 */
	private int[][] getView() {
		switch(direction) {
		case NORTH:
			return northView;
		case EAST:
			return eastView;
		case SOUTH:
			return southView;
		default:
			return westView;
		}
	}
}
