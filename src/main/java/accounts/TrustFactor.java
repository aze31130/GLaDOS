package accounts;

/*
 * @formatter:off
 * Enumeration all TrustFactor.
 * 
 * TrustFactor Algorithm Parameters:
 * - User level
 * - User exp
 * - User joined time
 * - Amount of message
 * - Amount of reaction
 */
public enum TrustFactor {
	TRUSTED(4),
	HIGH_TRUST(3),
	NEUTRAL(2),
	LOW_TRUST(1),
	UNTRUSTED(0);

	public final int level;

	private TrustFactor(int level) {
		this.level = level;
	}
}
