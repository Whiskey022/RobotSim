package uk.ac.reading.cs2ja16.viskantasjuodenas.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import uk.ac.reading.cs2ja16.viskantasjuodenas.robotManager.RobotArena;

public class Main extends Application {

	private int arenaWidth = 15;
	private int arenaHeight = 15;
	private int objectSize = 35;
	private int canvasWitdh = arenaWidth*objectSize;
	private int canvasHeight = arenaHeight*objectSize;
	private RobotArena robotArena;
	private ArenaCanvas arenaCanvas;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Robot Sim");
		
		//Set up robot arena and a canvas to draw it on
		robotArena = new RobotArena(arenaWidth, arenaHeight);
		arenaCanvas = new ArenaCanvas(canvasWitdh, canvasHeight, objectSize, robotArena);
	
		BorderPane bp = new BorderPane();

		bp.setTop(TopMenu.get(robotArena, arenaCanvas));							//Adding top menu
		bp.setCenter(arenaCanvas.getGroup());										//Adding arena canvas where robots are drawn
		bp.setBottom(new BottomToolbar(robotArena, arenaCanvas).getMenuBar());		//Adding button toolBar

		Scene scene = new Scene(bp, canvasWitdh*1.2, canvasHeight*1.4);
		primaryStage.setScene(scene);
		primaryStage.setMaximized(true);
		primaryStage.show();
	}

}
