package items;

import java.awt.Color;

/*
 * @formatter:off
 */
public enum Rarity {
	COMMON(0, new Color(157, 157, 157), "[1;39m"), // Gray
	UNUSUAL(1, new Color(192, 192, 192), "[1;39m"), // Gray light
	RARE(2, new Color(75, 105, 255), "[1;34m"), // Blue or cyan Color(85, 255, 255)
	EPIC(3, new Color(137, 71, 255), "[1;35m"), // Purple light
	LEGENDARY(4, new Color(43, 241, 14), "[1;32m"), // Green
	FABLED(5, new Color(235, 75, 75), "[1;31m"), // Red
	MYTHICAL(6, new Color(170, 0, 170), "[1;35m"), // Purple dark
	GODLY(7, new Color(15, 16, 53), "[1;40m[1;37m"), // Blue Navy
	EVENT(8, new Color(255, 128, 0), "[1;33m"), // Orange
	UNIQUE(9, new Color(240, 222, 54), "[1;33m"); // Yellow

	public final int level;
	public final Color color;
	public final String ansiCode;

	private Rarity(int level, Color color, String ansiCode) {
		this.level = level;
		this.color = color;
		this.ansiCode = ansiCode;
	}
}