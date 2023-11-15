package utils;

import java.time.Duration;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import net.dv8tion.jda.api.entities.Message;

public class TimeUtils {
	/*
	 * This function computes the delta between midnight and the given timestamp. If the message has
	 * been send before midnight, returns a negative delta
	 */
	public static long computeDelta(OffsetDateTime timestamp) {
		LocalDate today = LocalDate.now();
		OffsetDateTime midnight = today.atStartOfDay().atOffset(ZoneOffset.UTC);

		return Duration.between(midnight.toInstant(), timestamp.plusHours(1).toInstant())
				.toMillis();
	}
}
