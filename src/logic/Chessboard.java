package logic;

import Utils.Logger;
import Utils.Pair;
import Utils.Utils;

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
			chessboard.board[i][size - 2 + (i%2)] = new Pawn(Color.BLACK);
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
	
	private void actualizeState(boolean shouldChangePlayers) {		
		if (gameEnded()) {
			if (numberOfBlackCheckers != 0) {
				state = GameState.BLACK_WON;
			} else {
				state = GameState.WHITE_WON;
			}
		} else {
			
			if (!shouldChangePlayers) {
				return;
			}
			
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
	
	private boolean checkerExists(int x, int y) {
		return board[x][y] != null;
	}
	
	private boolean isInBorders(int x, int y, int dx, int dy) {
		return (x + dx < size && x + dx >= 0) &&
			   (y + dy < size && y + dy >= 0);
	}
	
	private boolean isRangeProper(int dx, int dy, Checker checker) {
		return Math.abs(dx) <= checker.getRange() && Math.abs(dy) <= checker.getRange();
	}
	
	private boolean isDirectionProper(int dy, Checker checker) {
		if (checker.canMoveBackward()) {
			return true;
		} else {
			if (checker.color == Color.BLACK) {
				return dy < 0;
			} else {
				return dy > 0;
			}
		}
	}
	
	private int numberOfCollisions(int x, int y, int dx, int dy) {
		int[] rangeX = Utils.rangeWithEnd(x, x + dx);
		int[] rangeY = Utils.rangeWithEnd(y, y + dy);
		
		int collisions = 0;
		
		for (int i = 1; i < rangeX.length; i++) {
			if (board[rangeX[i]][rangeY[i]] != null) {
				collisions++;
			}
		}
		
		return collisions;
	}
	
	private Pair<Integer, Integer> collisionPlace(int x, int y, int dx, int dy) {
		int[] rangeX = Utils.rangeWithEnd(x, x + dx);
		int[] rangeY = Utils.rangeWithEnd(y, y + dy);
		
		for (int i = 1; i < rangeX.length; i++) {
			if (board[rangeX[i]][rangeY[i]] != null) {
				return new Pair<Integer, Integer>(rangeX[i], rangeY[i]);
			}
		}
		
		return null;
	}
	
	private boolean canBeat(Checker beater, int beatedX, int beatedY) {
		return beater.color != board[beatedX][beatedY].color;
	}
	
	private boolean badCollisionOccurs(int x, int y, int dx, int dy) {
		return board[x + dx][y + dy] != null;
	}
	
	public boolean isMoveValid(Move move) {
		
		// TODO check if move-by-move by same player
		
		int dx = move.dx();
		int dy = move.dy();
		int x = move.getFromX();
		int y = move.getFromY();
		Checker checker = null;
		
		if (!checkerExists(x,y)) {
			Logger.log("Checker does not exist");
			return false;
		}
		
		checker = board[x][y];
		
		if (!isInBorders(x, y, dx, dy)) {
			Logger.log("Checker moves outside borders");
			return false;
		}
		
		if (badCollisionOccurs(x, y, dx, dy)) {
			Logger.log("Collision");
			return false;
		}
		
		if (!isDirectionProper(dy, checker)) {
			Logger.log("Wrong direction");
			return false;
		}
		
		int noCollisions = numberOfCollisions(x, y, dx, dy);
		
		if (noCollisions == 0) {
			
			if (!isRangeProper(dx, dy, checker)) {
				Logger.log("Move outside range");
				return false;
			}
			
			return true;
		} else if (noCollisions >= 2) {
			Logger.log("More than 1 collision");
			return false;
		} else {// 1 collision
			Pair<Integer, Integer> p = collisionPlace(x, y, dx, dy);
			
			int collisionX = p.first();
			int collisionY = p.second();
			
			if (!isRangeProper(collisionX - x, collisionY - y, checker)) {
				Logger.log("Move outside range");
				return false;
			}
			
			if (!canBeat(checker, collisionX, collisionY)) {
				Logger.log("Collision with same color");
				return false;
			}
			
			return true; //XXX probably everything checked
		}
		
	}
	
	private boolean canMoveTwice(int x, int y, int dx, int dy) {
		return false; // FIXME
	}
	
	public void doMove(Move move) { // call only after checking if move is proper
		int dx = move.dx();
		int dy = move.dy();
		int x = move.getFromX();
		int y = move.getFromY();
		Checker checker = board[x][y];
		Logger.log("Move: (" + x + ", " + y + ") +-> (" + dx + ", " + dy + ")");
		
		boolean twiceMove = canMoveTwice(x, y, dx, dy);
		boolean changePlayers = !twiceMove;
		
		if (numberOfCollisions(x, y, dx, dy) == 0) {
			board[x][y] = null;
			board[x + dx][y + dy] = checker;
		} else {// 1 collision
			Pair<Integer, Integer> collision = collisionPlace(x, y, dx, dy);
			board[x][y] = null;
			board[x + dx][y + dy] = checker;
			removeChecker(collision.first(), collision.second());
		}
		
		promoteQueens();
		actualizeState(changePlayers);
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
		Chessboard c = Chessboard.startingChessboard(8);
		System.out.println(c);
		c.isMoveValid(Move.newMove(5,7,4,6));
		c.doMove(Move.newMove(5,7,4,6));
		System.out.println(c);
	}
	
}
