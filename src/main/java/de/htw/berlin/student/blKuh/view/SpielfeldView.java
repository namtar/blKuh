package de.htw.berlin.student.blKuh.view;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import de.htw.berlin.student.blKuh.component.TileButton;
import de.htw.berlin.student.blKuh.model.Settings;

public class SpielfeldView extends JPanel {

	private static final long serialVersionUID = -8673240557748971201L;

	private JTable table;
	private Color[][] matrix;
	private ActionListener tileClickedActionListener;
	private TileButton[][] buttons;

	private JPanel buttonPanel;

	public SpielfeldView(Color[][] matrix) {

		this.matrix = matrix;
		// setupTable();
		setUpPlayground();
	}

	private void setUpPlayground() {

		Settings settings = Settings.getInstance();

		buttonPanel = new JPanel();
		buttons = new TileButton[settings.getDimensionY()][settings.getDimensionX()];
		setLayout(new BorderLayout());
		add(buttonPanel, BorderLayout.CENTER);

		buttonPanel.setLayout(new GridLayout(settings.getDimensionY(), settings.getDimensionX()));

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

	public void rebuild(Color[][] matrix) {

		this.matrix = matrix;
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				// ensure that the buttons map is synchron to the matrix, if not we have a state that may never happen
				if (buttons.length != matrix.length && buttons[i].length != matrix[i].length) {
					throw new IllegalStateException("Buttons array size is not in sync with the data matrix size");
				}
				buttons[i][j].setBackground(matrix[i][j]);

				// if there is no color and the button is not clickable remove action listener
				if (matrix[i][j] == null) {
					for (ActionListener listener : buttons[i][j].getActionListeners()) {
						buttons[i][j].removeActionListener(listener);

					}
					buttons[i][j].setBackground(Color.BLACK);
					buttons[i][j].setEnabled(false);
				}
			}
		}

		buttonPanel.validate();
		buttonPanel.repaint();
	}

	private void setupTable() {

		// TableColumnModel tcm = new DefaultTableColumnModel();
		// this.table.setColumnModel(tcm);
		// this.table.addColumn(aColumn);
		// TODO: table mit buttons.
		// tileClickedActionListener.actionPerformed();

		// we go the easy way and generate a two dimensional array of buttons
		TileButton[][] buttons = new TileButton[this.matrix.length][this.matrix[0].length];
		Dimension buttonSize = new Dimension(10, 10);

		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {

				TileButton tile = new TileButton(j, i, matrix[i][j]);
				tile.setSize(buttonSize);
				tile.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						tileClickedActionListener.actionPerformed(new ActionEvent(e.getSource(), e.getID(), e.getActionCommand()));
					}
				});

				buttons[i][j] = tile;
			}
		}

		String[] columnNames = new String[buttons[0].length];
		for (int i = 0; i < columnNames.length; i++) {
			columnNames[i] = "";
		}

		this.table = new JTable(buttons, columnNames);

		this.add(new JScrollPane(table));
	}

	public void rebuildTable(Color[][] matrix) {
		this.matrix = matrix;

		// TODO:
		// do not instantiate the table newly, instead set only the properties to existing buttons
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
