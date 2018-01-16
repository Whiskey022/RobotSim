package uk.ac.reading.cs2ja16.viskantasjuodenas.gui;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import uk.ac.reading.cs2ja16.viskantasjuodenas.robotManager.ArenaObject;
import uk.ac.reading.cs2ja16.viskantasjuodenas.robotManager.Robot;
import uk.ac.reading.cs2ja16.viskantasjuodenas.robotManager.RobotArena;

public class ArenaCanvas {

	private Group root;
	private Canvas canvas;
	private Canvas gridCanvas;
	private int canvasWidth;
	private int canvasHeight;
	private int objectSize;
	private RobotArena robotArena;
	private GraphicsContext gc;
	private GraphicsContext gcGrid;
	private boolean showGrid = true;
	private double step;
	Label arenaLabel = new Label();
	private String defaultLabelStyle = "-fx-label-padding: -20px 20px 0px, 0px";

	/**
	 * RobotCanvas constructor, sets up a new canvas
	 * 
	 * @param canvasWidth
	 *            canvas width
	 * @param canvasHeight
	 *            canvas height
	 */
	public ArenaCanvas(int canvasWidth, int canvasHeight, int robotSize, RobotArena robotArena) {
		this.canvasWidth = canvasWidth;
		this.canvasHeight = canvasHeight;
		this.objectSize = robotSize;
		this.robotArena = robotArena;

		root = new Group();
		canvas = new Canvas(canvasWidth, canvasHeight);
		gridCanvas = new Canvas(canvasWidth, canvasHeight);
		gcGrid = gridCanvas.getGraphicsContext2D();
		createCanvasGrid();
		root.getChildren().addAll(arenaLabel, gridCanvas, canvas);
		gc = canvas.getGraphicsContext2D();

		animateRobots();
	}

	/**
	 * Move robots by drawing them with animation
	 * 
	 * @param robots
	 *            array of robots to move
	 * @param robotsCounter
	 *            robot count
	 */
	public void animateRobots() {
		// Starting movement step
		step = 0.0;

		// Drawing animation
		new AnimationTimer() {
			public void handle(long l) {

				switch (robotArena.getStatus()) {
				case "not-drawn":
					drawObjects();
					break;
				case "move-once":
					moveRobots();
					break;
				case "move-continuous":
					if (step == 0)
						moveRobots();
					else
						robotArena.setStatus("draw-movement-continuous");
					break;
				case "draw-movement":
				case "draw-movement-continuous":
					animateMovingRobots();
					break;
				case "stop-movement":
					if (step > 0)
						animateMovingRobots();
					break;
				default:
					break;
				}

				updateArenaLabel();
			}
		}.start();
	}

	/**
	 * Draw standing robots
	 */
	private void drawObjects() {
		resetCanvas();
		// Draw each robot
		for (int i = 0; i < ArenaObject.getObjectsCount(); i++) {
			ArenaObject object = robotArena.getObjects().get(i);
			drawIt(object.getImage(), object.getX() * objectSize, object.getY() * objectSize, objectSize);
		}
		if (robotArena.getStatus() != "draw-movement-continuous") {
			robotArena.setStatus("stand");
		}
	}

	/**
	 * Start moving robots
	 */
	private void moveRobots() {
		robotArena.moveAllRobots();
		if (robotArena.getStatus() == "move-once") {
			robotArena.setStatus("draw-movement");
		} else {
			robotArena.setStatus("draw-movement-continuous");
		}
		
	}

	/**
	 * Draw moving robots
	 */
	private void animateMovingRobots() {
		int robotsMoving = ArenaObject.getObjectsCount();
		step += robotArena.getSpeed();

		// clear canvas and reset stroke
		resetCanvas();

		// Loop to draw all robots
		for (int i = 0; i < ArenaObject.getObjectsCount(); i++) {
			if (drawMovingRobot(robotArena.getObjects().get(i), step)) {
				robotsMoving--;
			}
		}

		// If position reached
		if (robotsMoving == 0) {
			// Check if any of the robots stepped on an item, update them if necessary
			if (robotArena.checkCollisions()) {
				drawObjects();
			}

			// Reset step
			step = 0.0;

			switch (robotArena.getStatus()) {
			case "draw-movement-continuous":
				robotArena.setStatus("move-continuous"); // If move continuous, move robots again
				break;
			default: // Else, stop animation
				robotArena.setStatus("stop");
				break;
			}
		}
	}

	/**
	 * Function to draw robot moving animation
	 * 
	 * @param object
	 *            Robot to draw
	 * @param currentNanoTime
	 *            current time
	 * @param startNanoTime
	 *            time when started drawing
	 * @param self
	 *            AnimationTimer object
	 */
	private boolean drawMovingRobot(ArenaObject object, double step) {
		// Boolean to check if robot already reached his position
		boolean positionReached = false;

		int newX = object.getX(), newY = object.getY(); // New coordinates
		double xToDraw, yToDraw; // Coordinates to draw at

		if (object.getDidMove()) {
			int oldX = ((Robot) object).getOldX(), oldY = ((Robot) object).getOldY(); // Previous coordinates
			// If robot has moved to new coordinates, calculate coordinates for drawing
			xToDraw = oldX + (newX - oldX) * step;
			yToDraw = oldY + (newY - oldY) * step;

			// Check if coordinates reached
			if (step > 1 || coordinateReached(xToDraw, newX) && coordinateReached(yToDraw, newY)) {
				positionReached = true;
			}
		} else {
			xToDraw = newX;
			yToDraw = newY;
			positionReached = true;
		}

		// Draw robot
		drawIt(object.getImage(), xToDraw * objectSize, yToDraw * objectSize, objectSize);

		return positionReached;
	}

	private void updateArenaLabel() {
		arenaLabel.setText(robotArena.getMessage());
		if (robotArena.isGoodMessage()) {
			arenaLabel.setStyle(defaultLabelStyle + "; -fx-text-fill: blue");
		} else {
			arenaLabel.setStyle(defaultLabelStyle + "; -fx-text-fill: red");
		}
	}

	/**
	 * Function to check if coordinates are pretty much reached
	 * 
	 * @param posToDraw
	 *            coordinate on which the object is being drawn (which keeps
	 *            changing)
	 * @param posToReach
	 *            coordinate to reach
	 * @return true if coordinate reached or if distance starts to increase
	 */
	private boolean coordinateReached(double posToDraw, int posToReach) {
		return Math.abs(posToDraw - posToReach) < 0.02 || Math.abs(posToDraw - posToReach) > 1;
	}

	/**
	 * drawIt ... draws object defined by given image at position and size
	 * 
	 * @param i
	 *            image
	 * @param x
	 *            x position
	 * @param y
	 *            y position
	 * @param sz
	 *            size
	 */
	private void drawIt(Image i, double x, double y, double sz) {
		// to draw centred at x,y, give top left position and x,y size
		gc.drawImage(i, x, y, sz, sz);
	}

	// https://stackoverflow.com/questions/27846659/how-to-draw-an-1-pixel-line-using-javafx-canvas
	private void createCanvasGrid() {
		gcGrid.setLineWidth(1.0);

		gcGrid.beginPath();
		gcGrid.clearRect(0, 0, canvasWidth, canvasHeight);
		if (showGrid) {
			for (int x = 0; x < canvasWidth; x += objectSize) {
				gcGrid.moveTo(x + 0.5, 0);
				gcGrid.lineTo(x + 0.5, canvasHeight);
				gcGrid.stroke();
			}
	
			for (int y = 0; y < canvasHeight; y += objectSize) {
	
				gcGrid.moveTo(0, y + 0.5);
				gcGrid.lineTo(canvasWidth, y + 0.5);
				gcGrid.stroke();
			}
		}
		gcGrid.setStroke(Color.BLACK);
		gcGrid.strokeRect(1, 1, canvasWidth - 2, canvasHeight - 2);
	}

	/**
	 * Reset canvas and stroke
	 */
	public void resetCanvas() {
		gc.clearRect(0, 0, canvasWidth, canvasHeight); // clear canvas
		gc.setStroke(Color.BLACK);
		gc.strokeRect(0, 0, canvasWidth, canvasHeight);
	}

	public void newArena(int arenaWidth, int arenaHeight) {
		objectSize = (int) (140 / Math.sqrt((Math.sqrt(arenaHeight * arenaWidth))));
		canvasWidth = arenaWidth * objectSize;
		canvasHeight = arenaHeight * objectSize;

		robotArena.reset();
		robotArena.setXSize(arenaWidth);
		robotArena.setYSize(arenaHeight);
		canvas.setHeight(canvasWidth);
		canvas.setWidth(canvasWidth);
		gridCanvas.setHeight(canvasWidth);
		gridCanvas.setWidth(canvasWidth);
		gc = canvas.getGraphicsContext2D();
		gcGrid = gridCanvas.getGraphicsContext2D();
		gc.clearRect(0, 0, canvasWidth, canvasHeight);
		createCanvasGrid();

		ArenaObject.setObjectCount(0);
	}
	
	public void setShowGrid(boolean value) {
		showGrid = value;
		createCanvasGrid();
	}
	
	public boolean getShowGrid() {
		return showGrid;
	}

	/**
	 * @return Group object of the canvas
	 */
	public Group getGroup() {
		return root;
	}

	/**
	 * @return Return Canvas object
	 */
	public Canvas getCanvas() {
		return canvas;
	}
}
