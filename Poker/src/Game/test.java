package Game;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class test {

	public static void main(String[] args) {
		Game game = new Game(new Card('S',14), new Card('H',14), new Card('H',11), new Card('S',11));
	
		//game.calculatepreflopOdds();
		try {
			iterateAllPreFlopHands();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void iterateAllPreFlopHands() throws IOException {
			Game game = new Game();
			File file = new File("preFlopOdds");
			try {
				FileOutputStream output = new FileOutputStream(file);
				
				int offset = 0;
				for(int i = 2; i <= 14; i++) {
					Card i_card = new Card('H',i);
					for(int j = 2; j <= 14; j++)	{
						Card j_card = new Card('D',j);
						for(int k = 2; k <= 14; k++) {
							Card k_card = new Card('C',k);
							for(int l = 2; l <= 14; l++)	{
								if(i <= j && k <= l && 14*i + j <= 14*k + l) {
									Card l_card = new Card('S',l);
									game.reset(i_card, j_card, k_card, l_card);
									int write_to_file = game.calculatepreflopOdds();
									// OutputBufferStream only accepts bytes, i could typecast the int
									// but doing it this way is twice as efficient
									//returnBytes[0] represents the 8 significant bits
									//returnBytes[1] represents the 8 lower bits.
									byte[] returnBytes;
									returnBytes = new byte[2];
									
									returnBytes[0] = (byte) (0xFF00 & write_to_file);
									returnBytes[1] = (byte) (0xFF & write_to_file);
									
									output.write(returnBytes);
									output.flush();
									offset+=2;
								}
							}
						}
					}
				}
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	}
		
}


