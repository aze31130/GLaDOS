package items;

import java.util.Map;
import org.json.JSONObject;
import utils.UpgradeRatesUtils;

public class Item implements Cloneable {
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

	private static final UpgradeRatesUtils rates[] = {
			// 0 -> 5 stars
			new UpgradeRatesUtils(95, 5, 0, 0),
			new UpgradeRatesUtils(90, 10, 0, 0),
			new UpgradeRatesUtils(85, 15, 0, 0),
			new UpgradeRatesUtils(80, 20, 0, 0),
			new UpgradeRatesUtils(75, 25, 0, 0),

			// 5 -> 10 stars
			new UpgradeRatesUtils(70, 25, 5, 0),
			new UpgradeRatesUtils(65, 25, 10, 0),
			new UpgradeRatesUtils(60, 25, 15, 0),
			new UpgradeRatesUtils(55, 25, 20, 0),
			new UpgradeRatesUtils(50, 25, 25, 0),

			// 10 -> 15 stars
			new UpgradeRatesUtils(45, 55, 0, 0),
			new UpgradeRatesUtils(40, 0, 60, 0),
			new UpgradeRatesUtils(35, 0, 65, 0),
			new UpgradeRatesUtils(30, 0, 70, 0),
			new UpgradeRatesUtils(30, 0, 70, 0),

			// 15 -> 20 stars
			new UpgradeRatesUtils(30, 0, 69, 1),
			new UpgradeRatesUtils(30, 0, 68, 2),
			new UpgradeRatesUtils(30, 0, 67, 3),
			new UpgradeRatesUtils(30, 0, 66, 4),
			new UpgradeRatesUtils(30, 0, 65, 5),

			// 20 -> 25 stars
			new UpgradeRatesUtils(20, 70, 0, 10),
			new UpgradeRatesUtils(20, 0, 68, 12),
			new UpgradeRatesUtils(20, 0, 65, 15),
			new UpgradeRatesUtils(20, 0, 63, 17),
			new UpgradeRatesUtils(20, 0, 62, 18),

			// 25 -> 30 stars
			new UpgradeRatesUtils(10, 0, 70, 20),
			new UpgradeRatesUtils(5, 0, 70, 25),
			new UpgradeRatesUtils(3, 0, 67, 30),
			new UpgradeRatesUtils(2, 0, 63, 35),
			new UpgradeRatesUtils(1, 0, 59, 40),
	};

	private static final Map<Rarity, int[]> upgradePrices = Map.of(
			Rarity.UNUSUAL, new int[] {400, 650, 800},
			Rarity.RARE, new int[] {750, 1200, 1800, 2300, 2850},
			Rarity.EPIC, new int[] {1300, 1900, 2400, 3100, 4500, 5500, 6700, 8500, 10200, 12100},
			Rarity.LEGENDARY, new int[] {2100, 3150, 4250, 5600, 7500, 9500, 17100, 25000, 32000, 36000},
			Rarity.FABLED, new int[] {5000, 9500, 13500, 16000, 21000, 29000, 32000, 39000, 50000, 63000,
					75000, 88000, 98000, 115000, 130000},
			Rarity.MYTHICAL, new int[] {12000, 18000, 25000, 46000, 65000},
			Rarity.GODLY, new int[] {20000, 31000, 45000},
			Rarity.UNIQUE, new int[] {30000, 50000, 75000, 95000, 135000},
			Rarity.EVENT, new int[] {500, 700, 1200, 1800, 2300, 3100, 4500, 6100, 7900, 9000, 10000, 12000, 13000, 14000, 16000,
					19000, 25000, 30000, 37000, 44000, 50000});

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

		if (this.broken)
			sb.append("[BROKEN] ");

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

	public boolean isMaxed() {
		return this.starForceLevel == this.starForceMaxLevel;
	}

	public int getStarForceCost() {
		/*
		 * Warning, if item is at max level, return 0 as the upgrade is not possible anymore
		 */
		if (isMaxed())
			return 0;
		return Item.upgradePrices.get(this.rarity)[this.starForceLevel];
	}

	public int getStarForceSuccessChance() {
		/*
		 * Warning, if item is at max level, return 0 as the upgrade is not possible anymore
		 */
		if (isMaxed())
			return 0;
		return Item.rates[this.starForceLevel].success();
	}

	public int getStarForceKeepChance() {
		/*
		 * Warning, if item is at max level, return 0 as the upgrade is not possible anymore
		 */
		if (isMaxed())
			return 0;
		return Item.rates[this.starForceLevel].keep();
	}

	public int getStarForceFailChance() {
		/*
		 * Warning, if item is at max level, return 0 as the upgrade is not possible anymore
		 */
		if (isMaxed())
			return 0;
		return Item.rates[this.starForceLevel].down();
	}

	public int getStarForceDestroyChance() {
		/*
		 * Warning, if item is at max level, return 0 as the upgrade is not possible anymore
		 */
		if (isMaxed())
			return 0;
		return Item.rates[this.starForceLevel].boom();
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

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
