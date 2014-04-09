package logic;

public class Queen extends Checker {

	public Queen(Color color) {
		super(color);
	}

	@Override
	public int getRange() {
		return Chessboard.MAX_SIZE;
	}

}
