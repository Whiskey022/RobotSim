package uk.ac.reading.cs2ja16.viskantasjuodenas.robotManager;

import java.util.Scanner;

public class RobotInterface {
	Scanner s; // scanner used for input from user
	RobotArena myArena; // arena in which robots are shown
	char[][] arenaDisplay;
	FileBrowser fileBrowser = new FileBrowser();

	/**
	 * constructor for RobotInterface sets up scanner used for input and the arena
	 * then has main loop allowing user to enter commands
	 */
	public RobotInterface() {
		s = new Scanner(System.in); // set up scanner for user input
		myArena = new RobotArena(20, 6, 5); // create arena of size 20*6
		setDisplayArena();
		// with up to 5 robots

		char ch = ' ';
		do {
			System.out.print("Enter (D)isplay arena, (M)ove robots, (A)dd robot, give (I)nformation, (N)ew arena, (S)ave arena, (L)oad arena or e(X)it > ");
			ch = s.next().charAt(0);
			s.nextLine();
			switch (ch) {
			case 'D':
			case 'd':
				myArena.showRobots(this);
				displayArena();
				break;
			case 'M':
			case 'm':
				myArena.moveAllRobots();
				clearArena();
				myArena.showRobots(this);
				displayArena();
				break;
			case 'A':
			case 'a':
				myArena.addRobot(); // add a new robot to arena
				break;
			case 'I':
			case 'i': // display info about each robot
				System.out.print(myArena.toString());
				break;
			case 'S':
			case 's':
				saveArena();
				break;
			case 'L':
			case 'l':
				loadArena();
				setDisplayArena();
				break;
			case 'N':
			case 'n':
				System.out.print("Enter arena's size and robots limit (as x y max) > ");
				setNewArena(s.nextInt(), s.nextInt(), s.nextInt());
				break;
			case 'x':
				ch = 'X'; // when X detected program ends
				break;
			}
		} while (ch != 'X'); // test if end
		s.close(); // close scanner
	}

	public void setNewArena (int x, int y, int maxRobots) {
		myArena = new RobotArena(x, y, maxRobots);
		setDisplayArena();
	}
	
	//Function to open browser in save mode, and save arena details
	public void saveArena () {
		fileBrowser.browse(FileBrowser.BrowserMode.SAVE);
		fileBrowser.saveFile(myArena.getDetails());
	}
	
	//Function to open browser in load mode, and update arena details
	public void loadArena () {
		fileBrowser.browse(FileBrowser.BrowserMode.LOAD);
		String loadedText = fileBrowser.loadFile();
		//Get arena and each robot details in separate string array
		String[] lines = loadedText.split("\\|");
		
		//Extract arena details
		String[] values = lines[0].split(":");
		myArena.setXSize(Integer.parseInt(values[0]));
		myArena.setYSize(Integer.parseInt(values[1]));
		myArena.setMaxRobots(Integer.parseInt(values[2]));
		myArena.setRobotsCounter(lines.length-2);
		
		//Create new robot array
		Robot[] robots = new Robot[myArena.getMaxRobots()];
		
		//Loop to create new robots with extracted details
		for (int i=0; i<lines.length-2; i++) {
			values = lines[i+1].split(":");
			robots[i] = new Robot(
					Integer.parseInt(values[1]),		//robot x
					Integer.parseInt(values[2]),		//robot y
					Direction.valueOf(values[3]),		//robot direction
					myArena								//robot's arena
					);
			robots[i].setId(Integer.parseInt(values[0]));	//Set ID
		}
		
		myArena.setRobots(robots);						//Give robots array to arena
	}

	void setDisplayArena() {
		//x and y +2 because walls take extra space
		int x = myArena.getXSize() + 2;
		int y = myArena.getYSize() + 2;
		arenaDisplay = new char[x][y];

		// Set first row
		arenaDisplay[0][0] = '/';
		for (int i = 1; i < x - 1; i++) {
			arenaDisplay[i][0] = '-';
		}
		arenaDisplay[x - 1][0] = '\\';

		// Set middle rows
		for (int j = 1; j < y - 1; j++) {
			arenaDisplay[0][j] = '|';
			for (int i = 1; i < x - 1; i++) {
				arenaDisplay[i][j] = ' ';
			}
			arenaDisplay[x - 1][j] = '|';
		}

		// Set last row
		arenaDisplay[0][y - 1] = '\\';
		for (int i = 1; i < x - 1; i++) {
			arenaDisplay[i][y - 1] = '-';
		}
		arenaDisplay[x - 1][y - 1] = '/';
	}

	void displayArena() {
		// Loop to printout all arena characters
		for (int j = 0; j < arenaDisplay[0].length; j++) {
			for (int i = 0; i < arenaDisplay.length; i++) {
				System.out.print(arenaDisplay[i][j]);
			}
			System.out.println();
		}
	}

	void showRobot(int x, int y) {
		arenaDisplay[x][y] = 'R';
	}

	private void clearArena() {
		for (int i = 1; i < arenaDisplay.length - 1; i++) {
			for (int j = 1; j < arenaDisplay[0].length - 1; j++) {
				arenaDisplay[i][j] = ' ';
			}
		}
	}

	public static void main(String[] args) {
		RobotInterface r = new RobotInterface(); // just call the interface
	}
}
