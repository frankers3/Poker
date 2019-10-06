package Game;

import java.util.ArrayList;
import java.util.Hashtable;

public class DrawTable {
	Hashtable<Integer,DrawItem> multidrawTable;
	DrawTable()	{
		multidrawTable = new Hashtable<Integer,DrawItem>();	
	}
	//will always contain the hand first - followed by all the draws
	public boolean inTable(int key)	{
		
		if(multidrawTable.containsKey(key))	{
			return true;
		}
		return false;
	}
	public int getKey(ArrayList<Integer> draws) {
		int key = 0;
		for(int i = 0 ; i < draws.size(); i++)	{
			key = key | (draws.get(i) << 6*i);
		}
		return key;
	}
	
	public void insertVal(ArrayList<Integer> draws)	{
		if(draws.size() < 2)	{
			return;
		}
		int key = getKey(draws);
		if(inTable(key) == true)	{
			multidrawTable.get(key).incrementValue();
		}else {
			//no previous entries 
			multidrawTable.put(key,new DrawItem(1,draws.size()));
		}
	}
	
	public void printKey(int key)	{
		int print = -1;
		int bits = 63;
		int increment = 0;
		while(true)	{
			print = key & bits;
			if(print == 0)
				break;
			System.out.print(print >> 6*increment);
			System.out.print(" - ");
			bits = bits << 6;
			increment++;
		}
		System.out.println("");
	}
	
	public ArrayList <Integer> getDraws(int key)	{
		ArrayList <Integer> drawArray = new ArrayList<Integer>();
		int current_draw = -1;
		int bits = 63; // = 0000 0000 0000 0000 0000 0000 0011 1111
		int increment = 0;
		while(true)	{
			current_draw = key & bits;
			if(current_draw == 0)
				break;
			drawArray.add(current_draw >> 6*increment);
			
			bits = bits << 6;
			increment++;
		}
		return drawArray;
	}
}
