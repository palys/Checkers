package logic;

public class Queen extends Checker {

	public Queen(Color color) {
		super(color);
	}

	@Override
	public int getRange() {
		return Chessboard.MAX_SIZE;
	}

	@Override
	public String toString() {
		if (this.color == Color.WHITE) {
			return "W";
		} else {
			return "B";
		}
	}

	@Override
	public boolean canMoveBackward() {
		return true;
	}

}
