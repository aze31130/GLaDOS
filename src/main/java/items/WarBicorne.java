package items;

import accounts.Account;

public class WarBicorne extends Item {
	public WarBicorne() {
		super(13, "War Bicorne Hat",
				"TODO",
				Rarity.LEGENDARY, 100, "https://TODO");
	}

	@Override
	public boolean conditionnalDrop(Account dropper) {
		return true;
	}
}
