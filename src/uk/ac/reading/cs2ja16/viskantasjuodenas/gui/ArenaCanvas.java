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
	private int canvasWidth;
	private int canvasHeight;
	private int robotSize;
	private RobotArena robotArena;
	private GraphicsContext gc;
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
		this.robotSize = robotSize;
		this.robotArena = robotArena;

		root = new Group();
		canvas = new Canvas(canvasWidth, canvasHeight);
		Canvas gridCanvas = createCanvasGrid(canvasWidth, canvasHeight);
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
				case "move-continuous":
					moveRobots();
					break;
				case "draw-movement":
				case "draw-movement-continuous":
				case "stop-movement":
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
			drawIt(object.getImage(), object.getX() * robotSize, object.getY() * robotSize, robotSize);
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
		switch(robotArena.getStatus()) {
		default:
			robotArena.setStatus("draw-movement-continuous");
			break;
		case "move-once":
			robotArena.setStatus("draw-movement");
			break;
		}
	}

	/**
	 * Draw moving robots
	 */
	private void animateMovingRobots() {
		boolean positionReached = false;
		step += 0.02;

		// clear canvas and reset stroke
		resetCanvas();

		// Loop to draw all robots
		for (int i = 0; i < ArenaObject.getObjectsCount(); i++) {
			if (drawMovingRobot(robotArena.getObjects().get(i), step)) {
				positionReached = true;
			}
		}

		// If position reached
		if (positionReached) {
			//Check if any of the robots stepped on an item, update them if necessary
			if (robotArena.checkCollisions()) {
				drawObjects();
			}
			
			//Reset step
			step = 0.0;
			
			switch(robotArena.getStatus()) {
			case "draw-movement-continuous":
				robotArena.moveAllRobots();					// If move continuous, move robots again
				break;
			default:										 // Else, stop animation
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
			if (coordinateReached(xToDraw, newX) && coordinateReached(yToDraw, newY)) {
				positionReached = true;
			}
		} else {
			xToDraw = newX;
			yToDraw = newY;
		}

		// Draw robot
		drawIt(object.getImage(), xToDraw * robotSize, yToDraw * robotSize, robotSize);

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
	
	//https://stackoverflow.com/questions/27846659/how-to-draw-an-1-pixel-line-using-javafx-canvas
	private Canvas createCanvasGrid(int width, int height) {
        Canvas canvas = new Canvas(width, height);
        GraphicsContext gc = canvas.getGraphicsContext2D() ;
        gc.setLineWidth(1.0);
        for (int x = 0; x < width; x+=robotSize) {
            gc.moveTo(x+0.5, 0);
            gc.lineTo(x+0.5, height);
            gc.stroke();
        }

        for (int y = 0; y < height; y+=robotSize) {
            
            gc.moveTo(0, y+0.5);
            gc.lineTo(width, y+0.5);
            gc.stroke();
        }
        
		gc.setStroke(Color.BLACK);
		gc.strokeRect(1, 1, canvasWidth-2, canvasHeight-2);
        
        return canvas ;
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
