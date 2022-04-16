import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Grid {
	private final Cell[] cells;

	Grid() {
		this.cells = new Cell[24];
		int x = 0;
		int y = 0;
		for(int i=0; i<24; i++) { //Adds all the cells
			this.cells[i] = new Cell(x+((6-Grid.getLineLength(y))/2), y);

			x++;
			if(x >= Grid.getLineLength(y)) {y++; x = 0;}
		}

		for(int i=0; i<24; i++) { //Gives each cell its neighbors
			Cell c = this.cells[i];

			c.setNeighbor(this.getCell(c.getX(), c.getY()-1), 0);
			c.setNeighbor(this.getCell(c.getX()+1, c.getY()), 1);
			c.setNeighbor(this.getCell(c.getX(), c.getY()+1), 2);
			c.setNeighbor(this.getCell(c.getX()-1, c.getY()), 3);
		}
	}

	/**
	 * @param x coordinate
	 * @param y coordinate
	 * @return Cell at (x,y) cartesian coordinates
	 */
	public Cell getCell(int x, int y) {
		int lineStart;
		switch(y) {
			default-> lineStart = 0;
			case 1 -> lineStart = 2;
			case 2 -> lineStart = 6;
			case 3 -> lineStart = 12;
			case 4 -> lineStart = 18;
			case 5 -> lineStart = 22;
		}
		return this.cells[(lineStart+x)];
	}

	/**
	 * @param n number
	 * @return Cell at index n
	 */
	public Cell getCell(int n) {
		return this.cells[n];
	}

	private static int getLineLength(int y) {
		return (y == 0 || y == 5) ? 2 : (y == 1 || y == 4) ? 4 : 6;
	}

	public void floodRandomCells(int i) {
		ArrayList<Cell> nonSubmergedCells = new ArrayList<>(Arrays.asList(this.cells));

		Random random = new Random();

		while(i > 0) { //While there are still un-submerged cells:
			int pickedIndex = random.nextInt(nonSubmergedCells.size());
			Cell pickedCell = this.getCell(pickedIndex);

			if(pickedCell.getSituation() == Situation.Dry || pickedCell.getSituation() == Situation.Inundated) { //If the cell can be inundated: inundate it
				pickedCell.flood();
				i--;
			} else { //If it can't (already submerged), remove it from the list of cells that can be, and find another one
				nonSubmergedCells.remove(pickedIndex);
			}

			if(nonSubmergedCells.size() == 0) {break;} //If no cell can be submerged, nothing is done
			//TODO: Check, can already submerged cells be picked?
		}
	}
}
