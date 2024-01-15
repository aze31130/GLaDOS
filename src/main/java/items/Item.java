package items;

import org.json.JSONObject;

public class Item {
	public int id;
	public String name;
	public ItemType type;
	public String lore;
	public Rarity rarity;
	public Double dropChance;

	public int starForceLevel;
	public int starForceMaxLevel;

	public boolean claimable;
	public boolean untradable;

	public int value;

	public String url;

	/*
	 * Specials bonuses that can be rerolled. This includes flat raw stats alongside % based stats.
	 */
	// public String potential;
	// public List<PairUtils<String, Integer>> Enchants;
	// public int starForceCost;

	public Item(int id, String name, ItemType type, String lore, Rarity rarity, Double dropChance,
			int starForceMaxLevel, boolean claimable, boolean untradable, int value) {
		this.id = id;
		this.name = name;
		this.type = type;
		this.lore = lore;
		this.rarity = rarity;
		this.dropChance = dropChance;
		this.starForceMaxLevel = starForceMaxLevel;
		this.claimable = claimable;
		this.untradable = untradable;
		this.value = value;
		this.url = "https://" + id;
	}

	public JSONObject toJson() {
		JSONObject item = new JSONObject();

		item.put("id", this.id);
		item.put("name", this.name);
		item.put("starForceLevel", this.starForceLevel);

		return item;
	}
}
