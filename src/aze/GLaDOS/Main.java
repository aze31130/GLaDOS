package aze.GLaDOS;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import aze.GLaDOS.Constants.Channels;
import aze.GLaDOS.Constants.Permissions;
import aze.GLaDOS.commands.Call;
import aze.GLaDOS.commands.Quote;
import aze.GLaDOS.commands.Status;
import aze.GLaDOS.database.JsonIO;
import aze.GLaDOS.events.GuildButtonClick;
import aze.GLaDOS.events.GuildMemberJoin;
import aze.GLaDOS.events.GuildMemberRemove;
import aze.GLaDOS.events.GuildMessageReactionAdd;
import aze.GLaDOS.events.GuildMessageReactionRemove;
import aze.GLaDOS.events.GuildMessageReceived;
import aze.GLaDOS.events.GuildSlashCommand;
import aze.GLaDOS.events.GuildVoiceJoin;
import aze.GLaDOS.events.GuildVoiceLeave;
import aze.GLaDOS.events.PrivateMessageReceived;
import aze.GLaDOS.utils.BuildEmbed;
import aze.GLaDOS.utils.Logger;
import aze.GLaDOS.utils.Permission;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

public class Main {
	public static void main(String[] args) throws Exception {
		GLaDOS glados = GLaDOS.getInstance();
		Logger log = new Logger(true);
		System.out.println(log + "Starting GLaDOS");
		glados.initialize();
		System.out.println(log + "Logging messages: " + Constants.logMessage);
		System.out.println(log + "Leveling: " + glados.leveling);
		System.out.println(log + "Maximum level: " + glados.maxLevel);
		System.out.println(log + "Ranking version: ALPHA");
		
		JDABuilder builder = JDABuilder.createDefault(glados.token);
		builder.enableIntents(GatewayIntent.GUILD_MEMBERS);
		builder.enableIntents(GatewayIntent.GUILD_MESSAGES);
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
		
		if(Constants.CheckPrivateMessages){
			jda.addEventListener(new PrivateMessageReceived());
		}
		
		if(Constants.logConnexions){
			jda.addEventListener(new GuildVoiceJoin());
			jda.addEventListener(new GuildVoiceLeave());
			//jda.addEventListener(new ListenerOnline());
			//jda.addEventListener(new GuildVoiceMute());
		}
		
		jda.awaitReady();
		
		/*
		for(Member m : jda.getGuilds().get(1).getMembers()) {
			Account.createAccount(m);
		}*/
		
		
		ScheduledExecutorService clock = Executors.newSingleThreadScheduledExecutor();
		clock.scheduleAtFixedRate(new Runnable() {
		    @Override
		    public void run() {
		    	Calendar cal = Calendar.getInstance();
				if(!Constants.FreeGameAnnonce && (cal.get(Calendar.HOUR_OF_DAY) == 17) && (cal.get(Calendar.MINUTE) <= 1) && (cal.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY)){
					System.out.println("Executed EpicGameAnnoune on " + cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE));
					Call.EpicFreeGame(jda.getTextChannelById(Channels.GAMER.id));
					Constants.FreeGameAnnonce = true;
				} else {
					Constants.FreeGameAnnonce = false;
				}
				
				if(!Constants.LockdownDayAnnonce && (cal.get(Calendar.HOUR_OF_DAY) == 0) && (cal.get(Calendar.MINUTE) <= 1)){
					System.out.println("Executed Random Quote on " + cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE));
					Quote.randomQuotes(jda.getTextChannelById(Channels.GENERAL.id));
					if(glados.leveling) {
						//Ranking.update();
						glados.backup();
						JsonIO.backup();
					}
					Constants.LockdownDayAnnonce = true;
				} else {
					Constants.LockdownDayAnnonce = false;
				}
		    }
		}, 0, 1, TimeUnit.MINUTES);
		ScheduledExecutorService randomActivity = Executors.newSingleThreadScheduledExecutor();
		randomActivity.scheduleAtFixedRate(new Runnable() {
		    @Override
		    public void run() {
		    	Random rng = new Random();
		    	if(rng.nextInt(4) == 0) {
		    		Status.randomActivity(jda);
		    	}
		    }
		}, 0, 1, TimeUnit.HOURS);
		System.out.println(log + "Done ! GLaDOS is running on version " + glados.version + " !");
	}
	
	public static void shutdown(GuildMessageReceivedEvent event, Member member){
		if(Permission.permissionLevel(member, Permissions.ADMIN.level)){
			String time = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
			System.out.println(time + " Shutting down now !");
			event.getJDA().shutdown();
			System.exit(0);
		} else {
			event.getChannel().sendMessage(BuildEmbed.errorPermissionEmbed(2).build()).queue();
		}
	}
}
