package baseball;

public enum Base {
	FIRST(1), SECOND(2), THIRD(3), HOME(4);
	private final int index;
	
	private Base(int index) {
		this.index = index;
	}
	
	public int toInt() {
		return index;
	}
}
