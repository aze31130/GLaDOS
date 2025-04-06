package tasks;

import glados.GLaDOS;
import net.dv8tion.jda.api.JDA;
import utils.AccountUtils;
import utils.Logging;

public class Backup implements Runnable, Logging {
	public JDA jda;

	public Backup(JDA jda) {
		this.jda = jda;
	}

	@Override
	public void run() {
		LOGGER.info("Backing up all accounts...");
		GLaDOS g = GLaDOS.getInstance();

		AccountUtils.backup(g.accounts);

		LOGGER.info("Saved " + g.accounts.size() + " accounts !");
	}
}
