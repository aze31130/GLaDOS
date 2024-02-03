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

	public boolean broken;
	public Double quality;

	public int value;

	public String url;

	/*
	 * Specials bonuses that can be rerolled. This includes flat raw stats alongside % based stats.
	 */
	// public String potential;
	// public List<PairUtils<String, Integer>> Enchants;

	public Item(int id, String name, ItemType type, String lore, Rarity rarity, Double dropChance, int starForceLevel,
			int starForceMaxLevel, boolean claimable, boolean untradable, boolean broken, Double quality, int value) {
		this.id = id;
		this.name = name;
		this.type = type;
		this.lore = lore;
		this.rarity = rarity;
		this.dropChance = dropChance;
		this.starForceLevel = starForceLevel;
		this.starForceMaxLevel = starForceMaxLevel;
		this.claimable = claimable;
		this.untradable = untradable;
		this.broken = broken;
		this.quality = quality;
		this.value = value;
		this.url = "https://" + id;
	}

	public String getFQName() {
		StringBuilder sb = new StringBuilder();

		sb.append("(" + String.format("%.2f%%", 100 * this.quality) + ") ");
		sb.append(this.name);

		if (this.starForceMaxLevel > 0)
			sb.append(" ");

		for (int i = 0; i < this.starForceLevel; i++)
			sb.append("★");

		for (int i = this.starForceLevel; i < this.starForceMaxLevel; i++)
			sb.append("☆");

		return sb.toString();
	}

	public int getStarForceCost() {
		return 0;
	}

	public int getStarForceSuccessChance() {
		return 0;
	}

	public int getStarForceFailChance() {
		return 0;
	}

	public int getStarForceDestroyChance() {
		return 0;
	}

	public JSONObject toJson() {
		JSONObject item = new JSONObject();

		item.put("id", this.id);
		item.put("name", this.name);
		item.put("starForceLevel", this.starForceLevel);
		item.put("broken", this.broken);
		item.put("quality", this.quality);

		return item;
	}
}
