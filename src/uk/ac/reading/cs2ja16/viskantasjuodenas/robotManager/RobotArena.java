package uk.ac.reading.cs2ja16.viskantasjuodenas.robotManager;

import java.util.ArrayList;
import java.util.Random;

/**
 * 
 * Robot arena object which stores robots and moves them around
 */
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
	 * Adds a new random robot at random location
	 * 
	 * @return status was adding was successful
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

	/**
	 * Add a robot
	 * 
	 * @param x
	 *            x coordinates
	 * @param y
	 *            y coordinates
	 * @param direction
	 *            its direction
	 * @param robotType
	 *            robot type
	 * @return status was adding was successful
	 */
	public String addRobot(int x, int y, Direction direction, String robotType) {

		// Check if provided coordinates are valid, change if necessary
		x = checkIfValidX(x);
		y = checkIfValidY(y);

		// If position not taken, add robot there
		if (!objectIsHere(x, y)) {
			objects.add(RobotType.getRobotObject(x, y, direction, robotType, this));
			return "success";
		}
		return "Position already taken";
	}

	/**
	 * Adds a new random item at random location
	 * 
	 * @return status was adding was successful
	 */
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

	/**
	 * Add an item
	 * 
	 * @param x
	 *            x coordinates
	 * @param y
	 *            y coordinates
	 * @param type
	 *            item type
	 * @return status was adding was successful
	 */
	public String addItem(int x, int y, String type) {

		// Check if provided coordinates are valid, change if necessary
		x = checkIfValidX(x);
		y = checkIfValidY(y);

		// If position not taken, add robot there
		if (!objectIsHere(x, y)) {
			objects.add(ItemType.getItemObject(x, y, type, this));
			return "success";
		}
		return "Position already taken";
	}

	/**
	 * Remove object from arena
	 * 
	 * @param obj
	 *            object to remove
	 */
	public void removeObject(ArenaObject obj) {
		objects.remove(objects.indexOf(obj));
		// Also decrease object count
		ArenaObject.setObjectCount(ArenaObject.getObjectsCount() - 1);
	}

	/**
	 * Check if object can move here
	 * 
	 * @param x
	 *            x coordinate to check
	 * @param y
	 *            y coordinate to check
	 * @return true if can move there
	 */
	public boolean canMoveHere(int x, int y) {
		// Check if its a border is in front
		if (x >= this.x || y >= this.y || x < 0 || y < 0) {
			return false;

		}
		// Check if an object is blocking the location
		else if (objectIsBlocking(x, y)) {
			return false;
		}
		return true;
	}

	/**
	 * Check if any object is on that location
	 * 
	 * @param x
	 *            x to check
	 * @param y
	 *            y to check
	 * @return true if object is there
	 */
	private boolean objectIsHere(int x, int y) {
		for (int i = 0; i < ArenaObject.getObjectsCount(); i++) {
			if (objects.get(i).isHere(x, y)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Check if an object is blocking the path
	 * 
	 * @param x
	 *            x to check
	 * @param y
	 *            y to check
	 * @return true if object is blocking the path
	 */
	private boolean objectIsBlocking(int x, int y) {
		for (int i = 0; i < ArenaObject.getObjectsCount(); i++) {
			ArenaObject object = objects.get(i);
			// Path is blocked if robot or wall is on location
			if (object.isHere(x, y) && (object.isRobot() || object.isWall())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Try to move all robots
	 */
	public void moveAllRobots() {
		for (ArenaObject object : objects) {
			if (object.isRobot()) {
				object.tryToMove();
				if (object.isRobot() && ((Robot) object).getChargeLevel() < 1) {
					message = "Robot ran out of juice at x: " + object.getX() + ", y: " + object.getY();
					goodMessage = false;
				}
			}
		}
	}
	
	/**
	 * convert arena's details for saving to file
	 * @return aren's details as String
	 */
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

	/**
	 * Check objects which have moved to the same location
	 * 
	 * @return true if collision was found
	 */
	public boolean checkCollisions() {
		boolean collisionsFound = false;
		for (int i = 0; i < ArenaObject.getObjectsCount(); i++) {
			for (int j = i + 1; j < ArenaObject.getObjectsCount(); j++) {
				ArenaObject obj1 = objects.get(i);
				ArenaObject obj2 = objects.get(j);
				if (obj1.isHere(obj2.getX(), obj2.getY())) {
					// If robot stepped on a charger
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
					}
					// If robot who destroys traps stepped on it
					else if ((obj1.getClass() == RobotSix.class && obj2.isTrap())
							|| (obj2.getClass() == RobotSix.class && obj1.isTrap())) {
						message = "Robot removed a trap at x: " + obj1.getX() + ", y: " + obj1.getY();
						goodMessage = true;
						if (obj1.getClass() == RobotSix.class) {
							removeObject(obj2);
						} else {
							removeObject(obj1);
						}
					}
					// If other types of robot stepped on a trap
					else if ((obj1.isRobot() && obj2.isTrap()) || (obj2.isRobot() && obj1.isTrap())) {
						message = "Robot removed by trap at x: " + obj1.getX() + ", y: " + obj1.getY();
						goodMessage = false;
						removeObject(obj1);
						removeObject(obj2);
					}
					// If robot who collects lights steps on one
					else if ((obj1.getClass() == RobotSeven.class) && obj2.isLight()) {
						message = "Robot removed light at x: " + obj1.getX() + ", y: " + obj1.getY();
						goodMessage = true;
						removeObject(obj2);
					} else if ((obj2.getClass() == RobotSeven.class) && obj1.isLight()) {
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

	/**
	 * Load arena with loaded data
	 * 
	 * @param loadedData
	 *            String format data from a file
	 */
	public void loadArena(String loadedData) {
		// Split lines
		String[] lines = loadedData.split("\\|");
		String[] values;

		// Remove all objects
		objects.clear();

		// Loop to create new objects with extracted details
		for (int i = 0; i < lines.length - 2; i++) {
			values = lines[i + 1].split(":");
			// Adding new object will all saved data
			objects.add(generateObject(Integer.parseInt(values[0]), Integer.parseInt(values[1]),
					Integer.parseInt(values[2]), values[3], values[4], values[5]));
		}
	}

	/**
	 * Create an object by given type
	 * 
	 * @param Id
	 * @param x
	 * @param y
	 * @param type
	 * @param direction
	 * @param charge
	 * @return created object
	 */
	private ArenaObject generateObject(int Id, int x, int y, String type, String direction, String charge) {

		// If direction is none, it's an item, not a robot
		if (direction.equals("None")) {
			ArenaObject object = ItemType.getItemObject(x, y, type, this);
			object.setId(Id);
			return object;
		} else {
			ArenaObject object = RobotType.getRobotObject(x, y, Direction.valueOf(direction), type, this);
			object.setId(Id);
			((Robot) object).setCharge(Integer.parseInt(charge));
			return object;
		}
	}

	/**
	 * Check if valid x coordinate, if not change it
	 * 
	 * @param x
	 * @return x coordinate
	 */
	private int checkIfValidX(int x) {
		if (x >= this.x) {
			x = this.x;
		} else if (x < 0) {
			x = 0;
		}
		return x;
	}

	/**
	 * Check if valid y coordinate, if not change it 
	 * @param y
	 * @return
	 */
	private int checkIfValidY(int y) {
		if (y >= this.y) {
			y = this.y;
		} else if (y < 0) {
			y = 0;
		}
		return y;
	}

	/**
	 * Reset charge for all robots
	 */
	public void resetCharge() {
		for (ArenaObject object : objects) {
			if (object.isRobot()) {
				((Robot) object).resetCharge();
			}
		}
		message = "Charge reset for all robots.";
		goodMessage = true;
	}

	/**
	 * Reset arena
	 */
	public void reset() {
		objects = new ArrayList<ArenaObject>();
		ArenaObject.setObjectCount(0);
		speed = 0.02;
		status = "not-drawn";
		message = "";
		goodMessage = true;
	}

	/**
	 * Set width of arena
	 * @param x	width of arena
	 */
	public void setXSize(int x) {
		this.x = x;
	}

	/**
	 * set height of arena
	 * @param y height of arena
	 */
	public void setYSize(int y) {
		this.y = y;
	}

	/**
	 * set status of arena
	 * @param status status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * set speed of arena
	 * @param speed speed to set
	 */
	public void setSpeed(double speed) {
		this.speed = speed;
	}

	/**
	 * get width of arena
	 * @return	width
	 */
	public int getXSize() {
		return x;
	}

	/**
	 * get height of arena
	 * @return	height
	 */
	public int getYSize() {
		return y;
	}

	/**
	 * get all arena's objects
	 * @return arena's objects
	 */
	public ArrayList<ArenaObject> getObjects() {
		return objects;
	}

	/**
	 * get arena's status
	 * @return	arena's status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * get arena's message
	 * @return	arena's message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * get arena's message type
	 * @return	true if good type
	 */
	public boolean isGoodMessage() {
		return goodMessage;
	}

	/**
	 * get arena's speed
	 * @return	arena's speed
	 */
	public double getSpeed() {
		return speed;
	}
}
