package commands;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import accounts.Permissions;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.entities.Message.Attachment;

public class Backup extends Command {
	public Backup(String name, String description, Permissions permissionLevel,
			List<OptionData> arguments) {
		super(name, description, permissionLevel, arguments);
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
	public void execute(SlashCommandInteractionEvent event) {
		Guild server = event.getGuild();
		TextChannel source = event.getGuildChannel().asTextChannel();

		int i = 0;
		List<TextChannel> channels = server.getTextChannels();
		for (TextChannel tc : channels) {
			source.sendMessage("Downloading channel " + tc.getAsMention() + "("
					+ (100 * i / channels.size()) + "%)").queue();
			try {
				downloadChannel(tc);
			} catch (Exception e) {
				source.sendMessage(e.toString()).queue();
			}
			i++;
		}

		source.sendMessage("Server's history has been downloaded !").queue();
	}
}
