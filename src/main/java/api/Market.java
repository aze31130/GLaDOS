package api;

import io.javalin.Javalin;

public class Market {
	public void register(Javalin api) {
		api.get("/market", ctx -> {
			ctx.result("TODO Market command");
		});
	}
}
