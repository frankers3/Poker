package Game;

import java.util.ArrayList;

public class PostFlopHand extends HandData	{
	
	ArrayList <Card> cards = new ArrayList<Card>();
	public PostFlopHand(int card1Val, int card2Val,boolean suited, ArrayList<Card> deadCards,ArrayList<Card> table)	{
		super();
		deadCards.addAll(table);
		Deck deck = new Deck(deadCards);
		deck.addCardsToDeck();
		if(suited)	{
			for(char suit: suits)	{
				Card card1 = new Card(suit,card1Val);
				Card card2 = new Card(suit,card2Val);
				
				if(deck.isInDeck(card1.suit,card1.val) && deck.isInDeck(card2.suit,card2.val))	{
					Card[] hand = new Card[] {card1,card2};
					Card [] this_table;
					if(table.size() == 3)	{
						this_table = new Card[]	{table.get(0),table.get(1),table.get(2)};
					}else if(table.size() == 4) {
						this_table = new Card[]	{table.get(0),table.get(1),table.get(2),table.get(3)};	
					}else {
						this_table = new Card[]	{table.get(0),table.get(1),table.get(2),table.get(3),table.get(4)};
					}
					HandCalc handCalc = new HandCalc(this_table,hand);
					
					
					handCalc.calcHand();
					int handNumber = mapNumberToHand(handCalc.handType,handCalc,true);
					draws.add(handNumber);
					
					checkDraws(handCalc,true);
					drawTable.insertVal(draws);
					draws.clear();
					combinations++;
				}
			}
		}else {
			for(char suit1 : suits)	{
				for(char suit2: suits)	{
					Card card1 = new Card(suit1,card1Val);
					Card card2 = new Card(suit2,card2Val);
					
					if(deck.isInDeck(card1) && deck.isInDeck(card2) && suit1 != suit2)	{
						Card[] hand = new Card[] {card1,card2};
						Card [] this_table;
						if(table.size() == 3)	{
							this_table = new Card[]	{table.get(0),table.get(1),table.get(2)};
						}else if(table.size() == 4) {
							this_table = new Card[]	{table.get(0),table.get(1),table.get(2),table.get(3)};	
						}else {
							this_table = new Card[]	{table.get(0),table.get(1),table.get(2),table.get(3),table.get(4)};
						}
						HandCalc handCalc = new HandCalc(this_table,hand);
						
						
						handCalc.calcHand();
						int handNumber = mapNumberToHand(handCalc.handType,handCalc,false);
						draws.add(handNumber);
						
						checkDraws(handCalc,false);
						drawTable.insertVal(draws);
						draws.clear();
						combinations++;
					}
				}
			}
		}
		if(card1Val == card2Val)
			combos = (int) combinations / 2;
		else
			combos = (int) combinations;
		dividebyCombinations();
	}
	
	public boolean AddCard(int val, char suit)	{
		if(cards.size() > 4) {	
			return false;
		}else {
			Card e = new Card(suit,val);
			for(Card card: cards) {
				if(card.equals(e))
					return false;
			}
			
			cards.add(e);
			return true;
		}
		
	}
	
	public boolean removeCard(int val, char suit)	{
		if(cards.size() <= 0) {	
			return false;
		}else {
			Card e = new Card(suit,val);
			for(Card card: cards) {
				if(card.equals(e))	{
					cards.remove(card);
					return true;
				}
			}
			
			
			return false;
		}
		
	}
}
