package items;

import accounts.Account;

public class SweetWaterHat extends Item {
	public SweetWaterHat() {
		super(13, "Sweet Water Hat",
				"TODO",
				Rarity.EPIC, 300, "https://TODO");
	}

	@Override
	public boolean conditionnalDrop(Account dropper) {
		return true;
	}
}
