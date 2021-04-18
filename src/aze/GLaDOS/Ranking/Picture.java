package aze.GLaDOS.Ranking;

import net.dv8tion.jda.api.entities.User;

public class Picture {
	public static void generatePicture(User user) {
		user.getDefaultAvatarUrl();
	}
}
