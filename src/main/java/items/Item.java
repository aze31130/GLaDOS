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

	private int value;

	/*
	 * Table used to store % of success, failure, critical failure and item explosion.
	 */
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
			new UpgradeRatesUtils(5, 0, 55, 40)
	};

	/*
	 * This table holds the % of increase value for each starforce level.
	 */
	private static final int upgradeValueIncrease[] = {
			// 0 -> 5 stars
			0, 5, 10, 15, 20,

			// 5 -> 10 stars
			25, 30, 35, 40, 45,

			// 10 -> 15 stars
			50, 60, 70, 80, 90,

			// 15 -> 20 stars
			100, 150, 200, 250, 300,

			// 20 -> 25 stars
			400, 500, 600, 700, 800,

			// 25 -> 30 stars
			900, 1000, 1500, 2000, 2500
	};

	private static final Map<Rarity, int[]> upgradePrices = Map.of(
			Rarity.UNUSUAL, new int[] {120, 250, 450},
			Rarity.RARE, new int[] {300, 450, 750, 1200, 1500},
			Rarity.EPIC, new int[] {500, 750, 990, 1350, 1650, 1850, 2100, 2350, 2550, 3000},
			Rarity.LEGENDARY, new int[] {850, 1150, 1400, 1700, 2200, 2650, 2950, 3400, 3950, 4500},
			Rarity.FABLED, new int[] {1150, 1350, 1600, 1950, 2450, 2900, 3500, 4100, 4750, 5200,
					5800, 6200, 6450, 6800, 7000},
			Rarity.MYTHICAL,
			new int[] {1450, 1650, 1850, 2100, 2600, 3100, 3750, 4600, 5200, 5650, 6050, 6550, 6950, 7100, 7350,
					7750, 8100, 8350, 8500, 8800},
			Rarity.GODLY,
			new int[] {1700, 2100, 2450, 2700, 3050, 3500, 3950, 4650, 5300, 5800, 6200, 6700, 7100, 7400, 7650,
					7950, 8200, 8500, 8700, 9000, 9100, 9150, 9200, 9250, 9300},
			Rarity.UNIQUE,
			new int[] {2000, 2350, 2650, 3000, 3450, 3700, 4200, 4700, 5400, 5950, 6300, 6900, 7450, 7700, 8100,
					8300, 8600, 8850, 9050, 9100, 9150, 9200, 9250, 9300, 9350, 9400, 9450, 9500,
					9550, 9600},
			Rarity.EVENT, new int[] {250, 550, 750, 1100, 1350, 1650, 1950, 2200, 2550, 2900, 3150, 3550, 3950, 4500, 4750,
					5100, 5550, 5800, 6100, 6500});

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

		// Ensure all event item and unique are quality maxed
		if (this.rarity.equals(Rarity.EVENT) || this.rarity.equals(Rarity.UNIQUE))
			this.quality = 1.0;
	}

	/*
	 * Apply value increase depending on the starforce level and quality
	 */
	public int getValue() {
		double baseWithStarForce = this.value * (100.0 + Item.upgradeValueIncrease[this.starForceLevel]) / 100.0;
		double finalValue = baseWithStarForce * (1.0 + this.quality);
		return (int) Math.round(finalValue);
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
