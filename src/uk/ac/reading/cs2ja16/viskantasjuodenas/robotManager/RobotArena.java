package uk.ac.reading.cs2ja16.viskantasjuodenas.robotManager;

import java.util.Random;
import java.util.Scanner;

public class RobotArena {

	private int x, y;
	private int maxRobots;
	private Robot[] robots;
	private int robotsCounter = 0;

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

	public void addRobot() {
		if (robotsCounter >= maxRobots) {
			System.out.println("ERROR: robots count has already reached max capacity");
		} else {
			// Initialise some values for adding a robot
			Random rand = new Random();
			int rx, ry;
			boolean robotAdded = false;

			// Loop to find a position for a robot, and add him to arena
			while (!robotAdded) {
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
					robots[robotsCounter] = new Robot(rx, ry, Direction.getRandomDirection(), this);
					robotAdded = true;
					robotsCounter++;
				}
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

	public void showRobots(RobotInterface r) {
		for (int i = 0; i < robotsCounter; i++) {
			robots[i].displayRobot(r);
		}
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
		for (int i = 0; i < robotsCounter; i++) {
			robots[i].tryToMove();
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

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);
		System.out.println("Provide arena's size and robots amount limit (as x y limit) >");

		int width = scanner.nextInt();
		int height = scanner.nextInt();
		int maxRobots = scanner.nextInt();

		RobotArena robotArena = new RobotArena(width, height, maxRobots);

		System.out.println("How many robots to add? >");

		int count = scanner.nextInt();

		for (int i = 0; i < count; i++) {
			robotArena.addRobot();
		}

		System.out.println(robotArena.toString());

		scanner.close();
	}
}
