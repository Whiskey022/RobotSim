package uk.ac.reading.cs2ja16.viskantasjuodenas.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import uk.ac.reading.cs2ja16.viskantasjuodenas.robotManager.RobotArena;

public class Main extends Application {

	private int robotSize = 40;
	private int arenaWidth = 10;
	private int arenaHeight = 5;
	private int canvasWitdh = arenaWidth*robotSize;
	private int canvasHeight = arenaHeight*robotSize;
	private RobotArena robotArena;
	private ArenaCanvas robotCanvas;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		robotArena = new RobotArena(arenaWidth, arenaHeight);
		robotCanvas = new ArenaCanvas(canvasWitdh, canvasHeight, robotSize, robotArena);

		primaryStage.setTitle("Robot Simulator");

		BorderPane bp = new BorderPane();

		bp.setTop(new TopMenu(robotArena).getMenuBar());
		bp.setCenter(robotCanvas.getGroup()); // put group in centre pane
		bp.setBottom(new BottomToolbar(robotArena, robotCanvas).getMenuBar()); /// add button to bottom

		Scene scene = new Scene(bp, canvasWitdh*1.2, canvasHeight*1.4);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
