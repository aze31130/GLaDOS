package items;

import java.util.List;
import utils.PairUtils;

public abstract class Items {
	public String name;
	public Rarity rarity;

	public String attackSpeed;
	/*
	 * Specials bonuses that can be rerolled. This includes flat raw stats alongside % based stats.
	 */
	public String potential;

	public List<PairUtils<String, Integer>> Enchants;

	public String dropChance;

	public String starForce;
	public int starForceCost;
}
