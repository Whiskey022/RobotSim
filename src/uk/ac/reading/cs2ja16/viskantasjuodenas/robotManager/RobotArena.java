package uk.ac.reading.cs2ja16.viskantasjuodenas.robotManager;

import java.util.Random;

import javafx.scene.image.Image;

public class RobotArena {

	private int x, y;
	private int maxRobots;
	private Robot[] robots;
	private int robotsCounter = 0;
	private String status = "stop";

	/**
	 * RobotArena constructor
	 * 
	 * @param 	x	arena width
	 * @param 	y	arena length
	 * @param	maxRobots	robots count limit
	 */
	public RobotArena(int x, int y, int maxRobots) {
		//If maxRobots is higher than arena can fit, throw error
		if (x * y < maxRobots) {
			System.out.println("ERROR: robots limit bigger than the size of arena");
		} else {
			this.x = x;
			this.y = y;
			this.maxRobots = maxRobots;
			robots = new Robot[maxRobots];
		}
	}

	/**
	 * Adds a new robot at random location with random image index
	 * @param	robotImagesCount	Provide the count of how many robot images there are available to calculate a random image index
	 */
	public String addRobot() {
		if (robotsCounter >= maxRobots) {
			System.out.println("ERROR: robots count has already reached max capacity");
			return "Robot count already reached max capacity";
		} else {
			// Initialise some values for adding a robot
			Random rand = new Random();
			int rx, ry;

			// Loop to find a position for a robot, and add him to arena
			// Attempts a certain count of times, so it doesn't get stuck on the loop
			for (int j=0; j<x*y*10; j++) {
				// Create random coordinates
				rx = rand.nextInt(x) + 1;
				ry = rand.nextInt(y) + 1;
				boolean posTaken = false;

				// Loop to check if position taken
				for (int i = 0; i < robotsCounter; i++) {
					if (robots[i].getX() == rx && robots[i].getY() == ry) {
						posTaken = true;
						break;
					}
				}

				// If position not taken, add robot there
				if (!posTaken) {
					robots[robotsCounter] = new Robot(rx, ry, Direction.getRandomDirection(), this, new RobotImages().getRandomImage());
					robotsCounter++;
					return "success";
				}
			}
			return "Failed to add a robot";
		}
	}
	
	public String addRobot(int x, int y, Direction direction, Image image) {
		if (robotsCounter >= maxRobots) {
			System.out.println("ERROR: robots count has already reached max capacity");
			return "Robot count already reached max capacity";
		} else {
			
			boolean posTaken = false;

			// Loop to check if position taken
			for (int i = 0; i < robotsCounter; i++) {
				if (robots[i].getX() == x && robots[i].getY() == y) {
					posTaken = true;
					break;
				}
			}

			// If position not taken, add robot there
			if (!posTaken) {
				//Check if x value valid, change if necessary
				if (x > this.x) {
					x = this.x;
				} else if (x < 1) {
					x = 1;
				}
				//Check if y value valid, change if necessary
				if (y > this.y) {
					y = this.y;
				} else if (y < 1) {
					y = 1;
				}
				robots[robotsCounter] = new Robot(x, y, direction, this, image);
				robotsCounter++;
				return "success";
			} else {
				System.out.println("ERROR: position already taken");
				return "Position already taken";
			}
		}
	}

	public String toString() {
		String res = "";

		for (int i = 0; i < robotsCounter; i++) {
			res += robots[i].toString() + "\n";
		}

		return res;
	}

	//Function to check if robot can mvove into position
	public boolean canMoveHere(int x, int y) {
		//Check if its a wall in front
		if (x > this.x || y > this.y || x <= 0 || y <= 0) {
			return false;
		//Check if a robot is in front
		} else if (robotIsHere(x, y)) {
			return false;
		}
		return true;
	}

	//Function to check if robot is at position
	private boolean robotIsHere(int x, int y) {
		for (int i = 0; i < robotsCounter; i++) {
			if (robots[i].isHere(x, y)) {
				return true;
			}
		}
		return false;
	}

	//Try to move every robot
	public void moveAllRobots() {
		int countOfRobotsMoved = 0;
		while (countOfRobotsMoved == 0) {
			for (int i = 0; i < robotsCounter; i++) {
				if (robots[i].tryToMove()) {
					countOfRobotsMoved++;
				}
			}
		}
	}
	
	public void setXSize(int x) {
		this.x = x;
	}

	public void setYSize(int y) {
		this.y = y;
	}
	
	public void setMaxRobots(int maxRobots) {
		this.maxRobots = maxRobots;
	}
	
	public void setRobots(Robot[] robots) {
		this.robots = robots;
	}

	public void setRobotsCounter(int robotsCounter) {
		this.robotsCounter = robotsCounter;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public int getXSize() {
		return x;
	}

	public int getYSize() {
		return y;
	}
	
	public int getMaxRobots() {
		return maxRobots;
	}
	
	public Robot[] getRobots() {
		return robots;
	}

	public int getRobotsCounter() {
		return robotsCounter;
	}
	
	public String getStatus() {
		return status;
	}
	
	//Convert all arena details to a string for saving into file
	public String getDetails() {
		String details = "";
		
		//Arena details
		details += x + ":" + y + ":" + maxRobots + "|";
		
		
		//Robots details
		for (int i=0 ;i<robotsCounter; i++) {
			details += robots[i].getId() + ":" + robots[i].getX() + ":"
					+ robots[i].getY() + ":" + robots[i].getDirection().toString() + "|";
		}
		
		return details;
	}
}
