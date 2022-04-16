import java.util.ArrayList;

public class Player {
	private Grid grid;
	private ArrayList<Card> hand;
	private ArrayList<Treasure> artefacts;

	Player(Grid g) {
		this.grid = g;
		this.hand = new ArrayList<>();
		this.artefacts = new ArrayList<>();
	}
}
