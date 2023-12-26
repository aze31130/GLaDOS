package items;

import org.json.JSONObject;
import accounts.Account;

public abstract class Item {
	public int id;
	public String name;
	public ItemType type;
	public String lore;
	public Rarity rarity;
	public int dropChance;

	public int starForceLevel;
	public int starForceMaxLevel;

	public boolean claimable;
	public boolean untradable;

	public int soldValue;

	public String url;

	/*
	 * Specials bonuses that can be rerolled. This includes flat raw stats alongside % based stats.
	 */
	// public String potential;
	// public List<PairUtils<String, Integer>> Enchants;
	// public int starForceCost;

	public Item(int id, String name, String lore, Rarity rarity, int dropChance,
			String url) {
		this.id = id;
		this.name = name;
		this.lore = lore;
		this.rarity = rarity;
		this.dropChance = dropChance;
		this.url = url;
	}

	public JSONObject toJson() {
		JSONObject item = new JSONObject();

		item.put("id", this.id);
		item.put("starForceLevel", this.starForceLevel);

		return item;
	}

	public abstract boolean conditionnalDrop(Account dropper);
}
