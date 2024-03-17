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
	BOW(":bow_and_arrow:"),
	GLOVES(":gloves:"),
	ITEM(":briefcase:"),
	MEDAL(":medal:"),
	RING(":ring:"),
	POTION(":tropical_drink:"),
	WAND(":magic_wand:"),
	SWORD(":dagger:"),
	NINJA(":knife:"),
	SPEAR(":french_bread:"),
	GUN(":gun:"),
	SCROLL(":scroll:"),
	LAUNCHER(":rocket:"),
	ROBOT(":robot:"),
	CAKE(":cake:"),
	BOOK(":book:"),
	SHIELD(":shield:"),
	FAN(":folding_hand_fan:");

	public final String emote;

	private ItemType(String emote) {
		this.emote = emote;
	}
}
