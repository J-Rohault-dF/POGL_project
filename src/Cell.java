public class Cell {
	private Cell[] neighbors; //Storing neighboring cells in an array, 0 for top, 1 for right, 2 for bottom, 3 for left
	private final int x;
	private final int y;

	//Constructor
	public Cell(int x, int y) {
		this.x = x;
		this.y = y;
		this.neighbors = new Cell[4];
	}

	//Getters
	public int getX() {return this.x;}
	public int getY() {return this.y;}

	public String toString() {
		return "<Cell "+this.x+" "+this.y+">";
	}

	//Neighbor management
	public void setNeighbor(Cell neighbor, int direction) {this.neighbors[direction] = neighbor;}
	public Cell getNeighbor(int direction) {return this.neighbors[direction];}
}
