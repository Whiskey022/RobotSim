package uk.ac.reading.cs2ja16.viskantasjuodenas.gui;

import java.awt.Dimension;
import java.awt.Toolkit;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import uk.ac.reading.cs2ja16.viskantasjuodenas.robotManager.RobotArena;

public class Main extends Application {

	private int arenaWidth = 20;
	private int arenaHeight = 20;
	private int objectSize = (int) (140/Math.sqrt((Math.sqrt(arenaHeight * arenaWidth))));
	private int canvasWitdh = arenaWidth*objectSize;
	private int canvasHeight = arenaHeight*objectSize;
	private RobotArena robotArena;
	private ArenaCanvas robotCanvas;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		calculateDimensions();
		
		robotArena = new RobotArena(arenaWidth, arenaHeight);
		robotCanvas = new ArenaCanvas(canvasWitdh, canvasHeight, objectSize, robotArena);

		primaryStage.setTitle("Robot Simulator");

		BorderPane bp = new BorderPane();

		bp.setTop(new TopMenu(robotArena).getMenuBar());
		bp.setCenter(robotCanvas.getGroup()); // put group in centre pane
		bp.setBottom(new BottomToolbar(robotArena, robotCanvas).getMenuBar()); /// add button to bottom

		Scene scene = new Scene(bp, canvasWitdh*1.2, canvasHeight*1.4);
		primaryStage.setScene(scene);
		primaryStage.setMaximized(true);
		primaryStage.show();
	}
	
	private void calculateDimensions() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double screenHeight = screenSize.getHeight();
		double screenWidth = screenSize.getWidth();
	}

}
