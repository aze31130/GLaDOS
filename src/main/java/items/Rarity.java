package items;

import java.awt.Color;

/*
 * @formatter:off
 */
public enum Rarity {
	EVENT(0, new Color(255, 128, 0)), // Orange
	COMMON(1, new Color(157, 157, 157)), // Gray
	UNUSUAL(2, new Color(192, 192, 192)), // Gray light
	RARE(3, new Color(75, 105, 255)), // Blue
	EXCEPTIONNAL(4, new Color(0, 0, 0)),
	EPIC(5, new Color(137, 71, 255)), // Purple light
	MARVELOUS(6, new Color(0, 0, 0)),
	LEGENDARY(7, new Color(43, 241, 14)), // Green
	FABLED(8, new Color(235, 75, 75)), // Red
	MYTHICAL(9, new Color(170, 0, 170)), // Purple dark
	GODLY(10, new Color(85, 255, 255)), // White cyan
	UNIQUE(11, new Color(240, 222, 54)); // Yellow

	public final int level;
	public final Color color;

	private Rarity(int level, Color color) {
		this.level = level;
		this.color = color;
	}
}
