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

		return Duration.between(midnight.toInstant(), timestamp.toInstant()).toMillis();
	}

	public static int compareTo(Message a, Message b) {
		Long deltaA = computeDelta(a.getTimeCreated());
		Long deltaB = computeDelta(a.getTimeCreated());

		if (deltaA >= 0 && deltaB >= 0)
			return deltaA < deltaB ? 1 : -1;
		if (deltaA < 0 && deltaB < 0)
			return deltaB.compareTo(deltaA);
		return deltaA < deltaB ? 1 : -1;
	}
}
