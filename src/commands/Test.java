package commands;

import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;

import constants.Constants.Channels;
import constants.Constants.Permissions;
import utils.BuildEmbed;
import utils.Counter;
import utils.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageReaction;
import net.dv8tion.jda.api.entities.TextChannel;

public class Test extends Command {
	
	public Test(String name, String alias, String description, String example,
			Boolean hidden, int permissionLevel) {
		super(name, alias, description, example, hidden, permissionLevel);
	}
	
	@Override
	public void execute(Argument args) {
		if(Permission.permissionLevel(args.account, args.member, Permissions.ADMIN.level)) {
			args.channel.sendMessage("Generating server's message history").queue();
			
			Guild server = args.channel.getJDA().getGuilds().get(1);
			
			
			for(TextChannel tc : server.getTextChannels()) {
				args.channel.sendMessage("Downloading channel " + tc.getAsMention()).queue();
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
					args.channel.sendMessage(e.toString()).queue();
				}
			}
			args.channel.sendMessage("Server message history has been created").queue();
		} else {
			args.channel.sendMessage(BuildEmbed.errorPermissionEmbed(1).build()).queue();
		}
	}
}
