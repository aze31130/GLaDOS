package aze.GLaDOS.Commands;

import java.util.Random;

import aze.GLaDOS.Constants;
import aze.GLaDOS.Constants.Roles;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class Status {
	public static void state(GuildMessageReceivedEvent event){
		String[] message = event.getMessage().getContentRaw().split("\\s+");
		
		if(message.length >= 2){
			Boolean isValidState = true;
			switch(message[1]){
			case "online":
				event.getJDA().getPresence().setStatus(OnlineStatus.ONLINE);
				break;
			case "idle":
				event.getJDA().getPresence().setStatus(OnlineStatus.IDLE);
				break;
			case "dnd":
				event.getJDA().getPresence().setStatus(OnlineStatus.DO_NOT_DISTURB);
				break;
			default:
				isValidState = false;
			}
			
			if(isValidState){
				EmbedBuilder sucess = new EmbedBuilder();
				sucess.setColor(0x22ff2a);
				sucess.setTitle("Successfully updated to " + message[1] + " state.");
				event.getChannel().sendMessage(sucess.build()).queue();
			} else {
				EmbedBuilder error = new EmbedBuilder();
				error.setColor(0xff3923);
				error.setTitle("Error in the command");
				error.setDescription("Unknown state: " + message[1] + ". All states are: <online / idle / dnd>");
				event.getChannel().sendMessage(error.build()).queue();
			}
		} else {
			EmbedBuilder error = new EmbedBuilder();
			error.setColor(0xff3923);
			error.setTitle("Error in the command");
			error.setDescription("Usage: " + Constants.commandPrefix + "state <online / idle / dnd>");
			event.getChannel().sendMessage(error.build()).queue();
		}
	}
	
	public static void activity(GuildMessageReceivedEvent event){
		String[] message = event.getMessage().getContentRaw().split("\\s+");
		
		if(message.length >= 3){
			Boolean isValidActivity = true;
			switch(message[1]){
				case "listening":
					event.getJDA().getPresence().setActivity(Activity.listening(concatene(message)));
					break;
				case "playing":
					event.getJDA().getPresence().setActivity(Activity.playing(concatene(message)));
					break;
				case "watching":
					event.getJDA().getPresence().setActivity(Activity.watching(concatene(message)));
					break;
				case "streaming":
					event.getJDA().getPresence().setActivity(Activity.streaming(concatene(message), "https://www.twitch.tv/ "));
					break;
				default:
					//event.getJDA().getPresence().setActivity(Activity.of(Activity.ActivityType.CUSTOM_STATUS, message[1] + concatene(message)));
					event.getJDA().getPresence().setActivity(Activity.of(Activity.ActivityType.CUSTOM_STATUS, message[1] + concatene(message)));
					isValidActivity = false;
			}
			
			if(isValidActivity){
				EmbedBuilder sucess = new EmbedBuilder();
				sucess.setColor(0x22ff2a);
				sucess.setTitle("Successfully updated to " + message[1] + " " + concatene(message) + "activity.");
				event.getChannel().sendMessage(sucess.build()).queue();
			} else {
				EmbedBuilder error = new EmbedBuilder();
				error.setColor(0xff3923);
				error.setTitle("Error in the command");
				error.setDescription("Unknown activity: " + message[1] + ". All activities are: <listening / playing / watching / streaming> <name>");
				event.getChannel().sendMessage(error.build()).queue();
			}
		} else {
			EmbedBuilder error = new EmbedBuilder();
			error.setColor(0xff3923);
			error.setTitle("Error in the command");
			error.setDescription("Usage: " + Constants.commandPrefix + "activity <listening / playing / watching / streaming> <name>");
			event.getChannel().sendMessage(error.build()).queue();
		}
	}
	
	public static void randomActivity(JDA jda) {
		String[] messages = {
				"with itself",
				"I'm not immature, I just know how to have fun",
				"Parachute for sale, used once, never opened!",
				"Life is beautiful... from Friday to Monday",
				"Before Coronavirus, I'd cough to cover a fart. Now I fart to cover a cough",
				"I'm having a quarantine party this weekend. None of you are invited!",
				"Time is precious. Waste it wisely",
				"I am nobody. Nobody is perfect. Therefore, I am perfect!",
				"I'm right 90% of the time, so why worry about the other 3%?",
				"When life gives you lemons, throw them at someone!",
				"Lucky for you, mirrors are unable to laugh out loud",
				"Why didn't you reply to my messages ?",
				"Birthdays are good for your health. Studies show those who have more Birthdays live longer.",
				"KID CATAPULTOR SIMULATOR 2021 VR",
				"I tried being normal once... the most boring hour of my life.",
				"Time to train for my favourite winter sport. Extreme Hibernation.",
				"It's so simple to be wise. Just think of something stupid to say and then don't say it",
				"Never make the same mistake twice, there are so many new ones to make",
				"Some people just need a High-Five, on the face",
				"I'm not sad for being single. Rather I'm thinking about my better half, who is single because of me",
				"I don't care what other say or think about me, at least I am attractive to bugs",
				"Bigfoot saw me yesterday but no one believes him",
				"I wish I were you, so I could be friends with me",
				"I'll be back before you can pronounce actillimandataquerin altosapaoyabayadoondib!",
				"Smart people like me don't use away messages... I am so smart!",
				"Back in 60 minutes. If not, read this status again",
				"I tried being awesome today, but I was just so tired from being awesome yesterday",
				"4 out of 5 voices in my head say: Go back to sleep!",
				"I can't sleep, because I'm depressed... but I'm depressed, because I can't sleep",
				"The cow goes moo, the sheep goes baaa, the duck goes quack, and I go Zzzzzzz",
				"Best part about being me is, I'm NOT you!",
				"I never forget a face, but in your case I'll be GLaD to make an exception",
				"Everyone is entitled to be stupid, but you abuse the privilege",
				"Leave me a message and I'll be sure to ignore it",
				"I'm obviously doing something more important than you",
				"I'm not here right now, because I'm waiting for you to get offline"
		};
		
		Random rng = new Random();
		int randomNumber = rng.nextInt(messages.length);
		jda.getPresence().setActivity(Activity.playing(messages[randomNumber]));
	}
	
	public static String concatene(String[] Table){
		String concatenated = "";
		int i = 2;
		while(i < Table.length){
			concatenated = concatenated + Table[i] + " ";
			i++;
		}
		return concatenated;
	}
	
	public static void updateSettings(GuildMessageReceivedEvent event){
		String[] message = event.getMessage().getContentRaw().split("\\s+");
		if(message.length >= 3){
			if(event.getGuild().getMemberById(event.getAuthor().getId()).getRoles().contains(event.getGuild().getRoleById(Roles.ADMIN.id))){
				Boolean isValidSetting = true;
				Boolean isValidUpdate = true;
				
				switch(message[1]){
					case "commandPrefix":
						if(message[2].length() == 1){
							Constants.commandPrefix = message[2];
						} else {
							isValidUpdate = false;
						}
						break;
					case "logMessage":
						if(message[2].equals("enable")){
							Constants.logMessage = true;
						} else {
							if(message[2].equals("disable")){
								Constants.logMessage = true;
							} else {
								isValidUpdate = false;
							}
						}
						break;
					default:
						isValidSetting = false;
				}
				
				if((isValidSetting) && (isValidUpdate)){
					EmbedBuilder sucess = new EmbedBuilder();
					sucess.setColor(0x22ff2a);
					sucess.setTitle("Successfully updated " + message[1] + " setting to "  + message[2]);
					event.getChannel().sendMessage(sucess.build()).queue();
				} else {
					EmbedBuilder error = new EmbedBuilder();
					error.setColor(0xff3923);
					error.setTitle("Error in the command");
					error.setDescription("Unknown setting or state: " + message[1] + ". All settings are: [commandPrefix / logMessage] <enable / disable>");
					event.getChannel().sendMessage(error.build()).queue();
				}
			} else {
				EmbedBuilder error = new EmbedBuilder();
				error.setColor(0xff3923);
				error.setTitle("Error");
				error.setDescription("You need to have the Administrator role in order to execute that.");
				event.getChannel().sendMessage(error.build()).queue();
			}
		} else {
			EmbedBuilder error = new EmbedBuilder();
			error.setColor(0xff3923);
			error.setTitle("Error in the command");
			error.setDescription("Usage: " + Constants.commandPrefix + "update-settings [setting] <enable / disable>");
			event.getChannel().sendMessage(error.build()).queue();
		}
	}
}
