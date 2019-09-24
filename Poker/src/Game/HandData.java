package Game;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class HandData {
	int combos = 0;
	//already made hands
	DecimalFormat format = new DecimalFormat("0.00");
	public double[] handType = new double[14];
	
	char[] suits = new char[] {'H','D','S','C'};
	public double[][] drawDoubleUps = new double[4][14];
	double combinations = 0;
	int handNum = 16;
	int drawNum = -1;
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
		

		
		Card[] hand;
		hand = new Card[] {card1,card2};
		Card[] table;
		for(int flop_card1 = 0; flop_card1 < deck.cards.size() -2; flop_card1++)	{
			for(int flop_card2 = flop_card1 + 1; flop_card2 < deck.cards.size() -1; flop_card2++)	{
				for(int flop_card3= flop_card2 + 1; flop_card3 < deck.cards.size(); flop_card3++)	{
					table = new Card[]	{deck.cards.get(flop_card1),deck.cards.get(flop_card2),deck.cards.get(flop_card3)};
					HandCalc handCalc = new HandCalc(table,hand);
					handNum = 16;
					drawNum = -1;
					handCalc.calcHand();
					mapNumberToHand(handCalc.handType,handCalc,true);
					checkDraws(handCalc,true);
					combinations++;
				}
			}
		}
		if(suited)
			twoCardFlush(card1Val,card2Val,deadCards);
		dividebyCombinations();
		
	}
	
	public HandData()	{
		
	}
	
	public int mapNumberToHand(int number, HandCalc handCalc, boolean changeData)	{
	
		if(number >= 6)	{
			handNum = 0;
			if(changeData)
				handType[0]++;
		}else if(number == 5)	{
			
			handNum = 1;
		}else if(number == 4)	{
			handNum = 2;
			if(changeData)
				handType[2]++;
		}else if(number == 3)	{
			handNum = 3;
			if(changeData)
				handType[3]++;
		}else if(number == 2)	{
			//
			handNum = 4;
			if(changeData)
				handType[4]++;
		}else if(number == 1)	{
			if(handCalc.topPair())	{
				handNum = 6;
				if(changeData)
					handType[6]++;
			}else if(handCalc.midPair())	{
				handNum = 7;
				if(changeData)
					handType[7]++;
			}else if(handCalc.weakPair())	{
				handNum = 8;
				if(changeData)
					handType[8]++;
			}else {
				if(handCalc.overPair())	{
					handNum = 5;
					if(changeData)
						handType[5]++;
					
					return handNum;
				}
				if(handCalc.overCards())	{
					if(changeData) {
						handType[13]++;
						drawDoubleUps[3][0]++;
					}
				}
					
				handNum = 9;	
				if(changeData)
					handType[9]++;
			
			}
		}else {
			handNum = 9;
			if(changeData)
				handType[9]++;
		}
		return handNum;
	}
	

	public void checkDraws(HandCalc handCalc, boolean changeData)	{
		
		if(handCalc.GutShot() && handCalc.handType < 4)	{
			if(changeData)	{
				handType[12]++;
				if(handNum < 9)	{
					drawDoubleUps[2][handNum]++;
					
				}else {
					int secondDraw = checkSecondDraw(2,handCalc);
					if(secondDraw > 0)
						drawDoubleUps[2][secondDraw]++;
				}
			}
		}
		if(handCalc.OESD() && handCalc.handType < 4)	{
			if(changeData)	{
				handType[11]++;
				if(handNum < 9)	{
					drawDoubleUps[1][handNum]++;
					
				}else {
				int secondDraw = checkSecondDraw(1,handCalc);
				if(secondDraw > 0)
					drawDoubleUps[1][secondDraw]++;
				}
			}
		}
		if(handCalc.overCards() && handCalc.handType < 1)	{
			if(changeData)	{
				handType[13]++;
				if(handNum < 9)	{
					drawDoubleUps[3][handNum]++;
				}else {
				int secondDraw = checkSecondDraw(3,handCalc);
				if(secondDraw > 0)
					drawDoubleUps[3][secondDraw]++;
				}
			}
		}
		
	}
	
	public void dividebyCombinations()	{
		int percentConverter = 100;
		for(int i = 0; i < handType.length; i++)	{
			handType[i] = percentConverter*handType[i] / combinations;
			for(int j = 0; j <4;j++)	{
				drawDoubleUps[j][i] = percentConverter*drawDoubleUps[j][i] / combinations;
			}
		}
		
		
	}
	
	public ArrayList<String> getData()	{
		ArrayList<String> data = new ArrayList<String>();
		
		data.add("Fullhouse or Better:\t" + (format.format(handType[0])) + "%");
		data.add("Flush:\t\t\t" + (format.format(handType[1]))+ "%");
		data.add("Straight:\t\t" + (format.format(handType[2]))+ "%");
		data.add("Set:\t\t\t" + (format.format(handType[3]))+ "%");
		data.add("Two Pair:\t\t" + (format.format(handType[4]))+ "%");
		data.add("Over Pair:\t\t" + (format.format(handType[5]))+ "%");
		data.add("Top Pair:\t\t" + (format.format(handType[6]))+ "%");
		data.add("Mid Pair:\t\t" + (format.format(handType[7]))+ "%");
		data.add("Weak Pair:\t\t" + (format.format(handType[8]))+ "%");
		data.add("No Made Hand:\t\t" + (format.format(handType[9]))+ "%");
		data.add("Flush Draw:\t\t" + (format.format(handType[10]))+ "%");
		data.add("OESD:\t\t\t" + (format.format(handType[11]))+ "%");
		data.add("Gut Shot:\t\t" + (format.format(handType[12])) + "%");
		data.add("Over Cards:\t\t" + (format.format(handType[13]))+ "%");
		return data;
	}
	
	public void twoCardFlush(int card1V, int card2V, ArrayList<Card> deadCards)	{
		Card[] table;
		Card[] hand;
		
		for(char suit : suits)	{
			Deck deck = new Deck(deadCards);
			deck.addCardsToDeck();
			Card card1 = new Card(suit,card1V);
			Card card2 = new Card(suit,card2V);
			if(deck.isInDeck(card1.suit, card1.val) && deck.isInDeck(card2.suit, card2.val))	{
	
			hand = new Card[]	{card1,card2};
			deck.removeCardfromDeck(card1.suit, card1.val);
			deck.removeCardfromDeck(card2.suit, card2.val);
			for(int flop_card1 = 0; flop_card1 < deck.cards.size() -2; flop_card1++)	{
				for(int flop_card2 = flop_card1 + 1; flop_card2 < deck.cards.size() -1; flop_card2++)	{
					for(int flop_card3= flop_card2 + 1; flop_card3 < deck.cards.size(); flop_card3++)	{
						table = new Card[]	{deck.cards.get(flop_card1),deck.cards.get(flop_card2),deck.cards.get(flop_card3)};
						HandCalc handCalc = new HandCalc(table,hand);
						handNum = -1;
						
						handCalc.calcHand();
						mapNumberToHand(handCalc.handType,handCalc,false);
						//the bug is mapNumber to hands fucking up the numbers
						//fix make number to hand return an int and then use that int to change the numbers.
						if(handCalc.handType > 5) {
							
						}else if(handCalc.handType == 5)	{
							
							handType[1]+=0.25;
						}else if(handCalc.flushDraw())	{
							
							if(handNum > 0)	{
								
								drawDoubleUps[0][handNum]+= 0.25;
							}else {
								int secondDraw = checkSecondDraw(0,handCalc);
								if(secondDraw > 0)
									drawDoubleUps[0][secondDraw]+=0.25;
								handType[10]+=0.25;
							}
						}
						
						
					}
				}
			}
		}
	}
	}
	
	public ArrayList<Integer> postFlop(ArrayList<Card> tableCards)	{
		
	}
	
	private int checkSecondDraw(int firstDrawNum, HandCalc handCalc)	{
			if(handCalc.GutShot() && handCalc.handType < 4 && firstDrawNum < 2)	{
			
				return 12;
				
			}
		
		if(handCalc.OESD() && handCalc.handType < 4 && firstDrawNum < 1)	{
				return 11;
			
			}
		
		if(handCalc.overCards() && handCalc.handType < 1 && firstDrawNum < 3)	{
				return 13;
				
			
			}
		return 0;
	}
}

	
	
