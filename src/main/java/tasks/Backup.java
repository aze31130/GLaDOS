package tasks;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import org.json.JSONArray;
import glados.GLaDOS;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.utils.FileUpload;
import utils.BuildEmbed;
import utils.FileUtils;

public class Backup implements Runnable {
	public JDA jda;

	public Backup(JDA jda) {
		this.jda = jda;
	}

	@Override
	public void run() {
		/*
		 * For now, GLaDOS will upload users data in a private channel. This ensures no data loss
		 * while getting the new infrastructure coming for April 2024.
		 */
		System.out.println("Backing up all accounts !");
		GLaDOS g = GLaDOS.getInstance();
		JSONArray accounts = new JSONArray(g.accounts);

		// Write backup file
		FileUtils.writeRawFile("accounts.json", accounts.toString(4));
		// Upload file to discord
		InputStream inputStream = new ByteArrayInputStream(accounts.toString().getBytes());
		jda.getTextChannelById(g.channelBackup)
				.sendMessageEmbeds(BuildEmbed.successEmbed("Account backup").build())
				.addFiles(FileUpload.fromData(inputStream, "accounts.json")).queue();

		System.out.println("Backup done !");
	}
}
