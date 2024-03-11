package items;

import java.awt.Color;

/*
 * @formatter:off
 */
public enum Rarity {
	COMMON(0, new Color(157, 157, 157), ":white_large_square:"), // Gray
	UNUSUAL(1, new Color(192, 192, 192), ":white_large_square:"), // Gray light
	RARE(2, new Color(75, 105, 255), ":blue_square:"), // Blue or cyan Color(85, 255, 255)
	EPIC(3, new Color(137, 71, 255), ":purple_square:"), // Purple light
	LEGENDARY(4, new Color(43, 241, 14), ":green_square:"), // Green
	FABLED(5, new Color(235, 75, 75), ":red_square:"), // Red
	MYTHICAL(6, new Color(170, 0, 170), ":purple_square:"), // Purple dark
	GODLY(7, new Color(15, 16, 53), ":black_large_square:"), // Blue Navy
	EVENT(8, new Color(255, 128, 0), ":orange_square:"), // Orange
	UNIQUE(9, new Color(240, 222, 54), ":yellow_square:"); // Yellow

	public final int level;
	public final Color color;
	public final String emote;

	private Rarity(int level, Color color, String emote) {
		this.level = level;
		this.color = color;
		this.emote = emote;
	}
}