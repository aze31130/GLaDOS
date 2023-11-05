package accounts;

/*
 * Enumeration of every permission level
 */
public enum Permissions {
	OWNER(3), ADMINISTRATOR(2), MODERATOR(1), NONE(0);

	public final int level;

	private Permissions(int level) {
		this.level = level;
	}
}
