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
	private ArenaCanvas robotCanvas;
	private static Stage primaryStage;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		
		primaryStage = stage;
				
		robotArena = new RobotArena(arenaWidth, arenaHeight);
		robotCanvas = new ArenaCanvas(canvasWitdh, canvasHeight, objectSize, robotArena);

		primaryStage.setTitle("Robot Simulator");

		BorderPane bp = new BorderPane();

		bp.setTop(TopMenu.get(robotArena, robotCanvas));
		bp.setCenter(robotCanvas.getGroup()); // put group in centre pane
		bp.setBottom(new BottomToolbar(robotArena, robotCanvas).getMenuBar()); /// add button to bottom

		Scene scene = new Scene(bp, canvasWitdh*1.2, canvasHeight*1.4);
		primaryStage.setScene(scene);
		primaryStage.setMaximized(true);
		primaryStage.show();
	}
	
	public void newArena(int arenaWidth, int arenaHeight) {
		this.arenaWidth = arenaWidth;
		this.arenaHeight = arenaHeight;
		robotArena = new RobotArena(arenaWidth, arenaHeight);
	}
	
	public static Stage getPrimaryStage() {
		return primaryStage;
	}

}
