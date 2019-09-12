package Game;

public class RangeBoard {
	int card_val_ranges = 13;
	HandData[][] board = new HandData[card_val_ranges][card_val_ranges];
	//0 = 0% , 50 = 50%, 100 = 100% etc
	int weighted_matrix[][] = new int[card_val_ranges][card_val_ranges];
	int total_weight_count = 0;
	HandData totalPercentage = new HandData();
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
		if(board[val1 - 2][val2 - 2] == null)	{
			if(val1 < val2)	{
				board[val1 - 2][val2 - 2] = new HandData(val1,val2,true,null);
			}else {
				board[val1 - 2][val2 - 2] = new HandData(val1,val2,false,null);
			}
		}
		total_weight_count -= weighted_matrix[val1 -2][val2 -2];
		adjustOdds(-weighted_matrix[val1 -2][val2 -2]*board[val1 -2][val2 -2].combos,val1,val2);
		weighted_matrix[val1 -2][val2 -2] = weight;
		//System.out.print(weight*board[val1 -2][val2 -2].combos);
		adjustOdds(weight*board[val1 -2][val2 -2].combos,val1,val2);
		total_weight_count+= weight * board[val1 - 2][val2 - 2].combos;
		
	}
	
	public void adjustOdds(int weight, int val1, int val2)	{
		totalPercentage.fullHouseOrBetter = adjust(totalPercentage.fullHouseOrBetter,weight,board[val1 - 2][val2 - 2].fullHouseOrBetter);
		totalPercentage.flush = adjust(totalPercentage.flush,weight,board[val1 - 2][val2 - 2].flush);
		totalPercentage.straight = adjust(totalPercentage.straight,weight,board[val1 - 2][val2 - 2].straight);
		totalPercentage.set = adjust(totalPercentage.set,weight,board[val1 - 2][val2 - 2].set);
		totalPercentage.twoPair = adjust(totalPercentage.twoPair,weight,board[val1 - 2][val2 - 2].twoPair);
		totalPercentage.topPair = adjust(totalPercentage.topPair,weight,board[val1 - 2][val2 - 2].topPair);
		totalPercentage.midPair = adjust(totalPercentage.midPair,weight,board[val1 - 2][val2 - 2].midPair);
		totalPercentage.weakPair = adjust(totalPercentage.weakPair,weight,board[val1 - 2][val2 - 2].weakPair);
		totalPercentage.noMadeHand = adjust(totalPercentage.noMadeHand,weight,board[val1 - 2][val2 - 2].noMadeHand);
		
		totalPercentage.flushDraw = adjust(totalPercentage.flushDraw,weight,board[val1 - 2][val2 - 2].flushDraw);
		totalPercentage.OESD = adjust(totalPercentage.OESD,weight,board[val1 - 2][val2 - 2].OESD);
		totalPercentage.gutShot = adjust(totalPercentage.gutShot,weight,board[val1 - 2][val2 - 2].gutShot);
		totalPercentage.overCards = adjust(totalPercentage.overCards,weight,board[val1 - 2][val2 - 2].overCards);
	}
	
	private float adjust(float val, int weight,float added_val)	{
		if(total_weight_count + weight == 0)
			return val;
		if(weight >= 0)	{
			return (val * total_weight_count/(total_weight_count + weight)) + (added_val * weight / (total_weight_count + weight));
		}else {
			return (val * (total_weight_count - weight)/ total_weight_count) + (added_val*weight / total_weight_count);
		}
	}
}
