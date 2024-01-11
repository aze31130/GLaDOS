package items;

/*
 * @formatter:off
 */
public enum Rarity {
	EVENT(0, 255, 128, 0), // Orange
	COMMON(1, 157, 157, 157), // Gray
	UNUSUAL(2, 192, 192, 192), // Gray light
	RARE(3, 75, 105, 255), // Blue
	EXCEPTIONNAL(4, 0, 0, 0),
	EPIC(5, 137, 71, 255), // Purple light
	MARVELOUS(6, 0, 0, 0),
	LEGENDARY(7, 43, 241, 14), // Green
	FABLED(8, 235, 75, 75), // Red
	MYTHICAL(9, 170, 0, 170), // Purple dark
	GODLY(10, 85, 255, 255), // White cyan
	UNIQUE(11, 240, 222, 54); // Yellow

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
