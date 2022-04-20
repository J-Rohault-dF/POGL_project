import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class CommandView extends JPanel {
	private ForbiddenIsland game;
	ActionsPanel actionsPanel;
	FindKeyPanel findKeyPanel;

	public CommandView(ForbiddenIsland game) {
		this.setLayout(new BoxLayout(this, 1));

		this.game = game;

		actionsPanel = new ActionsPanel(game);
		this.add(actionsPanel);

		findKeyPanel = new FindKeyPanel();
		this.add(findKeyPanel);

		JButton endRoundButton = new JButton("End round");
		endRoundButton.setActionCommand("endRound");
		this.add(endRoundButton);

		Controller ctrl = new Controller(game);
		endRoundButton.addActionListener(ctrl);
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

	public ActionsPanel(ForbiddenIsland game) {
		this.setLayout(new BoxLayout(this, 1));
		this.setBorder(new CompoundBorder(new LineBorder(Color.lightGray), new EmptyBorder(4, 4, 4, 4)));

		this.game = game;

		this.remainingActions = 0;
		this.label = new JLabel("No remaining actions");
		this.add(label);

		this.movementPanel = new MovementPanel(game, this);
		this.drainingPanel = new DrainingPanel();
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

	public void startTurn(Player player) {
		this.currentPlayer = player;
		this.remainingActions = 3;
		this.label.setText("Remaining actions: "+this.remainingActions);
	}

	public void decrementRemainingActions() {
		this.remainingActions--;
		this.label.setText("Remaining actions: "+this.remainingActions);

		if(this.remainingActions == 0) {
			this.label.setText("No remaining actions");
			this.disableButtons();
		}
	}

	protected void disableButtons() {
		this.movementPanel.disableButtons();
		this.drainingPanel.disableButtons();
		this.artifactPanel.disableButtons();
	}

	protected void enableButtons() {
		this.movementPanel.enableButtons(false); //TODO: Add to the player a “can move diagonally” property that is checked
		this.drainingPanel.enableButtons(false);
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
	public void enableButtons(boolean isDiagonal) { //TODO: Only enable buttons for which the tile exists
		this.buttons[1].setEnabled(true);
		this.buttons[3].setEnabled(true);
		this.buttons[5].setEnabled(true);
		this.buttons[7].setEnabled(true);

		if(isDiagonal) {
			this.buttons[0].setEnabled(true);
			this.buttons[2].setEnabled(true);
			this.buttons[6].setEnabled(true);
			this.buttons[8].setEnabled(true);
		}
	}
}

class DrainingPanel extends JPanel {
	JButton[] buttons;

	public DrainingPanel() {
		this.setLayout(new GridLayout(3, 3));

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

		this.disableButtons();

		for(JButton b : this.buttons) {this.add(b);}
	}

	public void disableButtons() {for(JButton b : this.buttons) {b.setEnabled(false);}}
	public void enableButtons(boolean isDiagonal) {
		this.buttons[1].setEnabled(true);
		this.buttons[3].setEnabled(true);
		this.buttons[4].setEnabled(true);
		this.buttons[5].setEnabled(true);
		this.buttons[7].setEnabled(true);

		if(isDiagonal) {
			this.buttons[0].setEnabled(true);
			this.buttons[2].setEnabled(true);
			this.buttons[6].setEnabled(true);
			this.buttons[8].setEnabled(true);
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

	public void hideInventory() {this.inventoryDisplayer.setText("");}
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
	JTextField feedbackText;

	public FindKeyPanel() {
		this.setLayout(new BoxLayout(this, 1));
		this.setBorder(new CompoundBorder(new LineBorder(Color.lightGray), new EmptyBorder(4, 4, 4, 4)));

		this.findKey = new JButton("Pick a key card");
		this.feedbackText = new JTextField("");
		this.feedbackText.setEnabled(false);
		this.feedbackText.setEditable(false);

		this.add(findKey);
		this.add(feedbackText);
	}

	public void enableButtons()  {this.findKey.setEnabled(true) ;}
	public void disableButtons() {this.findKey.setEnabled(false);}
}

class EndTurnPanel extends JPanel {
	JButton endTurn;

	public EndTurnPanel() {
		this.endTurn = new JButton("End turn");
		this.endTurn.setEnabled(false);

		this.add(endTurn);
	}

	public void enableButtons()  {this.endTurn.setEnabled(true) ;}
	public void disableButtons() {this.endTurn.setEnabled(false);}
}

class Controller implements ActionListener {
	ForbiddenIsland game;

	public Controller(ForbiddenIsland game) {this.game = game;}

	public void actionPerformed(ActionEvent e) {
		String actionCommand = ((JButton) e.getSource()).getActionCommand(); //Line copied from StackOverflow

		switch (actionCommand) {
			case "endRound" -> game.floodRandomCells(3);
		}

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
		String actionCommand = ((JButton) e.getSource()).getActionCommand(); //Same line copied from StackOverflow
		Player curPlayer = this.game.getCurrentPlayer();

		boolean didMove;
		switch(actionCommand) {
			case "1" -> didMove = curPlayer.movePlayer(0);
			case "3" -> didMove = curPlayer.movePlayer(3);
			case "5" -> didMove = curPlayer.movePlayer(1);
			case "7" -> didMove = curPlayer.movePlayer(2);
			default  -> didMove = false;
		}

		if(didMove) {this.ap.decrementRemainingActions();}

	}
}