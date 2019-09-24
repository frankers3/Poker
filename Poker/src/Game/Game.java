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
//no longer used 
	/*
	public int calculatepreflopOdds()	{
		Card[] myhand;
		Card[] theirhand;
		if(myCards.get(0).equals(theirCards.get(0)) || myCards.get(1).equals(theirCards.get(0)) ||
		   myCards.get(0).equals(theirCards.get(1)) || myCards.get(1).equals(theirCards.get(1)))	{
			return -1;
		}
		double pos = 0;
		double neg = 0; 
		gameDeck.addCardsToDeck();
		
		for(int i0 = 0; i0 < gameDeck.cards.size() -4; i0++) {
			
			for(int i1 = i0 + 1; i1 < gameDeck.cards.size() -3; i1++) {
				
				
				for(int i2 = i1 + 1; i2 < gameDeck.cards.size() - 2; i2++) {
					for(int i3 = i2 + 1; i3 < gameDeck.cards.size() - 1; i3++) {
						for(int i4 = i3 + 1; i4 < gameDeck.cards.size(); i4++) {
						
						
							
								//every possible community card
								myhand = new Card[] {myCards.get(0),myCards.get(1),gameDeck.cards.get(i0),gameDeck.cards.get(i1),gameDeck.cards.get(i2),gameDeck.cards.get(i3),gameDeck.cards.get(i4)};
								theirhand = new Card[] {theirCards.get(0),theirCards.get(1),gameDeck.cards.get(i0),gameDeck.cards.get(i1),gameDeck.cards.get(i2),gameDeck.cards.get(i3),gameDeck.cards.get(i4)};
								HandCalc mybesthand = new HandCalc(null,myhand);
								HandCalc theirbesthand = new HandCalc(null,theirhand);
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
		//System.out.print(pos);
		//System.out.print(neg);
		int rate = (int) (pos/(pos + neg) * 10000);
		System.out.println(rate);
		return rate;
	}
	
	
	*/
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