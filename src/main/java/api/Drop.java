package api;

import io.javalin.Javalin;

public class Drop {
	public void register(Javalin api) {
		api.get("/drop", ctx -> {
			ctx.result("TODO Drop command");
		});
	}
}
