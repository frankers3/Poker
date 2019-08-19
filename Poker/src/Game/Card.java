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
}


	

