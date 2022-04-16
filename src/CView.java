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
		for(int i=0; i<6; i++) {
			for(int j=0; j<6; j++) {
				paintCell(g, game.getCell(i, j));
			}
		}
	}

	private void paintCell(Graphics g, Cell c) {
		if(c == null) {return;} //If the cell is null, it is outside the board and thus not showed

		g.setColor(((c.getX()+c.getY())%2 == 0) ? Color.GRAY : Color.RED); //Grid pattern
		g.fillRect((c.getX()*SIZE), (c.getY()*SIZE), SIZE, SIZE);

		switch (c.getSituation()) { //Dryness overlay
			case Dry -> g.setColor(new Color(0, 0, 255, 0));
			case Inundated -> g.setColor(new Color(0, 0, 255, 127));
			case Submerged -> g.setColor(new Color(0, 0, 255, 191));
		}
		g.fillRect((c.getX()*SIZE), (c.getY()*SIZE), SIZE, SIZE);
	}
}

@SuppressWarnings("ALL")
class CommandView extends JPanel {
	private ForbiddenIsland game;

	public CommandView(ForbiddenIsland game) {
		this.game = game;
		JButton advanceButton = new JButton(">");
		this.add(advanceButton);

		Controller ctrl = new Controller(game);
		advanceButton.addActionListener(ctrl);
	}
}

class Controller implements ActionListener {
	ForbiddenIsland game;
	public Controller(ForbiddenIsland game) {this.game = game;}
	public void actionPerformed(ActionEvent e) {
		//game.advance();
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