package uk.ac.reading.cs2ja16.viskantasjuodenas.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import uk.ac.reading.cs2ja16.viskantasjuodenas.robotManager.RobotArena;

public class Main extends Application{
	
	private int canvasSize = 512;
	private int robotSize = 40;
	private RobotArena robotArena;
    private RobotCanvasGroup robotCanvas;

	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		robotArena = new RobotArena(canvasSize/(int)robotSize, canvasSize/robotSize, 10);
		robotCanvas = new RobotCanvasGroup(canvasSize, canvasSize, robotSize);
		
		primaryStage.setTitle("Robot Simulator");
		
		BorderPane bp = new BorderPane();
		
		bp.setTop(new TopMenu().getMenuBar());
		bp.setCenter(robotCanvas.getGroup());		// put group in centre pane
		bp.setBottom(new BottomMenu(robotArena, robotCanvas).getMenuBar());					/// add button to bottom
		
		Scene scene = new Scene(bp, canvasSize*1.4, canvasSize*1.2);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	
}
