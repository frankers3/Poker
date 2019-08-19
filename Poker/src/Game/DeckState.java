package Game;

public interface DeckState {
	public String getState();
	public boolean goNextState(Deck deck);
		
	
		
}

class EmptyDeck implements DeckState	{
	public String getState() {
		return "Empty";
	}
	
	public boolean goNextState(Deck deck) {
		if(deck.addCardsToDeck()) {
			deck.current_state = new UnShuffled();
			return true;
		}
		return false;
	}
}

class UnShuffled implements DeckState	{
	public String getState() {
		return "UnShuffled";
	}
	
	public boolean goNextState(Deck deck) {
		if(deck.shuffle()) {
			deck.current_state = new Shuffled();
			return true;
		}
		return false;
	}
}

class Shuffled implements DeckState	{
	public String getState() {
		return "Shuffled";
	}
	
	public boolean goNextState(Deck deck) {
		if(deck.startHand()) {
			deck.current_state = new Dealing();
			return true;
		}
		return false;
	
	}
}
class Dealing implements DeckState {
	public String getState()	{
		return "Dealing";
	}
	
	public boolean goNextState(Deck deck) {
		if(deck.refill()) {
			deck.current_state = new Shuffled();
			return true;
		}
		return false;
	}
}


