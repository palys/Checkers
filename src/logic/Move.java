package logic;

import java.security.InvalidParameterException;

public class Move {

	private final int fromX;
	
	private final int fromY;
	
	private final int toX;
	
	private final int toY;
	
	private Move(int fromX, int fromY, int toX, int toY) {
		this.fromX = fromX;
		this.fromY = fromY;
		this.toX = toX;
		this.toY = toY;
	}

	public int getFromX() {
		return fromX - 1;
	}

	public int getFromY() {
		return fromY - 1;
	}

	public int getToX() {
		return toX - 1;
	}

	public int getToY() {
		return toY - 1;
	}
	
	private static boolean isProper(int x1, int y1, int x2, int y2) {
		if (Math.abs(x1-x2) == Math.abs(y1-y2)) {
			return true;
		} else {
			return false;
		}
	}
	
	public static Move newMove(int fromX, int fromY, int toX, int toY) {
		
		if (!isProper(fromX, fromY, toX, toY)) {
			throw new InvalidParameterException("Invalid move");
		}
		return new Move(fromX, fromY, toX, toY);
	}
	
	public int dx() {
		return toX - fromX;
	}
	
	public int dy() {
		return toY - fromY;
	}
	
}
