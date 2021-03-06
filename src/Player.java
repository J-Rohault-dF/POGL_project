import java.util.ArrayList;

public class Player {
	private Board board;
	private Cell cell;
	private ArrayList<Card> hand;
	private ArrayList<Treasure> artefacts;

	Player(Board g, Cell c) {
		this.board = g;
		this.cell = c;
		this.hand = new ArrayList<>();
		this.artefacts = new ArrayList<>();
	}

	public String toString() {
		return "<Player at "+(this.cell)+">";
	}

	public boolean movePlayer(int direction) {
		//Check if available
		Cell neighbor = this.cell.getNeighbor(direction);

		//If not available then return false
		if(neighbor.getTile() == null) {return false;}

		//If available then move
		this.cell = neighbor;
		return true;
	}

	public Cell getCell() {return this.cell;}

	public void giveCard(Card c) {this.hand.add(c);}

	public String[] getInventory() {
		ArrayList<String> s = new ArrayList<>();

		for(Card c : hand) {
			s.add(c.getText());
		}
		/*for(Treasure t : artefacts) {
			s.add(t.getText());
		}*/

		return s.toArray(new String[0]);
	}
}
