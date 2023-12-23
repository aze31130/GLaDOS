package items;

import accounts.Account;

public class AttackSpeedScroll extends Item {
	public AttackSpeedScroll() {
		super(10, "Attack Speed Scroll",
				"Written by an old scrib, this scroll will increase your attack speed.",
				Rarity.EPIC, 300, "https://TODO");
	}

	@Override
	public boolean conditionnalDrop(Account dropper) {
		return dropper.level >= 80;
	}
}
