package de.htw.berlin.student.blKuh;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import de.htw.berlin.student.blKuh.component.TileButton;
import de.htw.berlin.student.blKuh.exceptions.NoNeighborsException;
import de.htw.berlin.student.blKuh.view.OptionsView;
import de.htw.berlin.student.blKuh.view.SpielfeldView;

/**
 * The main window of the blKuh swing application.
 * 
 * @author Matthias Drummer
 * @author Marcel Piater
 */
public class MainWindow extends JFrame implements ActionListener {

	private static final long serialVersionUID = -6730214882576119192L;
	private static final Logger LOGGER = Logger.getLogger(MainWindow.class.getName());

	private final Spielfeld spielFeld;
	private SpielfeldView spielfeldView;

	/**
	 * Constructor.
	 */
	public MainWindow() {

		// init up basic window values
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(800, 600));
		setResizable(false); // we want a window with a static size
		setTitle("Blinde Kuh");

		// initial create Spielfeld object.
		spielFeld = new Spielfeld();

		this.initComponents();

		pack();
		setVisible(true);
	}

	private void initComponents() {

		setJMenuBar(createJMenuBar());
	}

	private JMenuBar createJMenuBar() {

		JMenuBar bar = new JMenuBar();

		JMenu mainMenu = new JMenu();
		mainMenu.setText("Hauptmenü");

		JMenuItem exitItem = new JMenuItem("Beenden");
		exitItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(NORMAL);
			}
		});

		JMenuItem optionItem = new JMenuItem("Optionen");

		optionItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				// open options view
				OptionsView view = new OptionsView();
				getContentPane().removeAll();
				getContentPane().add(view);
				getContentPane().validate();
				getContentPane().repaint();
			}
		});

		JMenuItem backItem = new JMenuItem("Undo");

		backItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				spielFeld.undo();
				// rebuild visible playground
				spielfeldView.rebuild(spielFeld.getMatrix());
				// set points
				spielfeldView.setPoints(spielFeld.getPoints());
			}
		});

		JMenuItem newGameItem = new JMenuItem("Neues Spiel");

		newGameItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				// generate new playground with the values from the settings singleton.
				spielFeld.generate();
				getContentPane().removeAll();

				spielfeldView = new SpielfeldView(spielFeld.getMatrix());
				spielfeldView.setTileClickedActionListener(MainWindow.this);
				getContentPane().add(spielfeldView);
				getContentPane().validate();
				getContentPane().repaint();
			}
		});

		mainMenu.add(newGameItem);
		mainMenu.add(backItem);
		mainMenu.add(optionItem);
		mainMenu.add(exitItem);

		bar.add(mainMenu);

		return bar;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// method is executed when one tile in the spielfeldView was clicked.

		// call the spielfeld class and rebuild the view after calculation.
		if (e.getSource() instanceof TileButton) {
			TileButton button = (TileButton) e.getSource();
			try {
				spielFeld.performButtonClick(button.getChoordinateX(), button.getChoordinateY());
				spielfeldView.rebuild(spielFeld.getMatrix());
				// LOGGER.info("ActionPerfomend: "+ (TileButton) e.getSource());
				// set points
				spielfeldView.setPoints(spielFeld.getPoints());

				// check if there are moves left or if the games is won.
				Boolean hasMovesLeft = spielFeld.hasMovesLeft();
				if (hasMovesLeft == null) {
					// games is won
					JOptionPane.showMessageDialog(this, "Spiel gewonnen. Glückwunsch.");
				} else if (!hasMovesLeft) {
					// no more moves
					JOptionPane.showMessageDialog(this, "Es sind keine Spielzüge mehr möglich.");
				}

			} catch (NoNeighborsException e1) {
				// if no neighbours are present do nothing for the moment.
				LOGGER.info("NoNeighbours exception catched. There is nothing to do at the moment.");
			}
		}
	}
}
