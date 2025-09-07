package api;

import io.javalin.Javalin;

public class Buy {
	public void register(Javalin api) {
		api.get("/buy", ctx -> {
			ctx.result("TODO Buy command");
		});
	}
}
