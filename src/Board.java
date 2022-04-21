import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class Board {
	private final Cell[] cells;

	Board() {

		//Makes the deck of tiles
		ArrayList<Tile> tilesDeck = new ArrayList<>();
		tilesDeck.add(new Tile("Cave of Embers", TreasureType.ArdentCristal));
		tilesDeck.add(new Tile("Cave of Shadows", TreasureType.ArdentCristal));
		tilesDeck.add(new Tile("Temple of the Moon", TreasureType.SacredStone));
		tilesDeck.add(new Tile("Temple of the Sun", TreasureType.SacredStone));
		tilesDeck.add(new Tile("Coral Palace", TreasureType.WaveChalice));
		tilesDeck.add(new Tile("Tidal Palace", TreasureType.WaveChalice));
		tilesDeck.add(new Tile("Howling Garden", TreasureType.ZephyrStatue));
		tilesDeck.add(new Tile("Whispering Gardens", TreasureType.ZephyrStatue));
		tilesDeck.add(new Tile("Breakers Bridge", null));
		tilesDeck.add(new Tile("Bronze Gate", null));
		tilesDeck.add(new Tile("Copper Gate", null));
		tilesDeck.add(new Tile("Cliffs of Abandon", null));
		tilesDeck.add(new Tile("Crimson Forest", null));
		tilesDeck.add(new Tile("Dunes of Deception", null));
		tilesDeck.add(new Tile("Fools' Landing", null));
		tilesDeck.add(new Tile("Gold Gate", null));
		tilesDeck.add(new Tile("Iron Gate", null));
		tilesDeck.add(new Tile("Lost Lagoon", null));
		tilesDeck.add(new Tile("Misty Marsh", null));
		tilesDeck.add(new Tile("Observatory", null));
		tilesDeck.add(new Tile("Phantom Rock", null));
		tilesDeck.add(new Tile("Silver Gate", null));
		tilesDeck.add(new Tile("Twilight Hollow", null));
		tilesDeck.add(new Tile("Watchtower", null));
		Collections.shuffle(tilesDeck);

		//Makes the actual board
		this.cells = new Cell[36];
		for(int i=0; i<36; i++) { //Adds all the cells
			int x = i%6;
			int y = i/6;

			Cell c = new Cell(x, y, null);

			if( !( //Spaghetti code, but it does the job (removes cells out of the board)
					(x == 0 && y == 0) || (x == 1 && y == 0) || (x == 0 && y == 1) ||
							(x == 4 && y == 0) || (x == 5 && y == 0) || (x == 5 && y == 1) ||
							(x == 0 && y == 5) || (x == 1 && y == 5) || (x == 0 && y == 4) ||
							(x == 4 && y == 5) || (x == 5 && y == 5) || (x == 5 && y == 4)
			) ) {
				Tile t = tilesDeck.remove(0);
				c.setTile(t);
			}

			this.cells[i] = c;
		}

		for(int i=0; i<36; i++) { //Gives each cell its neighbors
			Cell c = this.cells[i];

			//Sets the 3x3 grid around (including itself for consistency) as its neighbors
			c.setNeighbor(this.getCell(c.getX()-1, c.getY()-1), 0);
			c.setNeighbor(this.getCell(c.getX()+0, c.getY()-1), 1);
			c.setNeighbor(this.getCell(c.getX()+1, c.getY()-1), 2);
			c.setNeighbor(this.getCell(c.getX()-1, c.getY()+0), 3);
			c.setNeighbor(this.getCell(c.getX()+0, c.getY()+0), 4);
			c.setNeighbor(this.getCell(c.getX()+1, c.getY()+0), 5);
			c.setNeighbor(this.getCell(c.getX()-1, c.getY()+1), 6);
			c.setNeighbor(this.getCell(c.getX()+0, c.getY()+1), 7);
			c.setNeighbor(this.getCell(c.getX()+1, c.getY()+1), 8);
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

		return this.cells[x+(6*y)];
	}

	/**
	 * @param n number
	 * @return Cell at index n
	 */
	public Cell getCell(int n) {
		return this.cells[n];
	}

	public String toString() {
		String s = "";

		for(int i=0; i<6; i++) {
			for(int j=0; j<6; j++) {
				if(this.getCell(i*6 + j).getTile() != null) {s += "@";}
				else {s += " ";}
			}
			s += '\n';
		}

		return s;
	}

	public void floodRandomCells(int i) {
		ArrayList<Cell> nonSubmergedCells = new ArrayList<>(Arrays.asList(this.cells));

		Random random = new Random();

		while(i > 0) { //While there are still un-submerged cells:
			int pickedIndex = random.nextInt(nonSubmergedCells.size());
			Cell pickedCell = this.getCell(pickedIndex);

			if(pickedCell.getTile() != null) { //If the cell can be inundated: inundate it
				pickedCell.getTile().flood();
				i--;
			}

			//If it can't (already submerged) or once it was submerged, remove it from the list of cells that can be, and find another one
			nonSubmergedCells.remove(pickedIndex);

			//TODO: If it makes a cell submerged, check if a player is on it, and if it is then make them do an emergency move

			if(nonSubmergedCells.size() == 0) {break;} //If no cell can be submerged, nothing is done
		}
	}
}
