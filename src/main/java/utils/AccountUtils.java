package utils;

import org.json.JSONArray;
import accounts.Account;
import glados.GLaDOS;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;

public class AccountUtils {
	public static void createEveryAccount(Guild g) {
		for (Member m : g.getMembers()) {
			// createAccount here
		}
	}

	public static void backupAccounts() {
		GLaDOS g = GLaDOS.getInstance();
		JSONArray accounts = new JSONArray();

		for (Account a : g.accounts)
			accounts.put(a.toJson());

		FileUtils.writeRawFile("accounts.json", accounts.toString(4));
	}

	public static Account getAccountById(String id) {
		GLaDOS g = GLaDOS.getInstance();
		// Check if account is registered
		Account result = g.accounts.stream().filter(a -> a.id == id).findFirst().orElse(null);

		// Create the account if not exist
		if (result == null) {
			// result = new Account(id, 0, 0, 0, TrustFactor.UNTRUSTED, Permissions.NONE);
			g.accounts.add(result);
		}

		return result;
	}
}
