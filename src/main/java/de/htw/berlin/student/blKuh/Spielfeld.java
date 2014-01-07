package de.htw.berlin.student.blKuh;

import java.awt.Color;
import java.util.List;
import java.util.Random;

import de.htw.berlin.student.blKuh.exceptions.NoNeighborsException;
import de.htw.berlin.student.blKuh.model.Settings;

/**
 * Class that holds the playground data and calculate its state when events happen.
 * 
 * @author Matthias Drummer
 * @author Marcel Piater
 */
public class Spielfeld {

	private Color[][] matrix;
	private Color[][] undoMatrix;
	private Settings settings;

	private int currentPoints = 0;
	private int undoPoints = 0;
	private int tileCounter = 0;
	private int columnCounter = 0;

	int dimensionX;
	int dimensionY;
	List<Color> colorsToUse;

	int numberOfEmptyColumns = 0;
	int undoNumberOfEmptyColumns = 0;

	/**
	 * Default constructor.
	 */
	public Spielfeld() {
		settings = Settings.getInstance();
		generate();
	}

	public void generate() {

		dimensionX = settings.getDimensionX();
		dimensionY = settings.getDimensionY();
		colorsToUse = settings.getColorsToUse();
		currentPoints = 0;
		undoPoints = 0;
		numberOfEmptyColumns = 0;
		undoNumberOfEmptyColumns = 0;

		matrix = new Color[dimensionY][dimensionX];

		undoMatrix = null;

		Random rand = new Random();

		for (int i = 0; i < dimensionY; i++) {
			for (int j = 0; j < dimensionX; j++) {
				int number = rand.nextInt(colorsToUse.size());
				matrix[i][j] = colorsToUse.get(number);
			}
		}

	}

	private void createUndo() {

		undoPoints = currentPoints;
		undoNumberOfEmptyColumns = numberOfEmptyColumns;

		undoMatrix = new Color[dimensionY][dimensionX];

		for (int i = 0; i < dimensionY; i++) {
			for (int j = 0; j < dimensionX; j++) {
				undoMatrix[i][j] = matrix[i][j];
			}
		}
	}

	/**
	 * Undoes one step if possible.
	 */
	public void undo() {

		if (undoMatrix != null) {

			for (int i = 0; i < dimensionY; i++) {
				for (int j = 0; j < dimensionX; j++) {
					matrix[i][j] = undoMatrix[i][j];
				}
			}

			numberOfEmptyColumns = undoNumberOfEmptyColumns;
			currentPoints = undoPoints;
		}
	}

	/**
	 * Method called when one tile has been clicked. The points are counted and an undo matrix is created.
	 * 
	 * @param choordX the x coordinate of the tile which has been clicked
	 * @param choordY the y coordinate of the tile which has been clicked
	 * @throws NoNeighborsException thrown if there are no neighbors nearby the clicked tile.
	 */
	public void performButtonClick(int choordX, int choordY) throws NoNeighborsException {
		// TODO: wird aufgerufen, wenn ein Button geklickt wurde. Die
		// Choorinaten entsprechen den Indexes auf der Matrix für x und y.
		// 1. prüfe nachbarn und füge diese einer zu entfernen Liste hinzu
		// wenn der gegebene Spielstein keine Nachbarn gleicher Farbe hat, dann
		// werfe die NoNeighborsExcetpion
		// throw new
		// NoNeighborsException("Tile has no neighbors with equal color.");
		// 2. Schließe Löcher, in dem man Steine nach unten Fallen lässt.
		// Unser Startstein ist ein Sonderfall. Der muss bevor der Rekursion
		// aufgerufen wird, prüfen ob er Nachbarn hat und seine Farbe
		// ermitteln.

		// set points to zero and undo
		tileCounter = 0;
		columnCounter = 0;

		Color colorToCompare = matrix[choordY][choordX];
		boolean hasNeighbors = hasNeighbors(choordY, choordX, colorToCompare);

		// TODO: prüfe ob überhaupt noch weitere Züge möglich sind.
		if (!hasNeighbors) {
			throw new NoNeighborsException("Tile has no neighbors with equal color.");
		} else {
			// create undo when there are neighbors.
			createUndo();
		}

		checkNeighbors(choordX, choordY, colorToCompare);
		cleanUpRows();

		// !important. Before cleaning up any columns count empty columns. This is not the best style because we have
		// additional iterations over the whole matrix.
		countPoints();
		cleanUpColumns();
	}

	private void countPoints() {

		countEmptyColumns();
		currentPoints += 2 * tileCounter - 2;

		if (columnCounter > numberOfEmptyColumns) {
			int diff = columnCounter - numberOfEmptyColumns;
			currentPoints += diff * 10;
			numberOfEmptyColumns = columnCounter;
		}
	}

	private void countEmptyColumns() {

		for (int i = dimensionX - 1; i >= 0; i--) {
			if (checkForEmptyColumn(i)) {
				columnCounter++;
			}
		}
	}

	private boolean hasNeighbors(int choordY, int choordX, Color colorToCompare) {

		boolean hasNeighbors = false;

		if ((choordY + 1) < matrix.length && matrix[choordY + 1][choordX] != null) {
			// do not check in a direction, where no neighbours can be
			if (matrix[choordY + 1][choordX].equals(colorToCompare)) {
				hasNeighbors = true;
			}
		}

		if ((choordY - 1) >= 0 && matrix[choordY - 1][choordX] != null) {
			// do not check in a direction, where no neighbours can be
			if (matrix[choordY - 1][choordX].equals(colorToCompare)) {
				hasNeighbors = true;
			}
		}

		if ((choordX + 1) < matrix[choordY].length && matrix[choordY][choordX + 1] != null) {
			if (matrix[choordY][choordX + 1].equals(colorToCompare)) {
				hasNeighbors = true;
			}
		}

		if ((choordX - 1) >= 0 && matrix[choordY][choordX - 1] != null) {
			if (matrix[choordY][choordX - 1].equals(colorToCompare)) {
				hasNeighbors = true;
			}
		}

		return hasNeighbors;
	}

	private void checkNeighbors(int choordX, int choordY, Color colorToCompare) {

		// wenn eigene Color gleich der colorToCompare dann mache weiter
		if (matrix[choordY][choordX].equals(colorToCompare)) {

			// wenn die Farbe übereinstimmt, dann entferne aus der Matrix
			matrix[choordY][choordX] = null;
			tileCounter++;

			if (((choordX + 1) != dimensionX) && matrix[choordY][choordX + 1] != null) {
				// wenn er einen Nachbar hat auf z.B. choordX + 1 und choordY
				checkNeighbors(choordX + 1, choordY, colorToCompare);
			}
			if (((choordX - 1) >= 0) && matrix[choordY][choordX - 1] != null) {
				checkNeighbors(choordX - 1, choordY, colorToCompare);
			}
			if (((choordY + 1) != dimensionY) && matrix[choordY + 1][choordX] != null) {
				checkNeighbors(choordX, choordY + 1, colorToCompare);
			}
			if (((choordY - 1) >= 0) && matrix[choordY - 1][choordX] != null) {
				checkNeighbors(choordX, choordY - 1, colorToCompare);
			}
		}

	}

	private void cleanUpRows() {

		for (int currentRow = dimensionY - 1; currentRow >= 0; currentRow--) {
			// if we have an empty cell, then check all cells above and close gaps
			for (int currentCell = 0; currentCell < dimensionX; currentCell++) {

				if (matrix[currentRow][currentCell] == null) {
					// do cleanup
					moveCells(currentRow, currentCell);
				}
			}
		}
	}

	private void moveCells(int currentRow, int currentCell) {

		for (int row = currentRow; row >= 0; row--) {

			// if tile found to replace then do it and break. next iteration will do
			if (matrix[row][currentCell] != null) {
				matrix[currentRow][currentCell] = matrix[row][currentCell];
				matrix[row][currentCell] = null;
				break;
			}
		}
	}

	private void cleanUpColumns() {

		for (int targetColumn = dimensionX - 1; targetColumn >= 0; targetColumn--) {
			if (checkForEmptyColumn(targetColumn)) {
				// search column which is not empty left of me
				if (targetColumn != 0) {
					for (int sourceColumn = targetColumn - 1; sourceColumn >= 0; sourceColumn--) {
						if (!checkForEmptyColumn(sourceColumn)) {
							moveColumn(sourceColumn, targetColumn);
							break;
						}
					}
				}
			}
		}
	}

	private boolean checkForEmptyColumn(int columnToCheck) {

		boolean isEmpty = true;

		for (int i = 0; i < dimensionY; i++) {
			if (matrix[i][columnToCheck] != null) {
				isEmpty = false;
				break;
			}
		}

		return isEmpty;
	}

	private void moveColumn(int sourceColumn, int targetColumn) {

		for (int i = 0; i < dimensionY; i++) {
			matrix[i][targetColumn] = matrix[i][sourceColumn];
			matrix[i][sourceColumn] = null;
		}
	}

	public Color[][] getMatrix() {
		return matrix;
	}

	/**
	 * Returns the current points.
	 * 
	 * @return the points
	 */
	public int getPoints() {
		return currentPoints;
	}

	/**
	 * Checks if there are any moves left to be performed.
	 * 
	 * @return true if there are moves left, false if there are no more moves and null if the game is won.
	 */
	public Boolean hasMovesLeft() {

		Boolean result = null;

		for (int i = 0; i < dimensionY; i++) {

			for (int j = 0; j < dimensionX; j++) {

				// check if we have not won.
				if (matrix[i][j] != null) {
					if (result == null) {
						result = false;
					}
					// check if there are any moves left
					if (hasNeighbors(i, j, matrix[i][j])) {
						result = Boolean.TRUE;
						return result;
					}
				}
			}
		}

		return result;
	}

}
