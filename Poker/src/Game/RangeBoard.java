package Game;

import java.util.ArrayList;

public class RangeBoard {
	int card_val_ranges = 13;
	HandData[][] board = new HandData[card_val_ranges][card_val_ranges];
	//0 = 0% , 50 = 50%, 100 = 100% etc
	int weighted_matrix[][] = new int[card_val_ranges][card_val_ranges];
	int total_weight_count = 0;
	//holds the handData of the range, and not a specific hanData
	HandData totalPreFlopPercentage = new HandData();
	boolean filt[] = new boolean[14];
	
	
	
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
		ArrayList<Card> deadCards = new ArrayList<Card>();
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
		setFilt();
		Filter filter = new Filter(filt,this);
		System.out.println(filter.hitPercentage);
		for(int i = 0; i < totalPreFlopPercentage.handType.length;i++)	{
			totalPreFlopPercentage.handType[i] = adjust(totalPreFlopPercentage.handType[i] ,weight,board[val1 - 2][val2 - 2].handType[i] );
			for(int j = 0; j < 4;j++)	{
				
				
				totalPreFlopPercentage.drawDoubleUps[j][i] = adjust(totalPreFlopPercentage.drawDoubleUps[j][i],weight,board[val1 -2][val2-2].drawDoubleUps[j][i]);
			}
		}
		
	
		
	}
	
	private double adjust(double val, int weight,double added_val)	{
		
		if(weight >= 0)	{
			if(total_weight_count + weight == 0)
				return 0;
			double ret_val = (val * total_weight_count/(total_weight_count + weight)) + (added_val * weight / (total_weight_count + weight));
			if(ret_val < 0.00001 && ret_val > -0.00001)	{
				return 0;
			}
			return ret_val;
		}else {
			double ret_val = (val * (total_weight_count - weight)/ total_weight_count) + (added_val*weight / total_weight_count);
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
	public void setFilt()	{
		for(int i = 2; i < filt.length;i++)	{
			filt[i] = true;
		}
	}
	
}
