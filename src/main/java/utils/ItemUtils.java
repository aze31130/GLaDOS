package utils;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import glados.GLaDOS;
import items.Item;

public class ItemUtils {
	/*
	 * This private constructor hides the implicit public one
	 */
	private ItemUtils() {
		throw new IllegalStateException("Utility class");
	}

	public static void generateItemChart() {
		GLaDOS glados = GLaDOS.getInstance();

		List<Map.Entry<String, Double>> dropRate = new ArrayList<>();

		for (Item i : glados.items) {
			dropRate.add(new AbstractMap.SimpleEntry<String, Double>(i.name,
					(double) (100 * i.dropChance / glados.itemTotalProb)));
		}

		dropRate.sort(Comparator.comparing(Map.Entry::getValue, Comparator.reverseOrder()));

		for (Map.Entry<String, Double> entry : dropRate) {
			System.out.println(entry.getKey() + " : " + entry.getValue() + "%");
		}
	}
}
