package uk.ac.reading.cs2ja16.viskantasjuodenas.robotManager;

import javafx.scene.image.Image;

public abstract class ArenaObject {
	
	protected int x;
	protected int y;
	protected int id;
	protected String objectType;
	protected Image image;
	protected static int objectsCount = 0;
	protected RobotArena robotArena;
	protected String type;
	
	public void countId() {
		objectsCount++;
		id = objectsCount;
	}
	
	public boolean isHere(int x, int y) {
		if (this.x == x && this.y == y) {
			return true;
		} else {
			return false;
		}
	}
	
	public abstract boolean tryToMove();
	public abstract boolean isRobot();
	public abstract boolean getDidMove();
	
	public void setId(int id) {
		this.id = id;
	}
	
	public static void setObjectCount(int count) {
		objectsCount = count;
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public int getId() {
		return id;
	}
	
	public Image getImage() {
		return image;
	}
	
	public static int getObjectsCount() {
		return objectsCount;
	}
	
	public String getType() {
		return type;
	}

	public void increaseCharge() {
		// TODO Auto-generated method stub
	}
	
	public boolean isWall() {
		return false;
	}
	
	public boolean isCharger() {
		return false;
	}
	
	public boolean isTrap() {
		return false;
	}
	
	public boolean isLight() {
		return false;
	}
}
