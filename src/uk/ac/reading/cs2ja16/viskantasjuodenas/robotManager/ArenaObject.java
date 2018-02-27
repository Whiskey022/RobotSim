package uk.ac.reading.cs2ja16.viskantasjuodenas.robotManager;

import javafx.scene.image.Image;

/**
 *	Abstract class for arena's objects
 */
public abstract class ArenaObject {
	
	protected int x;
	protected int y;
	protected int id;
	protected Image image;
	protected static int objectsCount = 0;
	protected RobotArena robotArena;
	protected String type;
	
	/**
	 * Get ID and count objects
	 */
	public void countId() {
		objectsCount++;
		id = objectsCount;
	}
	
	/**
	 * Check if object is on location
	 * @param x	x coordinate
	 * @param y	y coordinate
	 * @return	is object on location
	 */
	public boolean isHere(int x, int y) {
		if (this.x == x && this.y == y) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Try to move Robot
	 * @return true if object did move
	 */
	public abstract boolean tryToMove();
	
	/**
	 * 
	 * @return is robot
	 */
	public abstract boolean isRobot();
	
	/**
	 * 
	 * @return did object just move
	 */
	public abstract boolean getDidMove();
	
	/**
	 * set iD
	 * @param id Id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * set objects count
	 * @param count count to set
	 */
	public static void setObjectCount(int count) {
		objectsCount = count;
	}
	
	/**
	 * 
	 * @return x coordinate
	 */
	public int getX() {
		return x;
	}

	/**
	 * 
	 * @return y coordinate
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * 
	 * @return object's id
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * 
	 * @return object's image
	 */
	public Image getImage() {
		return image;
	}
	
	/**
	 * 
	 * @return objects' count
	 */
	public static int getObjectsCount() {
		return objectsCount;
	}
	
	/**
	 * 
	 * @return object's type
	 */
	public String getType() {
		return type;
	}
	/**
	 * 
	 * @return is wall
	 */
	public boolean isWall() {
		return false;
	}
	
	/**
	 * 
	 * @return is charger
	 */
	public boolean isCharger() {
		return false;
	}
	
	/**
	 * 
	 * @return is trap
	 */
	public boolean isTrap() {
		return false;
	}
	
	/**
	 * 
	 * @return is light
	 */
	public boolean isLight() {
		return false;
	}
	
	/**
	 * Increase robot's charge
	 */
	public void increaseCharge() {}			//Function to override for robot
}
