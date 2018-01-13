package uk.ac.reading.cs2ja16.viskantasjuodenas.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import uk.ac.reading.cs2ja16.viskantasjuodenas.robotManager.RobotArena;

public class BottomMenu {
	
	private HBox menuBox;
	private RobotArena robotArena;
	
	/**
	 * BottomMenu constructor, sets up a box of buttons
	 * @param	RobotArena object
	 * @param	RobotCanvasGroup object to interact with
	 */
	BottomMenu(RobotArena robotArena, RobotCanvasGroup robotCanvas) {
		this.robotArena= robotArena;
		
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
    			robotArena.setStatus("add-robot");
    		}
    	});
    	
    	//"Button for adding a random robot
    	//https://stackoverflow.com/questions/31556373/javafx-dialog-with-2-input-fields
    	Button customRobotBtn = new Button("Add Custom Robot");
    	customRobotBtn.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent event) {
    			AddRobotDialog.open(robotArena);
    		}
    	});
    	
    	//Button for moving robots only one position
    	Button moveRobotsOnceBtn = new Button("Move Robots Once");
    	moveRobotsOnceBtn.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent event) {
    			robotArena.setStatus("move-once");
    		}
    	});
    	
    	//Button for moving robots continuously
    	Button moveRobotsBtn = new Button("Move Robots");
    	moveRobotsBtn.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent event) {
    			robotArena.setStatus("move-continuous");
    		}
    	});
    	
    	//Button to stop robots moving continuously
    	Button stopRobotsBtn = new Button("Stop Robots");
    	stopRobotsBtn.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent event) {
    			robotArena.setStatus("stop-movement");
    		}
    	});
    	
    	menuBox.getChildren().addAll(randomRobotBtn, customRobotBtn, moveRobotsOnceBtn, moveRobotsBtn, stopRobotsBtn);
    	return menuBox;
    }
   	/**
	 * @return menuBox
	 */
	public HBox getMenuBar() {
		return menuBox;
	}
}
