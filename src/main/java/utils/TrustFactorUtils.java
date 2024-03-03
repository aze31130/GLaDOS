package utils;

import accounts.Account;

public class TrustFactorUtils {
	/*
	 * @formatter:off
	 * Trust Factor
	 * Each user is represented in a graph where nodes are users and links relations between them.
	 * Every user has 2 scores:
	 * - one associated to the node (the score)
	 * - one associated to the links (the connections)
	 * 
	 * In order to get a high trust factor rank, both scores must be at a certain level.
	 * 
	 * Metrics for TrustFactor Algorithm:
	 * 
	 * - Time joined (1 week => 50 points)
	 * - Special roles owned (Admin, Mod, Transcendant, Master of the cards, Imperator, Tornado, IT Support => 50000 points)
	 * - Amount of message (1 message => 1 point)
	 * - Amount of reaction received (1 reaction => 1 point)
	 * 
	 * - Compute message frequency (frequency => coefficient of total trust factor)
	 */

	 /*
	  * Returns the node score of a given user
	  */
	public int getScore(Account a) {
		return 0;
	}

	/*
	 * Returns the connection score of a given user
	 */
	public int getConnectionScore(Account a) {
		return 0;
	}
}
