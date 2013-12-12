package de.htw.berlin.student.blKuh.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * A singleton object for settings.
 * 
 * @author Matthias Drummer <s0542834>
 */
public class Settings {

	private static Settings instance;

	private int dimensionX;
	private int dimensionY;
	private List<Color> colorsToUse = new ArrayList<Color>();

	/**
	 * Private constructor to prevent instantiation.
	 */
	private Settings() {

		// when a new settings instance is created init it with default values.
		initDefaults();
	}

	private void initDefaults() {

		colorsToUse.add(Color.RED);
		colorsToUse.add(Color.BLUE);
		// number of colors 2-5

		// dimension range: >= 10 && <= 40
		dimensionX = 20;
		dimensionY = 15;
	}

	/**
	 * Returns the settings instance.
	 * 
	 * @return the {@link Settings} instance.
	 */
	public static Settings getInstance() {

		if (instance == null) {
			instance = new Settings();
		}
		return instance;
	}

	public void setGameAreaDimensions(int dimensionX, int dimensionY) {
		this.dimensionX = dimensionX;
		this.dimensionY = dimensionY;
	}

	public void setColorsToUse(List<Color> colorsToUse) {

		if (colorsToUse == null) {
			throw new IllegalArgumentException("The given colorsToUse may not be null");
		}

		this.colorsToUse = colorsToUse;
	}

	public List<Color> getColorsToUse() {
		return colorsToUse;
	}

	public int getDimensionX() {
		return dimensionX;
	}

	public int getDimensionY() {
		return dimensionY;
	}
}
