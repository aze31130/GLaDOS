package items;

import accounts.Account;

public class ImmunityScroll extends Item {
	public ImmunityScroll() {
		super(1, "Immunity Scroll", "Makes you invincible for 10 seconds",
				Rarity.LEGENDARY, 50, "https://TODO");
	}

	@Override
	public boolean conditionnalDrop(Account dropper) {
		return dropper.level >= 30;
	}
}
