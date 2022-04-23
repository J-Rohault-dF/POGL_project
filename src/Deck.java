import java.util.ArrayList;
import java.util.Collections;

public class Deck {
	ArrayList<Card> cards;

	public Deck() {
		cards = new ArrayList<>();

		//Add all the treasure cards
		for(TreasureType tt : new TreasureType[]{TreasureType.ArdentCristal, TreasureType.SacredStone, TreasureType.WaveChalice, TreasureType.ZephyrStatue}) {
			for(int i=0; i<5; i++) {
				cards.add(new TreasureCard(tt));
			}
		}

		/*for(int i=0; i<3; i++) {
			cards.add(new WaterRiseCard());
		}*/

		this.shuffle();
	}

	public void addCard(Card c) {this.cards.add(c);}

	public void shuffle() {Collections.shuffle(this.cards);}

	public Card draw() {return this.cards.remove(0);}
}
