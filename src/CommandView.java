import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class CommandView extends JPanel {
	ActionsPanel actionsPanel;
	FindKeyPanel findKeyPanel;
	EndTurnPanel endTurnPanel;

	public CommandView(ForbiddenIsland game) {
		this.setPreferredSize(new Dimension(256, 480));

		this.setLayout(new BoxLayout(this, 1));

		actionsPanel = new ActionsPanel(game, this);
		this.add(actionsPanel);

		findKeyPanel = new FindKeyPanel(game); //Only enable if it's possible to get a key
		this.add(findKeyPanel);

		endTurnPanel = new EndTurnPanel(game, actionsPanel);
		this.add(endTurnPanel);
	}

	public ActionsPanel getActionsPanel() {return actionsPanel;}
}

class ActionsPanel extends JPanel {
	int remainingActions;
	ForbiddenIsland game;
	MovementPanel movementPanel;
	DrainingPanel drainingPanel;
	InventoryPanel inventoryPanel;
	ArtifactPanel artifactPanel;
	Player currentPlayer;
	JLabel label;

	CommandView cv;

	public ActionsPanel(ForbiddenIsland game, CommandView cv) {
		this.setLayout(new BoxLayout(this, 1));
		this.setBorder(new CompoundBorder(new LineBorder(Color.lightGray), new EmptyBorder(4, 4, 4, 4)));

		this.game = game;
		this.cv = cv;

		this.remainingActions = 0;
		this.label = new JLabel("No remaining actions");
		this.add(label);

		this.movementPanel = new MovementPanel(game, this);
		this.drainingPanel = new DrainingPanel(game, this);
		this.inventoryPanel = new InventoryPanel();
		this.artifactPanel = new ArtifactPanel();

		this.add(new JLabel(" "));
		this.add(new JLabel("Movement:"));
		this.add(this.movementPanel);

		this.add(new JLabel(" "));
		this.add(new JLabel("Draining:"));
		this.add(this.drainingPanel);

		this.add(new JLabel(" "));
		this.add(new JLabel("Inventory:"));
		this.add(this.inventoryPanel);

		this.add(new JLabel(" "));
		this.add(new JLabel("Artifact:"));
		this.add(this.artifactPanel);

		this.enableButtons();
	}

	public void startTurn() {
		this.currentPlayer = this.game.nextPlayer();
		this.remainingActions = 3;
		this.label.setText("Remaining actions: "+this.remainingActions);

		this.resetButtons();
	}

	public void decrementRemainingActions() {
		this.remainingActions--;
		this.label.setText("Remaining actions: "+this.remainingActions);

		this.resetButtons();

		if(this.remainingActions == 0) {
			this.label.setText("No remaining actions");
			this.disableButtons();
		}
	}

	public void resetButtons() {
		this.movementPanel.enableButtons(false);
		this.drainingPanel.enableButtons(false, game);
		this.cv.findKeyPanel.enableButtons();
	}

	protected void disableButtons() {
		this.movementPanel.disableButtons();
		this.drainingPanel.disableButtons();
		this.artifactPanel.disableButtons();
	}

	protected void enableButtons() {
		this.movementPanel.enableButtons(false); //TODO: Add to the player a “can move diagonally” property that is checked
		this.drainingPanel.enableButtons(false, game);
		this.artifactPanel.enableButtons();
	}
}

class MovementPanel extends JPanel {
	ForbiddenIsland game;
	JButton[] buttons;

	public MovementPanel(ForbiddenIsland game, ActionsPanel ap) {
		this.setLayout(new GridLayout(3, 3));

		this.game = game;
		MovementController mc = new MovementController(game, ap);

		this.buttons = new JButton[9];

		this.buttons[0] = new JButton("↖");
		this.buttons[1] = new JButton("↑");
		this.buttons[2] = new JButton("↗");
		this.buttons[3] = new JButton("←");
		this.buttons[4] = new JButton(" ");
		this.buttons[5] = new JButton("→");
		this.buttons[6] = new JButton("↙");
		this.buttons[7] = new JButton("↓");
		this.buttons[8] = new JButton("↘");

		this.disableButtons();

		for (int i=0; i<9; i++) {
			this.add(this.buttons[i]);
			this.buttons[i].addActionListener(mc);
			this.buttons[i].setActionCommand(""+i);
		}
	}

	public void disableButtons() {for(JButton b : this.buttons) {b.setEnabled(false);}}
	public void enableButtons(boolean isDiagonal) {
		this.disableButtons(); //Disable all buttons before re-enabling some of them
		Player curPlayer = game.getCurrentPlayer();

		for(int i=0; i<9; i++) {
			Tile t = curPlayer.getCell().getNeighbor(i).getTile();
			if(!(isDiagonal || (i == 0 || i == 2 || i == 6 || i == 8)) && (i != 4)) { //Checks for diagonals, then removes the middle cell (can't move in-place)

				if(t != null) {
					this.buttons[i].setEnabled(true);
				}
			}
		}
	}
}

class DrainingPanel extends JPanel {
	ForbiddenIsland game;
	JButton[] buttons;

	public DrainingPanel(ForbiddenIsland game, ActionsPanel ap) {
		this.setLayout(new GridLayout(3, 3));

		this.game = game;
		DrainingController dc = new DrainingController(game, ap);

		this.buttons = new JButton[9];

		this.buttons[0] = new JButton(" ");
		this.buttons[1] = new JButton("•");
		this.buttons[2] = new JButton(" ");
		this.buttons[3] = new JButton("•");
		this.buttons[4] = new JButton("•");
		this.buttons[5] = new JButton("•");
		this.buttons[6] = new JButton(" ");
		this.buttons[7] = new JButton("•");
		this.buttons[8] = new JButton(" ");

		this.enableButtons(false, this.game);

		for (int i=0; i<9; i++) {
			this.add(this.buttons[i]);
			this.buttons[i].addActionListener(dc);
			this.buttons[i].setActionCommand(""+i);
		}
	}

	public void disableButtons() {for(JButton b : this.buttons) {b.setEnabled(false);}}
	public void enableButtons(boolean isDiagonal, ForbiddenIsland game) {
		this.disableButtons(); //Disable all buttons before re-enabling some of them
		Player curPlayer = game.getCurrentPlayer();

		for(int i=0; i<9; i++) {
			Tile t = curPlayer.getCell().getNeighbor(i).getTile();
			if(!(isDiagonal || (i == 0 || i == 2 || i == 6 || i == 8))) { //Would add a check for isDiagonal, but it doesn't need to be implemented just yet

				if(t != null && t.isFlooded()) {
					this.buttons[i].setEnabled(true);
				}
			}
		}
	}
}

class InventoryPanel extends JPanel {
	JTextArea inventoryDisplayer;

	public InventoryPanel() {
		this.inventoryDisplayer = new JTextArea();
		inventoryDisplayer.setEnabled(false);
		inventoryDisplayer.setEditable(false);

		this.add(inventoryDisplayer);
	}

	public void showInventory(String[] inventory) {
		inventoryDisplayer.setText("");
		for(String s : inventory) {
			inventoryDisplayer.append(s+"\n");
		}
	}
}

class ArtifactPanel extends JPanel {
	JButton takeArtifact;

	public ArtifactPanel() {
		this.takeArtifact = new JButton("Take artifact");
		this.disableButtons();

		this.add(takeArtifact);
	}

	public void disableButtons() {this.takeArtifact.setEnabled(false);}
	public void enableButtons()  {this.takeArtifact.setEnabled(true) ;}
}

class FindKeyPanel extends JPanel {
	JButton findKey;
	JLabel feedbackText;
	int cardsToPick;

	public FindKeyPanel(ForbiddenIsland game) {
		this.setLayout(new BoxLayout(this, 1));
		this.setBorder(new CompoundBorder(new LineBorder(Color.lightGray), new EmptyBorder(4, 4, 4, 4)));

		this.findKey = new JButton("Draw a card");

		DrawKeyController c = new DrawKeyController(game, this);
		this.findKey.addActionListener(c);

		this.feedbackText = new JLabel(" ");

		this.add(findKey);
		this.add(feedbackText);

		this.enableButtons();
	}

	public void enableButtons()  {
		this.findKey.setEnabled(true);
		this.cardsToPick = 2;
		this.feedbackText.setText(" ");
	}
	public void disableButtons() {this.findKey.setEnabled(false);}
	public void decrement() {
		this.cardsToPick--;
		if(this.cardsToPick <= 0) {this.disableButtons();}
	}

	public void showPick(Card drawn) {
		this.feedbackText.setText("Picked "+drawn.getText());
	}
}

class EndTurnPanel extends JPanel {
	JButton endTurn;

	public EndTurnPanel(ForbiddenIsland game, ActionsPanel ap) {
		this.endTurn = new JButton("End turn");
		this.endTurn.setEnabled(true);

		endTurn.setActionCommand("endRound");

		Controller ctrl = new Controller(game, ap);
		endTurn.addActionListener(ctrl);

		this.add(endTurn);
	}

	public void enableButtons()  {this.endTurn.setEnabled(true) ;}
	public void disableButtons() {this.endTurn.setEnabled(false);}
}

class Controller implements ActionListener {
	ForbiddenIsland game;
	ActionsPanel ap;

	public Controller(ForbiddenIsland game, ActionsPanel ap) {
		this.game = game;
		this.ap = ap;
	}

	public void actionPerformed(ActionEvent e) {
		String actionCommand = ((JButton) e.getSource()).getActionCommand(); //Line copied from StackOverflow

		game.floodRandomCells(3);
		ap.startTurn();

	}
}

class MovementController implements ActionListener {
	ForbiddenIsland game;
	ActionsPanel ap;

	public MovementController(ForbiddenIsland game, ActionsPanel ap) {
		this.game = game;
		this.ap = ap;
	}

	public void actionPerformed(ActionEvent e) {
		Player curPlayer = this.game.getCurrentPlayer();

		int ac = Integer.parseInt(((JButton) e.getSource()).getActionCommand()); //Same code from StackOverflow in this line

		boolean didMove = curPlayer.movePlayer(ac);

		if(didMove){this.ap.decrementRemainingActions();}

	}
}

class DrainingController implements ActionListener {
	ForbiddenIsland game;
	ActionsPanel ap;

	public DrainingController(ForbiddenIsland game, ActionsPanel ap) {
		this.game = game;
		this.ap = ap;
	}

	public void actionPerformed(ActionEvent e) {
		int ac = Integer.parseInt(((JButton) e.getSource()).getActionCommand()); //Same line copied from StackOverflow
		Player curPlayer = this.game.getCurrentPlayer();

		Tile t = curPlayer.getCell().getNeighbor(ac).getTile();

		boolean didDry; //Attempts drying the tile, didDry checks if it happened
		if(t == null) {didDry = false;}
		else {didDry = t.dry();}

		if(didDry) {this.ap.decrementRemainingActions();}

	}
}

class DrawKeyController implements ActionListener {
	ForbiddenIsland game;
	FindKeyPanel fkp;

	public DrawKeyController(ForbiddenIsland game, FindKeyPanel fkp) {
		this.game = game;
		this.fkp = fkp;
	}

	public void actionPerformed(ActionEvent e) {
		fkp.decrement();

		Card drawn = game.getDeck().draw(); //Draws the card
		game.getCurrentPlayer().giveCard(drawn);
		fkp.showPick(drawn);
	}
}