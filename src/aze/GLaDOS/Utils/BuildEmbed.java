package aze.GLaDOS.utils;

import java.awt.Color;
import java.util.Random;

import net.dv8tion.jda.api.EmbedBuilder;

public class BuildEmbed {
	//Embed for permission level error
	public static EmbedBuilder errorPermissionEmbed(int requiredPermissionLevel) {
		String roleName = "";
		switch(requiredPermissionLevel) {
			case 1:
				roleName = "Moderator";
				break;
			case 2:
				roleName = "Administrator";
				break;
			case 3:
				roleName = "Owner";
				break;
			default:
				roleName = "None";
				break;
		
		}
		EmbedBuilder embed = new EmbedBuilder();
		embed.setColor(Color.RED);
		embed.setTitle("Error");
		embed.setDescription("You need to have the " + roleName + " role in order to execute that.");
		return embed;
	}
	
	public static EmbedBuilder scoreEmbed(long score) {
		EmbedBuilder embed = new EmbedBuilder();
		embed.setColor(Color.GREEN);
		embed.setTitle("Tycoon Score");
		embed.setDescription(score + " :ice_cube:");
		return embed;
	}
	
	public static EmbedBuilder joinLeaveEmbed(String mention, Boolean joining) {
		Random rng = new Random();
		int randomNumber = rng.nextInt(5);
		String message;
		if(joining) {
			String[] messages = {
					"[member] is here ! Now it's time to raise our hands !",
					"[member] just arrived ! Hide the vodka quickly !",
					"Big [member] is here !",
					"[member] joined the game.",
					"[member] just did a barrel roll"
			};
			message = messages[randomNumber];
		} else {
			String[] messages = {
					"[member] may be gone, but he still lives on in our hearts.",
					"Oh no, we just loose [member].",
					"In this dark day, [member] decided to leave this place.",
					"[member] picked a chance card: Go to jail, do not pass go, do not collect 200$.",
					"[member] left. The party is now over."
			};
			message = messages[randomNumber];
		}
		EmbedBuilder embed = new EmbedBuilder();
		embed.setColor(0x66d8ff);
		embed.setDescription(message.replace("[member]", mention));
		return embed;
	}
	
	public static EmbedBuilder errorEmbed(String description){
		EmbedBuilder embed = new EmbedBuilder();
		embed.setTitle("Error");
		embed.setDescription(description);
		embed.setColor(Color.RED);
		return embed;
	}
}
