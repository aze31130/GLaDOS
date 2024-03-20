package tasks;

import java.util.Random;
import glados.GLaDOS;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Activity;

/*
 * Randomly updates GLaDOS's status.
 */
public class Status implements Runnable {
	private JDA jda;

	public Status(JDA jda) {
		this.jda = jda;
	}

	@Override
	public void run() {
		int rng = new Random().nextInt(100);

		if (rng > 30)
			return;

		GLaDOS glados = GLaDOS.getInstance();

		String randomActivity = glados.activities.getString(new Random().nextInt(glados.randomQuote.length()));

		jda.getPresence().setActivity(Activity.playing(randomActivity));
	}
}
