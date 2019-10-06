package Game;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Set;
public class HandData	{
	int combos = 0;
	//already made hands
	DecimalFormat format = new DecimalFormat("0.00");
	public double[] handType = new double[14];
	ArrayList<Integer> draws = new ArrayList<Integer>();
	char[] suits = new char[] {'H','D','S','C'};
	DrawTable drawTable = new DrawTable();
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
		if(!suited) {
			for(int flop_card1 = 0; flop_card1 < deck.cards.size() -2; flop_card1++)	{
				for(int flop_card2 = flop_card1 + 1; flop_card2 < deck.cards.size() -1; flop_card2++)	{
					for(int flop_card3= flop_card2 + 1; flop_card3 < deck.cards.size(); flop_card3++)	{
						table = new Card[]	{deck.cards.get(flop_card1),deck.cards.get(flop_card2),deck.cards.get(flop_card3)};
						HandCalc handCalc = new HandCalc(table,hand);
						handNum = 16;
						
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
		}else {
			twoCardFlush(card1Val,card2Val,deadCards);
		}
		dividebyCombinations();
		
	}
	
	public HandData()	{
		
	}
	
	public int mapNumberToHand(int number, HandCalc handCalc, boolean suited)	{
	
		if(number >= 6)	{
			handNum = 14;
			
				handType[0]++;
		}else if(number == 5 && suited)	{
			
				handNum = 1;
				handType[1]++;
			
		}else if(number == 4)	{
			handNum = 2;
			
			handType[2]++;
		}else if(number == 3)	{
			handNum = 3;
			
			handType[3]++;
		}else if(number == 2)	{
			//
			handNum = 4;
			
			handType[4]++;
		}else if(number == 1)	{
			if(handCalc.topPair())	{
				handNum = 6;
				
				handType[6]++;
			}else if(handCalc.midPair())	{
				handNum = 7;
				
				handType[7]++;
			}else if(handCalc.weakPair())	{
				handNum = 8;
				
				handType[8]++;
			}else {
				if(handCalc.overPair())	{
					handNum = 5;
				
					handType[5]++;
					
					return handNum;
				}
				if(handCalc.overCards())	{
					draws.add(13);
					handType[13]++;
						
					
				}
					
				handNum = 9;	
				
				handType[9]++;
			
			}
		}else {
			handNum = 9;
			handType[9]++;
		}
		return handNum;
	}
	

	public void checkDraws(HandCalc handCalc, boolean suited)	{
		
		if(handCalc.GutShot() && handCalc.handType < 4)	{
			
				handType[12]++;	
				draws.add(12);
		}
		if(handCalc.OESD() && handCalc.handType < 4)	{
			
				handType[11]++;
				draws.add(11);
			
		}
		if(handCalc.overCards() && handCalc.handType < 1)	{
			
				handType[13]++;
				draws.add(13);
			
		}
		
		if(handCalc.flushDraw() && handCalc.handType < 5 && suited)	{
			handType[10]++;
			draws.add(10);
		}
		
	}
	
	public void dividebyCombinations()	{
		int percentConverter = 100;
		double sum = 0;
		for(int i = 0; i < handType.length; i++)	{
			handType[i] = percentConverter*handType[i] / combinations;
			sum+= handType[i];
		}
		Set<Integer> keys = drawTable.multidrawTable.keySet();
		for(int key : keys)	{
			drawTable.multidrawTable.get(key).dividValue(combinations/percentConverter);
			drawTable.printKey(key);
			sum -= drawTable.multidrawTable.get(key).getValue() * (drawTable.multidrawTable.get(key).getDrawNum() - 1);
			System.out.println(drawTable.multidrawTable.get(key).getValue());
		}
		System.out.println(sum);
		
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
						int handNumber = mapNumberToHand(handCalc.handType,handCalc,true);
						draws.add(handNumber);
						
						checkDraws(handCalc,true);
						drawTable.insertVal(draws);
						draws.clear();
						combinations++;
						
					}
				}
			}
		}
	}
	}
	
	public ArrayList<Integer> postFlop(ArrayList<Card> tableCards)	{
		
	}
	
	public double hitPercentage(Filter filter)	{
		double sum = 0;
		ArrayList <Integer> noHit = new ArrayList<Integer>();
		for(int i = 0; i < filter.doesItHit.length;i++)	{
			if(filter.doesItHit[i] == false)	{
				noHit.add(i);
			}
		}
		for(int i = 0; i < handType.length; i++)	{
			if(!noHit.contains(i))
			sum+= handType[i];
		}
		Set<Integer> keys = drawTable.multidrawTable.keySet();
		for(int key : keys)	{
			int flag = 0;
			ArrayList <Integer> hit = drawTable.getDraws(key);
			for(int hitInt: hit)	{
				if(noHit.contains(hitInt))	{
					flag++;
				}
			}
				
				sum -= drawTable.multidrawTable.get(key).getValue() * Math.max(0, (drawTable.multidrawTable.get(key).getDrawNum() - (flag + 1)));
			
			
		}
		return sum;
	}
}

	
	
