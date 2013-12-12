package de.htw.berlin.student.blKuh.actionEvents;

import java.awt.event.ActionEvent;

/**
 * An action event implementation to be used, when a tile in the view was clicked.
 * 
 * @author Matthias Drummer
 */
public class TileClickedActionEvent extends ActionEvent {

	private static final long serialVersionUID = 222555341601029571L;	

	public TileClickedActionEvent(Object source, int id, String command) {
		super(source, id, command);
	}
}
