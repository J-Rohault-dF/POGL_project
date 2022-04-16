import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/*
ENTIRE CODE COPIED FROM CONWAY.JAVA
 */

@SuppressWarnings("FieldCanBeLocal")
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
		for(int n=0; n<24; n++) {
			paintTile(g, game.getTile(n));
		}
	}

	private void paintTile(Graphics g, Tile t) {
		if(t == null || t.getStatus() == Status.Submerged) {return;} //If the cell is null, it is outside the board and thus not showed

		g.setColor(((t.getX()+t.getY())%2 == 0) ? Color.GRAY : Color.RED); //Grid pattern
		g.fillRect((t.getX()*SIZE), (t.getY()*SIZE), SIZE, SIZE);

		switch (t.getStatus()) { //Dryness overlay
			case Dry -> g.setColor(new Color(0, 0, 255, 0));
			case Flooded -> g.setColor(new Color(0, 0, 255, 85));
		}
		g.fillRect((t.getX()*SIZE), (t.getY()*SIZE), SIZE, SIZE);
	}
}

@SuppressWarnings("FieldCanBeLocal")
class CommandView extends JPanel {
	private ForbiddenIsland game;

	public CommandView(ForbiddenIsland game) {
		this.game = game;
		JButton endRoundButton = new JButton("End round");
		endRoundButton.setActionCommand("endRound");
		this.add(endRoundButton);

		Controller ctrl = new Controller(game);
		endRoundButton.addActionListener(ctrl);
	}
}

class Controller implements ActionListener {
	ForbiddenIsland game;

	public Controller(ForbiddenIsland game) {this.game = game;}

	public void actionPerformed(ActionEvent e) {
		String actionCommand = ((JButton) e.getSource()).getActionCommand(); //Line copied from StackOverflow

		switch(actionCommand) {
			case "endRound" -> game.floodRandomCells(3);
		}

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