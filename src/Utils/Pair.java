package Utils;

public class Pair<U,V> {
	U u;
	V v;
	
	public Pair(U u, V v) {
		this.u = u;
		this.v = v;
	}
	
	public U first() {
		return u;
	}
	
	public V second() {
		return v;
	}
}
