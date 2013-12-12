package de.htw.berlin.student.blKuh;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import de.htw.berlin.student.blKuh.exceptions.NoNeighborsException;
import de.htw.berlin.student.blKuh.model.TileButton;
import de.htw.berlin.student.blKuh.view.OptionsView;
import de.htw.berlin.student.blKuh.view.SpielfeldView;

/**
 * The main window of the blKuh swing application.
 * 
 * @author Matthias Drummer
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
		// setContentPane(new SpielfeldView());

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
				// hier das Optionsmenü öffen und danach die Einstellungen in der Spielfeld Klasse speichern.
				// machen wir über ein popup und setzen die werte auf die spielfeld klasse.
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
				// hier einen Zug rückgängig machen falls möglich.
				// informationen dazu finden sich in der spielfeld klasse
			}
		});

		JMenuItem newGameItem = new JMenuItem("Neues Spiel");

		newGameItem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// hier ein neues spielfeld erzeugen anhand der zuvor eingestellten optionen.
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
				spielfeldView.rebuildTable(spielFeld.getMatrix());
				// TODO: count points
			} catch (NoNeighborsException e1) {
				// if no neighbours are present do nothing for the moment.
			}
		}
	}
}
