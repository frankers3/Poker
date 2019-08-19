package Game;

import java.util.ArrayList;
public class Deck {

	public ArrayList<Card> cards = new ArrayList<Card>();
	DeckState current_state = new EmptyDeck();
	public ArrayList<Card> cardsOutOfDeck = new ArrayList<Card>();
	public Deck(ArrayList<Card> cards)	{
		this.cardsOutOfDeck.addAll(cards);
	}
	public boolean addCardsToDeck()	{
			//create the deck by adding cards 
		
		for(int i = 2; i <=14; i++)	{
			
				char s = ' ';
				for(int suit = 1; suit <=4; suit++) {
					switch(suit) {
						case 1:
							s = 'H';
							break;
						case 2:
							s = 'D';
							break;
						case 3:
							s = 'C';
							break;
						case 4:
							s = 'S';
							break;
					
					}
					Card tobeAdded = new Card(s,i);
					
						cards.add(tobeAdded);
					
				
		
				}

		}
		
			for(int i = 0; i < cardsOutOfDeck.size(); i++) {
				for(int j = cards.size() -1; j >= 0; j--)	{
				
					if(cards.get(j).getVal() == cardsOutOfDeck.get(i).getVal() && cards.get(j).suit == cardsOutOfDeck.get(i).suit) {
						cards.remove(j);
					
				}
			}
		}
		System.out.println(cards);
		return true;	
	}
	
	public boolean shuffle()	{
		int length = this.cards.size();
		ArrayList<Card> shuffled = new ArrayList<Card>();
		//pick a random card an add it to a new list n times.
		for(int i = length - 1; i >= 0; i--) {
			Card toBeAdded = this.cards.remove(getRandomInteger(0,i));
			shuffled.add(toBeAdded);
		}
		//replace the old ordered list with the shuffled one.
		this.cards = shuffled;
		return true;
	}
	
	public boolean startHand() {
			return true;
	}
		
	public boolean refill() {
			return true;
	}


public static int getRandomInteger(int min, int max){
    int x =  (int) ((Math.random()*((max-min)+1))+min);
    return x;
}
public Card getCard() {
	if(current_state.getState() != "Dealing") {
			return null;
	}
	return cards.remove(0);
}
		
	
	
}