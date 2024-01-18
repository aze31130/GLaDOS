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
	EPIC(4, new Color(137, 71, 255)), // Purple light
	LEGENDARY(5, new Color(43, 241, 14)), // Green
	FABLED(6, new Color(235, 75, 75)), // Red
	MYTHICAL(7, new Color(170, 0, 170)), // Purple dark
	GODLY(8, new Color(15, 16, 53)), // Blue Navy
	UNIQUE(9, new Color(240, 222, 54)); // Yellow

	public final int level;
	public final Color color;

	private Rarity(int level, Color color) {
		this.level = level;
		this.color = color;
	}
}
// White cyannew Color(85, 255, 255)