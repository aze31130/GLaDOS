package commands;

import java.util.Arrays;
import org.json.JSONArray;
import org.json.JSONObject;
import accounts.Permissions;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import utils.FileUtils;

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
		JSONArray messages = new JSONArray();

		for (GuildChannel channel : server.getChannels()) {
			source.sendMessage("Downloading " + channel.getAsMention()).queue();
			source.sendTyping().queue();

			MessageChannel channel2 = (MessageChannel) channel;

			channel2.getIterableHistory().cache(false).forEachRemaining(message -> {
				JSONObject json = new JSONObject();
				json.put("authorId", message.getAuthor().getIdLong());
				json.put("authorName", message.getAuthor().getName());
				json.put("message", message.getContentRaw());
				json.put("posted", message.getTimeCreated());
				json.put("isEdited", message.isEdited());
				json.put("attachments", message.getAttachments().size());
				json.put("reactions", message.getReactions().size());
				json.put("isPinned", message.isPinned());
				json.put("channelId", message.getChannelId());
				json.put("channelName", message.getChannel().getName());
				messages.put(json);
				return true;
			});
		}

		FileUtils.writeRawFile("server.json", messages.toString(4));
	}

	@Override
	public void execute(SlashCommandInteractionEvent event) {
		String type = event.getOption("target").getAsString();
		Guild guild = event.getGuild();
		TextChannel source = event.getGuildChannel().asTextChannel();

		if (type.equals("server")) {
			source.sendMessage("Downloading server's history...").queue();
			serverBackup(guild, source);
			source.sendMessage("Backup action completed successfully.").queue();
			return;
		}

		if (type.equals("accounts")) {
			new tasks.Backup(event.getJDA()).run();
			return;
		}
	}
}
