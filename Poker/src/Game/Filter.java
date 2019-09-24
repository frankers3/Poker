package Game;

public class Filter {
	public double hitPercentage = 0;
	public Filter(boolean doesItHit[],RangeBoard rangeBoard)	{
		if(doesItHit.length != 14)
			System.out.println("ERROR");
		for(int i = 0; i < doesItHit.length; i++)	{
			if(doesItHit[i] == true)	{
				hitPercentage+= rangeBoard.totalPreFlopPercentage.handType[i];
				//this means it is a draw
				if(i >=10)	{
					for(int j = 0; j < doesItHit.length; j++)	{
						//System.out.println(rangeBoard.totalPreFlopPercentage.drawDoubleUps[i-10][j]);
						if(doesItHit[j] == true)	{
							
							hitPercentage -= rangeBoard.totalPreFlopPercentage.drawDoubleUps[i-10][j];
						}
					}
				}
			}
		}
		System.out.println(hitPercentage);
	}
}
