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

/**
 * File browsing dialog object to save or load arenas
 */
public class FileBrowser {

	FileFilter filter;
	File selFile;

	/**
	 * Enum for determining if browser is in saving or loading mode
	 */
	public static enum BrowserMode {
		SAVE, LOAD;
	}

	/**
	 * FileBrowser constructor, overrides file accepting getting description
	 * functions
	 */
	public FileBrowser() {
		filter = new FileFilter() {

			@Override
			public boolean accept(File f) {
				//accept txt files
				if (f.getAbsolutePath().endsWith(".txt"))
					return true;
				//accept if its directory
				if (f.isDirectory())
					return true;
				return false;
			}

			@Override
			public String getDescription() {
				// TODO Auto-generated method stub
				return "txt";
			}

		};
	}

	/**
	 * Open file browser
	 * 
	 * @param browserMode saving or loading browser mode
	 */
	public void browse(BrowserMode browserMode) {
		JFileChooser chooser = new JFileChooser() {
			private static final long serialVersionUID = 1L;

			//Overriding createDialog to force browser to appear on top of gui
			@Override
			protected JDialog createDialog(Component parent) throws HeadlessException {
				JDialog dialog = super.createDialog(parent);
				// config here as needed - just to see a difference
				dialog.setLocationByPlatform(true);
				// might help - can't know because I can't reproduce the problem
				dialog.setAlwaysOnTop(true);
				return dialog;
			}
		};
		
		//Setting up chooser selection mode and filter
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		chooser.setFileFilter(filter);
		
		// Select which mode to open the browser
		int returnVal = 0;
		switch (browserMode) {
		case SAVE:
			returnVal = chooser.showSaveDialog(null);
			break;
		case LOAD:
			returnVal = chooser.showOpenDialog(null);
			break;
		}
		
		// Set file to selected one
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			selFile = chooser.getSelectedFile();
			selFile = new File(setFileFormat(selFile.getAbsolutePath()));
		}
	}

	/**
	 * Load file's text content
	 * @return	file's text content
	 */
	public String loadFile() {
		String text = "";

		// File reader
		try {
			BufferedReader in = new BufferedReader(new FileReader(selFile));
			String line = null;
			// Read file lines
			while ((line = in.readLine()) != null) {
				text += line + "\n";
			}
			in.close();
		} catch (IOException ex) {
			AlertMessage.show("Error", "Failed to read file.", true);
		}

		return text;
	}

	/**
	 * Save String to a file
	 * @param text	String to save
	 */
	public void saveFile(String text) {
		//Try saving
		try {
			// Set up file writer
			FileWriter writer = new FileWriter(selFile);
			PrintWriter dataStream = new PrintWriter(writer);

			// Write text to file
			dataStream.print(text);

			dataStream.close();
		} catch (FileNotFoundException e) {
			AlertMessage.show("Error", "Failed to save file.", true);
			e.printStackTrace();
		} catch (IOException e) {
			AlertMessage.show("Error", "Failed to save file.", true);
			e.printStackTrace();
		}
	}

	/**
	 * Set file name to have .txt extension
	 * @param name	filename to add extension to
	 * @return	filename with extension
	 */
	private String setFileFormat(String name) {
		//If filename already has an extension, remove that and add txt
		name = name.split("\\.")[0];
		return name + ".txt";
	}
}