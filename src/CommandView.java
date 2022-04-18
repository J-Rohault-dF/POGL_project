import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class CommandView extends JPanel {
	private ForbiddenIsland game;
	ActionsPanel actionsPanel;

	public CommandView(ForbiddenIsland game) {
		this.game = game;
		JButton endRoundButton = new JButton("End round");
		endRoundButton.setActionCommand("endRound");
		this.add(endRoundButton);

		actionsPanel = new ActionsPanel();
		this.add(actionsPanel);

		Controller ctrl = new Controller(game);
		endRoundButton.addActionListener(ctrl);
	}

	public ActionsPanel getActionsPanel() {return actionsPanel;}
}

class ActionsPanel extends JPanel {
	int remainingActions;
	MovementPanel movementPanel;
	DrainingPanel drainingPanel;
	InventoryPanel inventoryPanel;
	ArtifactPanel artifactPanel;
	Player currentPlayer;
	JLabel label;

	public ActionsPanel() {
		this.remainingActions = 0;
		this.label = new JLabel("No remaining actions");
		this.add(label);

		this.movementPanel = new MovementPanel();
		this.drainingPanel = new DrainingPanel();
		this.inventoryPanel = new InventoryPanel();
		this.artifactPanel = new ArtifactPanel();
	}

	public void startTurn(Player player) {
		this.currentPlayer = player;
		this.remainingActions = 3;
		this.label.setText("Remaining actions: "+this.remainingActions);
	}

	public void removeOneRemainingAction() {
		this.remainingActions--;
		this.label.setText("Remaining actions: "+this.remainingActions);
	}
}

class MovementPanel extends JPanel {
	JButton[] buttons;

	public MovementPanel() {
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

		for(JButton b : this.buttons) {this.add(b);}
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
		for(String s : inventory) {
			inventoryDisplayer.append(s);
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

	public Controller(ForbiddenIsland game) {
		this.game = game;
	}

	public void actionPerformed(ActionEvent e) {
		String actionCommand = ((JButton) e.getSource()).getActionCommand(); //Line copied from StackOverflow

		switch (actionCommand) {
			case "endRound" -> game.floodRandomCells(3);
		}

	}
}