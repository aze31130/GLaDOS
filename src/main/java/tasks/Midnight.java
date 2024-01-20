package tasks;

import java.time.LocalDateTime;
import accounts.Account;
import commands.Trigger;
import glados.GLaDOS;
import net.dv8tion.jda.api.JDA;
import utils.Logging;

/*
 * @formatter:off
 * Each day at midnight, the following tasks executes:
 * - Random Quote
 * - Midnight ranking
 * - Reset drop capability on each account
 */
public class Midnight implements Runnable, Logging {
	public JDA jda;

	public Midnight(JDA jda) {
		this.jda = jda;
	}

	@Override
	public void run() {
		LOGGER.info("Executed Random Quote at " + LocalDateTime.now().toString());
		GLaDOS glados = GLaDOS.getInstance();

		Trigger.callMessage(jda.getTextChannelById(glados.channelGeneral), "Midnight");
		
		// Resets the ability for all accounts to drop
		for (Account a : glados.accounts)
			a.canDrop = true;

		try {
			Thread.sleep(15000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		Trigger.midnightRank(jda.getTextChannelById(glados.channelGeneral));
	}
}
