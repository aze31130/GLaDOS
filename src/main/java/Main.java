import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import events.*;
import tasks.*;
import glados.GLaDOS;
import utils.Logger;
import utils.TimeUtils;
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
					new ModalReceived(), new SlashCommandInteraction(), new VoiceUpdate(),
					new VoiceMute()};

			for (ListenerAdapter event : events)
				jda.addEventListener(event);

			jda.awaitReady();

			glados.registerCommands(jda);

			ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(4);

			scheduler.scheduleAtFixedRate(new Midnight(jda), TimeUtils.getMidnightDelay(), 86400000,
					TimeUnit.MILLISECONDS);

			scheduler.scheduleAtFixedRate(new EpicGames(jda), TimeUtils.getEpicGameDelay(),
					7 * 86400, TimeUnit.SECONDS);

			scheduler.scheduleAtFixedRate(new Status(jda), 0, 6, TimeUnit.HOURS);

			scheduler.scheduleAtFixedRate(new Backup(jda), 0, 12, TimeUnit.HOURS);

			System.out
					.println(log + "Done ! GLaDOS is running on version " + glados.version + " !");
		} catch (InterruptedException e) {
			System.err.println(log + "Thread interrupted !");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
