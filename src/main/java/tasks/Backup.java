package tasks;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import org.json.JSONArray;
import accounts.Account;
import glados.GLaDOS;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.utils.FileUpload;
import utils.BuildEmbed;
import utils.FileUtils;
import utils.Logging;

public class Backup implements Runnable, Logging {
	public JDA jda;

	public Backup(JDA jda) {
		this.jda = jda;
	}

	@Override
	public void run() {
		/*
		 * For now, GLaDOS will upload users data in a private channel. This ensures no data loss while
		 * getting the new infrastructure coming for April 2024.
		 */
		LOGGER.info("Backing up all accounts !");
		GLaDOS g = GLaDOS.getInstance();
		JSONArray accounts = new JSONArray();

		for (Account a : g.accounts)
			accounts.put(a.toJson());

		// Write backup file
		FileUtils.writeRawFile("accounts.json", accounts.toString(4));
		// Upload file to discord
		InputStream inputStream = new ByteArrayInputStream(accounts.toString().getBytes());
		jda.getTextChannelById(g.getRoleId("backup").get()).sendMessageEmbeds(BuildEmbed.successEmbed("Account backup").build())
				.addFiles(FileUpload.fromData(inputStream, "accounts.json")).complete();

		LOGGER.info("Backup done !");
	}
}
