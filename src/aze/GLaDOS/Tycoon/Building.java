package aze.GLaDOS.tycoon;

public abstract class Building {
	public enum Type {
		ATTRACTION,
		HOUSE,
		SHOP
	}
	
	protected Type type;
	protected int MAX_LEVEL = 3;
	
	public Type getType() {
		return this.type;
	}
	
	//Method to override
	public void upgrade(long money) {
	}
}
