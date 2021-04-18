package aze.GLaDOS;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import aze.GLaDOS.Constants.Channels;
import aze.GLaDOS.Commands.Call;
import aze.GLaDOS.Commands.Meme;
import aze.GLaDOS.Commands.Status;
import aze.GLaDOS.Events.GuildMemberJoin;
import aze.GLaDOS.Events.GuildMemberRemove;
import aze.GLaDOS.Events.GuildMessageReactionAdd;
import aze.GLaDOS.Events.GuildMessageReactionRemove;
import aze.GLaDOS.Events.GuildMessageReceived;
import aze.GLaDOS.Events.GuildSlashCommand;
import aze.GLaDOS.Events.GuildVoiceJoin;
import aze.GLaDOS.Events.GuildVoiceLeave;
import aze.GLaDOS.Events.PrivateMessageReceived;
import aze.GLaDOS.Utils.BuildEmbed;
import aze.GLaDOS.Utils.JsonIO;
import aze.GLaDOS.Utils.Permission;
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
		glados.initialize();
		String time = new SimpleDateFormat("[dd/MM/yyyy HH:mm:ss]").format(new Date());
		System.out.println(time + " Starting GLaDOS version " + glados.version);
		System.out.println(time + " Logging messages: " + Constants.logMessage);
		System.out.println(time + " Logging connexions: " + Constants.logConnexions);
		System.out.println(time + " Ranking version: ");
		JsonIO.loadAccounts();

		//THIS IS A TEMPORARY CODE THAT WILL BE REMOVED ON MAY 16TH
		System.out.println(time + " Delay of 30 seconds has begun ! Connect the server to the network.");
		//Thread.sleep(30000);
		System.out.println(time + " Delay of 30 seconds ended ! Trying to connect to websocket...");
		//THIS IS A TEMPORARY CODE THAT WILL BE REMOVED ON MAY 16TH
		
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
					Meme.randomQuotes(jda.getTextChannelsByName("general", true).get(0));
					if(Constants.isAccountLoaded && JsonIO.isBackupFolderMounted()) {
						//Ranking.update();
						JsonIO.backupAccounts();
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
		System.out.println(time + " Done ! GLaDOS is running ! ");
	}
	
	public static void shutdown(GuildMessageReceivedEvent event, Member member){
		if(Permission.permissionLevel(member, 2)){
			String time = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
			System.out.println(time + " Shutting down now !");
			event.getJDA().shutdown();
			System.exit(0);
		} else {
			event.getChannel().sendMessage(BuildEmbed.errorPermissionEmbed(2).build()).queue();
		}
	}
}
