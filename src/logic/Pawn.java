package logic;

public class Pawn extends Checker {

	public Pawn(Color color) {
		super(color);
	}

	@Override
	public int getRange() {
		return 1;
	}
	
	
}
