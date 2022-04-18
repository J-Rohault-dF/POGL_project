public class Tile {
	private String name;
	private Cell cell;
	private TreasureType treasure;
	private boolean isFlooded;

	public Tile(Cell cell) {
		this.isFlooded = false;
		this.cell = cell;
	}

	//Getters
	public boolean isFlooded() {return this.isFlooded;}
	public Cell getCell() {return this.cell;}

	public String toString() {return "< Tile "+this.name+" "+this.isFlooded +">";}

	//Methods
	public void flood() {
		//TODO: Replace “submerged” with actually making the tile null
		if(!this.isFlooded) {this.isFlooded = true;}
		else {this.cell.setTile(null);}
	}
}
