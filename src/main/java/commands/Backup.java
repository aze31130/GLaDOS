package commands;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;
import org.json.JSONArray;
import org.json.JSONObject;
import accounts.Permissions;
import glados.GLaDOS;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.utils.FileUpload;

public class Backup extends Command {
	public Backup() {
		super("backup", "Download a backup of the entire server. Admin privileges required",
				Permissions.ADMINISTRATOR, Arrays.asList(new OptionData(OptionType.STRING, "target",
						"The target of the backup action")
								.addChoice("server", "server")
								.addChoice("accounts", "accounts")
								.setRequired(true)));
	}

	public void serverBackup(Guild server, TextChannel source) {
		for (GuildChannel channel : server.getChannels()) {
			source.sendMessage("Downloading " + channel.getAsMention()).queue();
			source.sendTyping().queue();
			JSONArray messages = new JSONArray();

			MessageChannel channel2 = (MessageChannel) channel;

			channel2.getIterableHistory().cache(false).forEachRemaining(message -> {
				JSONObject json = new JSONObject();
				json.put("authorId", message.getAuthor().getIdLong());
				json.put("authorName", message.getAuthor().getName());
				json.put("message", message.getContentRaw());
				json.put("date", message.getTimeCreated());
				messages.put(json);

				// for (Attachment a : me.getAttachments()) {
				// linkedFilesArray.put(a.getUrl());
				// linkedFilesArray.put(a.getProxyUrl());
				// }
				return true;
			});
		}
	}

	public void accountBackup(GLaDOS glados, TextChannel backupChannel) {
		JSONArray accounts = new JSONArray(glados.accounts);
		InputStream inputStream = new ByteArrayInputStream(accounts.toString().getBytes());

		backupChannel.sendMessage("Account backup")
				.addFiles(FileUpload.fromData(inputStream, "accounts.json")).queue();
	}

	@Override
	public void execute(SlashCommandInteractionEvent event) {
		String type = event.getOption("target").getAsString();
		Guild guild = event.getGuild();
		GLaDOS glados = GLaDOS.getInstance();
		TextChannel source = event.getGuildChannel().asTextChannel();

		if (type.equals("server")) {
			source.sendMessage("Downloading server's history...").queue();
			serverBackup(guild, source);
			source.sendMessage("Backup action completed successfully.").queue();
			return;
		}

		if (type.equals("accounts")) {
			accountBackup(glados, event.getJDA().getTextChannelById(glados.channelBackup));
			source.sendMessage("Successfully backed up account database.").queue();
			return;
		}
	}
}
