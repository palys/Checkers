package logic;

public abstract class Player {
	
	protected Color color;

	public Player(Color color) {
		this.color = color;
	}
	
	public abstract void notifyMeAboutStart(Chessboard startingChessboard);
	
	public abstract void sendMeNewGameState(Chessboard newState);
	
}
