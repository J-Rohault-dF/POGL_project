import java.util.ArrayList;

public class ForbiddenIsland {
	private Grid grid;
	private ArrayList<Player> players;
	private boolean isFinished;
	private int currentPlayer;

	ForbiddenIsland() {
		this.grid = new Grid();
		this.players = new ArrayList<>();
		for(int i=0; i<4; i++) {this.players.add(new Player());}
		this.isFinished = false;
		this.currentPlayer = 0;
	}

	public static void main(String[] args) {
		//TODO: some parameters and setup here

		ForbiddenIsland game = new ForbiddenIsland();

		System.out.println(game.grid);

		while(!game.isFinished) {
			int currentPlayer = (game.currentPlayer+1)%game.players.size();

			//STEP ONE

			//STEP TWO

			//STEP THREE

			game.isFinished = true; //for testing
		}
	}
}
