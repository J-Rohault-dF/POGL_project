import java.util.*;
import java.awt.*;
import javax.swing.*;

/*
ENTIRE CODE COPIED FROM CONWAY.JAVA
 */

public class CView {
	private JFrame frame;
	private GridView grid;
	private CommandView commands;

	public CView(ForbiddenIsland game) {
		frame = new JFrame();
		frame.setTitle("Forbidden Island");
		frame.setLayout(new FlowLayout());

		grid = new GridView(game);
		frame.add(grid);
		commands = new CommandView(game);
		frame.add(commands);

		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public ActionsPanel getActionsPanel() {return this.commands.getActionsPanel();}
}

class GridView extends JPanel implements Observer {
	private final ForbiddenIsland game;
	private final static int SIZE = 64;

	public int WIDTH = 620;
	public int HEIGHT = 480;

	public GridView(ForbiddenIsland game) {
		this.game = game;
		game.addObserver(this);
		Dimension dim = new Dimension(this.WIDTH, this.HEIGHT);
		this.setPreferredSize(dim);
	}

	public void update() {repaint();}

	public void paintComponent(Graphics g) {

		super.repaint();
		this.repaint();
		for(int n=0; n<36; n++) {
			paintCell(g, game.getCell(n));
		}

		for(int i=0; i<4; i++) {
			paintPlayer(g, game.getPlayers()[i], (game.getCurrentPlayerNumber() == i));
		}
	}

	private void paintCell(Graphics g, Cell c) {
		Tile t = c.getTile();

		g.setColor(new Color(238, 238, 238));
		g.fillRect((c.getX() * SIZE), (c.getY() * SIZE), SIZE, SIZE); //Hide previous board

		if(t != null) { //If the cell is null, it is outside the board and thus not showed

			//System.out.println(""+(t != null && t.getStatus() != Status.Submerged));

			g.setColor(Color.GRAY);
			g.fillRect((c.getX() * SIZE + (int) (SIZE * 0.05)), (c.getY() * SIZE + (int) (SIZE * 0.05)), (int) (SIZE * 0.9), (int) (SIZE * 0.9));

			//Dryness overlay
			if (t.isFlooded()) {g.setColor(new Color(0, 0, 255, 85));}
			else               {g.setColor(new Color(0, 0, 255, 0));}

			//Drew rectangle
			g.fillRect((c.getX() * SIZE + (int) (SIZE * 0.2)), (c.getY() * SIZE + (int) (SIZE * 0.2)), (int) (SIZE * 0.6), (int) (SIZE * 0.6));

			//Draw the artifacts areas
			if(t.getTreasureType() != null) {
				switch (t.getTreasureType()) {
					case ArdentCristal -> g.setColor(Color.red);
					case SacredStone -> g.setColor(Color.black);
					case WaveChalice -> g.setColor(Color.blue);
					case ZephyrStatue -> g.setColor(Color.yellow);
				}
				g.fillOval(c.getX() * SIZE + (int) (SIZE * 0.10), c.getY() * SIZE + (int) (SIZE * 0.60), (int) (SIZE * 0.30), (int) (SIZE * 0.30));
			}

			//Draw the helicopter tile
			if(t.getName() == "Fools' Landing") {
				g.setColor(Color.lightGray);
				g.fillOval((c.getX() * SIZE + (int) (SIZE * 0.2)), (c.getY() * SIZE + (int) (SIZE * 0.2)), (int) (SIZE * 0.6), (int) (SIZE * 0.6));
			}
		}
	}

	private void paintPlayer(Graphics g, Player p, boolean isCurrentPlayer) {
		if(isCurrentPlayer) {g.setColor(new Color(0, 255, 0));}
		else {g.setColor(new Color(0, 191, 0));}

		g.fillRect(p.getCell().getX() * SIZE + (int)(SIZE*0.3), p.getCell().getY() * SIZE + (int)(SIZE*0.3), (int)(SIZE*0.4), (int)(SIZE*0.4));
	}
}

interface Observer {
	void update();
}

abstract class Observable {
	private ArrayList<Observer> observers;
	public Observable() {
		this.observers = new ArrayList<>();
	}

	public void addObserver(Observer o) {
		observers.add(o);
	}

	public void notifyObservers() {
		for(Observer o : observers) {
			o.update();
		}
	}
}