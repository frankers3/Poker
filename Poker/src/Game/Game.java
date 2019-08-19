package Game;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Game {
	Deck gameDeck;
	GameState state = new preFlop();
	ArrayList <Card> communityCards= new ArrayList<Card>();
	ArrayList <Card> myCards = new ArrayList<Card>();
	ArrayList <Card> theirCards = new ArrayList<Card>();
	
	public Game(Card mycard1, Card mycard2, Card card1, Card card2) {
		ArrayList <Card> ourCards= new ArrayList<Card>();
		ourCards.addAll(Arrays.asList(mycard1,mycard2,card1,card2));
		myCards.addAll(Arrays.asList(mycard1,mycard2));
		theirCards.addAll(Arrays.asList(card1,card2));
		
		gameDeck = new Deck(ourCards);
		
	}
	public Game() {
		// TODO Auto-generated constructor stub
	}
	public boolean dealFlop() {
		for(int i =0; i < 3; i++)	{
		communityCards.add(gameDeck.getCard());
		}
		if(communityCards.size() ==3 ) {
			return true;
		}
		return false;
	}
	
	public boolean dealTurn() {
		communityCards.add(gameDeck.getCard());
		
		if(communityCards.size() == 4) {
			return true;
		}
		return false;
	}
	
	public boolean dealRiver() {
		communityCards.add(gameDeck.getCard());
		
		if(communityCards.size() == 5) {
			return true;
		}
		return false;
	}
	//this is going to look digusting and there isn't really much
	//i can do about that :(
	//these vals will be stored in a text file
	public int calculatepreflopOdds()	{
		Card[] myhand;
		Card[] theirhand;
		double pos = 0;
		double neg = 0; 
		gameDeck.addCardsToDeck();
		System.out.print(gameDeck.cards.size());
		for(Card firstcard : gameDeck.cards) {
			System.out.println(firstcard.getVal());
			for(Card secondcard : gameDeck.cards) {
				if(firstcard != secondcard) {
				
				for(Card thirdcard : gameDeck.cards) {
					if(firstcard != thirdcard && secondcard != thirdcard) {
					for(Card fourthcard : gameDeck.cards) {
						if(firstcard != fourthcard && secondcard != fourthcard && thirdcard != fourthcard)
						for(Card fifthcard : gameDeck.cards) {
							if(firstcard != fifthcard && secondcard != fifthcard && thirdcard != fifthcard && fourthcard != fifthcard)	{
								//every possible community card
								myhand = new Card[] {myCards.get(0),myCards.get(1),firstcard,secondcard,thirdcard,fourthcard,fifthcard};
								theirhand = new Card[] {theirCards.get(0),theirCards.get(1),firstcard,secondcard,thirdcard,fourthcard,fifthcard};
								HandCalc mybesthand = new HandCalc(myhand);
								HandCalc theirbesthand = new HandCalc(theirhand);
								int val = mybesthand.isBetter(theirbesthand);
								if(val == 1)	{
									pos++;
								} else if(val == -1)	{
									neg++;
								}else {
									pos+= 0.5;
									neg+= 0.5;
								}
							}
							
							
						}	
						
					}	
					}
				}	
				}
			}
			
			
		}
		System.out.print(pos);
		System.out.print(neg);
		int rate = (int) (pos/(pos + neg) * 10000);
		System.out.print(rate);
		return rate;
	}
	
	
	
	public void reset(Card a, Card b, Card c, Card d) {
		myCards.clear();
		theirCards.clear();
		myCards.add(a);
		myCards.add(b);
		theirCards.add(c);
		theirCards.add(d);
		ArrayList<Card> temp = new ArrayList<Card>();
		temp.addAll(myCards);
		temp.addAll(theirCards);
		gameDeck = new Deck(temp);
	}
	
}