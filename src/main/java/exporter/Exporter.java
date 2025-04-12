package exporter;

import utils.Logging;
import java.io.IOException;
import java.net.InetSocketAddress;
import com.sun.net.httpserver.*;

public class Exporter implements Logging {
	public static void httpServer() {
		try {
			HttpServer server = HttpServer.create(new InetSocketAddress(9100), 0);
			server.createContext("/metrics", new MetricsHandler());
			server.setExecutor(null);
			server.start();
			LOGGER.info("Metrics Exporter running on port 9100");
		} catch (IOException e) {
			LOGGER.severe(e.getMessage());
			e.printStackTrace();
		}
	}
}
