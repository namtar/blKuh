package de.htw.berlin.student.blKuh;

import java.awt.Dimension;

import javax.swing.JFrame;

/**
 * The main window of the blKuh swing application.
 * 
 * @author Matthias Drummer
 */
public class MainWindow extends JFrame {

	private static final long serialVersionUID = -6730214882576119192L;

	/**
	 * Constructor.
	 */
	public MainWindow() {

		// init up basic window values
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(800, 600));
		setResizable(false); // we want a window with a static size
		setTitle("Blinde Kuh");
		setContentPane(new SpielfeldView());

		pack();
		setVisible(true);
	}
}
