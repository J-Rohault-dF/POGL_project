import java.util.ArrayList;

public class Player {
	private Grid grid;
	private Cell cell;
	private ArrayList<Card> hand;
	private ArrayList<Treasure> artefacts;

	Player(Grid g, Cell c) {
		this.grid = g;
		this.cell = c;
		this.hand = new ArrayList<>();
		this.artefacts = new ArrayList<>();
	}

	public boolean movePlayer(int direction) {
		//Check if available
		Cell neighbor = this.cell.getNeighbor(direction);

		//If not available then return false
		if(neighbor == null) {return false;}

		//If available then move
		this.cell = neighbor;
		return true;
	}
}
