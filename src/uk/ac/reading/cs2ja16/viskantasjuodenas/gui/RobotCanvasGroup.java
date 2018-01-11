package uk.ac.reading.cs2ja16.viskantasjuodenas.gui;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import uk.ac.reading.cs2ja16.viskantasjuodenas.robotManager.Robot;

public class RobotCanvasGroup {
	
	private Group root;
	private Canvas canvas;
	private int canvasWidth;
	private int canvasHeight;
	private int robotSize;
	private GraphicsContext gc;
	private Image[] robotImages = {
    		new Image(getClass().getResourceAsStream("robot_01.png")),
    		new Image(getClass().getResourceAsStream("robot_02.png")),
    		new Image(getClass().getResourceAsStream("robot_03.png")),
    		new Image(getClass().getResourceAsStream("robot_04.png")),
    		new Image(getClass().getResourceAsStream("robot_05.png")),
    		new Image(getClass().getResourceAsStream("robot_06.png")),
    		new Image(getClass().getResourceAsStream("robot_07.png")),
    		new Image(getClass().getResourceAsStream("robot_08.png")),
    		new Image(getClass().getResourceAsStream("robot_09.png")),
    		};
	
	/**
	 * RobotCanvas constructor, sets up a new canvas
	 * @param	canvasWidth		canvas width
	 * @param	canvasHeight	canvas height
	 */
	RobotCanvasGroup(int canvasWidth, int canvasHeight, int robotSize) {
		this.canvasWidth = canvasWidth;
		this.canvasHeight = canvasHeight;
		this.robotSize = robotSize;
		
		root = new Group();
		canvas = new Canvas(canvasWidth, canvasHeight);
		root.getChildren().add(canvas);
		gc = canvas.getGraphicsContext2D();
		gc.setStroke(Color.BLACK);
		gc.strokeRect(0, 0, canvasWidth, canvasHeight);
	}
	
	/**
   	 *  Draw robots
   	 *  @param	robots	array of robots
   	 *  @param	robotsCounter	count of robots added
   	 */
   	public void drawRobots(Robot[] robots, int robotsCounter) {
   		gc.clearRect(0,  0,  canvasWidth,  canvasHeight);		// clear canvas
   		gc.setStroke(Color.BLACK);
   		gc.strokeRect(0, 0, canvasWidth, canvasHeight);
   		for (int i=0; i<robotsCounter; i++) {
   			drawIt(robotImages[robots[i].getImageIndex()],
   					robots[i].getX()*robotSize,
   					robots[i].getY()*robotSize,
   					robotSize);
   		}
   	}
   	
   	/**
   	 *  Move robots by drawing them with animation
   	 *  @param	robots	array of robots to move
   	 *  @param	robotsCounter	robot count
   	 */
   	public void moveRobots(Robot[] robots, int robotsCounter) {	   
   		//Drawing start time
	    final long startNanoTime = System.nanoTime();
	    
	    //Drawing animation
	    new AnimationTimer() {
	    	public void handle(long currentNanoTime) {
	    		// clear canvas and reset stroke
	       		gc.clearRect(0,  0,  canvasWidth,  canvasHeight);
	       		gc.setStroke(Color.BLACK);
	    	    gc.strokeRect(0, 0, canvasWidth, canvasHeight);
	    	    
	    	    //Loop to draw all robots
	    		for (int i=0; i<robotsCounter; i++) {
	    			drawRobot(robots[i], currentNanoTime, startNanoTime, this);
		   		}
	    	}
	    }.start();;
   	}
   	
   	
   	/**
   	 * Function to draw robot moving animation
   	 * @param robot	Robot to draw
   	 * @param currentNanoTime	current time
   	 * @param startNanoTime	time when started drawing
   	 * @param self	AnimationTimer object
   	 */
   	private void drawRobot(Robot robot, long currentNanoTime, long startNanoTime, AnimationTimer self) {
   		
   		int oldX = robot.getOldX(), oldY = robot.getOldY();		//Previous coordinates
   		int newX = robot.getX(), newY = robot.getY();			//New coordinates
   		double xToDraw, yToDraw;								//Coordinates to draw at
   		
   		//If robot has moved to new coordinates, calculate coordinates for drawing
		if (robot.getRobotMoved()) {
			xToDraw = oldX + (newX - oldX) * (currentNanoTime - startNanoTime) / 1000000000.0;
			yToDraw = oldY + (newY - oldY) * (currentNanoTime - startNanoTime) / 1000000000.0;
			//Stop if drawn coordinates are already very close to the robot's new coordinates
			if (Math.abs(xToDraw - newX) < 0.05 && Math.abs(yToDraw - newY) < 0.05) {
				self.stop();
			}    				
		} else {		//Else no need to calculate animations
			xToDraw = newX;
			yToDraw = newY;
		}
		
		//Draw robot
		drawIt(robotImages[robot.getImageIndex()],
					xToDraw * robotSize,
					yToDraw * robotSize,
					robotSize);		 
   	}
   	
	/**
	 * drawIt ... draws object defined by given image at position and size
	 * @param i		image
	 * @param x		xposition
	 * @param y		yposition
	 * @param sz	size
	 */
	private void drawIt (Image i, double x, double y, double sz) {
		// to draw centred at x,y, give top left position and x,y size
		gc.drawImage(i, x - sz/2, y - sz/2, sz, sz);
	}
	
	/**
	 * @return	Group object of the canvas
	 */
	public Group getGroup() {
		return root;
	}
	
	/**
	 * @return	Return Canvas object
	 */
	public Canvas getCanvas() {
		return canvas;
	}
	
	/**
	 * @return	Image array for robots
	 */
	public Image[] getRobotImages() {
		return robotImages;
	}
}
