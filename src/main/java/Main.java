import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import commands.Call;
import events.*;
import glados.GLaDOS;
import utils.Logger;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

public class Main {
	public static void main(String[] args) {
		GLaDOS glados = GLaDOS.getInstance();
		Logger log = new Logger(true);

		System.out.println(log + "Starting GLaDOS");
		glados.initialize();
		System.out.println(log + "Logging messages: " + glados.logMessages);
		System.out.println(log + "Leveling: " + glados.leveling);
		System.out.println(log + "Maximum level: " + glados.maxLevel);

		try {
			JDABuilder builder = JDABuilder.createDefault(glados.token);

			builder.enableIntents(GatewayIntent.GUILD_MEMBERS);
			builder.enableIntents(GatewayIntent.GUILD_MESSAGES);
			builder.enableIntents(GatewayIntent.MESSAGE_CONTENT);
			builder.enableIntents(GatewayIntent.GUILD_PRESENCES);
			builder.enableIntents(GatewayIntent.GUILD_MESSAGE_REACTIONS);
			builder.enableIntents(GatewayIntent.GUILD_EMOJIS_AND_STICKERS);
			builder.enableIntents(GatewayIntent.GUILD_INVITES);

			builder.setChunkingFilter(ChunkingFilter.ALL);
			builder.setMemberCachePolicy(MemberCachePolicy.ALL);
			JDA jda = builder.build();

			jda.setAutoReconnect(true);
			jda.addEventListener(new ButtonClick());
			jda.addEventListener(new MemberJoin());
			jda.addEventListener(new MemberRemove());
			jda.addEventListener(new MessageReactionAdd());
			jda.addEventListener(new MessageReactionRemove());
			jda.addEventListener(new MessageReceived());
			jda.addEventListener(new SlashCommandInteraction());
			jda.addEventListener(new VoiceUpdate());
			jda.addEventListener(new VoiceMute());
			jda.awaitReady();

			glados.registerCommands(jda);

			ScheduledExecutorService clock = Executors.newSingleThreadScheduledExecutor();
			clock.scheduleAtFixedRate(new Runnable() {
				@Override
				public void run() {
					Calendar cal = Calendar.getInstance();

					if ((glados.metricLogging && cal.get(Calendar.SECOND) <= 10)) {
						// Log into the database every online account
						// DataLogger.log(jda.getGuildById(glados.guildId).retrieveMetaData().complete()
						// .getApproximatePresences());
					}

					if (!glados.FreeGameAnnonce && (cal.get(Calendar.HOUR_OF_DAY) == 17)
							&& (cal.get(Calendar.MINUTE) == 0) && (cal.get(Calendar.SECOND) <= 10)
							&& (cal.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY)) {
						System.out.println(log + "Executed EpicGameAnnoune at "
								+ cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE));
						Call.callMessage(jda.getTextChannelById(glados.channelGamer), "Gamer");
						glados.FreeGameAnnonce = true;
					} else {
						glados.FreeGameAnnonce = false;
					}

					if (!glados.DailyQuote && (cal.get(Calendar.HOUR_OF_DAY) == 0)
							&& (cal.get(Calendar.MINUTE) == 0)
							&& (cal.get(Calendar.SECOND) <= 10)) {
						System.out.println(log + "Executed Random Quote at "
								+ cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE));
						Call.callMessage(jda.getTextChannelById(glados.channelGamer), "Midnight");

						// if(glados.leveling) {
						// Ranking.update();
						// glados.backup();
						// JsonIO.backup();
						// }
						glados.DailyQuote = true;
					} else {
						glados.DailyQuote = false;
					}
				}
			}, 0, 10, TimeUnit.SECONDS);
			System.out
					.println(log + "Done ! GLaDOS is running on version " + glados.version + " !");

		} catch (InterruptedException e) {
			System.err.println(log + "Thread interrupted !");
		} catch (Exception e) {
			e.printStackTrace();
		}

		/*
		 * TODO: each day, compute midnight winner
		 * 
		 * Guild server = jda.getGuilds().get(1);
		 * 
		 * //TextChannel tc = server.getTextChannelById("676731153444765709"); for (TextChannel tc :
		 * server.getTextChannels()) { jda.getTextChannelById(Channels.BOT_SNAPSHOT.id).
		 * sendMessage("Downloading channel " + tc.getAsMention()).queue(); try { JSONArray
		 * messageArray = new JSONArray();
		 * tc.getIterableHistory().cache(false).forEachRemaining((me) -> { JSONObject json = new
		 * JSONObject(); json.clear(); json.put("author", me.getAuthor().getIdLong());
		 * json.put("message", me.getContentRaw()); json.put("date", me.getTimeCreated());
		 * messageArray.put(json);
		 * 
		 * return true; });
		 * 
		 * FileWriter jsonFile = new FileWriter("./data/channels/" + tc.getName() + ".json");
		 * jsonFile.write(messageArray.toString()); jsonFile.flush(); jsonFile.close(); }
		 * catch(Exception e) {
		 * jda.getTextChannelById(Channels.BOT_SNAPSHOT.id).sendMessage(e.toString()). queue(); } }
		 * 
		 * (Inserting April Joke...) Congratulations, the test is now over. Happy April Fool's Day!
		 */
	}
}
