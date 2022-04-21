import java.awt.*;
import java.util.ArrayList;

public class ForbiddenIsland extends Observable {
	private Board board;
	private Player[] players;
	private boolean isFinished;
	private int currentPlayer;

	ForbiddenIsland() {
		this.board = new Board();
		this.players = new Player[4];

		this.players[0] = new Player(this.board, this.board.getCell(2, 2)); //TODO: Put actual starting positions
		this.players[1] = new Player(this.board, this.board.getCell(2, 3));
		this.players[2] = new Player(this.board, this.board.getCell(3, 2));
		this.players[3] = new Player(this.board, this.board.getCell(3, 3));

		this.isFinished = false;
		this.currentPlayer = 0;
	}

	public static void main(String[] args) {
		//TODO: some parameters and setup here

		EventQueue.invokeLater(() -> {
			ForbiddenIsland game = new ForbiddenIsland();
			CView view = new CView(game);
			int currentPlayer;

			while (!game.isFinished) {

				view.getActionsPanel().startTurn();
				game.notifyObservers();

				//STEP ONE

				//STEP TWO

				//STEP THREE

				game.isFinished = true; //for testing
			}
		});
	}

	//Functions relayed to the board
	public Cell getCell(int x, int y) {return this.board.getCell(x, y);}
	public Cell getCell(int n) {return this.board.getCell(n);}
	public void floodRandomCells(int i) {this.board.floodRandomCells(i);}

	public Board getBoard() {return this.board;}

	//Functions about players
	public Player[] getPlayers() {return this.players;}
	public Player getCurrentPlayer() {return this.players[currentPlayer];}
	public int getCurrentPlayerNumber() {return this.currentPlayer;}

	public Player nextPlayer() {
		int curPlayer = (this.currentPlayer + 1) % 4;
		return this.players[currentPlayer];
	}

}

