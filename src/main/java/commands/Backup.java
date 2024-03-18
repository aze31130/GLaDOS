package commands;

import java.util.Arrays;
import org.json.JSONArray;
import org.json.JSONObject;
import accounts.Permission;
import net.dv8tion.jda.api.entities.Message.Attachment;
import net.dv8tion.jda.api.entities.MessageReaction;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import utils.BuildEmbed;
import utils.FileUtils;
import utils.Logging;

public class Backup extends Command implements Logging {
	public Backup() {
		super(
				"backup",
				"Download a backup of the entire server. Admin privileges required",
				Permission.ADMINISTRATOR,
				Tag.SYSTEM,
				Arrays.asList(
						new OptionData(OptionType.CHANNEL, "target", "The channel you want to backup.", true)));
	}

	@Override
	public void execute(SlashCommandInteractionEvent event) {
		MessageChannel target = (MessageChannel) event.getOption("target").getAsChannel();

		event.getHook().sendMessage("Downloading channel " + target.getAsMention() + "...").complete();

		JSONArray messages = new JSONArray();

		target.getIterableHistory().forEachRemaining(message -> {
			LOGGER.info(target.getName() + " " + messages.length());
			JSONObject jsonMessage = new JSONObject();
			jsonMessage.put("id", message.getIdLong());
			jsonMessage.put("authorId", message.getAuthor().getIdLong());
			jsonMessage.put("authorName", message.getAuthor().getName());
			jsonMessage.put("message", message.getContentRaw());
			jsonMessage.put("posted", message.getTimeCreated());
			jsonMessage.put("isEdited", message.isEdited());
			jsonMessage.put("isPinned", message.isPinned());
			jsonMessage.put("channelId", message.getChannelIdLong());
			jsonMessage.put("channelName", target.getName());

			JSONArray attachments = new JSONArray();
			for (Attachment attachment : message.getAttachments())
				attachments.put(attachment.getUrl());
			jsonMessage.put("attachments", attachments);

			JSONArray reactions = new JSONArray();
			for (MessageReaction reaction : message.getReactions()) {
				JSONObject jsonReaction = new JSONObject();

				jsonReaction.put("emote", reaction.getEmoji().toString());
				try {
					jsonReaction.put("count", reaction.getCount());
				} catch (IllegalStateException e) {
					e.printStackTrace();
					jsonReaction.put("count", 0);
				}
				reactions.put(jsonReaction);
			}

			jsonMessage.put("reactions", reactions);

			messages.put(jsonMessage);
			return true;
		});
		FileUtils.writeRawFile(target.getName() + ".json", messages.toString(4));

		event.getHook().sendMessageEmbeds(
				BuildEmbed.successEmbed("Downloaded " + target.getAsMention() + " successfully").build()).complete();
	}
}
