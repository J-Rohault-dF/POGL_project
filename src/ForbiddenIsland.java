import java.awt.*;
import java.util.ArrayList;

public class ForbiddenIsland extends Observable {
	private Board board;
	private ArrayList<Player> players;
	private boolean isFinished;
	private int currentPlayer;

	ForbiddenIsland() {
		this.board = new Board();
		this.players = new ArrayList<>();

		this.players.add(new Player(this.board, new Cell(2, 2))); //TODO: Put actual starting positions
		this.players.add(new Player(this.board, new Cell(2, 3)));
		this.players.add(new Player(this.board, new Cell(3, 2)));
		this.players.add(new Player(this.board, new Cell(3, 3)));

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
	public Tile getTile(int x, int y) {return this.board.getTile(x, y);}
	public Tile getTile(int n) {return this.board.getTile(n);}
	public void floodRandomCells(int i) {this.board.floodRandomCells(i);}
}

