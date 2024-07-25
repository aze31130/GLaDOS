package items;

/*
 * @formatter:off
 */
public enum ConditionnalDrop {
	/*
	 * The item is unique. It cannot be dropped again as long as someone holds it in its inventory
	 */
	UNIQUE_ITEM,

	/*
	 * This item can only be obtained once per account during special times.
	 * Drops 100% of the time during event time
	 */
	EVENT_ITEM,

	/*
	 * The item can only be dropped at christmas
	 */
	CHRISTMAS_ITEM,

	/*
	 * The item can only be dropped at halloween
	 */
	HALLOWEEN_ITEM,

	/*
	 * The item can only be dropped at valentines day
	 */
	VALENTINE_ITEM,

	/*
	 * The item can only be obtained at new year
	 */
	NEW_YEAR_ITEM;
}
