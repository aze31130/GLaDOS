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
import net.dv8tion.jda.api.hooks.ListenerAdapter;
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

			GatewayIntent intents[] = {GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGES,
					GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_PRESENCES,
					GatewayIntent.GUILD_MESSAGE_REACTIONS, GatewayIntent.GUILD_EMOJIS_AND_STICKERS,
					GatewayIntent.GUILD_INVITES};

			for (GatewayIntent intent : intents)
				builder.enableIntents(intent);

			builder.setChunkingFilter(ChunkingFilter.ALL);
			builder.setMemberCachePolicy(MemberCachePolicy.ALL);
			JDA jda = builder.build();

			jda.setAutoReconnect(true);

			ListenerAdapter events[] = {new ButtonClick(), new MemberJoin(), new MemberRemove(),
					new MessageReactionAdd(), new MessageReactionRemove(), new MessageReceived(),
					new SlashCommandInteraction(), new VoiceUpdate(), new VoiceMute()};

			for (ListenerAdapter event : events)
				jda.addEventListener(event);

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
						Call.callMessage(jda.getTextChannelById(glados.channelGeneral), "Midnight");
						try {
							Thread.sleep(10000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						Call.midnightRank(jda.getTextChannelById(glados.channelGeneral));

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
	}
}
