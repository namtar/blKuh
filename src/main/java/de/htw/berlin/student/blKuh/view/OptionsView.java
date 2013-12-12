package de.htw.berlin.student.blKuh.view;

import java.awt.BorderLayout;

import info.clearthought.layout.TableLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import de.htw.berlin.student.blKuh.model.Settings;

/**
 * A panel for the options view to adjust color and playground size.
 * 
 * @author Matthias Drummer <s0542834>
 */
public class OptionsView extends JPanel {

	private static final long serialVersionUID = 4658090708394976895L;

	private Settings settings;

	private JLabel dimensionXLabel;

	private JLabel dimensionYLabel;

	private JTextField dimensionXTextField;

	private JTextField dimensionYTextField;

	/**
	 * Constructor.
	 */
	public OptionsView() {
		this.settings = Settings.getInstance();
		initUiElements();
	}

	private void initUiElements() {

		JPanel fieldsPanel = new JPanel();

		// init table layout with 2 rows and cols
		double[] rows = { TableLayout.FILL, TableLayout.FILL };
		double[] cols = { TableLayout.FILL, TableLayout.FILL };

		TableLayout tableLayout = new TableLayout(rows, cols);
		fieldsPanel.setLayout(tableLayout);

		dimensionXLabel = new JLabel("Breite");
		dimensionYLabel = new JLabel("Höhe");

		dimensionXTextField = new JTextField(settings.getDimensionX());
		dimensionYTextField = new JTextField(settings.getDimensionY());

		fieldsPanel.add(dimensionXLabel, "0,0");
		fieldsPanel.add(dimensionXTextField, "0,1");
		fieldsPanel.add(dimensionYLabel, "1,0");
		fieldsPanel.add(dimensionYTextField, "1,1");

		this.add(fieldsPanel, BorderLayout.NORTH);
		setLayout(new BorderLayout());
	}
}
