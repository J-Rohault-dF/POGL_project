import java.util.ArrayList;

public class ForbiddenIsland {
	private Grid grid;
	private ArrayList<Player> players;
	private boolean isFinished;
	private int currentPlayer;

	ForbiddenIsland() {
		this.grid = new Grid();
		this.players = new ArrayList<>();
		this.isFinished = false;
		this.currentPlayer = 0;
	}

	public static void main(String[] args) {
		//TODO: some parameters and setup here

		ForbiddenIsland game = new ForbiddenIsland();

		while(!game.isFinished) {
			int currentPlayer = (game.currentPlayer+1)%game.players.size();

			//STEP ONE

			//STEP TWO

			//STEP THREE
		}
	}
}
