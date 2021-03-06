package uk.ac.reading.cs2ja16.viskantasjuodenas.robotManager;

/**
 * RobotThree, changes direction to a random one
 *
 */
public class RobotThree extends Robot{

	/**
	 * Robot constructor, sets up his location, direction, robotArena, Id, and imageIndex
	 * @param	x	x coordinate
	 * @param	y	y coordinate
	 * @param	direction	robot's initial direction
	 * @param	robotArena	robotArena the robot belongs to	
	 */
	public RobotThree(int x, int y, Direction direction, RobotArena robotArena) {
		this.initialX = x;
		this.initialY = y;
		this.x = x;
		this.y = y;
		this.direction = direction;
		this.robotArena = robotArena;
		this.image = new ArenaImage().getRobotImage(2);
		countId();
		type = "RobotThree";
	}
	
	//Function to move
	@Override
	public boolean tryToMove() {
		//Move if charged
		if (charge > 0) {
			//Try 8 times to move/ change direction
			for (int i = 0; i < 8; i++) {
				
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
				// First 4 attempts try to get random direction
				if (i < 4) {
					direction = Direction.getRandomDirection();
				}
				//Otherwise also try next directions
				else {
					direction = direction.getNextDirection();
				}
			}
		}
		charge--;
		didMove = false;
		return false;
	}
}
