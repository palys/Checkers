package logic;

public abstract class Checker {

	protected final Color color;
	
	public Checker(Color color) {
		this.color = color;
	}
	
	public Color getColor() {
		return this.color;
	}
	
	public abstract int getRange();
	public abstract String toString();
}
