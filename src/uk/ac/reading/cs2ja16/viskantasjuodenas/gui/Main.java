package uk.ac.reading.cs2ja16.viskantasjuodenas.gui;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import uk.ac.reading.cs2ja16.viskantasjuodenas.robotManager.RobotArena;

public class Main extends Application{
	
	private int canvasSize = 512;
	private double robotSize = 40;
    private VBox rtPane;
    private RobotArena robotArena;

	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		robotArena = new RobotArena(canvasSize/(int)robotSize, canvasSize/(int)robotSize, 10);
		
		primaryStage.setTitle("Robot Simulator");
		
		BorderPane bp = new BorderPane();
		
		bp.setTop(new TopMenu().getMenuBar());
		
		Group root = new Group();					// create group
	    Canvas canvas = new Canvas( canvasSize, canvasSize );
	    											// and canvas to draw in
	    root.getChildren().add( canvas );			// and add canvas to group
	    bp.setCenter(root);							// put group in centre pane

	    rtPane = new VBox();						// set vBox for listing data
	    bp.setRight(rtPane);						// put in right pane

	    bp.setBottom(new BottomMenu(robotArena, canvas).getMenuBar());					/// add button to bottom
		
		Scene scene = new Scene(bp, canvasSize*1.4, canvasSize*1.2);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	
}
