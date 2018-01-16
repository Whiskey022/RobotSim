package uk.ac.reading.cs2ja16.viskantasjuodenas.gui;

import java.awt.Component;
import java.awt.HeadlessException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

public class FileBrowser {
	
	FileFilter filter;
	File selFile;
	
	//Enum for browser modes
	public static enum BrowserMode{
		SAVE, LOAD;
	}

	public FileBrowser(){
		filter = new FileFilter() {

			@Override
			public boolean accept(File f) {
				if(f.getAbsolutePath().endsWith(".txt")) return true;
				if(f.isDirectory()) return true;
				return false;
			}

			@Override
			public String getDescription() {
				// TODO Auto-generated method stub
				return "txt";
			}
			
		};
	}
	
	//Function to open file browser
	public void browse(BrowserMode browserMode) {
		JFileChooser chooser = new JFileChooser() {
			private static final long serialVersionUID = 1L;

			@Override
             protected JDialog createDialog(Component parent)
                     throws HeadlessException {
                 JDialog dialog = super.createDialog(parent);
                 // config here as needed - just to see a difference
                 dialog.setLocationByPlatform(true);
                 // might help - can't know because I can't reproduce the problem
                 dialog.setAlwaysOnTop(true);
                 return dialog;
             }
		};
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		chooser.setFileFilter(filter);
		int returnVal = 0;
		//Select which mode to open the browser
		switch (browserMode) {
		case SAVE:
			returnVal = chooser.showSaveDialog(null);
			break;
		case LOAD:
			returnVal = chooser.showOpenDialog(null);
			break;
		}
		//Set file to selected one
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			selFile = chooser.getSelectedFile();
			selFile = new File(setFileFormat(selFile.getAbsolutePath()));
		}
	}
	
	public String loadFile () {
		System.out.println("You chose to open this file: " + selFile.getName());
		String text = "";
		
		//File reader
		try{
	        BufferedReader in = new BufferedReader(new FileReader(selFile));
	        String line = null;
	        //Read file lines
	        while ((line = in.readLine()) != null) {
	        	text += line + "\n";
	        }
	    in.close();
	    }catch(IOException ex)
	    {
	           System.err.println("Open plaintext error: "+ex);
	    }
		
		return text;
	}
	
	public void saveFile (String text) {
		try {
			//Set up file writer
			FileWriter writer = new FileWriter(selFile);
			PrintWriter dataStream = new PrintWriter(writer);
			
			//Write text to file
			dataStream.print(text);
			
			dataStream.close();				
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Set file format to .txt
	private String setFileFormat (String name) {
		name = name.split("\\.")[0];
		
		return name + ".txt";
	}
}