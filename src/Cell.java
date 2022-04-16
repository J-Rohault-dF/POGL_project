public class Cell {
	private Situation situation;
	private final int x;
	private final int y;

	//Constructor
	public Cell(int x, int y) {
		this.x = x;
		this.y = y;
		this.situation = Situation.Dry;
	}

	//Getters
	public int getX() {return this.x;}
	public int getY() {return this.y;}
	public Situation getSituation() {return this.situation;}

	public String toString() {
		return "<Cell "+this.x+" "+this.y+">";
	}
}
