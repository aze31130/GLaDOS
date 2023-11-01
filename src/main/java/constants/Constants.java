package constants;

public class Constants {
	public static boolean logMessage = false;
	public static boolean logConnexions = false;
	public static Boolean FreeGameAnnonce = false;
	public static Boolean LockdownDayAnnonce = false;
	public static Boolean CheckPrivateMessages = false;
	public static Boolean EnableLogging = false;
	public static Boolean EpitaAnnonce = false;
	public static final String OwnerId = "327775924403830784";
	public static final String GuildId = "676731153444765706";

	public static final String Config_File = "settings.json";

	public static final int MAX_RETRIEVE_MESSAGES = 100000;
	
	public static final String RoleChannelName = "role";
	
	public enum Channels {
		GAMER("702846310436438036"),
		NSFW("699751218179997816"),
		ROLE("753590868270776331"),
		BOT_SNAPSHOT("724568288356597780"),
		GENERAL("676731153444765709"),
		EPITA("689491329402667064"),
		MEME("709396567894786060");
		
		public final String id;
		
		private Channels(String id) {
			this.id = id;
		}
	}
	
	//Enumeration of every role id
	public enum Roles {
		ADMIN("677262572860342333"),
		MOD("678538602338189314"),
		MEMBER("753758106579632188"),
		ARTISTIC("753757926317096960"),
		INTERNATIONAL("771686251647467551"),
		GAMER("753757719017685073"),
		BROADCAST_MESSENGER("1106324476242690111"),
		DEVELOPER("830156830374232064"),
		NSFW("699751539832651836"),
		GLOBAL("779398365523607573"),
		ING1("899330612454899732");
		
		public final String id;
		
		private Roles(String id) {
			this.id = id;
		}
	}
	
	
	/* Enumeration of every permission level
	 * +-------+-------+-----+------+
	 * | OWNER | ADMIN | MOD | NONE |
	 * +-------+-------+-----+------+
	 * |   3   |   2   |  1  |   0  |
	 * +-------+-------+-----+------+
	 */
	public enum Permissions {
		OWNER(3),
		ADMIN(2),
		MOD(1),
		NONE(0);
		
		public final int level;
		
		private Permissions(int level) {
			this.level = level;
		}
	}
}
