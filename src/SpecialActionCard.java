public class SpecialActionCard implements Card {
	SpecialActionType type;

	public String getText() {
		switch(type) {
			case Helicopter -> {return "Helicopter special action card";}
			case Sandbags   -> {return "Sandbags special action card";}
		}
		return "";
	}
}

enum SpecialActionType {
	Helicopter,
	Sandbags,
}
