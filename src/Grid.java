import java.util.ArrayList;
import java.util.Arrays;

public class Grid {
	private Cell[][] cells;

	Grid() {
		cells = new Cell[6][];
		for(int i=0; i<6; i++) {
			int length = (i == 0 || i == 5) ? 2 : (i == 1 || i == 4) ? 4 : 6;
			cells[i] = createLine(length);
		}
	}

	public String toString() {
		String string = "";
		for(Cell[] line:this.cells) {
			for(Cell c:line) {
				if(c == null) {string += ' ';}
				else {string += '@';}
			}
			string += '\n';
		}
		return string;
	}

	static private Cell[] createLine(int length) {
		//if( !(length == 2 || length == 4 || length == 6) ) {throw new java.lang.Exception("The length of the line must be 2, 4, or 6; the length provided is "+(String.valueOf(length)));}
		Cell[] line = new Cell[6];

		int pad = (6-length)/2;
		for(int i=pad; i<(pad+length); i++) {
			line[i] = new Cell(); //Fills the line with cells
		}

		return line;
	}
}
