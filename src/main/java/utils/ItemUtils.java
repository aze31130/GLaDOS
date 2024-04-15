package utils;

import java.security.SecureRandom;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import accounts.Account;
import glados.GLaDOS;
import items.Item;
import items.Rarity;

public class ItemUtils implements Logging {

	public static final int AMOUNT_ITEM_PER_PAGE = 10;

	/*
	 * This private constructor hides the implicit public one
	 */
	private ItemUtils() {
		throw new IllegalStateException("Utility class");
	}

	/*
	 * This function checks if a given trade is possible or not.
	 */
	public static boolean isTradePossible(Account author, Account target, Item srcItem, int srcMoney, Item dstItem,
			int dstMoney) {
		if (srcItem.untradable || dstItem.untradable)
			return false;

		// Check if the author is the target
		if (author.user.getId().equals(target.user.getId()))
			return false;

		// Check src money
		if (author.money < srcMoney)
			return false;

		// Check src item
		if (author.getItemByFQName(srcItem.getFQName()).isEmpty())
			return false;

		// Check dst money
		if (target.money < dstMoney)
			return false;

		// Check src item
		if (target.getItemByFQName(dstItem.getFQName()).isEmpty())
			return false;
		return true;
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
	 * This function returns a random item based on all item weight. The input list is read only and the
	 * new object is cloned from the given list.
	 */
	public static Item getRandomItem(List<Item> itemList) {
		GLaDOS glados = GLaDOS.getInstance();
		SecureRandom random = new SecureRandom();
		double dropValue = random.nextDouble(glados.itemTotalProb + 1);

		long cumulativeProbability = 0;
		Item droppedItem = null;

		// if (TimeUtils.isSpecialDay()) {
		// Guaranteeing specific drops on event days
		// TODO
		// return;
		// }

		for (Item item : glados.items) {
			cumulativeProbability += item.dropChance;
			if (dropValue <= cumulativeProbability) {
				// Check if drop requirements are fulfilled
				if (ItemUtils.checkDropConditions(item)) {
					try {
						droppedItem = (Item) item.clone();
					} catch (CloneNotSupportedException e) {
						e.printStackTrace();
					}
					break;
				}
			}
		}

		/*
		 * TODO Very rare edge case: if the last item has been skipped due to unsuffisient drop conditions.
		 * In this case, we simply send an error message and allow the user to drop again.
		 */

		return droppedItem;
	}

	/*
	 * This function ensures that a given item is owned by a user
	 */
	public static boolean userOwnItem(Account account, String itemFQName) {
		Optional<Item> item = account.inventory.stream().filter(i -> i.getFQName().equals(itemFQName)).findFirst();

		return item.isPresent();
	}

	/*
	 * This function returns the items at the given page number. The item limit is defined in the
	 * AMOUNT_ITEM_PER_PAGE static variable.
	 */
	public static List<Item> getUserInventory(Account a, int inventoryPage) {
		List<Item> result = new ArrayList<>();

		List<Item> sortedInventory = a.inventory.stream()
				.sorted(Comparator.comparingInt((Item item) -> item.rarity.level)
						.thenComparing(item -> item.name)
						.thenComparing(item -> item.starForceLevel))
				.collect(Collectors.toList());
		Collections.reverse(sortedInventory);

		// Check if the given page is valid
		int lastPage = (int) Math.ceil((double) sortedInventory.size() / AMOUNT_ITEM_PER_PAGE);

		if ((inventoryPage <= 0) || (lastPage < inventoryPage))
			return result;

		for (int indexMin = (inventoryPage - 1) * AMOUNT_ITEM_PER_PAGE; indexMin < sortedInventory.size(); indexMin++) {
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

		Map<Rarity, Long> rarityCountMap =
				virtualInventory.stream().collect(Collectors.groupingBy(item -> item.rarity, Collectors.counting()));

		List<Map.Entry<Rarity, Long>> sortedList = rarityCountMap.entrySet().stream()
				.sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
				.collect(Collectors.toList());

		for (Map.Entry<Rarity, Long> entry : sortedList)
			LOGGER.info(entry.getKey() + ": " + entry.getValue());
	}

	public static double getRarityDropRate(Rarity r) {
		GLaDOS glados = GLaDOS.getInstance();

		Double totalDropRate = 0.0;

		for (Item i : glados.items) {
			if (i.rarity.equals(r)) {
				Double dropPercentage = (double) (100 * (i.dropChance / glados.itemTotalProb));
				totalDropRate += dropPercentage;
			}
		}

		return totalDropRate;
	}

	public static double getRarityTotalWeight(Rarity r) {
		GLaDOS glados = GLaDOS.getInstance();

		int totalWeight = 0;

		for (Item i : glados.items)
			if (i.rarity.equals(r))
				totalWeight += i.dropChance;

		return totalWeight;
	}

	public static void generateItemChartDropRate() {
		Rarity allRarity[] = {
				Rarity.COMMON,
				Rarity.UNUSUAL,
				Rarity.RARE,
				Rarity.EPIC,
				Rarity.LEGENDARY,
				Rarity.FABLED,
				Rarity.MYTHICAL,
				Rarity.GODLY,
				Rarity.UNIQUE,
		};

		for (Rarity r : allRarity)
			LOGGER.info(r.name() + " " + getRarityDropRate(r) + "% Weight: " + getRarityTotalWeight(r));
	}

	public static void generateItemChartValue() {
		GLaDOS glados = GLaDOS.getInstance();

		List<Map.Entry<Item, Integer>> value = new ArrayList<>();

		for (Item i : glados.items)
			value.add(new AbstractMap.SimpleEntry<Item, Integer>(i, i.value));

		value.sort(Comparator.comparing(Map.Entry::getValue, Comparator.reverseOrder()));

		for (Map.Entry<Item, Integer> entry : value)
			LOGGER.info(entry.getKey().rarity + " " + entry.getKey().name + " : " + entry.getValue());
	}
}
