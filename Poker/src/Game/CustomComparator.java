package Game;

import java.util.Comparator;

public class CustomComparator implements Comparator <Card>{

	@Override
	public int compare(Card arg0, Card arg1) {
		return arg0.getVal().compareTo(arg1.getVal());
	}

	
	
}
