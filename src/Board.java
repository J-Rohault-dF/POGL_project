import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Board {
	private final Tile[] tiles;

	Board() {
		this.tiles = new Tile[24];
		int x = 0;
		int y = 0;
		for(int i=0; i<24; i++) { //Adds all the cells
			this.tiles[i] = new Tile(new Cell(x+((6- Board.getLineLength(y))/2), y));

			x++;
			if(x >= Board.getLineLength(y)) {y++; x = 0;}
		}

		for(int i=0; i<24; i++) { //Gives each cell its neighbors
			Cell c = this.tiles[i].getCell();

			c.setNeighbor(this.getTile(c.getX(), c.getY()-1).getCell(), 0);
			c.setNeighbor(this.getTile(c.getX()+1, c.getY()).getCell(), 1);
			c.setNeighbor(this.getTile(c.getX(), c.getY()+1).getCell(), 2);
			c.setNeighbor(this.getTile(c.getX()-1, c.getY()).getCell(), 3);
		}
	}

	/**
	 * @param x coordinate
	 * @param y coordinate
	 * @return Cell at (x,y) cartesian coordinates
	 */
	public Tile getTile(int x, int y) {
		int lineStart;
		switch(y) {
			default-> lineStart = 0;
			case 1 -> lineStart = 2;
			case 2 -> lineStart = 6;
			case 3 -> lineStart = 12;
			case 4 -> lineStart = 18;
			case 5 -> lineStart = 22;
		}
		return this.tiles[(lineStart+x)];
	}

	/**
	 * @param n number
	 * @return Cell at index n
	 */
	public Tile getTile(int n) {
		return this.tiles[n];
	}

	private static int getLineLength(int y) {
		return (y == 0 || y == 5) ? 2 : (y == 1 || y == 4) ? 4 : 6;
	}

	public void floodRandomCells(int i) {
		ArrayList<Tile> nonSubmergedCells = new ArrayList<>(Arrays.asList(this.tiles));

		Random random = new Random();

		while(i > 0) { //While there are still un-submerged cells:
			int pickedIndex = random.nextInt(nonSubmergedCells.size());
			Tile pickedTile = this.getTile(pickedIndex);

			if(pickedTile.getSituation() == Situation.Dry || pickedTile.getSituation() == Situation.Inundated) { //If the cell can be inundated: inundate it
				pickedTile.flood();
				i--;
			} else { //If it can't (already submerged), remove it from the list of cells that can be, and find another one
				nonSubmergedCells.remove(pickedIndex);
			}

			if(nonSubmergedCells.size() == 0) {break;} //If no cell can be submerged, nothing is done
			//TODO: Check, can already submerged cells be picked?
		}
	}
}
