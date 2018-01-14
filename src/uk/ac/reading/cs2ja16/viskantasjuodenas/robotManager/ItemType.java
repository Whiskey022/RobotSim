package uk.ac.reading.cs2ja16.viskantasjuodenas.robotManager;

import java.util.Arrays;
import java.util.Random;

public class ItemType {

	private static String[] items = { "Wall", "Charger"};

	public static int getCount() {
		return items.length;
	}

	public static String getRandom() {
		return items[new Random().nextInt(items.length)];
	}

	public static String get(int index) {
		return items[index];
	}

	public static int getIndex(String name) {
		return Arrays.asList(items).indexOf(name);
	}

	public static ArenaObject getItemObject(int x, int y, String type, RobotArena robotArena) {
		switch(type) {
		default:
			return new Wall(x, y, robotArena);
		case "Charger":
			return new Charger(x, y, robotArena);
		}
	}
}
