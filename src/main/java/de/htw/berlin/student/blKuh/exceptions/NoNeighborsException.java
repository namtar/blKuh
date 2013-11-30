package de.htw.berlin.student.blKuh.exceptions;

/**
 * Exception to be thrown if the given tile has no neighbors.
 * 
 * @author MP
 */
public class NoNeighborsException extends Exception {

	private static final long serialVersionUID = -932257347944473958L;

	/**
	 * Constructor.
	 * 
	 * @param message
	 *            the message for the exception
	 */
	public NoNeighborsException(String message) {
		super(message);
	}
}
