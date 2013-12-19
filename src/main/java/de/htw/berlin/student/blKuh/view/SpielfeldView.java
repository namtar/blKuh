package de.htw.berlin.student.blKuh.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import de.htw.berlin.student.blKuh.model.TileButton;

public class SpielfeldView extends JPanel {

	private static final long serialVersionUID = -8673240557748971201L;

	private JTable table;
	private Color[][] matrix;
	private ActionListener tileClickedActionListener;

	public SpielfeldView(Color[][] matrix) {

		this.matrix = matrix;

		this.table = new JTable();

		this.add(new JScrollPane(table));
		setupTable();
	}

	private void setupTable() {

		// TableColumnModel tcm = new DefaultTableColumnModel();
		// this.table.setColumnModel(tcm);
		// this.table.addColumn(aColumn);
		// TODO: table mit buttons.
		// tileClickedActionListener.actionPerformed();

		// we go the easy way and generate a two dimensional array of buttons
		TileButton[][] buttons = new TileButton[this.matrix.length][this.matrix[0].length];

		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {

				TileButton tile = new TileButton(j, i, matrix[i][j]);
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
		this.validate();
		this.repaint();
	}

	public void rebuildTable(Color[][] matrix) {
		this.matrix = matrix;
		setupTable();
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
