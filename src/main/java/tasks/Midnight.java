package tasks;

import java.time.LocalDateTime;
import commands.Call;
import glados.GLaDOS;
import net.dv8tion.jda.api.JDA;

/*
 * @formatter:off
 * Each day at midnight, the following tasks executes:
 * - Random Quote
 * - Midnight ranking
 * - Reset drop capability on each account
 */
public class Midnight implements Runnable {
	public JDA jda;

	public Midnight(JDA jda) {
		this.jda = jda;
	}

	@Override
	public void run() {
		System.out.println("Executed Random Quote at " + LocalDateTime.now().toString());
		GLaDOS glados = GLaDOS.getInstance();

		Call.callMessage(jda.getTextChannelById(glados.channelGeneral), "Midnight");

		//TOOD Reset drop capability

		try {
			Thread.sleep(15000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		Call.midnightRank(jda.getTextChannelById(glados.channelGeneral));
	}
}
