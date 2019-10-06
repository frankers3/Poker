package Controller;

import java.util.ArrayList;

import Game.Filter;
import Game.RangeBoard;

public class Controller {
	public RangeBoard rangeBoard;
	Filter filter = new Filter();
	State state;
	public Controller()	{
		rangeBoard = new RangeBoard();
		state = State.postflop;
	}
	
	public ArrayList<String> getData()	{
		if(state == State.preflop)	{
			return rangeBoard.totalPreFlopPercentage.getData();
		}
		
		if(state == State.postflop)	{
			rangeBoard.setPostFlop();
			return rangeBoard.totalPostFlopPercentage.getData();
		}
		return null;
	}
	
	public double getHitPercentage()	{
		if(state == State.preflop)	{
			return rangeBoard.calcFlopHitPercentage(true,filter);
		}
		
		if(state == State.postflop)	{
			rangeBoard.setPostFlop();
			return rangeBoard.calcFlopHitPercentage(false,filter);
		}
		return 0;
	}
	
	public Filter getFilter()	{
		return filter;
	}
	
	public void updateFlopCards()	{
		
	}
}
