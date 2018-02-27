package uk.ac.reading.cs2ja16.viskantasjuodenas.robotManager;

import java.util.Arrays;
import java.util.Random;

/**
 * 
 * Class to store item class names
 */
public class ItemType {

	private static String[] items = { "Wall", "Charger", "Trap", "Light" };

	/**
	 * 
	 * @return count of available items
	 */
	public static int getCount() {
		return items.length;
	}

	/**
	 * Calculates a balanced way to get random item
	 * 
	 * @return random item
	 */
	public static String getRandom() {
		int rnd = new Random().nextInt(100);
		if (rnd < 40) {
			return items[0]; // Wall
		}
		if (rnd < 70) {
			return items[1]; // Charger
		}
		if (rnd < 80) {
			return items[2]; // Trap
		}
		return items[3]; // Light
	}

	/**
	 * 
	 * @param index index for items array
	 * @return item by index
	 */
	public static String get(int index) {
		return items[index];
	}

	/**
	 * 
	 * @param name	name of item type
	 * @return index by name
	 */
	public static int getIndex(String name) {
		return Arrays.asList(items).indexOf(name);
	}

	/**
	 * Create an object by provided name
	 * 
	 * @param x
	 *            x location
	 * @param y
	 *            y location
	 * @param type
	 *            object type
	 * @param robotArena
	 *            robot arena
	 * @return item object
	 */
	public static ArenaObject getItemObject(int x, int y, String type, RobotArena robotArena) {
		switch (type) {
		default:
			return new Wall(x, y, robotArena);
		case "Charger":
			return new Charger(x, y, robotArena);
		case "Trap":
			return new Trap(x, y, robotArena);
		case "Light":
			return new Light(x, y, robotArena);
		}

	}
}
