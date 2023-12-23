package items;

import accounts.Account;

public class ImmunityRing extends Item {
	public ImmunityRing() {
		super(14, "Immunity Ring",
				"TODO",
				Rarity.EPIC, 300, "https://TODO");
	}

	@Override
	public boolean conditionnalDrop(Account dropper) {
		return true;
	}
}
