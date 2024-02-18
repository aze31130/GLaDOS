package items;

/*
 * @formatter:off
 */
public enum ItemType {
	NECKLACE(":prayer_beads:"),
	BRACELET(":prayer_beads:"),
	HELMET(":military_helmet:"),
	CHESTPLATE(":shirt:"),
	LEGGINGS(":jeans:"),
	BOOTS(":boot:"),
	GLOVES(":gloves:"),
	ITEM(""),
	MEDAL(":medal:"),
	RING(":ring:"),
	POTION(":tropical_drink:"),
	WAND(":magic_wand:"),
	SWORD(":dagger:"),
	NINJA(":knife:"),
	SPEAR(""),
	GUN(":gun:"),
	SCROLL(":scroll:");

	public final String emote;

	private ItemType(String emote) {
		this.emote = emote;
	}
}
