package de.htw.berlin.student.blKuh.component;

import java.awt.Color;

import javax.swing.JButton;

/**
 * A class for the tile.
 * 
 * @author Matthias Drummer
 */
public class TileButton extends JButton {

	private static final long serialVersionUID = -5876261296109382081L;

	int choordinateX;
	int choordinateY;

	/**
	 * Constructor
	 * 
	 * @param choordinateX the index for the x value
	 * @param choordinateY the index for the y value
	 * @param color the color of the tile
	 */
	public TileButton(int choordinateX, int choordinateY, Color color) {
		super();

		this.choordinateX = choordinateX;
		this.choordinateY = choordinateY;
		setBackground(color);
	}

	public int getChoordinateX() {
		return choordinateX;
	}

	public int getChoordinateY() {
		return choordinateY;
	}

	@Override
	public String toString() {
		return "TileButton [choordinateX=" + choordinateX + ", choordinateY=" + choordinateY + ", toString()=" + super.toString() + "]";
	}

}
