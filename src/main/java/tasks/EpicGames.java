package tasks;

import java.time.LocalDateTime;
import commands.Trigger;
import glados.GLaDOS;
import net.dv8tion.jda.api.JDA;
import utils.Logging;

public class EpicGames implements Runnable, Logging {
	public JDA jda;

	public EpicGames(JDA jda) {
		this.jda = jda;
	}

	@Override
	public void run() {
		LOGGER.info("Executed EpicGameAnnoune at " + LocalDateTime.now().toString());
		GLaDOS glados = GLaDOS.getInstance();

		Trigger.callMessage(jda.getTextChannelById(glados.getChannelId("gamer").get()), "Gamer");
	}
}
