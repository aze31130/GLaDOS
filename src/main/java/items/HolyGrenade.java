package items;

import accounts.Account;

public class HolyGrenade extends Item {
	public HolyGrenade() {
		super(11, "Holy Hand Grenade of Antioch",
				"TODO",
				Rarity.EPIC, 300, "https://TODO");
	}

	@Override
	public boolean conditionnalDrop(Account dropper) {
		return dropper.level >= 80;
	}
}
