package uk.ac.reading.cs2ja16.viskantasjuodenas.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import uk.ac.reading.cs2ja16.viskantasjuodenas.robotManager.RobotArena;

public class BottomMenu {
	
	private HBox menuBox;
	private RobotArena robotArena;
	private RobotCanvasGroup robotCanvas;
	
	/**
	 * BottomMenu constructor, sets up a box of buttons
	 * @param	RobotArena object
	 * @param	RobotCanvasGroup object to interact with
	 */
	BottomMenu(RobotArena robotArena, RobotCanvasGroup robotCanvas) {
		this.robotArena= robotArena;
		this.robotCanvas = robotCanvas;
		
		menuBox = setButtons();
	}
	
	/**
	 * set up the bottom menu of buttons
	 * @return a VBox of menu buttons
	 */
    private HBox setButtons() {
    	HBox menuBox = new HBox();
    	
    	//"Button for adding a random robot
    	Button randomRobotBtn = new Button("Add Random Robot");
    	randomRobotBtn.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent event) {
    			robotArena.addRobot(robotCanvas.getRobotImages().length);
    			robotCanvas.drawRobots(robotArena.getRobots(), robotArena.getRobotsCounter());
    		}
    	});
    	
    	//"Button for moving robots
    	Button moveRobotsBtn = new Button("Move Robots");
    	moveRobotsBtn.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent event) {
    			robotArena.moveAllRobots();
    			robotCanvas.moveRobots(robotArena.getRobots(), robotArena.getRobotsCounter());
    		}
    	});
    	
    	menuBox.getChildren().addAll(randomRobotBtn, moveRobotsBtn);
    	return menuBox;
    }
   	
   	/**
	 * @return menuBox
	 */
	public HBox getMenuBar() {
		return menuBox;
	}
}
