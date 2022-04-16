public class Tile {
	private Cell cell;
	private String name;
	private TreasureType treasure;
	private Situation situation;

	public Tile(Cell c) {
		this.cell = c;
		this.situation = Situation.Dry;
	}

	//Getters
	public int getX() {return this.cell.getX();}
	public int getY() {return this.cell.getY();}
	public Situation getSituation() {return this.situation;}
	public Cell getCell() {return this.cell;}

	//Methods
	public void flood() {
		switch(this.situation) {
			case Dry -> this.situation = Situation.Inundated;
			case Inundated -> this.situation = Situation.Submerged;
		}
	}
}
