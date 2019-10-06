package Game;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class RangeBoard {
	int card_val_ranges = 13;
	
	HandData[][] board = new HandData[card_val_ranges][card_val_ranges];
	HandData[][] postFlopBoard = new HandData[card_val_ranges][card_val_ranges];
	//0 = 0% , 50 = 50%, 100 = 100% etc
	int weighted_matrix[][] = new int[card_val_ranges][card_val_ranges];
	int total_weight_count = 0;
	int post_flop_weight_count = 0;
	ArrayList<Card> deadCards = new ArrayList<Card>();
	//holds the handData of the range, and not a specific handData
	public HandData totalPreFlopPercentage = new HandData();
	
	public HandData totalPostFlopPercentage = new HandData();
	
	
	
	
	public RangeBoard()	{
		for(int val_card1 = 2; val_card1 <= 14; val_card1++)	{
			for(int val_card2 = 2; val_card2 <= 14; val_card2++)	{
				//since 7 8 -> 8 7, we will let 7 8 be suited and 8 7 be unsuited
				board[val_card1 - 2][val_card2 - 2] = null;
				weighted_matrix[val_card1 - 2][val_card2 - 2] = 0;
			}
		}
		
		
		
	}
	public void addValueToMatrix(int val1,int val2,int weight)	{
		
		//deadCards.add(new Card('D',13));
		//deadCards.add(new Card('C',12));
		if(board[val1 - 2][val2 - 2] == null)	{
			if(val1 <= val2)	{
				board[val1 - 2][val2 - 2] = new HandData(val1,val2,false,deadCards);
			}else {
				board[val1 - 2][val2 - 2] = new HandData(val1,val2,true,deadCards);
			}
		}
		total_weight_count -= weighted_matrix[val1 -2][val2 -2] * board[val1 - 2][val2 - 2].combos;
		adjustOdds(-weighted_matrix[val1 -2][val2 -2]*board[val1 -2][val2 -2].combos,val1,val2);
		
		weighted_matrix[val1 -2][val2 -2] = weight;
		//System.out.print(weight*board[val1 -2][val2 -2].combos);
		adjustOdds(weight*board[val1 -2][val2 -2].combos,val1,val2);
		total_weight_count+= weight * board[val1 - 2][val2 - 2].combos;
		
	}
	
	public void adjustOdds(int weight, int val1, int val2)	{
	
		for(int i = 0; i < totalPreFlopPercentage.handType.length;i++)	{
			totalPreFlopPercentage.handType[i] = adjust(totalPreFlopPercentage.handType[i] ,weight,board[val1 - 2][val2 - 2].handType[i],total_weight_count);
		}
		
		
	
		
	}
	
	private double adjust(double val, int weight,double added_val,int weight_count)	{
		
		if(weight >= 0)	{
			if(weight_count + weight == 0)
				return 0;
			double ret_val = (val * weight_count/(weight_count + weight)) + (added_val * weight / (weight_count + weight));
			if(ret_val < 0.00001 && ret_val > -0.00001)	{
				return 0;
			}
			return ret_val;
		}else {
			double ret_val = (val * (weight_count - weight)/ weight_count) + (added_val*weight / weight_count);
			if(ret_val < 0.00001 && ret_val > -0.00001)	{
				return 0;
			}
			return ret_val;
		}
	}
	
	public int getMatrixVal(int x, int y)	{
		return weighted_matrix[x][y];
	}
	
	public HandData getTotalPercentage()	{
		return totalPreFlopPercentage;
	}
	
	public void rangeAgainstCards()	{
		
	}
	
	public double calcFlopHitPercentage(boolean preFlop, Filter filter)	{
		double val = 0;
		
		for(int i = 0; i < 13; i++)	{
			for(int j = 0; j < 13;j++)	{
				if(preFlop) {
					if(board[i][j] != null)
						val+= (getMatrixVal(i,j) *board[i][j].combos * board[i][j].hitPercentage(filter));
				}else {
					if(postFlopBoard[i][j] != null)
						val+= (getMatrixVal(i,j) *postFlopBoard[i][j].combos * postFlopBoard[i][j].hitPercentage(filter));
					}
				}
		}
		val = val / total_weight_count;
		System.out.println(val);
		return val;
		
	}
	
	
	private void adjustPostFlop(PostFlopHand postFlop,int weight)	{
		for(int i = 0; i < totalPostFlopPercentage.handType.length;i++)	{
			//System.out.println(postFlop.handType[i]);
			totalPostFlopPercentage.handType[i] = adjust(totalPostFlopPercentage.handType[i] ,weight,postFlop.handType[i],post_flop_weight_count);
		}
		post_flop_weight_count += weight;
	}
	
	
	public void setPostFlop()	{
		totalPostFlopPercentage = new HandData();
		post_flop_weight_count = 0;
		ArrayList<Card> flopCards = new ArrayList<Card>();
		flopCards.add(new Card('H',11));
		flopCards.add(new Card('H',10));
		flopCards.add(new Card('H',12));
		for(int val_card1 = 2; val_card1 <= 14; val_card1++)	{
			for(int val_card2 = 2; val_card2 <= 14; val_card2++)	{
				//since 7 8 -> 8 7, we will let 7 8 be suited and 8 7 be unsuited
				if(weighted_matrix[val_card1 - 2][val_card2 - 2] != 0)	{
					PostFlopHand weightedHand;
					if(val_card1 <= val_card2) {
						weightedHand = new PostFlopHand(val_card1, val_card2,false,deadCards,flopCards);
					}else {
						weightedHand = new PostFlopHand(val_card1, val_card2,true,deadCards,flopCards);
					}
					postFlopBoard[val_card1 - 2][val_card2 - 2] = weightedHand;
					System.out.println(weightedHand.combos);
					adjustPostFlop(weightedHand,weighted_matrix[val_card1 - 2][val_card2 - 2]*weightedHand.combos);
				}
			}
		}
	}
}
