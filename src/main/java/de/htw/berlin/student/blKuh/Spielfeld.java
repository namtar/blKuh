package de.htw.berlin.student.blKuh;

import java.awt.Color;
import java.util.List;
import java.util.Random;

import de.htw.berlin.student.blKuh.exceptions.NoNeighborsException;
import de.htw.berlin.student.blKuh.model.Settings;

public class Spielfeld {

	private Color[][] matrix;
	private Settings settings;

	int dimensionX;
	int dimensionY;
	List<Color> colorsToUse;

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

		matrix = new Color[dimensionY][dimensionX];

		Random rand = new Random();

		for (int i = 0; i < dimensionY; i++) {
			for (int j = 0; j < dimensionX; j++) {
				int number = rand.nextInt(colorsToUse.size());
				matrix[i][j] = colorsToUse.get(number);
			}
		}

	}

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
		Color colorToCompare = matrix[choordY][choordX];
		boolean hasNeighbors = false;
		if (matrix[choordY + 1][choordX].equals(colorToCompare)) {
			// TODO: checke die array grenzen
			hasNeighbors = true;
		}
		if (matrix[choordY - 1][choordX].equals(colorToCompare)) {
			hasNeighbors = true;
		}
		if (matrix[choordY][choordX + 1].equals(colorToCompare)) {
			hasNeighbors = true;
		}
		if (matrix[choordY][choordX - 1].equals(colorToCompare)) {
			hasNeighbors = true;
		}
		if (!hasNeighbors) {
			throw new NoNeighborsException("Tile has no neighbors with equal color.");
		}

		checkNeighbors(choordX, choordY, colorToCompare);
		cleanUpRows();
		// 3. Entferne leere Columns (cleanUpColums)
		cleanUpColumns();
		// ggf ein Rückgabewert, damit die Oberfläche neu gezeichnet werden
		// kann.

	}

	private void checkNeighbors(int choordX, int choordY, Color colorToCompare) {

		// wenn eigene Color gleich der colorToCompare dann mache weiter
		if (matrix[choordY][choordX].equals(colorToCompare)) {

			// wenn die Farbe übereinstimmt, dann entferne aus der Matrix
			matrix[choordY][choordX] = null;

			if (((choordX + 1) != dimensionX) && matrix[choordY][choordX + 1] != null) {
				// wenn er einen Nachbar hat auf z.B. choordX + 1 und choordY
				checkNeighbors(choordX + 1, choordY, colorToCompare);
			}
		}

	}

	private void cleanUpRows() {

		for (int targetRow = dimensionY - 1; targetRow >= 0; targetRow--) {
			if (checkForEmptyRow(targetRow)) {
				// search row which ist not empty over me
				if (targetRow != 0) {
					for (int sourceRow = targetRow - 1; sourceRow >= 0; sourceRow--) {
						if (!checkForEmptyRow(sourceRow)) {
							moveRow(sourceRow, targetRow);
							break;
						}
					}
				}
			}
			// TODO: räumt die Rows auf und entfernt die Löcher.
			// fange auf max index an zu iterieren. Graphisch sozusagen von
			// unten
			// nach oben.
		}
	}

	private boolean checkForEmptyRow(int rowToCheck) {

		boolean isEmpty = true;

		for (int j = 0; j < dimensionX; j++) {
			if (matrix[j][rowToCheck] != null) {
				isEmpty = false;
				break;
			}
		}

		return isEmpty;
	}

	private void moveRow(int sourceRow, int targetRow) {

		for (int j = 0; j < dimensionX; j++) {
			matrix[j][targetRow] = matrix[j][sourceRow];
			matrix[j][sourceRow] = null;
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
}
