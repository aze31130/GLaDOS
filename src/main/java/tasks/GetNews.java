package tasks;

import java.util.List;
import org.json.JSONObject;
import com.apptasticsoftware.rssreader.Item;
import com.apptasticsoftware.rssreader.RssReader;
import glados.GLaDOS;
import net.dv8tion.jda.api.JDA;
import news.News;
import utils.BuildEmbed;
import utils.Logging;

public class GetNews implements Runnable, Logging {
	public JDA jda;

	public GetNews(JDA jda) {
		this.jda = jda;

		GLaDOS glados = GLaDOS.getInstance();

		// Fill rss cache at first launch
		for (int i = 0; i < glados.rssFeeds.length(); i++) {
			JSONObject feedObj = glados.rssFeeds.getJSONObject(i);
			String feedLink = feedObj.getString("link");
			LOGGER.info("Reading " + feedLink);

			try {
				List<Item> items = new RssReader().read(feedLink).toList();
				for (Item it : items) {
					int hash = it.getLink().get().hashCode();
					glados.rssNews.add(hash);
				}
			} catch (Exception e) {
				LOGGER.severe(e.toString() + " for URL " + feedLink);
				jda.getTextChannelById(glados.getChannelId("system").get()).sendMessage(e.toString() + " for URL " + feedLink).queue();
			}
		}
	}

	@Override
	public void run() {
		GLaDOS glados = GLaDOS.getInstance();
		LOGGER.info("Calling news method with " + glados.rssFeeds.length() + " news.");

		// Check if news are new
		for (int i = 0; i < glados.rssFeeds.length(); i++) {
			JSONObject feedObj = glados.rssFeeds.getJSONObject(i);
			String feedName = feedObj.getString("name");
			String feedLink = feedObj.getString("link");

			try {
				List<Item> items = new RssReader().read(feedLink).toList();

				// Post only new feed
				for (Item it : items) {
					int hash = it.getLink().get().hashCode();
					if (!glados.rssNews.contains(hash)) {

						News n = new News(it.getTitle(), it.getDescription(), it.getLink(), feedName, it.getPubDate());

						jda.getTextChannelById(glados.getChannelId("news").get()).sendMessageEmbeds(BuildEmbed.rssNewsEmbed(n).build()).queue();
						glados.rssNews.add(hash);
					}
				}
			} catch (Exception e) {
				LOGGER.severe(e.toString() + " for URL " + feedLink);
				jda.getTextChannelById(glados.getChannelId("system").get()).sendMessage(e.toString() + " for URL " + feedLink).queue();
			}
		}
	}
}
