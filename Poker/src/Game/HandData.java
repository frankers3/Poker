package Game;

import java.util.ArrayList;

public class HandData {
	int combos = 0;
	//already made hands
	char[] suits = new char[] {'H','D','S','C'};
	float fullHouseOrBetter = 0;
	float flush = 0;
	float straight = 0;
	float set = 0;
	float twoPair = 0;
	float topPair = 0;
	float midPair = 0;
	float weakPair = 0;
	float noMadeHand = 0;
	//draws
	float overCards = 0;
	float OESD = 0;
	float gutShot = 0;
	float flushDraw = 0;
	float combinations = 0;
	public HandData(int card1Val, int card2Val,boolean suited, ArrayList<Card> deadCards) {
		
		Card card1 = null;
		Card card2 = null;
		Deck deck = new Deck(deadCards);
		deck.addCardsToDeck();
		for(char suit1 : suits) {
			for(char suit2 : suits) {
				if(suited && suit1 == suit2) {
					if(deck.isInDeck(suit1, card1Val) && deck.isInDeck(suit2, card2Val))	{
						if(combos == 0)	{
							card1 = new Card(suit1,card1Val);
							card2 = new Card(suit2,card2Val);
							
						}
						combos++;
					}
				}
				if(!suited && suit1 != suit2) {
					if(deck.isInDeck(suit1, card1Val) && deck.isInDeck(suit2, card2Val))	{
						if(combos == 0)	{
							card1 = new Card(suit1,card1Val);
							card2 = new Card(suit2,card2Val);
							
						}
						combos++;
					}
				}
			}
		}
		if(combos == 0) {
			return;
		}
		if(card1Val == card2Val)	{
			combos = combos / 2;
		}
		deck.removeCardfromDeck(card1.suit, card1.val);
		deck.removeCardfromDeck(card2.suit, card2.val);
		
		System.out.println(card1Val);
		System.out.println(card2Val);
		System.out.println(combos);
		
		Card[] hand;
		hand = new Card[] {card1,card2};
		Card[] table;
		for(int flop_card1 = 0; flop_card1 < deck.cards.size() -2; flop_card1++)	{
			for(int flop_card2 = flop_card1 + 1; flop_card2 < deck.cards.size() -1; flop_card2++)	{
				for(int flop_card3= flop_card2 + 1; flop_card3 < deck.cards.size(); flop_card3++)	{
					table = new Card[]	{deck.cards.get(flop_card1),deck.cards.get(flop_card2),deck.cards.get(flop_card3)};
					HandCalc handCalc = new HandCalc(table,hand);
					
					handCalc.calcHand();
					mapNumberToHand(handCalc.handType,handCalc);
					checkDraws(handCalc);
					combinations++;
				}
			}
		}
		dividebyCombinations();
		System.out.println(fullHouseOrBetter);
		System.out.println(flushDraw);
		System.out.println(gutShot);
		System.out.println(OESD);
		System.out.println(overCards);
		System.out.println("----------------");
		
	}
	
	public HandData()	{
		
	}
	
	public void mapNumberToHand(int number, HandCalc handCalc)	{
		if(number >= 6)	{
			fullHouseOrBetter++;
		}else if(number == 5)	{
			flush++;
		}else if(number == 4)	{
			straight++;
		}else if(number == 3)	{
			set++;
		}else if(number == 2)	{
			twoPair++;
		}else if(number == 1)	{
			if(handCalc.topPair())	{
				topPair++;
			}else if(handCalc.midPair())	{
				midPair++;
			}else if(handCalc.weakPair())	{
				weakPair++;
			}else {
				noMadeHand++;
			}
		}else {
			noMadeHand++;
		}
	}
	

	public void checkDraws(HandCalc handCalc)	{
		if(handCalc.flushDraw() && handCalc.handType < 5) {
			flushDraw++;
		}
		if(handCalc.GutShot() && handCalc.handType < 4)	{
			gutShot++;
		}
		if(handCalc.OESD() && handCalc.handType < 4)	{
			OESD++;
		}
		if(handCalc.overCards() && handCalc.handType < 4)	{
			overCards++;
		}
	}
	
	public void dividebyCombinations()	{
		int percentConverter = 100;
		fullHouseOrBetter = percentConverter*fullHouseOrBetter / combinations;
		flush = percentConverter*flush / combinations;
		straight = percentConverter*straight / combinations;
		set = percentConverter*set / combinations;
		twoPair = percentConverter*twoPair / combinations;
		topPair = percentConverter*topPair / combinations;
		midPair = percentConverter*midPair / combinations;
		weakPair = percentConverter*weakPair / combinations;
		noMadeHand = percentConverter*noMadeHand / combinations;
		
		overCards = percentConverter*overCards / combinations;
		OESD = percentConverter*OESD / combinations;
		gutShot = percentConverter*gutShot / combinations;
		flushDraw = percentConverter*flushDraw / combinations;
	}
}
