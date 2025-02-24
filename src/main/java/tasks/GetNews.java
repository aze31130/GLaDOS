package tasks;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import org.json.JSONObject;
import com.apptasticsoftware.rssreader.Item;
import com.apptasticsoftware.rssreader.RssReader;
import glados.GLaDOS;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.utils.FileUpload;
import news.News;
import utils.BuildEmbed;
import utils.HttpUtils;
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
			}
		}
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

				// Post only new feed
				for (Item it : items) {
					int hash = it.getLink().get().hashCode();
					if (!glados.rssNews.contains(hash)) {

						News n = new News(it.getTitle(), it.getDescription(), it.getLink(), feedName, it.getPubDate());
						glados.rssNewsSumUp.add(n);

						jda.getTextChannelById(glados.getRoleId("news").get()).sendMessageEmbeds(BuildEmbed.rssNewsEmbed(n).build()).queue();
						glados.rssNews.add(hash);
					}
				}
			} catch (Exception e) {
				LOGGER.severe(e.toString() + " for URL " + feedLink);
			}
		}

		LocalDateTime now = LocalDateTime.now();
		if (now.getHour() == 7) {
			// Call LLM to sumup text
			String llmAnwser = HttpUtils.sendLLMQuery(glados.rssNewsSumUp);

			// If anwser is too long, write it in a text file
			if (llmAnwser.length() >= 2000) {
				InputStream inputStream = new ByteArrayInputStream(llmAnwser.getBytes());
				jda.getTextChannelById(glados.getRoleId("hacker").get())
						.sendMessage(":bookmark: Cyber-News Sumup")
						.addFiles(FileUpload.fromData(inputStream, "inference.txt")).queue();
			} else {
				jda.getTextChannelById(glados.getRoleId("hacker").get()).sendMessage(llmAnwser).queue();
			}

			glados.rssNewsSumUp.clear();
		}
	}
}
