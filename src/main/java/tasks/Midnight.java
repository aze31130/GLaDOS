package tasks;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.json.JSONObject;
import accounts.Account;
import commands.Trigger;
import glados.GLaDOS;
import net.dv8tion.jda.api.JDA;
import utils.ItemUtils;
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
		
		// Resets the ability for all accounts to drop
		for (Account a : glados.accounts)
			a.canDrop = true;

		Trigger.callMessage(jda.getTextChannelById(glados.getChannelId("general").get()), "Midnight");

		ItemUtils.resetMarket();

		try {
			Thread.sleep(12000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		Trigger.midnightRank(jda.getTextChannelById(glados.getChannelId("general").get()));

		// Check for birthday events
		LocalDate today = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		for (int i = 0; i < glados.birthdays.length(); i++) {
			JSONObject person = glados.birthdays.getJSONObject(i);

			LocalDate birthdate = LocalDate.parse(person.getString("date"), formatter);

			if (birthdate.getMonthValue() == today.getMonthValue() && birthdate.getDayOfMonth() == today.getDayOfMonth()) {
				jda.getTextChannelById(glados.getChannelId("general").get()).sendMessage("Happy birthday <@" + person.getString("id") + "> ! May this special day full of treasures and wonders ! :birthday::cake::gift::balloon::partying_face::tada::confetti_ball:").queue();
			}
		}
	}
}
