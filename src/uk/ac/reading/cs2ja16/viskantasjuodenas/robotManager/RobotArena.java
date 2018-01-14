package uk.ac.reading.cs2ja16.viskantasjuodenas.robotManager;

import java.util.ArrayList;
import java.util.Random;

public class RobotArena {

	private int x, y;
	private ArrayList<ArenaObject> objects;
	private String status = "stop";

	/**
	 * RobotArena constructor
	 * 
	 * @param x
	 *            arena width
	 * @param y
	 *            arena length
	 */
	public RobotArena(int x, int y) {
		this.x = x;
		this.y = y;
		objects = new ArrayList<ArenaObject>();
	}

	/**
	 * Adds a new robot at random location with random image index
	 * 
	 * @param robotImagesCount
	 *            Provide the count of how many robot images there are available to
	 *            calculate a random image index
	 */
	public String addRobot() {

		// Initialise some values for adding a robot
		Random rand = new Random();
		int x, y;

		// Loop to find a position for a robot, and add him to arena
		// Attempts a certain count of times, so it doesn't get stuck on the loop
		for (int j = 0; j < this.x * this.y * 100; j++) {
			// Create random coordinates
			x = rand.nextInt(this.x) + 1;
			y = rand.nextInt(this.y) + 1;

			// If position not taken, add robot there
			if (!objectIsHere(x, y)) {
				objects.add(RobotType.getRobotObject(x, y, Direction.getRandomDirection(), RobotType.getRandom(), this));
				return "success";
			}
		}
		return "Failed to add a robot";
	}

	public String addRobot(int x, int y, Direction direction, String robotType) {
		// If position not taken, add robot there
		if (!objectIsHere(x, y)) {
			// Check if x value valid, change if necessary
			if (x > this.x) {
				x = this.x;
			} else if (x < 1) {
				x = 1;
			}
			// Check if y value valid, change if necessary
			if (y > this.y) {
				y = this.y;
			} else if (y < 1) {
				y = 1;
			}
			objects.add(RobotType.getRobotObject(x, y, direction, robotType, this));
			return "success";
		}
		System.out.println("ERROR: position already taken");
		return "Position already taken";
	}

	public String addItem() {
		// Initialise some values for adding a robot
		Random rand = new Random();
		int x, y;

		// Loop to find a position for an object, and add it to arena
		// Attempts a certain count of times, so it doesn't get stuck on the loop
		for (int j = 0; j < this.x * this.y * 10; j++) {
			// Create random coordinates
			x = rand.nextInt(this.x) + 1;
			y = rand.nextInt(this.y) + 1;

			// If position not taken, add robot there
			if (!objectIsHere(x, y)) {
				objects.add(randomItem(x, y));
				return "success";
			}
		}
		return "Failed to add a robot";
	}

	public String addItem(int y, int x, String type) {
		// If position not taken, add robot there
		if (!objectIsHere(x, y)) {
			// Check if x value valid, change if necessary
			if (x > this.x) {
				x = this.x;
			} else if (x < 1) {
				x = 1;
			}
			// Check if y value valid, change if necessary
			if (y > this.y) {
				y = this.y;
			} else if (y < 1) {
				y = 1;
			}
			objects.add(newItem(x, y, type));
			return "success";
		}
		System.out.println("ERROR: position already taken");
		return "Position already taken";
	}

	public String toString() {
		String res = "";

		for (int i = 0; i < ArenaObject.getObjectsCount(); i++) {
			res += "\n";
		}

		return res;
	}

	// Function to check if robot can move into position
	public boolean canMoveHere(int x, int y) {
		// Check if its a wall in front
		if (x > this.x || y > this.y || x <= 0 || y <= 0) {
			return false;
			// Check if a robot is in front
		} else if (objectIsBlocking(x, y)) {
			return false;
		}
		return true;
	}

	// Function to check if robot is at position
	private boolean objectIsHere(int x, int y) {
		for (int i = 0; i < ArenaObject.getObjectsCount(); i++) {
			if (objects.get(i).isHere(x, y)) {
				return true;
			}
		}
		return false;
	}

	// Function to check if robot is at position
	private boolean objectIsBlocking(int x, int y) {
		for (int i = 0; i < ArenaObject.getObjectsCount(); i++) {
			ArenaObject object = objects.get(i);
			if (object.isHere(x, y) && (object.isRobot() || object.isWall())) {
				return true;
			}
		}
		return false;
	}

	// Try to move every robot
	public void moveAllRobots() {
		checkCollisions();
		int countOfRobotsMoved = 0, timesTried= 0;
		while (countOfRobotsMoved == 0 && timesTried < 10) {
			for (int i = 0; i < ArenaObject.getObjectsCount(); i++) {
				if (objects.get(i).isRobot() && objects.get(i).tryToMove()) {
					countOfRobotsMoved++;
				}
			}
			timesTried++;
		}
	}
	
	private void checkCollisions(){
		for (int i = 0; i < ArenaObject.getObjectsCount(); i++) {
			for (int j=i+1; j < ArenaObject.getObjectsCount(); j++) {
				ArenaObject obj1 = objects.get(i);
				ArenaObject obj2 = objects.get(j);
				if (obj1.isHere(obj2.getX(), obj2.getY())) {
					if (obj1.isRobot() && obj2.isCharger()) {
						obj1.increaseCharge();
					}
				}
			}
		}
	}

	private ArenaObject randomItem(int x, int y) {
		return ItemType.getItemObject(x, y, ItemType.getRandom(), this);
	}

	private ArenaObject newItem(int x, int y, String type) {
		switch (type) {
		case "wall":
			return new Wall(x, y, this);
		default:
			return randomItem(x, y);
		}
	}

	public void setXSize(int x) {
		this.x = x;
	}

	public void setYSize(int y) {
		this.y = y;
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

	public ArrayList<ArenaObject> getObjects() {
		return objects;
	}

	public int getObjectCount() {
		return ArenaObject.getObjectsCount();
	}

	public String getStatus() {
		return status;
	}

	// Convert all arena details to a string for saving into file
	public String getDetails() {
		String details = "";

		// Arena details
		details += x + ":" + y + "|";

		// Robots details
		for (int i = 0; i < ArenaObject.getObjectsCount(); i++) {
			details += objects.get(i).getId() + ":" + objects.get(i).getX() + ":" + objects.get(i).getY() + "|";
		}

		return details;
	}
}
