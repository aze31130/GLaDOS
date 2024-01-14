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

	public int soldValue;

	public String url;

	/*
	 * Specials bonuses that can be rerolled. This includes flat raw stats alongside % based stats.
	 */
	// public String potential;
	// public List<PairUtils<String, Integer>> Enchants;
	// public int starForceCost;

	public Item(int id, String name, String lore, Rarity rarity, Double dropChance) {
		this.id = id;
		this.name = name;
		this.lore = lore;
		this.rarity = rarity;
		this.dropChance = dropChance;
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
