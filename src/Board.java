import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Board {
	private final Cell[] cells;

	Board() {
		this.cells = new Cell[36];
		for(int i=0; i<36; i++) { //Adds all the cells
			int x = i%6;
			int y = i/6;

			Cell c = new Cell(x, y, new Tile());

			if( //Spaghetti code, but it does the job (removes cells out of the board)
					(x == 0 && y == 0) || (x == 1 && y == 0) || (x == 0 && y == 1) ||
							(x == 4 && y == 0) || (x == 5 && y == 0) || (x == 5 && y == 1) ||
							(x == 0 && y == 5) || (x == 1 && y == 5) || (x == 0 && y == 4) ||
							(x == 4 && y == 5) || (x == 5 && y == 5) || (x == 5 && y == 4)
			) {
				c.setTile(null);
			}

			this.cells[i] = new Cell(i%6, i/6, new Tile());
		}

		for(int i=0; i<36; i++) { //Gives each cell its neighbors
			Cell c = this.cells[i];

			System.out.println(""+i+" "+c);
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
		if(x < 0 || x > 5 || y < 0 || y > 5) {return new Cell(x, y, null);}
		if( //Spaghetti code, but it does the job (removes cells out of the board)
			(x == 0 && y == 0) || (x == 1 && y == 0) || (x == 0 && y == 1) ||
			(x == 4 && y == 0) || (x == 5 && y == 0) || (x == 5 && y == 1) ||
			(x == 0 && y == 5) || (x == 1 && y == 5) || (x == 0 && y == 4) ||
			(x == 4 && y == 5) || (x == 5 && y == 5) || (x == 5 && y == 4)
		) {return new Cell(x, y, null);}

		return this.cells[x+(6*x)];
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

			if(pickedCell.getTile().getStatus() == Status.Dry || pickedCell.getTile().getStatus() == Status.Flooded) { //If the cell can be inundated: inundate it
				pickedCell.getTile().flood();
				i--;
			} else { //If it can't (already submerged), remove it from the list of cells that can be, and find another one
				nonSubmergedCells.remove(pickedIndex);
			}

			if(nonSubmergedCells.size() == 0) {break;} //If no cell can be submerged, nothing is done
		}
	}
}
