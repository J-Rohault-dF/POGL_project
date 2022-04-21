public class Tile {
	private String name;
	private Cell cell;
	private TreasureType treasure;
	private boolean isFlooded;

	public Tile(String name, TreasureType treasure) {
		this.name = name;
		this.treasure = treasure;
		this.isFlooded = false;
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

	public boolean dry() {
		if(this.isFlooded) {
			this.isFlooded = false;
			return true;
		}
		else {return false;}
	}

	public void setCell(Cell cell) {this.cell = cell;}
}
