package utils;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import accounts.Account;
import glados.GLaDOS;
import items.Item;

public class ItemUtils implements Logging {
	/*
	 * This private constructor hides the implicit public one
	 */
	private ItemUtils() {
		throw new IllegalStateException("Utility class");
	}

	/*
	 * This function checks if the drop conditions are fulfilled
	 */
	public static Boolean checkDropConditions(Item i) {
		// for (ConditionnalDrop cd : i)
		return true;
	}

	/*
	 * This function returns the items (5 max) at the given page number.
	 */
	public static List<Item> getUserInventory(Account a, int inventoryPage) {
		List<Item> result = new ArrayList<>();

		// Check if the given page is valid
		int lastPage = (int) Math.ceil((double) a.inventory.size() / 5);

		if ((inventoryPage <= 0) || (lastPage < inventoryPage))
			return result;

		for (int indexMin = (inventoryPage - 1) * 5; indexMin < a.inventory.size(); indexMin++) {
			if (result.size() == 5)
				break;
			result.add(a.inventory.get(indexMin));
		}

		return result;
	}

	public static void generateItemChartDropRate() {
		GLaDOS glados = GLaDOS.getInstance();

		List<Map.Entry<Item, Double>> dropRate = new ArrayList<>();

		for (Item i : glados.items) {
			dropRate.add(new AbstractMap.SimpleEntry<Item, Double>(i,
					(double) (100 * (i.dropChance / glados.itemTotalProb))));
		}

		dropRate.sort(Comparator.comparing(Map.Entry::getValue, Comparator.reverseOrder()));

		for (Map.Entry<Item, Double> entry : dropRate) {
			Logging.LOGGER.info(entry.getKey().rarity + " " + entry.getKey().name + " : "
					+ entry.getValue() + "%");
		}
	}

	public static void generateItemChartValue() {
		GLaDOS glados = GLaDOS.getInstance();

		List<Map.Entry<Item, Integer>> value = new ArrayList<>();

		for (Item i : glados.items) {
			value.add(new AbstractMap.SimpleEntry<Item, Integer>(i, i.value));
		}

		value.sort(Comparator.comparing(Map.Entry::getValue, Comparator.reverseOrder()));

		for (Map.Entry<Item, Integer> entry : value) {
			Logging.LOGGER.info(entry.getKey().rarity + " " + entry.getKey().name + " : "
					+ entry.getValue());
		}
	}
}
