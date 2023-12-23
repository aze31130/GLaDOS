package items;

import accounts.Account;

public class Cataclyst extends Item {
	public Cataclyst() {
		super(12, "Cataclyst",
				"TODO",
				Rarity.EPIC, 300, "https://TODO");
	}

	@Override
	public boolean conditionnalDrop(Account dropper) {
		return true;
	}
}
