package logic;

import Utils.Logger;

public class Chessboard {
	
	public final static int MAX_SIZE = 1000;
	
	private final int size;
	
	private Checker[][] board;
	
	private int numberOfBlackCheckers = 0;
	
	private int numberOfWhiteCheckers = 0;
	
	private GameState state;
	
	public Chessboard(int size) {
		this.size = size;
		this.board = new Checker[size][size];
		this.state = GameState.WHITE_MOVE;
	}
	
	public static Chessboard emptyChessboard(int size) {
		Chessboard chessboard = new Chessboard(size);
		
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				chessboard.board[i][j] = null;
			}
		}
		
		return chessboard;
	}
	
	public static Chessboard startingChessboard(int size) {
		
		Chessboard chessboard = Chessboard.emptyChessboard(size);
		
		for (int i = 0; i < size; i++) {
			chessboard.board[i][i%2] = new Pawn(Color.WHITE);
			chessboard.board[i][size - 1 - (i%2)] = new Pawn(Color.BLACK);
		}
		
		chessboard.numberOfBlackCheckers = size;
		chessboard.numberOfWhiteCheckers = size;
		
		return chessboard;
	}
	
	public GameState getState() {
		return state;
	}
	
	private void removeChecker(int x, int y) {
		Color checkerColor = board[x][y].getColor();
		board[x][y] = null;
		
		if (checkerColor == Color.WHITE) {
			numberOfWhiteCheckers --;
		} else {
			numberOfBlackCheckers --;
		}
	}
	
	private boolean gameEnded() {
		if (numberOfBlackCheckers * numberOfWhiteCheckers == 0) {
			return true;
		}
		return false;
	}
	
	private void actualizeState() {
		if (gameEnded()) {
			if (numberOfBlackCheckers != 0) {
				state = GameState.BLACK_WON;
			} else {
				state = GameState.WHITE_WON;
			}
		} else {
			if (state == GameState.WHITE_MOVE) {
				state = GameState.BLACK_MOVE;
			} else {
				state = GameState.WHITE_MOVE;
			}
		}
	}
	
	private void promoteQueens() {
		for (int i = 0; i < size; i++) {
			if (board[i][0] != null) {
				if (board[i][0].getColor() == Color.BLACK) {
					board[i][0] = new Queen(Color.BLACK);
				}
			}
			
			if (board[i][size - 1] != null) {
				if (board[i][size - 1].getColor() == Color.WHITE) {
					board[i][size - 1] = new Queen(Color.WHITE);
				}
			}
		}
	}
	
	private boolean checkerExist(int x, int y) {
		return board[x][y] != null;
	}
	
	private boolean isInBorders(int x, int y, int dx, int dy) {
		return (x + dx < size && x + dx >= 0) &&
			   (y + dy < size && y + dy >= 0);
	}
	
	public boolean isMoveValid(Move move) {
		int dx = move.dx();
		int dy = move.dy();
		int x = move.getFromX();
		int y = move.getFromY();
		
		if (!checkerExist(x,y)) {
			Logger.log("Checker does not exist");
			return false;
		}
		
		if (!isInBorders(x, y, dx, dy)) {
			Logger.log("Checker moves outside borders");
			return false;
		}
		
		
		return true;
	}
	
	public void doMove(Move move) {
		//TODO make move
		
		promoteQueens();
		actualizeState();
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		for (int i = size - 1; i >= 0; i--) {
			builder.append((char)('a' + i))
				   .append(": ");
			
			for (int j = 0; j < size; j++) {
				if (board[j][i] != null) {
					builder.append(board[j][i].toString());
				} else {
					builder.append("_");
				}
				
				builder.append(" ");
			}
			builder.append("\n");
		}
		
		builder.append("   ");
		
		for (int i = 0; i < size; i++) {
			builder.append(i + 1)
				   .append(" ");
		}
		
		return builder.toString();
	}
	
	public static void main(String[] args) {
		Chessboard c = Chessboard.startingChessboard(5);
		System.out.println(c);
	}
	
}
