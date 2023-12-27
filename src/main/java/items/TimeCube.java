package items;

import accounts.Account;

public class TimeCube extends Item {
	public TimeCube() {
		super(1, "TimeCube", "TODO", Rarity.UNIQUE, 300,
				"https://TODO");
	}

	@Override
	public boolean conditionnalDrop(Account dropper) {
		return dropper.level >= 50;
	}
}
