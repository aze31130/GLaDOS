package utils;

import java.security.SecureRandom;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import accounts.Account;
import glados.GLaDOS;
import items.Item;
import items.Rarity;

public class ItemUtils implements Logging {

	public static final int AMOUNT_ITEM_PER_PAGE = 5;

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
		if (i.rarity.equals(Rarity.EVENT))
			return false;

		// TODO Add more checks here

		return true;
	}

	/*
	 * This function ensures that a given item is owned by a user
	 */
	public static boolean userOwnItem(Account account, String itemFQName) {
		Optional<Item> item = account.inventory.stream()
				.filter(i -> i.getFQName().equals(itemFQName)).findFirst();

		return item.isPresent();
	}

	/*
	 * This function returns the items at the given page number. The item limit is defined in the
	 * AMOUNT_ITEM_PER_PAGE static variable.
	 */
	public static List<Item> getUserInventory(Account a, int inventoryPage) {
		List<Item> result = new ArrayList<>();

		List<Item> sortedInventory = a.inventory.stream()
				.sorted(Comparator.comparingInt(item -> item.rarity.level))
				.collect(Collectors.toList());
		Collections.reverse(sortedInventory);

		// Check if the given page is valid
		int lastPage = (int) Math.ceil((double) sortedInventory.size() / AMOUNT_ITEM_PER_PAGE);

		if ((inventoryPage <= 0) || (lastPage < inventoryPage))
			return result;

		for (int indexMin = (inventoryPage - 1) * AMOUNT_ITEM_PER_PAGE; indexMin < sortedInventory
				.size(); indexMin++) {
			if (result.size() == AMOUNT_ITEM_PER_PAGE)
				break;
			result.add(sortedInventory.get(indexMin));
		}

		return result;
	}

	public static void simulateDrops(int amount) {
		GLaDOS glados = GLaDOS.getInstance();
		List<Item> virtualInventory = new ArrayList<>();
		SecureRandom random = new SecureRandom();

		for (int i = 0; i < amount; i++) {
			// Drop item
			double dropValue = random.nextDouble(glados.itemTotalProb + 1);
			long cumulativeProbability = 0;
			Item droppedItem = null;

			for (Item item : glados.items) {
				cumulativeProbability += item.dropChance;
				if (dropValue <= cumulativeProbability) {
					// Check if drop requirements are fulfilled
					if (ItemUtils.checkDropConditions(item)) {
						droppedItem = item;
						break;
					}
				}
			}

			if (droppedItem != null)
				virtualInventory.add(droppedItem);
		}

		Map<Rarity, Long> rarityCountMap = virtualInventory.stream()
				.collect(Collectors.groupingBy(item -> item.rarity, Collectors.counting()));

		List<Map.Entry<Rarity, Long>> sortedList = rarityCountMap.entrySet().stream()
				.sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
				.collect(Collectors.toList());

		for (Map.Entry<Rarity, Long> entry : sortedList)
			LOGGER.info(entry.getKey() + ": " + entry.getValue());
	}

	public static void generateItemChartDropRate() {
		GLaDOS glados = GLaDOS.getInstance();

		List<Map.Entry<Item, Double>> dropRate = new ArrayList<>();

		Map<Rarity, Double> rarityPercentages = new HashMap<>();

		for (Item i : glados.items) {
			Double dropPercentage = (double) (100 * (i.dropChance / glados.itemTotalProb));

			if (rarityPercentages.containsKey(i.rarity)) {
				rarityPercentages.put(i.rarity, rarityPercentages.get(i.rarity) + dropPercentage);
			} else {
				rarityPercentages.put(i.rarity, dropPercentage);
			}

			dropRate.add(new AbstractMap.SimpleEntry<>(i, dropPercentage));
		}

		dropRate.sort(Comparator.comparing(Map.Entry::getValue, Comparator.reverseOrder()));

		for (Map.Entry<Item, Double> entry : dropRate) {
			LOGGER.info(entry.getKey().rarity + " " + entry.getKey().name + " : "
					+ entry.getValue() + "%");
		}

		LOGGER.info("---");
		rarityPercentages.entrySet().stream()
				.sorted(Map.Entry.<Rarity, Double>comparingByValue().reversed())
				.forEach(entry -> LOGGER.info(entry.getKey() + " " + entry.getValue()));
	}

	public static void generateItemChartValue() {
		GLaDOS glados = GLaDOS.getInstance();

		List<Map.Entry<Item, Integer>> value = new ArrayList<>();

		for (Item i : glados.items) {
			value.add(new AbstractMap.SimpleEntry<Item, Integer>(i, i.value));
		}

		value.sort(Comparator.comparing(Map.Entry::getValue, Comparator.reverseOrder()));

		for (Map.Entry<Item, Integer> entry : value) {
			LOGGER.info(entry.getKey().rarity + " " + entry.getKey().name + " : "
					+ entry.getValue());
		}
	}
}
