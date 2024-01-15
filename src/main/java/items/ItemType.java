package items;

/*
 * @formatter:off
 */
public enum ItemType {
	NECKLACE(""),
	BRACELET(""),
	HELMET(""),
	CHESTPLATE(""),
	LEGGINGS(""),
	BOOTS(""),
	ITEM(""),
	RING(""),
	POTION(""),
	WAND(""),
	SWORD(""),
	SPEAR(""),
	SCROLL(":scroll:");

	public final String emote;

	private ItemType(String emote) {
		this.emote = emote;
	}
}
