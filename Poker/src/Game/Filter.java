package Game;

public class Filter {
	
	public boolean doesItHit[] = new boolean[14];
	public Filter(boolean doesItHit[])	{
		if(doesItHit.length != 14)
			System.out.println("ERROR");
		for(int i = 0; i < 14; i++)	{
			this.doesItHit[i] = doesItHit[i];
		}
		
	}
	
	public Filter()	{
		if(doesItHit.length != 14)
			System.out.println("ERROR");
		for(int i = 0; i < 14; i++)	{
			this.doesItHit[i] = true;
		}
		
	}
	
	
	public void changeFilter(int numb, boolean toChange)	{
		doesItHit[numb] = toChange;
	}
	
	public void toggleFilter(int numb) {
		doesItHit[numb] = !doesItHit[numb];
	}
	
	
}
