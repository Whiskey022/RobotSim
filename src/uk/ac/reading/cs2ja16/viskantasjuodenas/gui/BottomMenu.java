package uk.ac.reading.cs2ja16.viskantasjuodenas.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import uk.ac.reading.cs2ja16.viskantasjuodenas.robotManager.Robot;
import uk.ac.reading.cs2ja16.viskantasjuodenas.robotManager.RobotArena;

public class BottomMenu {
	
	private VBox menuBox;
	private int canvasSize;
	private double robotSize = 40;
	private GraphicsContext gc;
    private Image robot1 = new Image(getClass().getResourceAsStream("robot_01.png"));
	private RobotArena robotArena;
	
	/**
	 * BottomMenu constructor, sets up a box of buttons
	 */
	BottomMenu(RobotArena robotArena, Canvas canvas) {
		this.robotArena= robotArena;
		gc = canvas.getGraphicsContext2D();
		canvasSize = (int) canvas.getHeight();
		menuBox = setButtons();
	}
	
	/**
	 * set up the bottom menu of buttons
	 * @return a VBox of menu buttons
	 */
    private VBox setButtons() {
    	VBox menuBox = new VBox();
    			// create button
    	Button addRobotBtn = new Button("Add Robot");
    			// now add handler
    	addRobotBtn.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent event) {
    			robotArena.addRobot();
    			drawRobots();
    				// and its action to draw earth at random angle
    		}
    	});
    	
    	menuBox.getChildren().add(addRobotBtn);
    	return menuBox;
    }
    
    /**
   	 *  draw robots
   	 */
   	private void drawRobots() {
   		gc.clearRect(0,  0,  canvasSize,  canvasSize);		// clear canvas
   		Robot[] robots = robotArena.getRobots();
   		for (int i=0; i<robots.length; i++) {
   			drawIt(robot1, robots[i].getX()*robotSize, robots[i].getY()*robotSize, robotSize);	// draw Sun
   		}
   	}
   	
   	/**
        * drawIt ... draws object defined by given image at position and size
        * @param i		image
        * @param x		xposition
        * @param y
        * @param sz	size
        */
   	public void drawIt (Image i, double x, double y, double sz) {
   			// to draw centred at x,y, give top left position and x,y size
   		gc.drawImage(i, x - sz/2, y - sz/2, sz, sz);
   	}
   	
   	/**
	 * @return menuBox
	 */
	public VBox getMenuBar() {
		return menuBox;
	}
}
