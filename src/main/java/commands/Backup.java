package commands;

import java.util.Arrays;
import org.json.JSONArray;
import org.json.JSONObject;
import accounts.Permission;
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
				Permission.ADMINISTRATOR, Arrays.asList(new OptionData(OptionType.CHANNEL, "target",
						"The targeted channel you want to backup")
								.setRequired(true)));
	}

	public void channelBackup(MessageChannel source, MessageChannel channel) {
		JSONArray messages = new JSONArray();

		channel.getIterableHistory().cache(true).forEachRemaining(message -> {
			System.out.println(messages.length());
			JSONObject jsonMessage = new JSONObject();
			jsonMessage.put("authorId", message.getAuthor().getIdLong());
			jsonMessage.put("authorName", message.getAuthor().getName());
			jsonMessage.put("message", message.getContentRaw());
			jsonMessage.put("posted", message.getTimeCreated());
			jsonMessage.put("isEdited", message.isEdited());
			jsonMessage.put("isPinned", message.isPinned());
			jsonMessage.put("channelId", message.getChannelIdLong());
			jsonMessage.put("channelName", message.getChannel().getName());

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

		source.sendMessageEmbeds(
				BuildEmbed.successEmbed("Channel backup completed successfully").build())
				.queue();
	}

	@Override
	public void execute(SlashCommandInteractionEvent event) {
		MessageChannel channel = (MessageChannel) event.getOption("target").getAsChannel();
		TextChannel source = event.getGuildChannel().asTextChannel();

		source.sendMessage("Downloading " + channel.getAsMention()).complete();
		channelBackup(source, channel);
	}
}
