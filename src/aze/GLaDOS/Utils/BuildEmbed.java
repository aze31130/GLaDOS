package aze.GLaDOS.Utils;

import java.awt.Color;
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
	
	public static EmbedBuilder errorEmbed(String description){
		EmbedBuilder embed = new EmbedBuilder();
		embed.setTitle("Error");
		embed.setDescription(description);
		embed.setColor(Color.RED);
		return embed;
	}
}
