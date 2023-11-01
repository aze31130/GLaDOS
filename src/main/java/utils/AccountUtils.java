package utils;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;

public class AccountUtils {
	public static void createEveryAccount(Guild g) {
		for (Member m : g.getMembers()) {
			// createAccount here
		}
	}
}
