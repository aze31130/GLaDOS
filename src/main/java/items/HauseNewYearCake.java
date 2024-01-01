package items;

import accounts.Account;

public class HauseNewYearCake extends Item {
	public HauseNewYearCake() {
		super(31, "Hausemaster's New Year Cake",
				"During the annual new year party, Hause's creation crumbled into a buttery debacle, leaving the once-hopeful confection with a tale of woe and a legacy of poorly-cooked butter. Only droppable in december.",
				Rarity.EVENT, 250, "https://TODO");
	}

	@Override
	public boolean conditionnalDrop(Account dropper) {
		return true;
	}
}
