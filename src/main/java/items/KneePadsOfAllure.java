package items;

import accounts.Account;

public class KneePadsOfAllure extends Item {
	public KneePadsOfAllure() {
		super(14, "Knee Pads Of Allure",
				"TODO",
				Rarity.EPIC, 0, "https://TODO");
	}

	@Override
	public boolean conditionnalDrop(Account dropper) {
		return true;
	}
}
