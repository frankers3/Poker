package Game;

public class Card {

	
	char suit; 
	Integer val;
	public Card(char suit, int val)	{
		this.suit = suit;
		this.val = val;
	}
	
	public String toString(){
	
		return val + " " + suit;
		
	}
	public Integer getVal() {
		return val;
	}
	
	public boolean equals(Card card)	{
		if(card.suit == this.suit && card.val == this.val)	{
			return true;
		}
		return false;
	}
}


	

