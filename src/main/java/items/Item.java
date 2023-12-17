package items;

import accounts.Account;

public abstract class Item {
	public String name;
	public String inGameName;
	public String lore;
	public Rarity rarity;
	public int dropChance;

	public int starForceLevel;

	public String url;

	/*
	 * Specials bonuses that can be rerolled. This includes flat raw stats alongside % based stats.
	 */
	// public String potential;
	// public List<PairUtils<String, Integer>> Enchants;
	// public int starForceCost;

	public Item(String name, String inGameName, String lore, Rarity rarity, int dropChance,
			String url) {
		this.name = name;
		this.inGameName = inGameName;
		this.lore = lore;
		this.rarity = rarity;
		this.dropChance = dropChance;
		this.url = url;
	}

	public abstract boolean conditionnalDrop(Account dropper);
}
