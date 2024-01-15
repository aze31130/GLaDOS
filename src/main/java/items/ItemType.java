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
	RING(":ring:"),
	POTION(":tropical_drink:"),
	WAND(":magic_wand:"),
	SWORD(":dagger:"),
	SPEAR(""),
	SCROLL(":scroll:");

	public final String emote;

	private ItemType(String emote) {
		this.emote = emote;
	}
}
