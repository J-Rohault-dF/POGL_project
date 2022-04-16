public class Tile {
	private Cell cell;
	private String name;
	private TreasureType treasure;
	private Status status;

	public Tile(Cell c) {
		this.cell = c;
		this.status = Status.Dry;
	}

	//Getters
	public int getX() {return this.cell.getX();}
	public int getY() {return this.cell.getY();}
	public Status getStatus() {return this.status;}
	public Cell getCell() {return this.cell;}

	//Methods
	public void flood() {
		//TODO: Replace “submerged” with actually making the tile null
		switch(this.status) {
			case Dry -> this.status = Status.Flooded;
			case Flooded -> this.status = Status.Submerged;
		}
	}
}
