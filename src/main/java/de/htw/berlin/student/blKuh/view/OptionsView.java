package de.htw.berlin.student.blKuh.view;

import info.clearthought.layout.TableLayout;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import de.htw.berlin.student.blKuh.model.Settings;

/**
 * A panel for the options view to adjust color and playground size.
 * 
 * @author Matthias Drummer <s0542834>
 */
public class OptionsView extends JPanel {

	private static final long serialVersionUID = 4658090708394976895L;
	private static final Logger LOGGER = Logger.getLogger(OptionsView.class.getName());

	private Settings settings;

	private JLabel dimensionXLabel;
	private JLabel dimensionYLabel;
	private JLabel numberOfColorsLabel;

	private JTextField dimensionXTextField;
	private JTextField dimensionYTextField;

	private JSlider numberOfColorsSlider;

	private JPanel buttonsPanel;
	private JLabel errorMessageLabel;

	/**
	 * Constructor.
	 */
	public OptionsView() {
		this.settings = Settings.getInstance();
		initUiElements();
	}

	private void initUiElements() {

		JPanel fieldsPanel = new JPanel();

		JPanel centerWrapper = new JPanel();
		BoxLayout boxLayout = new BoxLayout(centerWrapper, BoxLayout.PAGE_AXIS);
		centerWrapper.setLayout(boxLayout);

		// create error message panel
		JPanel messagePanel = new JPanel();
		errorMessageLabel = new JLabel("");
		messagePanel.add(errorMessageLabel);

		initNumberOfColorSlider();

		// init table layout with 2 rows and cols
		double[] rows = { TableLayout.FILL, TableLayout.FILL, TableLayout.FILL };
		double[] cols = { TableLayout.FILL, TableLayout.FILL };

		TableLayout tableLayout = new TableLayout(cols, rows);
		fieldsPanel.setLayout(tableLayout);

		dimensionXLabel = new JLabel("Breite");
		dimensionYLabel = new JLabel("Höhe");
		numberOfColorsLabel = new JLabel("Anzahl Farben");

		dimensionXTextField = new JTextField();
		dimensionXTextField.setPreferredSize(new Dimension(10, dimensionXTextField.getHeight()));
		dimensionXTextField.setText(String.valueOf(settings.getDimensionX()));
		dimensionYTextField = new JTextField();
		dimensionYTextField.setText(String.valueOf(settings.getDimensionY()));

		fieldsPanel.add(dimensionXLabel, "0,0");
		fieldsPanel.add(dimensionXTextField, "1,0");
		fieldsPanel.add(dimensionYLabel, "0,1");
		fieldsPanel.add(dimensionYTextField, "1,1");
		fieldsPanel.add(numberOfColorsLabel, "0,2");
		fieldsPanel.add(numberOfColorsSlider, "1,2");

		setLayout(new BorderLayout());
		this.add(fieldsPanel, BorderLayout.NORTH);

		buttonsPanel = new JPanel(new FlowLayout());
		initColorButtons();

		centerWrapper.add(buttonsPanel);
		centerWrapper.add(messagePanel);

		this.add(centerWrapper, BorderLayout.CENTER);
		this.add(createFooter(), BorderLayout.SOUTH);

	}

	private JPanel createFooter() {

		JPanel panel = new JPanel();

		panel.setLayout(new FlowLayout());

		JButton saveButton = new JButton("Speichern");
		saveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// schreibe alle Einstellungen in das Settings Objekt und validiere ob die Einstellungen in Ordnung
				// sind.
				boolean validationError = false;

				int dimensionX = Integer.valueOf(dimensionXTextField.getText());
				int dimensionY = Integer.valueOf(dimensionYTextField.getText());

				List<Color> colorsToUse = new ArrayList<Color>();

				for (Component component : buttonsPanel.getComponents()) {
					if (component instanceof JButton) {
						JButton button = (JButton) component;

						if (colorsToUse.contains(button.getBackground())) {
							validationError = true;
							errorMessageLabel.setText("<html>Es darf keine Farbdubletten geben.<br>Einstellungen wurden nicht gespeichert</html>");
							break;
						}
						colorsToUse.add(button.getBackground());
					}
				}

				// validate dimensions before proceeding if no error has occured before
				if (!validationError) {
					if (dimensionX < 10 || dimensionX > 40 || dimensionY < 10 || dimensionY > 40) {
						errorMessageLabel.setText("Die erlaubten Werte für X und oder Y müssen >= 10 und <= 40 sein.");
						validationError = true;
					}
				}

				if (!validationError) {
					settings.setGameAreaDimensions(dimensionX, dimensionY);
					settings.setColorsToUse(colorsToUse);
					errorMessageLabel.setText("");
					JOptionPane.showMessageDialog(OptionsView.this, "Gespeichert");
				}
			}
		});

		panel.add(saveButton);

		return panel;
	}

	private void initColorButtons() {

		buttonsPanel.removeAll();
		for (int i = 0; i < numberOfColorsSlider.getValue(); i++) {
			JButton button = new JButton();
			button.setPreferredSize(new Dimension(20, 20));

			if (settings.getColorsToUse().size() > i) {
				button.setBackground(settings.getColorsToUse().get(i));
			} else {
				button.setBackground(Color.GRAY);
			}

			button.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub

					// verhindere, dass mehrmals die gleiche Farbe ausgewählt werden darf.
					// stelle sicher, dass nur soviele Farben in der Liste sind, wie im slider ausgewählt.

					JButton eventButton = (JButton) e.getSource();

					eventButton.setBackground(JColorChooser.showDialog(eventButton, "Farbauswahl", eventButton.getBackground()));
				}
			});

			buttonsPanel.add(button);
		}

	}

	private void initNumberOfColorSlider() {

		numberOfColorsSlider = new JSlider(2, 5);
		numberOfColorsSlider.setMinorTickSpacing(1);
		numberOfColorsSlider.setMajorTickSpacing(1);
		numberOfColorsSlider.setPaintTicks(true);
		numberOfColorsSlider.setPaintTrack(true);
		numberOfColorsSlider.setPaintLabels(true);
		numberOfColorsSlider.setValueIsAdjusting(true);
		numberOfColorsSlider.setValue(settings.getColorsToUse().size());
		numberOfColorsSlider.setSnapToTicks(true);
		numberOfColorsSlider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {

				initColorButtons();
				validate();
				repaint();
			}
		});
	}
}
