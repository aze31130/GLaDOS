package utils;

import java.util.List;
import org.json.JSONArray;
import accounts.Account;
import glados.GLaDOS;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;

public class AccountUtils {
	/*
	 * Returns the according emote depending on the ranking of a user
	 */
	public static String getMedalEmoji(int rank) {
		return switch (rank) {
			case 1 -> ":first_place: ";
			case 2 -> ":second_place: ";
			case 3 -> ":third_place: ";
			default -> "";
		};
	}

	public static void createEveryAccount(Guild guild) {
		GLaDOS glados = GLaDOS.getInstance();

		for (Member m : guild.getMembers())
			glados.getAccount(m.getUser());
	}

	public static void backup(List<Account> input) {
		JSONArray accounts = new JSONArray();

		for (Account a : input)
			accounts.put(a.toJson());

		// Write backup file
		FileUtils.writeRawFile("accounts.json", accounts.toString(4));
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
}
