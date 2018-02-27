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

/**
 * Robot arena's canvas, all objects are drawn and animated in this class
 */
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
	private boolean showGrid = true; // Draw grid or not
	private double step; // How fast the robots move to their direction
	Label arenaLabel = new Label(); // Label for arena's messages
	// Label's style
	private String defaultLabelStyle = "-fx-label-padding: -20px 20px 0px, 0px";

	/**
	 * RobotCanvas constructor, sets up new canvas
	 * 
	 * @param canvasWidth
	 *            canvas width
	 * @param canvasHeight
	 *            canvas height
	 * @param robotSize
	 *            robot size in pixels
	 * @param robotArena
	 *            robotArena to draw
	 */
	public ArenaCanvas(int canvasWidth, int canvasHeight, int robotSize, RobotArena robotArena) {
		this.canvasWidth = canvasWidth;
		this.canvasHeight = canvasHeight;
		this.objectSize = robotSize;
		this.robotArena = robotArena;

		root = new Group();
		canvas = new Canvas(canvasWidth, canvasHeight);
		gridCanvas = new Canvas(canvasWidth, canvasHeight); // Separate canvas for drawing a grid
		gcGrid = gridCanvas.getGraphicsContext2D();
		createCanvasGrid(); // Draw grid
		root.getChildren().addAll(arenaLabel, gridCanvas, canvas);
		gc = canvas.getGraphicsContext2D();

		animateRobots(); // Start animation
	}

	/**
	 * Constantly draw robots
	 */
	public void animateRobots() {
		// Starting movement step
		step = 0.0;

		// Animation constantly running, does stuff according to what is arena's status
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
					if (step == 0) // Move robots only if they are not moving
						moveRobots();
					else
						robotArena.setStatus("draw-movement-continuous"); // Else continue drawing
					break;
				case "draw-movement":
				case "draw-movement-continuous":
					animateMovingRobots();
					break;
				case "stop-movement":
					if (step > 0) // Step drawing movement after robots finish their current move
						animateMovingRobots();
					break;
				default:
					break;
				}

				// Update arena's message
				updateArenaLabel();
			}
		}.start();
	}

	/**
	 * Draw standing robots
	 */
	private void drawObjects() {
		// Clear canvas
		resetCanvas();

		// Draw each robot
		for (int i = 0; i < ArenaObject.getObjectsCount(); i++) {
			ArenaObject object = robotArena.getObjects().get(i);
			drawIt(object.getImage(), object.getX() * objectSize, object.getY() * objectSize, objectSize);
		}

		// Stop drawing if status is not continuous movement
		if (robotArena.getStatus() != "draw-movement-continuous") {
			robotArena.setStatus("stand");
		}
	}

	/**
	 * Move robots, change status to 'draw-movement' according to previous status
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
	 * Animate all moving robots
	 */
	private void animateMovingRobots() {
		// Count for how many objects are moving
		int robotsMoving = ArenaObject.getObjectsCount();

		// Step keeps increasing (they get closer to their next location)
		step += robotArena.getSpeed();

		// clear canvas
		resetCanvas();

		// Loop to draw all robots
		for (int i = 0; i < ArenaObject.getObjectsCount(); i++) {
			if (drawMovingRobot(robotArena.getObjects().get(i), step)) { // If object reached its position, remove count
																			// of robotsMoving
				robotsMoving--;
			}
		}

		// If no robots are moving
		if (robotsMoving == 0) {

			// Check if any of the robots stepped on an item, update them if necessary
			if (robotArena.checkCollisions()) {
				drawObjects();
			}

			// Reset step
			step = 0.0;

			// Change arena's status
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
	 * @param step
	 *            How far has the robot moved towards new location
	 */
	private boolean drawMovingRobot(ArenaObject object, double step) {
		// Boolean to check if robot already reached his position
		boolean positionReached = false;

		int newX = object.getX(), newY = object.getY(); // New coordinates
		double xToDraw, yToDraw; // Coordinates to draw at

		// Calculate animation only if the robot has moved
		if (object.getDidMove()) {
			// Previous coordinates
			int oldX = ((Robot) object).getOldX(), oldY = ((Robot) object).getOldY();

			// Calculate coordinates for drawing
			xToDraw = oldX + (newX - oldX) * step;
			yToDraw = oldY + (newY - oldY) * step;

			// Check if robot reached its location
			if (step > 1 || coordinateReached(xToDraw, newX) && coordinateReached(yToDraw, newY)) {
				positionReached = true;
			}
		}
		// If object isn't moving, no need for animation calculation, just draw its
		// current position
		else {
			xToDraw = newX;
			yToDraw = newY;
			positionReached = true;
		}

		// Draw robot
		drawIt(object.getImage(), xToDraw * objectSize, yToDraw * objectSize, objectSize);

		return positionReached;
	}

	/**
	 * Function to update arena's message
	 */
	private void updateArenaLabel() {
		arenaLabel.setText(robotArena.getMessage());
		// Change text colour according to message type
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
	/**
	 * Draw grid if settings for it is true
	 */
	private void createCanvasGrid() {
		gcGrid.setLineWidth(1.0);
		gcGrid.beginPath();
		gcGrid.clearRect(0, 0, canvasWidth, canvasHeight);
		
		//Draw only if showGrid is true
		if (showGrid) {
			//Draw vertically
			for (int x = 0; x < canvasWidth; x += objectSize) {
				gcGrid.moveTo(x + 0.5, 0);
				gcGrid.lineTo(x + 0.5, canvasHeight);
				gcGrid.stroke();
			}

			//Draw horizontally
			for (int y = 0; y < canvasHeight; y += objectSize) {
				gcGrid.moveTo(0, y + 0.5);
				gcGrid.lineTo(canvasWidth, y + 0.5);
				gcGrid.stroke();
			}
		}
		//Draw borders
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

	/**
	 * Create a new arena, and canvas for it
	 * @param arenaWidth	width of new arena
	 * @param arenaHeight	height of new arena
	 */
	public void newArena(int arenaWidth, int arenaHeight) {
		//Calculate object size so arena fits into GUI
		objectSize = calculateObjectSize(arenaWidth, arenaHeight);
		canvasWidth = arenaWidth * objectSize;
		canvasHeight = arenaHeight * objectSize;

		//Reset canvas and graphicsContext
		robotArena.reset();
		robotArena.setXSize(arenaWidth);
		robotArena.setYSize(arenaHeight);
		canvas.setHeight(canvasHeight);
		canvas.setWidth(canvasWidth);
		gridCanvas.setHeight(canvasHeight);
		gridCanvas.setWidth(canvasWidth);
		gc = canvas.getGraphicsContext2D();
		gcGrid = gridCanvas.getGraphicsContext2D();
		gc.clearRect(0, 0, canvasWidth, canvasHeight);
		createCanvasGrid();

		//Set objects count to 0
		ArenaObject.setObjectCount(0);
	}

	/**
	 * Calculate object size so the arena would fit onto GUI
	 * @param arenaWidth	arena width
	 * @param arenaHeight	arena height
	 * @return	object's new size
	 */
	private int calculateObjectSize(int arenaWidth, int arenaHeight) {
		int objectSize = 100;
		int width = arenaWidth;
		int height = arenaHeight;
		//Loop to get object small enough so arena's dimensions are not too big
		while (width * objectSize > 900 || height * objectSize > 500) {
			objectSize--;
		}
		return objectSize;
	}

	/**
	 * Set showGrid
	 * @param value true or false
	 */
	public void setShowGrid(boolean value) {
		showGrid = value;
		createCanvasGrid();		//Create grid canvas immediately
	}

	/**
	 * 
	 * @return showGrid value
	 */
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
