package Game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class HandCalc {
	public ArrayList <Card> cards = new ArrayList<Card>();
	public ArrayList <Card> hand = new ArrayList<Card>();
	public ArrayList <Card> table = new ArrayList<Card>();
	public int handType;
	public int handVal;
	private char flush_suit;
	private char flush_draw_suit;
	private int straightDraw = -1;
	public HandCalc(Card[] table_cards,Card[] hand){
		if(table_cards.length < 3 || hand.length != 2) {
			return;
		}
		
		for(Card i : table_cards) {
			this.cards.add(i);
			this.table.add(i);
		}
		for(Card i :hand)	{
			this.cards.add(i);
			this.hand.add(i);
		}
		Collections.sort(this.cards,new CustomComparator());
		Collections.sort(this.hand,new CustomComparator());
		Collections.sort(this.table,new CustomComparator());
	}
	
	public HandCalc(ArrayList <Card> table){
		if(table.size() < 3) {
			return;
		}
		
		for(Card i : table) {
			this.cards.add(i);
			
		}
		
		Collections.sort(this.cards,new CustomComparator());
		
	}
	
	public int findStraight()	{
		int checker = 0;
		for (int i = cards.size() -2; i >= 0; i--) {
			if(this.cards.get(i).getVal() + 1 == this.cards.get(i + 1).getVal())	{
				checker++;
				
			}	else {
				if(this.cards.get(i).getVal() != this.cards.get(i+1).getVal())
					checker = 0;
			}
			//this means a straight has been found
			if(checker == 4) {
				//return the highest card in the straight
				return this.cards.get(i).getVal() + 4;
			}
		}
		checker = 0;
		if(cards.get(cards.size() -1).getVal() == 14)	{
			for(int i = 0; i < cards.size() -2; i++) {
				if(this.cards.get(i).getVal() + 1 == this.cards.get(i + 1).getVal())	{
					checker++;
				}	else {
					if(this.cards.get(i) != this.cards.get(i + 1))
						checker = 0;
				}
				//this means a straight has been found
				if(checker == 3) {
					//return the highest card in the straight
					if(this.cards.get(i + 1).getVal() == 5)
						return 5;
				}
			}
		}
		//else return 0
		return 0;
	}
	
	public int findStraightFlush()	{
		int checker = 0;
		ArrayList<Card> flush_cards = new ArrayList<Card>();
		for(int i = 0; i <= cards.size() -1; i++) {
			if(this.cards.get(i).suit == this.flush_suit)	{
				flush_cards.add(this.cards.get(i));
			}
		}
		
		for (int i = flush_cards.size() -2; i >= 0; i--) {
			if(flush_cards.get(i).getVal() + 1 == flush_cards.get(i + 1).getVal())	{
				checker++;
			}	else {
				checker = 0;
			}
			//this means a straight flush has been found
			if(checker == 4) {
				//return the highest card in the straight
				return flush_cards.get(i).getVal() + 4;
			}
		}
		checker = 0;
		if(flush_cards.get(flush_cards.size()-1).getVal() == 14) {
			
			for(int i = 0; i < flush_cards.size() -2; i++)	{
					if(flush_cards.get(i).getVal() + 1 == flush_cards.get(i + 1).getVal())	{
						checker++;
					}else {
						checker = 0;
					}
					if(checker == 3) {
						//return the highest card in the straight
						if(this.cards.get(i + 1).getVal() == 5) {
							return 5;
					}
				}
			}
		}
		//else return 0
		return 0;
	}
	
	public int findFlush()	{
		int[] suit;
		char[] map;
		suit = new int[] {0,0,0,0};
		map = new char[] {'H','D','C','S'};
		for(Card i : cards) {
			if(i.suit == 'H')	{
				suit[0]++;
			}else if(i.suit == 'D') {
				suit[1]++;
			}else if(i.suit == 'C')	{
				suit[2]++;
			}else if(i.suit == 'S')	{
				suit[3]++;
			}
		}
		int flush_val = 0;
		for(int i = 0; i < 4; i++) {
			//there is a flush
			if(suit[i] >= 5) {
				char flush_suit = map[i];
				this.flush_suit = flush_suit;
				int flush_card = 4;
				
				//find the highest card in the flush
				
				for(int j = cards.size() -1; j >= 0; j--)	{
					if(cards.get(j).suit == flush_suit && flush_card >= 0)	{
						flush_val += cards.get(j).getVal() * Math.pow(13, flush_card);
				
						flush_card--;
					}
				}
			}
		}
		return flush_val;
	}
	
	int findSet()	{
		for(int i = cards.size() - 3; i >= 0; i--)	{
			if(cards.get(i).getVal() == cards.get(i+1).getVal() && cards.get(i).getVal() == cards.get(i+2).getVal()) {
				int set_val = cards.get(i).getVal();
				cards.remove(i + 2);
				cards.remove(i +1);
				cards.remove(i);
				return set_val;
			}
		}
		return 0;
	}
	
	int findPair()	{
		for(int i = cards.size() - 2;i >= 0; i--)	{
			if(cards.get(i).getVal() == cards.get(i+1).getVal()) {
				int pair_val = cards.get(i).getVal();
				cards.remove(i + 1);
				cards.remove(i);
				return pair_val;
			}
		}
		return 0;
	}
	
	
	
	int findFour(int setVal)	{
		
		for(Card i : cards)	{
			if(i.getVal() == setVal)	{
				cards.remove(i);
				int highest_card = cards.get(cards.size() -1).getVal();
				return setVal * 13 + highest_card;
			}
		}
		return 0;
	}
	
	int findFullHouse(int setVal)	{
		int pair = findPair();
		if(pair > 0) {
			return 13*setVal + pair;
		}
		return 0;
	}
	
	public void calcHand()	{
		
		int currentBestHand = 0;
		int currentBestVal = 0;
		int checker;
		checker = findStraight();
		if(checker != 0)	{
			currentBestHand = 4;
			currentBestVal = checker;
			checker = findFlush();
			if(checker != 0)	{
				currentBestHand = 5;
				currentBestVal = checker;
				checker = findStraightFlush();
				if(checker != 0)	{
						handType = 8;
						handVal = checker;
						resetCards();
						return;
				}
			}
		}
		if(currentBestHand != 4 && currentBestHand != 5)	{
			checker = findFlush();
			if(checker != 0)	{
				currentBestHand = 5;
				currentBestVal = checker;
			}
		}
		checker = findSet();
		int checker1 = 0;
		//finding full house and for of a kind
		if(checker != 0)	{
			checker1 = findFour(checker);
			if(checker1 != 0) {
				handType = 7;
				handVal = checker1;
				resetCards();
				return;
			}
			checker1 = findFullHouse(checker);
			if(checker1 != 0) {
				handType = 6;
				handVal = checker1;
				resetCards();
				return;
			}
		}
		//now since the flush and straights  which were found above are next best
		if(currentBestHand != 0)	{
			handType = currentBestHand;
			handVal = currentBestVal;
			resetCards();
			return;
		}
		//checker contains setVal()
		if(checker != 0)	{
			handType = 3;
			handVal = (int) (checker * Math.pow(13, 2) + cards.get(cards.size() -1).getVal() * 13 + cards.get(cards.size() -2).getVal());
			resetCards();
			return;
		}
		
		checker = findPair();
		if(checker != 0) {
			checker1 = findPair();
			if(checker1 != 0) {
				handType = 2;
				handVal = 13 * 13 * checker + 13 * checker1 + cards.get(cards.size() -1).getVal(); 
				resetCards();
				return;
			}
			handType = 1;
			handVal = (int) (Math.pow(13, 3) * checker + Math.pow(13, 2) * cards.get(cards.size() -1).getVal() + 13 * cards.get(cards.size() -2).getVal() + cards.get(cards.size() -3).getVal());
			resetCards();
			return;
		}
		handType = 0;
		handVal = (int) (Math.pow(13, 4) * cards.get(cards.size() -1).getVal() + Math.pow(13, 3) * cards.get(cards.size() -2).getVal() + Math.pow(13, 2) * cards.get(cards.size() -3).getVal() + 13 * cards.get(cards.size() -4).getVal() + cards.get(cards.size() -5).getVal());
		resetCards();
		return;
		
		
	}
	
	public int isBetter(HandCalc opponentHand)	{
		this.calcHand();
		opponentHand.calcHand();
		if(this.handType > opponentHand.handType) {
			return 1;
		}	else if(this.handType < opponentHand.handType) {
			return -1;
		} if(this.handType == opponentHand.handType) {
			if(this.handVal > opponentHand.handVal)	{
				return 1;
			} else if(this.handVal < opponentHand.handVal)	{
				return -1;
			} else if(this.handVal == opponentHand.handVal)	{
				return 0;
			}
		}
		return -2;
	}
	
	public boolean OESD()	{
		if(straightDraw == -1) {
			straightDraw = straightDraw();
		}
		if(straightDraw == 2)
			return true;
		return false;
	}
	
	public boolean GutShot()	{
		if(straightDraw == -1) {
			straightDraw = straightDraw();
		}
		if(straightDraw == 1)
			return true;
		return false;
	}
	
	public boolean overCards()	{
		int highest = table.get(table.size() -1).getVal();
		int bottomCard = hand.get(0).getVal();
		if(highest < bottomCard)	{
			return true;
		}
		return false;
	}
	
	public boolean flushDraw()	{
		int[] suit;
		char map[];
		suit = new int[] {0,0,0,0};
		map = new char[] {'H','D','C','S'};
		for(Card i : cards) {
			if(i.suit == 'H')	{
				suit[0]++;
			}else if(i.suit == 'D') {
				suit[1]++;
			}else if(i.suit == 'C')	{
				suit[2]++;
			}else if(i.suit == 'S')	{
				suit[3]++;
			}
		}
		
		for(int i = 0; i < 4; i++) {
			//there is a flush draw
			if(suit[i] == 4) {
				flush_draw_suit = map[i];
				return true;
			}
		}
		return false;
	}
	
	public int straightDraw() {
		//will be incremented for every possible card value that returns a straight.
		int straightChances = 0;
		for(int card_val = 2; card_val <= 14; card_val++)	{
			Card straightCard = new Card('H',card_val);
			addSorted(straightCard);
			if(this.findStraight() != 0)	{
				straightChances++;
				
			}
			cards.remove(straightCard);
		}
		return straightChances;
	}
	
public boolean topPair()	{
	if(handType == 1)	{
		if(hand.get(0).getVal() == table.get(table.size() -1).getVal() || hand.get(1).getVal() == table.get(table.size() -1).getVal())	{
			return true;
		}
	}
	if(handType == 2)	{
		if(hand.get(0).getVal() == table.get(table.size() -1).getVal() || hand.get(1).getVal() == table.get(table.size() -1).getVal())	{
			if(this.tablePair() == "Pair")	{
				return true;
			}
		}
	}
		
	return false;
}

public boolean midPair()	{
	if(handType == 1)
		if(hand.get(0).getVal() == table.get(table.size() -2).getVal() || hand.get(1).getVal() == table.get(table.size() -2).getVal())	{
			return true;
		}
	if(hand.get(0).getVal() == hand.get(1).getVal() && table.get(table.size() -1).getVal() > hand.get(0).getVal()&& table.get(table.size() -2).getVal() < hand.get(0).getVal())	{
			return true;
	}
	return false;
}

public boolean weakPair()	{
	if(handType == 1)	{
		for(int i = 0; i < table.size() -2; i++)	{
			if(hand.get(0).getVal() == table.get(i).getVal() || hand.get(1).getVal() == table.get(i).getVal()) {
				return true;
			}
		}
	}
	if(hand.get(0).getVal() == hand.get(1).getVal() && table.get(table.size() -2).getVal() > hand.get(0).getVal())	{
		return true;
}
	return false;
}

public boolean overPair()	{
	if(hand.get(0).getVal() == hand.get(1).getVal() && hand.get(0).getVal() > table.get(table.size() -1).getVal()) {
		return true;
	}
		return false;
}

private void addSorted(Card card)	{
	
	if(cards.get(0).getVal() >= card.getVal())	{
		cards.add(0, card);
		return;
	}
	for(int i = 0; i < cards.size() - 1; i++)	{
		if(cards.get(i).getVal() <= card.getVal() && cards.get(i + 1).getVal() >= card.getVal()) {
			cards.add(i + 1, card);
			return;
		}
	}
	cards.add(cards.size(), card);
}

public boolean nutFlush()	{
	if(this.handType == 5) {
		int nut_card_val = 14;
		for(int i = cards.size() -1; i >=0; i --)	{
			if(cards.get(i).suit == flush_suit && cards.get(i).val == nut_card_val)	{
				if(cards.get(i).equals(hand.get(0)) || cards.get(i).equals(hand.get(1)))	{
					return true;
				}else {
					nut_card_val--;
				}
			}
		}
	}
	return false;
}

public boolean nutFlushDraw()	{
	if(this.flushDraw()) {
		int nut_card_val = 14;
		for(int i = cards.size() -1; i >=0; i --)	{
			if(cards.get(i).suit == flush_suit && cards.get(i).val == nut_card_val)	{
				if(cards.get(i).equals(hand.get(0)) || cards.get(i).equals(hand.get(1)))	{
					return true;
				}else {
					nut_card_val--;
				}
			}
		}
	}
	return false;
}


public String tablePair()	{
	HandCalc hand = new HandCalc(table);
	int val = hand.findSet();
	if(val != 0)	{
		if(hand.findFour(val) != 0)	{
			return "Four of a Kind";
		}
		if(hand.findPair() != 0)	{
			return "Full House";
		}
			return "Set";
	}
	val = hand.findPair();
	if(val != 0)	{
		if(hand.findPair() != 0) {
			return "Two Pair";
		}
		return "Pair";
	}
	return " ";
}

public void resetCards()	{
	if(cards.size() != 5) {
		cards.clear();
		cards.addAll(table);
		cards.addAll(hand);
		Collections.sort(this.cards,new CustomComparator());
	}
}
}