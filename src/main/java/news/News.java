package news;

import java.util.Optional;

public record News(String title, String description, String url, String source, String pubDate) {
	/*
	 * This constructor ensures that Discord's embeds limitations are never reached
	 */
	public News(Optional<String> title, Optional<String> description, Optional<String> url, String source, Optional<String> pubDate) {
		this(
				// Ensure title and description length is short enough to be read quickly
				title.map(t -> t.length() > 250 ? t.substring(0, 250) + "..." : t).orElse("?"),
				description.map(d -> d.length() > 500 ? d.substring(0, 500) + "..." : d).orElse("?"),
				url.orElse("?"),
				source,
				pubDate.orElse("?"));
	}
}
