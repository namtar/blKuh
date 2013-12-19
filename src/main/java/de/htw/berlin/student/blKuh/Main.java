package de.htw.berlin.student.blKuh;

import java.util.UUID;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

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

				try {
					UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (UnsupportedLookAndFeelException e) {
					e.printStackTrace();
				}

				MainWindow mainWindow = new MainWindow();
			}
		};

		SwingUtilities.invokeLater(runner);

	}

}
