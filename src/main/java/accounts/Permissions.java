package accounts;

/*
 * Enumeration of every permission level +-------+-------+-----+------+ | OWNER | ADMIN | MOD | NONE
 * | +-------+-------+-----+------+ | 3 | 2 | 1 | 0 | +-------+-------+-----+------+
 */
public enum Permissions {
	OWNER(3), ADMIN(2), MOD(1), NONE(0);

	public final int level;

	private Permissions(int level) {
		this.level = level;
	}
}
