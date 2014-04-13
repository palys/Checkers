package logic;

public class Pawn extends Checker {

	public Pawn(Color color) {
		super(color);
	}

	@Override
	public int getRange() {
		return 1;
	}

	@Override
	public String toString() {
		if (this.color == Color.WHITE) {
			return "w";
		} else {
			return "b";
		}
	}

}
