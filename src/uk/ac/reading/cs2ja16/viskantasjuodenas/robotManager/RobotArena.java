package uk.ac.reading.cs2ja16.viskantasjuodenas.robotManager;

import java.util.ArrayList;
import java.util.Random;

public class RobotArena {

	private int x, y;
	private ArrayList<ArenaObject> objects;
	private String status;
	private String message = "";
	private Boolean goodMessage = true;
	private double speed = 0.02;

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
		status = "not-drawn";
		ArenaObject.setObjectCount(0);
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
			x = rand.nextInt(this.x);
			y = rand.nextInt(this.y);

			// If position not taken, add robot there
			if (!objectIsHere(x, y)) {
				objects.add(
						RobotType.getRobotObject(x, y, Direction.getRandomDirection(), RobotType.getRandom(), this));
				return "success";
			}
		}
		return "Failed to add a robot";
	}

	public String addRobot(int x, int y, Direction direction, String robotType) {

		x = checkIfValidX(x);
		y = checkIfValidY(y);

		// If position not taken, add robot there
		if (!objectIsHere(x, y)) {
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
			x = rand.nextInt(this.x);
			y = rand.nextInt(this.y);

			// If position not taken, add robot there
			if (!objectIsHere(x, y)) {
				objects.add(ItemType.getItemObject(x, y, ItemType.getRandom(), this));
				return "success";
			}
		}
		return "Failed to add a robot";
	}

	public String addItem(int x, int y, String type) {

		x = checkIfValidX(x);
		y = checkIfValidY(y);

		// If position not taken, add robot there
		if (!objectIsHere(x, y)) {
			objects.add(ItemType.getItemObject(x, y, type, this));
			return "success";
		}
		System.out.println("ERROR: position already taken");
		return "Position already taken";
	}

	public void removeObject(ArenaObject obj) {
		objects.remove(objects.indexOf(obj));
		ArenaObject.setObjectCount(ArenaObject.getObjectsCount() - 1);
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
		if (x >= this.x || y >= this.y || x < 0 || y < 0) {
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
		int countOfRobotsMoved = 0, timesTried = 0;
		while (countOfRobotsMoved == 0 && timesTried < 10) {
			for (ArenaObject object : objects) {
				if (object.isRobot()) {
					if (object.tryToMove()) {
						countOfRobotsMoved++;
					} else if (((Robot) object).getChargeLevel() < 1) {
						message = "Robot ran out of juice at x: " + object.getX() + ", y: " + object.getY();
						goodMessage = false;
					}
				}

			}
			timesTried++;
		}
	}

	public boolean checkCollisions() {
		boolean collisionsFound = false;
		for (int i = 0; i < ArenaObject.getObjectsCount(); i++) {
			for (int j = i + 1; j < ArenaObject.getObjectsCount(); j++) {
				ArenaObject obj1 = objects.get(i);
				ArenaObject obj2 = objects.get(j);
				if (obj1.isHere(obj2.getX(), obj2.getY())) {
					if (obj1.isRobot() && obj2.isCharger()) {
						obj1.increaseCharge();
						message = "Robot charged at x: " + obj1.getX() + ", y: " + obj1.getY();
						goodMessage = true;
						removeObject(obj2);
					} else if (obj2.isRobot() && obj1.isCharger()) {
						obj2.increaseCharge();
						message = "Robot charged at x: " + obj2.getX() + ", y: " + obj2.getY();
						goodMessage = true;
						removeObject(obj1);
					} else if ((obj1.isRobot() && obj2.isTrap()) || (obj2.isRobot() && obj1.isTrap())) {
						message = "Robot removed by trap at x: " + obj1.getX() + ", y: " + obj1.getY();
						goodMessage = false;
						removeObject(obj1);
						removeObject(obj2);
					} else if ((obj1.getClass() == RobotEight.class) && obj2.isLight()) {
						message = "Robot removed light at x: " + obj1.getX() + ", y: " + obj1.getY();
						goodMessage = true;
						removeObject(obj2);
					} else if ((obj2.getClass() == RobotEight.class) && obj1.isLight()) {
						message = "Robot removed light at x: " + obj1.getX() + ", y: " + obj1.getY();
						goodMessage = true;
						removeObject(obj1);
					}
					collisionsFound = true;
				}
			}
		}
		return collisionsFound;
	}

	// Function to open browser in load mode, and update arena details
	public void loadArena(String loadedData) {
		String[] lines = loadedData.split("\\|");

		// Extract arena details
		String[] values = lines[0].split(":");

		objects.clear();
		
		// Loop to create new robots with extracted details
		for (int i = 0; i < lines.length - 2; i++) {
			values = lines[i + 1].split(":");
			objects.add(generateObject(Integer.parseInt(values[0]), Integer.parseInt(values[1]),
					Integer.parseInt(values[2]), values[3], values[4], values[5]));
		}
	}

	private ArenaObject generateObject(int Id, int x, int y, String type, String direction, String charge) {
		ArenaObject object = null;
		switch (type) {
		case "Wall":
			object = new Wall(x, y, this);
			break;
		case "Charger":
			object = new Charger(x, y, this);
			break;
		case "Trap":
			object = new Trap(x, y, this);
			break;
		case "Light":
			object = new Light(x, y, this);
			break;
		case "RobotOne":
			object = new RobotOne(x, y, Direction.valueOf(direction), this);
			break;
		case "RobotTwo":
			object = new RobotTwo(x, y, Direction.valueOf(direction), this);
			break;
		case "RobotThree":
			object = new RobotThree(x, y, Direction.valueOf(direction), this);
			break;
		case "RobotFour":
			object = new RobotFour(x, y, Direction.valueOf(direction), this);
			break;
		case "RobotEight":
			object = new RobotEight(x, y, Direction.valueOf(direction), this);
			break;
		}
		if (object.isRobot()) {
			((Robot) object).setCharge(Integer.parseInt(charge));
		}
		if (object != null) {
			object.setId(Id);
		}
		return object;
	}

	private int checkIfValidX(int x) {
		if (x >= this.x) {
			x = this.x;
		} else if (x < 0) {
			x = 0;
		}
		return x;
	}

	private int checkIfValidY(int y) {
		if (y >= this.y) {
			y = this.y;
		} else if (y < 0) {
			y = 0;
		}
		return y;
	}

	public void resetCharge() {
		for (ArenaObject object : objects) {
			if (object.isRobot()) {
				((Robot) object).resetCharge();
			}
		}
		message = "Charge reset for all robots.";
		goodMessage = true;
	}

	public void reset() {
		objects = new ArrayList<ArenaObject>();
		ArenaObject.setObjectCount(0);
		speed = 0.02;
		status = "not-drawn";
		message = "";
		goodMessage = true;
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

	public void setSpeed(double speed) {
		this.speed = speed;
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

	public String getMessage() {
		return message;
	}

	public boolean isGoodMessage() {
		return goodMessage;
	}

	public double getSpeed() {
		return speed;
	}

	// Convert all arena details to a string for saving into file
	public String getDetails() {
		String details = "";

		// Arena details
		details += x + ":" + y + ":" + "|";

		// Object details
		for (ArenaObject object : objects) {
			String direction = "None";
			String charge = "None";
			if (object.isRobot()) {
				direction = ((Robot) object).getDirection().toString();
				charge = String.valueOf(((Robot) object).getChargeLevel());
			}
			details += object.getId() + ":" + object.getX() + ":" + object.getY() + ":" + object.getType() + ":"
					+ direction + ":" + charge + "|";
		}

		return details;
	}
}
