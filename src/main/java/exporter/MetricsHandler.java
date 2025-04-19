package exporter;

import java.io.IOException;
import java.io.OutputStream;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import accounts.Account;
import glados.GLaDOS;
import items.Rarity;
import utils.ItemUtils;
import utils.Logging;

public class MetricsHandler implements Logging, HttpHandler {
	@Override
	public void handle(HttpExchange exchange) {
		try {
			GLaDOS g = GLaDOS.getInstance();
			StringBuilder sb = new StringBuilder();

			for (Account a : g.accounts) {
				sb.append("glados_items{account=\"" + a.user.getName() + "\",statistic=\"inventory_value\"} " + a.getInventoryValue() + "\n");
				sb.append("glados_items{account=\"" + a.user.getName() + "\",statistic=\"inventory_size\"} " + a.inventory.size() + "\n");
				sb.append("glados_items{account=\"" + a.user.getName() + "\",statistic=\"money\"} " + a.money + "\n");
			}

			Rarity allRarity[] = {
					Rarity.COMMON,
					Rarity.UNUSUAL,
					Rarity.RARE,
					Rarity.EPIC,
					Rarity.LEGENDARY,
					Rarity.FABLED,
					Rarity.MYTHICAL,
					Rarity.GODLY,
					Rarity.UNIQUE,
			};

			for (Rarity r : allRarity)
				sb.append("glados_items_amount{rarity=\"" + r.toString().toLowerCase() + "\"} " + ItemUtils.getTotalAmountOfRarity(r) + "\n");

			String response = sb.toString();

			exchange.getResponseHeaders().set("Content-Type", "text/plain");
			exchange.sendResponseHeaders(200, response.length());

			OutputStream os = exchange.getResponseBody();
			os.write(response.getBytes());
			os.close();
		} catch (IOException e) {
			LOGGER.severe(e.getMessage());
			e.printStackTrace();
		}
	}
}
