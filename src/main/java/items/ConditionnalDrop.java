package items;

/*
 * @formatter:off
 */
public enum ConditionnalDrop {
	/*
	 * The item cannot be dropped twice as long as someone holds it in its inventory
	 */
	UNIQUE_ITEM,

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
