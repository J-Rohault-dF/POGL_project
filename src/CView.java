import java.util.*;
import java.awt.*;
import java.awt.event.*;
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
				paintCell(g, game.getCell(i, j), i, j);
			}
		}
	}

	private void paintCell(Graphics g, Cell c, int x, int y) {
		g.setColor(((x+y)%2 == 0) ? Color.BLACK : Color.GRAY);
		g.fillRect((x*SIZE), (y*SIZE), SIZE, SIZE);
	}
}

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
	public void update();
}

abstract class Observable {
	private ArrayList<Observer> observers;
	public Observable() {
		this.observers = new ArrayList<Observer>();
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