import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import events.*;
import tasks.*;
import glados.GLaDOS;
import utils.Logging;
import utils.TimeUtils;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

public class Main implements Logging {
	public static void main(String[] args) {
		System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tF %1$tT] [%4$s] %5$s%6$s%n");
		GLaDOS glados = GLaDOS.getInstance();

		LOGGER.info("Starting GLaDOS");
		glados.initialize();

		JDABuilder builder = JDABuilder.createDefault(glados.token);

		GatewayIntent intents[] =
				{GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT,
						GatewayIntent.GUILD_PRESENCES, GatewayIntent.GUILD_MESSAGE_REACTIONS,
						GatewayIntent.GUILD_EMOJIS_AND_STICKERS, GatewayIntent.GUILD_INVITES};

		for (GatewayIntent intent : intents)
			builder.enableIntents(intent);

		builder.setChunkingFilter(ChunkingFilter.ALL);
		builder.setMemberCachePolicy(MemberCachePolicy.ALL);
		JDA jda = builder.build();

		jda.setAutoReconnect(true);

		ListenerAdapter events[] = {new AutoComplete(), new ButtonClick(), new ChannelCreate(), new ChannelDelete(),
				new ContextMessage(), new ContextUser(), new MemberJoin(), new MemberRemove(), new MessageDelete(),
				new MessageReactionAdd(), new MessageReactionRemove(), new MessageReceived(), new ModalReceived(),
				new SlashCommandInteraction(), new VoiceUpdate(), new VoiceMute()};

		for (ListenerAdapter event : events)
			jda.addEventListener(event);

		try {
			jda.awaitReady();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		glados.registerCommands(jda);
		glados.loadAccounts(jda);

		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(5);
		scheduler.scheduleAtFixedRate(new Midnight(jda), TimeUtils.getMidnightDelay(), 86400000, TimeUnit.MILLISECONDS);
		scheduler.scheduleAtFixedRate(new EpicGames(jda), TimeUtils.getEpicGameDelay(), 7 * 86400, TimeUnit.SECONDS);
		scheduler.scheduleAtFixedRate(new Status(jda), 0, 10, TimeUnit.HOURS);
		scheduler.scheduleAtFixedRate(new Backup(jda), 12, 24, TimeUnit.HOURS);
		scheduler.scheduleAtFixedRate(new News(jda), 0, 1, TimeUnit.HOURS);

		LOGGER.info("Done ! GLaDOS is running on version " + glados.version + " !");
	}
}
