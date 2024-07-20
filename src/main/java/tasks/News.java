package tasks;

import java.io.IOException;
import java.util.List;
import org.json.JSONObject;
import com.apptasticsoftware.rssreader.Item;
import com.apptasticsoftware.rssreader.RssReader;
import glados.GLaDOS;
import net.dv8tion.jda.api.JDA;
import utils.BuildEmbed;
import utils.Logging;

public class News implements Runnable, Logging {
	public JDA jda;

	public News(JDA jda) {
		this.jda = jda;
	}

	@Override
	public void run() {
		GLaDOS glados = GLaDOS.getInstance();

		// Check if news are new
		for (int i = 0; i < glados.rssFeeds.length(); i++) {
			JSONObject feedObj = glados.rssFeeds.getJSONObject(i);
			String feedName = feedObj.getString("name");
			String feedLink = feedObj.getString("link");

			try {
				List<Item> items = new RssReader().read(feedLink).toList();

				// Fill rss cache if first launch and do not post updates
				if (glados.rssNews.size() == 0) {
					for (Item it : items)
						glados.rssNews.add(it.hashCode());
					return;
				}

				// Post only new
				for (Item it : items) {
					int hash = it.hashCode();
					if (!glados.rssNews.contains(hash)) {
						jda.getTextChannelById(glados.channelBotSnapshot)
								.sendMessageEmbeds(BuildEmbed.rssNewsEmbed(it, feedName).build())
								.queue();
						glados.rssNews.add(hash);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
