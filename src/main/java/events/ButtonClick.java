package events;

import java.util.ArrayList;
import java.util.HashMap;
import glados.GLaDOS;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import utils.BuildEmbed;

public class ButtonClick extends ListenerAdapter {
	public void onButtonInteraction(ButtonInteractionEvent event) {
		GLaDOS glados = GLaDOS.getInstance();
		glados.requestsAmount++;

		String trigger = event.getComponentId();

		HashMap<String, String> dictionary = new HashMap<>();
		dictionary.put("Broadcast", glados.roleBroadcastMessenger);
		dictionary.put("Gamer", glados.roleGamer);
		dictionary.put("Member", glados.roleMember);
		dictionary.put("Artistic", glados.roleArtistic);
		dictionary.put("International", glados.roleInternational);
		dictionary.put("Developer", glados.roleDeveloper);
		dictionary.put("NSFW", glados.roleNsfw);
		dictionary.put(glados.roleBroadcastMessenger, glados.roleBroadcastMessenger);
		dictionary.put(glados.roleGamer, glados.roleGamer);
		dictionary.put(glados.roleMember, glados.roleMember);
		dictionary.put(glados.roleArtistic, glados.roleArtistic);
		dictionary.put(glados.roleInternational, glados.roleInternational);
		dictionary.put(glados.roleDeveloper, glados.roleDeveloper);
		dictionary.put(glados.roleNsfw, glados.roleNsfw);

		// Check if action is add role
		if (trigger.startsWith("+")) {
			trigger = trigger.replace("+", "");
			if (!dictionary.containsKey(trigger)) {
				event.reply("You cannot get this role by doing that !").setEphemeral(true)
						.setSuppressedNotifications(true).queue();
				return;
			}

			trigger.replace("+", "");
			event.getGuild().addRoleToMember(event.getMember().getUser(),
					event.getGuild().getRoleById(dictionary.get(trigger))).queue();

			event.reply("Successfully added role <@&" + dictionary.get(trigger) + "> !")
					.setEphemeral(true).setSuppressedNotifications(true).queue();
			return;
		}

		// Check if action is remove role
		if (trigger.startsWith("-")) {
			trigger = trigger.replace("-", "");
			if (!dictionary.containsKey(trigger)) {
				event.reply("You cannot get rid of this role by doing that !").setEphemeral(true)
						.setSuppressedNotifications(true).queue();
				return;
			}

			event.getGuild().removeRoleFromMember(event.getMember().getUser(),
					event.getGuild().getRoleById(dictionary.get(trigger))).queue();

			event.reply("Successfully removed role <@&" + dictionary.get(trigger) + "> !")
					.setEphemeral(true).setSuppressedNotifications(true).queue();
			return;
		}

		// Check if action is question
		if (trigger.startsWith("?")) {
			if (glados.goodAnswer.equals(trigger)) {
				event.replyEmbeds(BuildEmbed
						.successEmbed("Good answer " + event.getButton().getLabel() + " !").build())
						.queue();
				// Removes buttons when good answer is triggered
				event.getMessage().editMessageComponents(new ArrayList<>()).queue();
				glados.goodAnswer = "";
			} else {
				event.replyEmbeds(BuildEmbed
						.errorEmbed("Wrong answer " + event.getButton().getLabel() + " !").build())
						.queue();
			}
			return;
		}

		// Else, check if the trigger is the good anwser
		event.replyEmbeds(BuildEmbed
				.errorEmbed("Unknown signal " + event.getButton().getLabel() + " !").build())
				.queue();
		event.getChannel().sendMessage(event.getMessage().getJumpUrl()).queue();
	}
}
