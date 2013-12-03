package de.htw.berlin.student.blKuh.view;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;

public class SpielfeldView extends JPanel {

	private static final long serialVersionUID = -8673240557748971201L;

	private JTable table;
	private Color[][] matrix;

	public SpielfeldView(Color[][] matrix) {

		this.matrix = matrix;

		this.add(new JLabel("Marcel Test"));
		this.table = new JTable(4, 4);
		this.add(table);
		setupTable();
	}

	private void setupTable() {

		// TableColumnModel tcm = new DefaultTableColumnModel();
		// this.table.setColumnModel(tcm);
		// this.table.addColumn(aColumn);
		// TODO: table mit buttons.
	}
}
