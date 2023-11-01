import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import events.GuildButtonClick;
import events.GuildMemberJoin;
import events.GuildMemberRemove;
import events.GuildMessageReactionAdd;
import events.GuildMessageReactionRemove;
import events.GuildMessageReceived;
import events.GuildSlashCommand;
import events.GuildVoiceJoin;
import glados.GLaDOS;
import utils.DataLogger;
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
		System.out.println(log + "Ranking version: ALPHA");

		try {
			JDABuilder builder = JDABuilder.createDefault(glados.token);

			builder.enableIntents(GatewayIntent.GUILD_MEMBERS);
			builder.enableIntents(GatewayIntent.GUILD_MESSAGES);
			builder.enableIntents(GatewayIntent.GUILD_PRESENCES);
			builder.setChunkingFilter(ChunkingFilter.ALL);
			builder.setMemberCachePolicy(MemberCachePolicy.ALL);
			JDA jda = builder.build();
			jda.setAutoReconnect(true);
			jda.addEventListener(new GuildMemberJoin());
			jda.addEventListener(new GuildMessageReceived());
			jda.addEventListener(new GuildMemberRemove());
			jda.addEventListener(new GuildMessageReactionAdd());
			jda.addEventListener(new GuildMessageReactionRemove());
			jda.addEventListener(new GuildSlashCommand());
			jda.addEventListener(new GuildButtonClick());
			jda.addEventListener(new GuildVoiceJoin());

			// if(constants.Constants.logConnexions){
			// jda.addEventListener(new ListenerOnline());
			// jda.addEventListener(new GuildVoiceMute());
			// }
			jda.awaitReady();

			ScheduledExecutorService clock = Executors.newSingleThreadScheduledExecutor();
			clock.scheduleAtFixedRate(new Runnable() {
				@Override
				public void run() {
					Calendar cal = Calendar.getInstance();

					if ((glados.metricLogging && cal.get(Calendar.SECOND) <= 10)) {
						// Log into the database every online account
						DataLogger.log(jda.getGuildById(glados.guildId).retrieveMetaData().complete()
								.getApproximatePresences());
					}

					if (!glados.FreeGameAnnonce && (cal.get(Calendar.HOUR_OF_DAY) == 17)
							&& (cal.get(Calendar.MINUTE) == 0) && (cal.get(Calendar.SECOND) <= 10)
							&& (cal.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY)) {
						System.out.println(log + "Executed EpicGameAnnoune at " + cal.get(Calendar.HOUR_OF_DAY) + ":"
								+ cal.get(Calendar.MINUTE));
						// glados.executeCommand(
						// "Call",
						// new Argument(
						// jda.getGuildById(glados.guildId).getMemberById(glados.ownerId),
						// jda.getTextChannelById(glados.channelGamer),
						// new String[] { "Gamer" },
						// null));
						glados.FreeGameAnnonce = true;
					} else {
						glados.FreeGameAnnonce = false;
					}

					if (!glados.DailyQuote && (cal.get(Calendar.HOUR_OF_DAY) == 0) && (cal.get(Calendar.MINUTE) == 0)
							&& (cal.get(Calendar.SECOND) <= 10)) {
						System.out.println(log + "Executed Random Quote at " + cal.get(Calendar.HOUR_OF_DAY) + ":"
								+ cal.get(Calendar.MINUTE));
						// glados.executeCommand(
						// "Call",
						// new Argument(
						// jda.getGuildById(glados.guildId).getMemberById(glados.ownerId),
						// jda.getTextChannelById(glados.channelGeneral),
						// new String[] { "Midnight" },
						// null));

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
			System.out.println(log + "Done ! GLaDOS is running on version " + glados.version + " !");

		} catch (InterruptedException e) {
			System.err.println(log + "Thread interrupted !");
		} catch (Exception e) {
			System.err.println(log + "Invalid token given ! ");
		}

		/*
		 * Guild server = jda.getGuilds().get(1);
		 * 
		 * //TextChannel tc = server.getTextChannelById("676731153444765709");
		 * for (TextChannel tc : server.getTextChannels()) {
		 * jda.getTextChannelById(Channels.BOT_SNAPSHOT.id).
		 * sendMessage("Downloading channel " + tc.getAsMention()).queue();
		 * try {
		 * JSONArray messageArray = new JSONArray();
		 * tc.getIterableHistory().cache(false).forEachRemaining((me) -> {
		 * JSONObject json = new JSONObject();
		 * json.clear();
		 * json.put("author", me.getAuthor().getIdLong());
		 * json.put("message", me.getContentRaw());
		 * json.put("date", me.getTimeCreated());
		 * messageArray.put(json);
		 * 
		 * return true;
		 * });
		 * 
		 * FileWriter jsonFile = new FileWriter("./data/channels/" + tc.getName() +
		 * ".json");
		 * jsonFile.write(messageArray.toString());
		 * jsonFile.flush();
		 * jsonFile.close();
		 * } catch(Exception e) {
		 * jda.getTextChannelById(Channels.BOT_SNAPSHOT.id).sendMessage(e.toString()).
		 * queue();
		 * }
		 * }
		 * 
		 * (Inserting April Joke...) Congratulations, the test is now over. Happy April
		 * Fool's Day!
		 */
	}
}
