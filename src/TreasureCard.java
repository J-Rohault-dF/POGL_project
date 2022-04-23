public class TreasureCard implements Card {
	TreasureType type;

	public TreasureCard(TreasureType type) {
		this.type = type;
	}

	public String getText() {
		switch(type) {
			case ArdentCristal -> {return "Ardent Cristal key";}
			case SacredStone   -> {return "Sacred Stone key";}
			case WaveChalice   -> {return "Wave Chalice key";}
			case ZephyrStatue  -> {return "Zephyr Statue key";}
		}
		return "";
	}
}
