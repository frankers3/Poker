package Game;

public interface GameState {
	public String getState();
	public boolean goToNextState(Game game);
}

class preFlop implements GameState {
	public String getState()	{
		return "preFlop";
	}
	
	public boolean goToNextState(Game game) {
		if(game.dealFlop())	{
			game.state = new flop();
			return true;
		}
		return false;
	}
}

class flop implements GameState {
	public String getState()	{
		return "flop";
	}
	
	public boolean goToNextState(Game game) {
		if(game.dealTurn())	{
			game.state = new turn();
			return true;
		}
		return false;
	}
	
}

class turn implements GameState {
	public String getState()	{
		return "turn";
	}
	
	public boolean goToNextState(Game game) {
		if(game.dealRiver())	{
			game.state = new river();
			return true;
		}
		return false;
	}

	
}

class river implements GameState {
	public String getState()	{
		return "river";
	}
	
	public boolean goToNextState(Game game) {
		game.gameDeck.refill();
		game.state = new preFlop();
		return true;
	}
	
}