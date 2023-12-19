package utils;

import java.awt.Color;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import org.json.JSONArray;
import org.json.JSONObject;
import glados.GLaDOS;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Message.Attachment;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class MessagesUtils {
	public static void get1000(MessageChannel channel, Consumer<List<Message>> callback) {
		List<Message> messages = new ArrayList<>(1000);
		channel.getIterableHistory().cache(false).forEachAsync((message) -> {
			messages.add(message);
			return messages.size() < 1000;
		}).thenRun(() -> callback.accept(messages));
	}

	private void downloadChannel(MessageChannel tc) throws IOException {
		JSONArray channelArray = new JSONArray();
		JSONArray messageArray = new JSONArray();
		JSONArray linkedFilesArray = new JSONArray();

		tc.getIterableHistory().cache(false).forEachRemaining((me) -> {
			JSONObject json = new JSONObject();
			json.clear();
			json.put("authorId", me.getAuthor().getIdLong());
			json.put("authorName", me.getAuthor().getName());
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

	public static void downloadServer(JDA jda) {
		Guild server = jda.getGuilds().get(1);
		GLaDOS g = GLaDOS.getInstance();
		MessageChannel channel = server.getTextChannelById(g.channelBotSnapshot);

		for (MessageChannel tc : server.getTextChannels()) {
			channel.sendMessage("Creatin a json array of channel: " + tc.getAsMention()).queue();
			try {
				// jsonChannel(tc);
			} catch (Exception e) {
				channel.sendMessage(e.toString()).queue();
			}

			channel.sendMessage("Successfully created json file of ./" + tc.getName().toLowerCase()
					+ ".txt file").queue();
		}
	}

	public static void jsonChannel(MessageChannel channel) throws IOException {
		JSONArray messageArray = new JSONArray();

		channel.getIterableHistory().cache(false).forEachRemaining((me) -> {
			JSONObject json = new JSONObject();
			json.clear();
			if (me.getContentRaw().length() > 0) {
				json.put("message", me.getContentRaw());
				messageArray.put(json);
			}
			return true;
		});

		FileWriter jsonFile = new FileWriter("./data/channels/" + channel.getName() + ".json");
		jsonFile.write(messageArray.toString());
		jsonFile.flush();
		jsonFile.close();
	}

	public static void downloadChannel(MessageChannel channel, Member member) {
		channel.sendMessage("Downloading channel: " + channel.getAsMention()).queue();
		// channel.getIterableHistory().cache(false).forEachAsync((me) -> {
		channel.getIterableHistory().cache(false).forEachRemaining((me) -> {
			try {
				FileWriter fw = new FileWriter(channel.getName() + ".txt", true);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter out = new PrintWriter(bw);
				out.print("[" + me.getTimeCreated() + "] [" + me.getAuthor().getAsMention() + "] "
						+ me.getContentRaw() + "\n");
				out.close();
			} catch (IOException e) {
				channel.sendMessage(e.toString());
			}
			return true;
		});
		channel.sendMessage("Successfully downloaded " + 0 + " messages in ./"
				+ channel.getName().toLowerCase() + ".txt file").queue();
	}

	public static void countMessages(MessageReceivedEvent event, Member member) {
		event.getChannel().sendTyping().queue();

		EmbedBuilder info = new EmbedBuilder();
		info.setColor(Color.ORANGE);
		info.setTitle(
				"Counting every messages in this channel... Note that because of the computer power of the raspberry pi, this may take a while !");
		event.getChannel().sendMessageEmbeds(info.build()).queue();

		try {
			// CompletableFuture<List<Message>> messages =
			// event.getChannel().getIterableHistory().takeAsync(5000);

			// List<Message> message = messages.get();

			List<Message> message = new ArrayList<>(1000000);
			event.getChannel().getIterableHistory().cache(false).forEachAsync((me) -> {
				message.add(me);
				System.out.println(message.size());
				// return message.size() < Main.MessageRetrieveLimit;
				return true;
			});

			Thread.sleep(10000);
			event.getChannel().sendMessage(message.size() + " messages").queue();

			// get1000(event.getChannel(), (messages) ->
			// event.getChannel().purgeMessages(messages));

			int counter = 0;
			for (Message o : message) {
				o.getId();
				System.out.println(o.getAuthor().getName() + " " + o.getContentRaw());
				counter++;
			}

			EmbedBuilder result = new EmbedBuilder();
			result.setColor(Color.GREEN);
			result.setTitle("Done ! On this channel, " + counter + " messages have been sent !");
			event.getChannel().sendMessageEmbeds(result.build()).queue();

		} catch (Exception e) {
			EmbedBuilder error = new EmbedBuilder();
			error.setColor(Color.RED);
			error.setTitle("FATAL ERROR");
			error.setDescription(e.toString());
			event.getChannel().sendMessageEmbeds(error.build()).queue();
		} catch (OutOfMemoryError e) {
			EmbedBuilder error = new EmbedBuilder();
			error.setColor(Color.RED);
			error.setTitle("FATAL ERROR");
			error.setDescription(e.toString());
			event.getChannel().sendMessageEmbeds(error.build()).queue();
		}
	}
}
