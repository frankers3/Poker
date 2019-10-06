package Game;

public class DrawItem {
	double value;
	int drawNum;
	public DrawItem(int val, int dN)	{
		value = val;
		drawNum = dN;
	}
	

	public double getValue() {
		return value;
	}
	public void dividValue(double val) {
		if(val == 0)
			return;
		value = value / val;
	}
	public void incrementValue()	{
		value = value + 1;
	}
	public int getDrawNum() {
		return drawNum;
	}
	public void setDrawNum(int drawNum) {
		this.drawNum = drawNum;
	}

}