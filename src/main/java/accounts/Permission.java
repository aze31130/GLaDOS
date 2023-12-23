package accounts;

/*
 * @formatter:off
 * Enumeration of every permission level
 */
public enum Permission {
	OWNER(3),
	ADMINISTRATOR(2),
	MODERATOR(1),
	NONE(0);

	public final int level;

	private Permission(int level) {
		this.level = level;
	}
}
