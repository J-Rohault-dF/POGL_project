import java.util.ArrayList;

public class Grid {
	private ArrayList<ArrayList<Cell>> cells;

	Grid() {
		cells = new ArrayList<>();
		for(int i=0; i<6; i++) {
			int length = (i == 0 || i == 5) ? 2 : (i == 1 || i == 4) ? 4 : 6;
			cells.set(i, createLine(length));
		}
	}

	static private ArrayList<Cell> createLine(int length) {
		//if( !(length == 2 || length == 4 || length == 6) ) {throw new java.lang.Exception("The length of the line must be 2, 4, or 6; the length provided is "+(String.valueOf(length)));}
		ArrayList<Cell> line = new ArrayList<>();

		int pad = (6-length)/2;
		for(int i=pad; i<(pad+length); i++) {
			line.set(i, new Cell()); //Fills the line with cells
		}

		return line;
	}
}
