package utils;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import glados.GLaDOS;
import items.Item;

public class ItemUtils implements Logging {
	/*
	 * This private constructor hides the implicit public one
	 */
	private ItemUtils() {
		throw new IllegalStateException("Utility class");
	}

	public static void generateItemChart() {
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
}
