package Game;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class test {

	public static void main(String[] args) {
	
		//Game game = new Game(new Card('H',14),new Card('H',13),new Card('S',11),new Card('S',10));
		
		//game.calculatepreflopOdds();

			
		HandData handData = new HandData(10,11,true,null);
		RangeBoard rangeBoard = new RangeBoard();
		
		rangeBoard.addValueToMatrix(8, 6, 100);
		rangeBoard.addValueToMatrix(7, 6, 100);
		rangeBoard.addValueToMatrix(6, 6, 100);
		rangeBoard.addValueToMatrix(6, 6, 0);
		rangeBoard.addValueToMatrix(6, 6, 100);
		rangeBoard.addValueToMatrix(6, 6, 0);
		rangeBoard.addValueToMatrix(6, 6, 100);
		
		

		System.out.println(rangeBoard.totalPercentage.OESD);
		/*File file = new File("2SuitedHand");
		
		
		try {
			FileOutputStream output = new FileOutputStream(file);
	
			System.out.print(true);
			new MyThread(2,14,output,"1");
		
			//System.out.print(true);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}*/
		//game.calculatepreflopOdds();
		
			
		
	}
	
}

