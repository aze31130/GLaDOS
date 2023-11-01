package commands;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.entities.Message.Attachment;
import utils.BuildEmbed;
import utils.Permission;

public class Backup extends Command {
	public Backup(String name, String description, int permissionLevel) {
		super(name, description, permissionLevel);
	}

	private void downloadChannel(MessageChannel tc) throws IOException {
		JSONArray channelArray = new JSONArray();
		JSONArray messageArray = new JSONArray();
		JSONArray linkedFilesArray = new JSONArray();

		tc.getIterableHistory().cache(false).forEachRemaining((me) -> {
			JSONObject json = new JSONObject();
			json.clear();
			json.put("authorId", me.getAuthor().getIdLong());
			json.put("authorName", me.getAuthor().getAsTag());
			json.put("message", me.getContentRaw());
			json.put("date", me.getTimeCreated());
			messageArray.put(json);

			for (Attachment a : me.getAttachments()) {
				linkedFilesArray.put(a.getUrl());
				linkedFilesArray.put(a.getProxyUrl());
			}

			return true;
		});

		channelArray.put(messageArray);
		channelArray.put(linkedFilesArray);

		File folder = new File("./backup");

		if (!folder.exists())
			folder.mkdir();

		FileWriter jsonFile = new FileWriter("./backup/" + tc.getName() + ".json");
		jsonFile.write(channelArray.toString(4));
		jsonFile.flush();
		jsonFile.close();
	}

	@Override
	public void execute(Argument args) {
		if (!Permission.permissionLevel(args.member, 2)) {
			args.channel
					.sendMessageEmbeds(BuildEmbed
							.errorEmbed("You need to have the Administrator role in order to execute that.").build())
					.queue();
			return;
		}
		Guild server = args.channel.getJDA().getGuilds().get(1);

		// Check if the user provided a specific channel to download
		if (args.arguments.length > 0) {
			// Get the channel
			TextChannel tc = server.getTextChannelsByName(args.arguments[0], true).get(0);
			args.channel.sendMessage("Downloading channel " + tc.getAsMention()).queue();

			// Download it
			try {
				downloadChannel(tc);
			} catch (Exception e) {
				args.channel.sendMessage(e.toString()).queue();
			}
		} else {
			List<TextChannel> channels = server.getTextChannels();
			int i = 0;
			// Iterate on every channel and download content sequentially
			for (TextChannel tc : channels) {
				args.channel
						.sendMessage(
								"Downloading channel " + tc.getAsMention() + "(" + (100 * i / channels.size()) + "%)")
						.queue();
				try {
					downloadChannel(tc);
				} catch (Exception e) {
					args.channel.sendMessage(e.toString()).queue();
				}
				i++;
			}
		}
		args.channel.sendMessage("Server's history has been downloaded !").queue();
	}
}
