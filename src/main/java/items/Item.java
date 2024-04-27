package items;

import java.util.Map;
import org.json.JSONObject;
import utils.UpgradeRatesUtils;

public class Item implements Cloneable {
	public int id;
	public String name, lore;
	public ItemType type;
	public Rarity rarity;
	public Double dropChance;

	public int starForceLevel, starForceMaxLevel;

	public boolean claimable, untradable;

	public boolean broken;
	public Double quality;

	public int value;

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
			new UpgradeRatesUtils(10, 0, 65, 25),
			new UpgradeRatesUtils(10, 0, 60, 30),
			new UpgradeRatesUtils(10, 0, 55, 35),
			new UpgradeRatesUtils(5, 0, 55, 40),
	};

	private static final Map<Rarity, int[]> upgradePrices = Map.of(
			Rarity.UNUSUAL, new int[] {400, 650, 800},
			Rarity.RARE, new int[] {750, 1200, 1800, 2300, 2800},
			Rarity.EPIC, new int[] {1300, 1900, 2400, 3100, 4500, 5500, 6700, 8500, 10200, 12100},
			Rarity.LEGENDARY, new int[] {2100, 3150, 4250, 5600, 7500, 9500, 17100, 24000, 31500, 36000},
			Rarity.FABLED, new int[] {3500, 6000, 8500, 11000, 16000, 21000, 25000, 31000, 37000, 42000,
					48000, 52000, 60000, 65000, 70000},
			Rarity.MYTHICAL,
			new int[] {5000, 7500, 10000, 14000, 19000, 23000, 26500, 33000, 39000, 45000, 53000, 61000, 69000, 74000, 80000,
					84500, 91000, 98000, 100000, 100000},
			Rarity.GODLY,
			new int[] {8000, 11500, 13000, 15500, 21000, 26000, 28500, 36000, 40000, 47000, 55000, 62000, 71000, 76000, 85000,
					94000, 100000, 100000, 100000, 100000, 100000, 100000, 100000, 100000, 100000},
			Rarity.UNIQUE,
			new int[] {10000, 12000, 15000, 18000, 24000, 28000, 31500, 37000, 42000, 48000, 58000, 65000, 75000, 86000, 91000,
					100000, 100000, 100000, 100000, 100000, 100000, 100000, 100000, 100000, 100000, 100000, 100000, 100000,
					100000, 100000},
			Rarity.EVENT, new int[] {500, 700, 1200, 1800, 2300, 3100, 4500, 6100, 7900, 9000, 10000, 12000, 13000, 14000, 16000,
					19000, 25000, 30000, 37000, 44000, 50000});

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
	}

	public String getFQName() {
		StringBuilder sb = new StringBuilder();

		if (this.broken)
			sb.append("[BROKEN] ");

		sb.append("(" + String.format("%.2f%%", 100 * this.quality) + ") ");
		sb.append(this.name);

		if (this.starForceLevel > 0 || this.starForceMaxLevel > 0)
			sb.append(" ");

		for (int i = 0; i < this.starForceLevel; i++)
			sb.append("★");

		for (int i = this.starForceLevel; i < this.starForceMaxLevel; i++)
			sb.append("☆");

		return sb.toString();
	}

	public void makeLegit() {
		if (this.starForceLevel > this.starForceMaxLevel)
			this.starForceLevel = this.starForceMaxLevel;

		// Allow max quality to be reached
		if (this.quality > 0.9999)
			this.quality = 1.0;
		if (this.quality < 0.0001)
			this.quality = 0.0;

		// Ensure max quality cannot be exceeded
		if (this.quality > 1)
			this.quality = 1.0;
		if (this.quality < 0)
			this.quality = 0.0;

		// Ensure all event item are quality maxed
		if (this.rarity.equals(Rarity.EVENT))
			this.quality = 1.0;
	}

	public Double getQuality() {
		return this.quality;
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
		JSONObject item = new JSONObject()
				.put("id", this.id)
				.put("name", this.name)
				.put("starForceLevel", this.starForceLevel)
				.put("broken", this.broken)
				.put("quality", this.quality);

		return item;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
