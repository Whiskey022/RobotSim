package uk.ac.reading.cs2ja16.viskantasjuodenas.robotManager;

public class RobotThree extends Robot{
	/**
	 * Robot constructor, sets up his location, direction, robotArena, Id, and imageIndex
	 * @param	x	x coordinate
	 * @param	y	y coordinate
	 * @param	direction	robot's initial direction
	 * @param	robotArena	robotArena the robot belongs to	
	 * @param	imageIndex	robot's image index, stored to have a consistent image
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
	}
	
	//Function to move
		@Override
		public boolean tryToMove() {
			if (charge > 0) {
				for (int i=0; i<8; i++) {
					//Set next coordinates
					int nextX = x, nextY = y;
					switch (direction) {
					case NORTH:
						nextY--;
						break;
					case EAST:
						nextX++;
						break;
					case SOUTH:
						nextY++;
						break;
					case WEST:
						nextX--;
						break;
					}
					//If robot can move there, set next coordinates to current
					if (robotArena.canMoveHere(nextX, nextY)) {
						charge--;
						oldX = x;
						oldY = y;
						x = nextX;
						y = nextY;
						didMove = true;
						return true;
					}
					//First 4 attempts try to get random direction
					if (i < 4) {
						direction = Direction.getRandomDirection();
					} else {
						direction = direction.getNextDirection();
					}
				}
			}
			charge--;
			didMove = false;
			return false;
		}
}
