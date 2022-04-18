public class Tile {
	private String name;
	private Cell cell;
	private TreasureType treasure;
	private Status status;

	public Tile(Cell cell) {
		this.status = Status.Dry;
		this.cell = cell;
	}

	//Getters
	public Status getStatus() {return this.status;}
	public Cell getCell() {return this.cell;}

	public String toString() {return "< Tile "+this.name+" "+this.status+">";}

	//Methods
	public void flood() {
		//TODO: Replace “submerged” with actually making the tile null
		switch(this.status) {
			case Dry -> this.status = Status.Flooded;
			case Flooded -> this.cell.setTile(null);
		}
	}
}
