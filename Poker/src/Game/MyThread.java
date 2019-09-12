package Game;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

class MyThread implements Runnable {
	Thread t;
	public int low;
	public int high;
	public FileOutputStream file;
	public String name;
	MyThread(int low, int high, FileOutputStream file, String name)	{
		this.low = low;
		this.high = high;
		this.file = file;
		this.name = name;
		t = new Thread(this,name);
		System.out.print(true);
		t.start();
	}
	@Override
	public void run() {
		Game game = new Game();
		
		try {
			
			
			
			for(int i = low; i <= high; i++) {
				Card i_card = new Card('H',i);
			
				for(int j = 2; j <= 14; j++)	{
					Card j_card = new Card('H',j);
					
					for(int k = 2; k <= 14; k++) {
						Card k_card = new Card('C',k);
						Card kH_card = new Card('H',k);
						for(int l = 2; l <= 14; l++)	{
							if(i < j && k < l && 14*i +j <= 14*k +l) {
								
								
								Card l_card = new Card('C',l);
								Card lH_card = new Card('H',l);
								System.out.println("Cards odds Added:");
								System.out.println(i);
								System.out.println(j);
								System.out.println(k);
								System.out.println(l);
								System.out.println("----------------");
								int temp = 0;
								float divisor = 4;
								game.reset(i_card, j_card, k_card, l_card);
								int write_to_file = game.calculatepreflopOdds() *3;
								game.reset(i_card, j_card , kH_card, lH_card);
								temp = game.calculatepreflopOdds();
								if(temp != -1)	{
									write_to_file += temp;	
									
								}
								
								
								write_to_file = (int) (write_to_file / divisor);
								// OutputBufferStream only accepts bytes, i could typecast the int
								// but doing it this way is twice as efficient
								//returnBytes[0] represents the 8 significant bits
								//returnBytes[1] represents the 8 lower bits.
								byte[] returnBytes;
								returnBytes = new byte[2];
					
								returnBytes[0] = (byte) ((0xFF00 & write_to_file) >> 8);
								returnBytes[1] = (byte) (0xFF & write_to_file);
								
								
								file.write(returnBytes);
								file.flush();
								
							}else {
								byte[] returnBytes;
								returnBytes = new byte[2];
					
								returnBytes[0] = 0;
								returnBytes[1] = 0;
								
								
								file.write(returnBytes);
								file.flush();
							}
							
						}
					}
				}
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
}
	
		
	}
	




