package api;

import org.json.JSONArray;
import glados.GLaDOS;
import io.javalin.Javalin;
import items.Item;

public class Market {
	public void register(Javalin api) {
		api.get("/market", ctx -> {
			GLaDOS g = GLaDOS.getInstance();
			JSONArray result = new JSONArray();

			for (Item i : g.market)
				result.put(i.toJson());

			ctx.json(result.toString());
		});
	}
}
