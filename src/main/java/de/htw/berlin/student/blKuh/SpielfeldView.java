package de.htw.berlin.student.blKuh;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;

public class SpielfeldView extends JPanel {

	private static final long serialVersionUID = -8673240557748971201L;

	private JTable table;

	public SpielfeldView() {
		this.add(new JLabel("Marcel Test"));
		this.table = new JTable(4, 4);
		this.add(table);
		setupTable();
	}

	private void setupTable() {

		// TableColumnModel tcm = new DefaultTableColumnModel();
//		this.table.setColumnModel(tcm);
//		this.table.addColumn(aColumn);
		// TODO: table mit buttons.
	}
}
