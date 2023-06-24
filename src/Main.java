package main;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import constants.Constants;
import constants.Constants.Channels;
import commands.Argument;
import commands.Call;
import events.GuildButtonClick;
import events.GuildMemberJoin;
import events.GuildMemberRemove;
import events.GuildMessageReactionAdd;
import events.GuildMessageReactionRemove;
import events.GuildMessageReceived;
import events.GuildSlashCommand;
import events.GuildVoiceJoin;
import events.GuildVoiceLeave;
import events.PrivateMessageReceived;
import utils.DataLogger;
import utils.Logger;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.exceptions.*;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

public class Main {
	public static void main(String[] args) {
		GLaDOS glados = GLaDOS.getInstance();
		Logger log = new Logger(true);

		System.out.println(log + "Starting GLaDOS");
		glados.initialize();
		System.out.println(log + "Logging messages: " + constants.Constants.logMessage);
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
			
			if(constants.Constants.CheckPrivateMessages){
				jda.addEventListener(new PrivateMessageReceived());
			}
			
			if(constants.Constants.logConnexions){
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
					//Log into the database every online account
					DataLogger.log(jda.getGuildById(Constants.GuildId).retrieveMetaData().complete().getApproximatePresences());

					Calendar cal = Calendar.getInstance();
					if(!constants.Constants.FreeGameAnnonce && (cal.get(Calendar.HOUR_OF_DAY) == 17) && (cal.get(Calendar.MINUTE) == 0) && (cal.get(Calendar.SECOND) <= 10) && (cal.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY)){
						System.out.println(log + "Executed EpicGameAnnoune at " + cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE));
						glados.executeCommand(
							"Call",
							new Argument(null,
								jda.getGuildById(constants.Constants.GuildId).getMemberById(constants.Constants.OwnerId),
								jda.getTextChannelById(Channels.GAMER.id),
								new String[]{"Gamer"},
								null
							)
						);
						constants.Constants.FreeGameAnnonce = true;
					} else {
						constants.Constants.FreeGameAnnonce = false;
					}
					
					if(!constants.Constants.LockdownDayAnnonce && (cal.get(Calendar.HOUR_OF_DAY) == 0) && (cal.get(Calendar.MINUTE) == 0) && (cal.get(Calendar.SECOND) <= 10)){
						System.out.println(log + "Executed Random Quote at " + cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE));
						glados.executeCommand(
							"Call",
							new Argument(null,
								jda.getGuildById(constants.Constants.GuildId).getMemberById(constants.Constants.OwnerId),
								jda.getTextChannelById(Channels.GENERAL.id),
								new String[]{"Midnight"},
								null
							)
						);

						if(glados.leveling) {
							//Ranking.update();
							glados.backup();
							//JsonIO.backup();
						}
						constants.Constants.LockdownDayAnnonce = true;
					} else {
						constants.Constants.LockdownDayAnnonce = false;
					}
					
					/*
					if((cal.get(Calendar.MINUTE) % 15) == 0) {
						jda.getGuildById("676731153444765706").retrieveMetaData()
						.map(Guild.MetaData::getApproximatePresences)
						.queue(amount -> jda.getTextChannelById(Channels.BOT_SNAPSHOT.id)
								.sendMessage("Report " + new Logger(false) + " ```" + amount + " people online.```").queue());
					}
					*/
				}
			}, 0, 10, TimeUnit.SECONDS);
			System.out.println(log + "Done ! GLaDOS is running on version " + glados.version + " !");

		} catch (InterruptedException e) {
			System.err.println(log + "Thread interrupted !");
		} catch (Exception e) {
			System.err.println(log + "Invalid token given ! ");
		}
	
	/*
	Guild server = jda.getGuilds().get(1);
	
	//TextChannel tc = server.getTextChannelById("676731153444765709");
	for (TextChannel tc : server.getTextChannels()) {
		jda.getTextChannelById(Channels.BOT_SNAPSHOT.id).sendMessage("Downloading channel " + tc.getAsMention()).queue();
		try {
			JSONArray messageArray = new JSONArray();
			tc.getIterableHistory().cache(false).forEachRemaining((me) -> {
				JSONObject json = new JSONObject();
				json.clear();
				json.put("author", me.getAuthor().getIdLong());
				json.put("message", me.getContentRaw());
				json.put("date", me.getTimeCreated());
				messageArray.put(json);
				
				return true;
			});
			
			FileWriter jsonFile = new FileWriter("./data/channels/" + tc.getName() + ".json");
			jsonFile.write(messageArray.toString());
			jsonFile.flush();
			jsonFile.close();
		} catch(Exception e) {
			jda.getTextChannelById(Channels.BOT_SNAPSHOT.id).sendMessage(e.toString()).queue();
		}
	}
	*/	
	}
}
