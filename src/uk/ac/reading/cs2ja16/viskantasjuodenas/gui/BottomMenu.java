package uk.ac.reading.cs2ja16.viskantasjuodenas.gui;

import java.util.Optional;

import javax.swing.event.ChangeListener;

import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.Image;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.util.Pair;
import uk.ac.reading.cs2ja16.viskantasjuodenas.robotManager.Direction;
import uk.ac.reading.cs2ja16.viskantasjuodenas.robotManager.RobotArena;
import uk.ac.reading.cs2ja16.viskantasjuodenas.robotManager.RobotImages;

public class BottomMenu {
	
	private HBox menuBox;
	private RobotArena robotArena;
	private GraphicsContext gc;
	
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
    			openAddRobotDialog();
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
    
    private void openAddRobotDialog() {
    	// Create the custom dialog.
		Dialog<Pair<String, String>> dialog = new Dialog<>();
		dialog.setTitle("Add a custom Robot");
		
		// Set the button types.
		ButtonType addBtnType = new ButtonType("Add", ButtonData.APPLY);
		dialog.getDialogPane().getButtonTypes().addAll(addBtnType, ButtonType.CANCEL);
		
		//Set parent grid pane
		GridPane parentPane = new GridPane();
		parentPane.setHgap(10);
		parentPane.setVgap(10);
		parentPane.setPadding(new Insets(20, 150, 10, 10));
			
		//Grid pane for coordinates
		GridPane positionPane = new GridPane();
		TextField xField = new TextField();
		xField.setPromptText("X coordinate");
		xField.setMaxWidth(100);
		TextField yField = new TextField();
		yField.setPromptText("Y coordinate");
		yField.setMaxWidth(100);
		//Add text fields to the grid pane
		positionPane.add(new Label("X:  "), 0, 0);
		positionPane.add(xField, 1, 0);
		positionPane.add(new Label("   Y:  "), 2, 0);
		positionPane.add(yField, 3, 0);
				
		//Set up direction selection box
		GridPane directionPane = new GridPane();
		ChoiceBox<String> dirBox = new ChoiceBox<String>();
		//Create a list of directions
		ObservableList<String> dirList = FXCollections.observableArrayList();
		dirList.add("Random");
		for (int i=0; i<Direction.values().length; i++) {
			dirList.add(Direction.values()[i].toString());
		}
		dirBox.setItems(dirList);
		dirBox.setValue("Random");
		//Add direction box to direction grid pane
		directionPane.add(new Label("Select robot's direction:    "), 0, 0);
		directionPane.add(dirBox, 1, 0);
				
		//Set up a grid pane of image selection
		GridPane imagePane = new GridPane();
		ChoiceBox<String> imageBox = new ChoiceBox<String>();
		//Create a list of images indexes
		ObservableList<String> imageList = FXCollections.observableArrayList();
		imageList.add("Random");
		for (int i=0; i<new RobotImages().getSize(); i++) {
			imageList.add(String.valueOf(i));
		}
		imageBox.setItems(imageList);
		imageBox.setValue("Random");
		//https://stackoverflow.com/questions/14522680/javafx-choicebox-events
		imageBox.getSelectionModel().selectedItemProperty()
			.addListener( (ObservableValue<? extends String> observable, String oldValue, String newValue) 
					->  drawIt(newValue, 20, 20, 20));
		//Set up Group object to draw robot image
		Group root = new Group();
		Canvas canvas = new Canvas(50, 50);
		root.getChildren().add(canvas);
		gc = canvas.getGraphicsContext2D();
		//Add image box to images grid pane
		imagePane.add(new Label("Select robot image index: "), 0, 0);
		imagePane.add(imageBox, 1, 0);
		imagePane.add(root, 2, 0);
		
		//Add all grid panes to the parent pane
		parentPane.add(new Label("Input robot coordinates"), 0, 0);
		parentPane.add(positionPane, 0, 1);
		parentPane.add(directionPane, 0, 2);
		parentPane.add(imagePane, 0, 3);

		dialog.getDialogPane().setContent(parentPane);
		
		// Request focus on the username field by default.
		Platform.runLater(() -> xField.requestFocus());
		
		// Convert the result to a username-password-pair when the login button is clicked.
		dialog.setResultConverter(dialogButton -> {
		    if (dialogButton == addBtnType) {
		        return new Pair<>(xField.getText(), yField.getText());
		    }
		    return null;
		});
		
		Optional<Pair<String, String>> result = dialog.showAndWait();
		
		result.ifPresent(pair -> {
		    System.out.println("From=" + pair.getKey() + ", To=" + pair.getValue());
		});
    }
    
    /**
	 * drawIt ... draws object defined by given image at position and size
	 * @param i		image
	 * @param x		x position
	 * @param y		y position
	 * @param sz	size
	 */
    private void drawIt (String imageIndex, double x, double y, double sz) {
   		gc.clearRect(0,  0,  50, 50);		// clear canvas
    	if (imageIndex != "Random") {
    		// to draw centred at x,y, give top left position and x,y size
    		gc.drawImage(new RobotImages().getImage(Integer.parseInt(imageIndex)), x - sz/2, y - sz/2, sz, sz);
    	}
	}
   	
   	/**
	 * @return menuBox
	 */
	public HBox getMenuBar() {
		return menuBox;
	}
}
