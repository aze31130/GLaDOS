package utils;

public class EmoteCounter {
	public String name;
	public int count;
	
	public EmoteCounter(String name) {
		this.name = name;
		this.count = 0;
	}
	
	public void add(int amount) {
		this.count += amount;
	}
	
	public int getAmount() {
		return this.count;
	}
}
