public class Tile {
	private String name;
	private TreasureType treasure;
	private Status status;

	public Tile() {
		this.status = Status.Dry;
	}

	//Getters
	public Status getStatus() {return this.status;}

	public String toString() {return "< Tile "+this.name+" "+this.status+">";}

	//Methods
	public void flood() {
		//TODO: Replace “submerged” with actually making the tile null
		switch(this.status) {
			case Dry -> this.status = Status.Flooded;
			case Flooded -> this.status = Status.Submerged;
		}
	}
}
