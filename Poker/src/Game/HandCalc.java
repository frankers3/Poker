package Game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class HandCalc {
	public ArrayList <Card> cards = new ArrayList<Card>();
	public int handType;
	public int handVal;
	public HandCalc(Card[] cards)	{
		if(cards.length != 7) {
			return;
		}
		for(Card i : cards) {
			this.cards.add(i);
		}
		Collections.sort(this.cards,new CustomComparator());
	}
	
	public int findStraight()	{
		int checker = 0;
		for (int i = 5; i >= 0; i--) {
			if(this.cards.get(i).getVal() + 1 == this.cards.get(i + 1).getVal())	{
				checker++;
				
			}	else {
				checker = 0;
			}
			//this means a straight has been found
			if(checker == 4) {
				//return the highest card in the straight
				return this.cards.get(i).getVal() + 4;
			}
		}
		checker = 0;
		if(cards.get(6).getVal() == 14)	{
			for(int i = 0; i < 5; i++) {
				if(this.cards.get(i).getVal() + 1 == this.cards.get(i + 1).getVal())	{
					checker++;
				}	else {
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
		for (int i = 5; i >= 0; i--) {
			if(this.cards.get(i).getVal() + 1 == this.cards.get(i + 1).getVal() && this.cards.get(i).suit == this.cards.get(i + 1).suit)	{
				checker++;
			}	else {
				checker = 0;
			}
			//this means a straight flush has been found
			if(checker == 4) {
				//return the highest card in the straight
				return this.cards.get(i).getVal() + 4;
			}
		}
		checker = 0;
		if(this.cards.get(6).getVal() == 14) {
			char suit = cards.get(6).suit;
			for(int i = 0; i < 5; i++)	{
					if(cards.get(i).getVal() + 1 == this.cards.get(i + 1).getVal() && this.cards.get(i).suit == suit && this.cards.get(i + 1).suit == suit)	{
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
				int flush_card = 4;
				
				//find the highest card in the flush
				
				for(int j = 6; j >= 0; j--)	{
					if(cards.get(j).suit == flush_suit)	{
						flush_val += cards.get(i).getVal() * Math.pow(13, flush_card);
				
						flush_card--;
					}
				}
			}
		}
		return flush_val;
	}
	
	int findSet()	{
		for(int i = 4; i >= 0; i--)	{
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
				int highest_card = cards.get(2).getVal();
				return setVal * 13 + highest_card;
			}
		}
		return 0;
	}
	
	int findFullHouse(int setVal)	{
		int pair = findPair();
		if(pair > 0) {
			return 13*setVal + findPair();
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
						currentBestHand = 8;
						currentBestVal = checker;
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
				return;
			}
			checker1 = findFullHouse(checker);
			if(checker1 != 0) {
				handType = 6;
				handVal = checker1;
				return;
			}
		}
		//now since the flush and straights  which were found above are next best
		if(currentBestHand != 0)	{
			handType = currentBestHand;
			handVal = currentBestVal;
			return;
		}
		//checker contains setVal()
		if(checker != 0)	{
			handType = 3;
			handVal = (int) (checker * Math.pow(13, 2) + cards.get(3).getVal() * 13 + cards.get(2).getVal());
			return;
		}
		
		checker = findPair();
		if(checker != 0) {
			checker1 = findPair();
			if(checker1 != 0) {
				handType = 2;
				handVal = 13 * 13 * checker + 13 * checker1 + cards.get(2).getVal(); 
				return;
			}
			handType = 1;
			handVal = (int) (Math.pow(13, 3) * checker + Math.pow(13, 2) * cards.get(4).getVal() + 13 * cards.get(3).getVal() + cards.get(2).getVal());
			return;
		}
		handType = 0;
		handVal = (int) (Math.pow(13, 4) * cards.get(6).getVal() + Math.pow(13, 3) * cards.get(5).getVal() + Math.pow(13, 2) * cards.get(4).getVal() + 13 * cards.get(3).getVal() + cards.get(2).getVal());
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
	
}


