package de.htw.berlin.student.blKuh;

import javax.swing.SwingUtilities;

/**
 * Main class for the blKuh game.
 * 
 * @author Matthias Drummer
 */
public class Main {

	public static void main(String[] args) {

		Runnable runner = new Runnable() {
			
			@Override
			public void run() {

				MainWindow mainWindow = new MainWindow();											
			}
		};
		
		SwingUtilities.invokeLater(runner);

	}

}
