package utils;

import accounts.Account;
import glados.GLaDOS;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;

public class AccountUtils {
	public static void createEveryAccount(Guild guild) {
		GLaDOS glados = GLaDOS.getInstance();

		for (Member m : guild.getMembers())
			glados.getAccountById(m);
	}

	/*
	 * Returns the amount of required exp for a given level. Note, total exp to level 100:
	 * 20,503,333,300
	 */
	public static int getRequiredExperience(int level) {
		int baseExp = 10;
		double exponent = 4;
		return (int) (baseExp * (Math.pow((level + 1), exponent)));
	}

	public static String getExperiencePercentage(int level, long experience) {
		return (experience * 100 / getRequiredExperience(level)) + "%";
	}

	public static void checkLevelUp(Account account) {
		while (account.experience >= getRequiredExperience(account.level)) {
			account.experience -= getRequiredExperience(account.level);
			account.level++;
		}
	}
}
