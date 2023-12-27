package commands;

import java.util.Arrays;
import org.json.JSONArray;
import org.json.JSONObject;
import accounts.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageReaction;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.Message.Attachment;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import utils.BuildEmbed;
import utils.FileUtils;

public class Backup extends Command {
	public Backup() {
		super("backup", "Download a backup of the entire server. Admin privileges required",
				Permission.ADMINISTRATOR, Arrays.asList(new OptionData(OptionType.STRING, "target",
						"The target of the backup action")
								.addChoice("server", "server")
								.addChoice("accounts", "accounts")
								.setRequired(true)));
	}

	public void serverBackup(Guild server, TextChannel source) {
		for (TextChannel channel : server.getTextChannels()) {
			JSONArray messages = new JSONArray();

			source.sendMessage("Downloading " + channel.getAsMention()).queue();

			MessageChannel channel2 = (MessageChannel) channel;

			channel2.getIterableHistory().cache(false).forEachRemaining(message -> {
				System.out.println(message.getContentRaw());

				JSONObject jsonMessage = new JSONObject();
				jsonMessage.put("authorId", message.getAuthor().getIdLong());
				jsonMessage.put("authorName", message.getAuthor().getName());
				jsonMessage.put("message", message.getContentRaw());
				jsonMessage.put("posted", message.getTimeCreated());
				jsonMessage.put("isEdited", message.isEdited());
				jsonMessage.put("isPinned", message.isPinned());
				jsonMessage.put("channelId", message.getChannelIdLong());
				jsonMessage.put("channelName", message.getChannel().getName());
				jsonMessage.put("reactionCount", message.getReactions().size());

				JSONArray attachments = new JSONArray();
				for (Attachment attachment : message.getAttachments())
					attachments.put(attachment.getUrl());
				jsonMessage.put("attachments", attachments);

				JSONArray reactions = new JSONArray();
				for (MessageReaction reaction : message.getReactions()) {
					JSONObject jsonReaction = new JSONObject();

					jsonReaction.put("emote", reaction.getEmoji().toString());
					jsonReaction.put("count", reaction.getCount());

					JSONArray jsonReactionAuthors = new JSONArray();
					for (User reactor : reaction.retrieveUsers().complete()) {
						JSONObject jsonReactionAuthor = new JSONObject();

						jsonReactionAuthor.put("authorId", reactor.getIdLong());
						jsonReactionAuthor.put("authorName", reactor.getName());

						jsonReactionAuthors.put(jsonReactionAuthor);
					}

					jsonReaction.put("reactionAuthors", jsonReactionAuthors);
					reactions.put(jsonReaction);
				}

				jsonMessage.put("reactions", reactions);

				messages.put(jsonMessage);
				return true;
			});
			FileUtils.writeRawFile(channel.getName() + ".json", messages.toString(4));
		}

		source.sendMessageEmbeds(BuildEmbed.successEmbed("Backup completed successfully").build())
				.queue();
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
