package de.htw.berlin.student.blKuh.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import de.htw.berlin.student.blKuh.component.TileButton;
import de.htw.berlin.student.blKuh.model.Settings;

/**
 * A view panel that displays the playground that consists of clickable buttons and displays the gained points.
 * 
 * @author Matthias Drummer
 * @author Marcel Piater
 */
public class SpielfeldView extends JPanel {

	private static final long serialVersionUID = -8673240557748971201L;

	private Color[][] matrix;
	private ActionListener tileClickedActionListener;
	private TileButton[][] buttons;

	private JPanel buttonPanel;

	private JLabel pointsValueLabel;

	/**
	 * Constructor.
	 * 
	 * @param matrix a two dimensional array of type color which represents our playground.
	 */
	public SpielfeldView(Color[][] matrix) {

		this.matrix = matrix;
		setUpPlayground();
	}

	private void setUpPlayground() {

		Settings settings = Settings.getInstance();

		buttonPanel = new JPanel();
		buttons = new TileButton[settings.getDimensionY()][settings.getDimensionX()];
		setLayout(new BorderLayout());
		add(buttonPanel, BorderLayout.CENTER);
		add(setUpFooter(), BorderLayout.SOUTH);

		buttonPanel.setLayout(new GridLayout(settings.getDimensionY(), settings.getDimensionX()));

		// create playground and fill it with clickable buttons.
		for (int i = 0; i < settings.getDimensionY(); i++) {
			for (int j = 0; j < settings.getDimensionX(); j++) {

				TileButton button = new TileButton(j, i, matrix[i][j]);
				button.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						tileClickedActionListener.actionPerformed(e);
					}
				});

				buttons[i][j] = button;
				buttonPanel.add(button);
			}
		}
	}

	private JPanel setUpFooter() {

		// footer shall display the points.
		JPanel footer = new JPanel();
		JLabel pointsLabel = new JLabel("Punkte: ");
		pointsValueLabel = new JLabel("0");

		footer.add(pointsLabel);
		footer.add(pointsValueLabel);

		return footer;
	}

	/**
	 * Sets the points to the label.
	 * 
	 * @param points
	 */
	public void setPoints(int points) {
		pointsValueLabel.setText(String.valueOf(points));
	}

	/**
	 * Rebuilds the visible playground with the values given by the color matrix.
	 * 
	 * @param matrix the playground area as two dimensional color array
	 */
	public void rebuild(Color[][] matrix) {

		this.matrix = matrix;
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				// ensure that the buttons map is synchron to the matrix, if not we have a state that may never happen
				if (buttons.length != matrix.length && buttons[i].length != matrix[i].length) {
					throw new IllegalStateException("Buttons array size is not in sync with the data matrix size");
				}
				buttons[i][j].setBackground(matrix[i][j]);
				buttons[i][j].setEnabled(true);

				// if there is no tile, set color black and disable button.
				if (matrix[i][j] == null) {
					buttons[i][j].setBackground(Color.BLACK);
					buttons[i][j].setEnabled(false);
				}
			}
		}

		buttonPanel.validate();
		buttonPanel.repaint();
	}

	/**
	 * Sets the tile clicked action listener. It will fire an event when a button has been clicked.
	 * 
	 * @param tileClickedActionListener the tileClickedActionListener
	 */
	public void setTileClickedActionListener(ActionListener tileClickedActionListener) {
		this.tileClickedActionListener = tileClickedActionListener;
	}
}
