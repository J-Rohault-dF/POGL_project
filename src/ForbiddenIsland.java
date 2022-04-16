import java.awt.*;
import java.util.ArrayList;

public class ForbiddenIsland extends Observable {
	private Grid grid;
	private ArrayList<Player> players;
	private boolean isFinished;
	private int currentPlayer;

	ForbiddenIsland() {
		this.grid = new Grid();
		this.players = new ArrayList<>();
		for(int i=0; i<4; i++) {this.players.add(new Player(this.grid));}
		this.isFinished = false;
		this.currentPlayer = 0;
	}

	public static void main(String[] args) {
		//TODO: some parameters and setup here

		EventQueue.invokeLater(() -> {
			ForbiddenIsland game = new ForbiddenIsland();
			CView view = new CView(game);

			while (!game.isFinished) {
				int currentPlayer = (game.currentPlayer + 1) % game.players.size();

				//STEP ONE

				//STEP TWO

				//STEP THREE

				game.isFinished = true; //for testing
			}
		});
	}

	//Functions relayed to the grid
	public Cell getCell(int x, int y) {return this.grid.getCell(x, y);}
	public Cell getCell(int n) {return this.grid.getCell(n);}
	public void floodRandomCells(int i) {this.grid.floodRandomCells(i);}
}

