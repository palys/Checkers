package logic;

public class GameLogic {
	
	Player[] players = new Player[2];
	
	Chessboard board = null;
	
	private GameLogic(int size, Player player) {
		players[0] = player;
		board = Chessboard.startingChessboard(size);
	}
	public static GameLogic vsPlayer(int size, Player player) {
	
		return new GameLogic(size, player);
	}
	
	public static GameLogic vsBot(int size, Player player) {
		GameLogic game = new GameLogic(size, player);
		game.join(new Bot(Color.BLACK));		
		return game;
	}
	
	private void notifyPlayersAboutStart() {
		for (Player p : players) {
			p.notifyMeAboutStart(board);
		}
	}
	
	public boolean join(Player player) {
		if (players[1] == null) {
			players[1] = player;
			notifyPlayersAboutStart();
			return true;
		} else {
			return false;
		}
	}
	
	public boolean doMove(Move move, Player player) {
		if (!board.isMoveValid(move)) {
			return false;
		}
		
		// TODO check if proper player
		
		board.doMove(move);
		
		for (Player p : players) {
			p.sendMeNewGameState(board);
		}
		return true;
	}
	
}
