package tasks;

import java.time.LocalDateTime;
import commands.Trigger;
import glados.GLaDOS;
import net.dv8tion.jda.api.JDA;

public class EpicGames implements Runnable {
	public JDA jda;

	public EpicGames(JDA jda) {
		this.jda = jda;
	}

	@Override
	public void run() {
		System.out.println("Executed EpicGameAnnoune at " + LocalDateTime.now().toString());
		GLaDOS glados = GLaDOS.getInstance();

		Trigger.callMessage(jda.getTextChannelById(glados.channelGamer), "Gamer");
	}
}
