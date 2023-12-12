package items;

public enum Rarity {
	EVENT(0, 0, 0, 0), // Orange
	COMMON(1, 0, 0, 0), // Gray
	RARE(2, 75, 105, 255), // Blue
	EPIC(3, 137, 71, 255), // Purple light
	LEGENDARY(4, 43, 241, 14), // Green
	FABLED(5, 235, 75, 75), // Red
	MYTHICAL(6, 0, 0, 0), // Purple dark
	GODLY(7, 0, 0, 0), // White cyan
	UNIQUE(8, 240, 222, 54); // Yellow

	public final int level;
	public final int red;
	public final int green;
	public final int blue;

	private Rarity(int level, int red, int green, int blue) {
		this.level = level;
		this.red = red;
		this.green = green;
		this.blue = blue;
	}
}
