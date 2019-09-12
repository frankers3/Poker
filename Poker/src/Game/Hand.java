package Game;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Hand {
	public int value;
	public boolean suited;
	
	public Hand(int cardval1,int cardval2, boolean suit) {
		if(cardval1 > cardval2) {
			//value is expressed this way because this is how it is stored in the 
			//file 0 representing 2 etc
			this.value = 13*(cardval2-2) + (cardval1 - 2);
		}else {
			this.value = 13*(cardval1 -2) + (cardval2 -2);
		}
		this.suited = suit;
	}
	
	public float getHandOdds(Hand otherHand)	{
		if(this.value == otherHand.value && this.suited == otherHand.suited)
			return 50;
		int file_offset = -1;
		File file = new File("preFlopOdds");
		if(this.suited)	{
			if(otherHand.suited ^ this.suited)	{
				//one suited hands
				file = new File("1SuitedHand");
				if(this.suited)	{
					file_offset = (this.value * 13 *13 + otherHand.value)*2;
				}else {
					file_offset = (otherHand.value * 13 *13 + this.value)*2;
				}
			}else {
				//unsuited or both suited
				if(this.suited && otherHand.suited)	{
					file = new File("2SuitedHand");
				}else {
					file = new File("preFlopOdds");
				}
				if(this.value > otherHand.value)	{
					file_offset = (otherHand.value *13*13 + this.value)*2;
					
				}else {
					file_offset = (this.value *13*13 + otherHand.value)*2;
				}
			}
		}
		
		try {
			
			RandomAccessFile file_pointer = new RandomAccessFile(file, "r");
			System.out.print(file_offset);
			file_pointer.seek(file_offset);
			//System.out.print(file_offset);
			//System.out.print(file_pointer.readShort());
			
			return file_pointer.readShort();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
		
	}
	
	
}
