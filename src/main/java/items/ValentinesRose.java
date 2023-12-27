package items;

import accounts.Account;

public class ValentinesRose extends Item {
	public ValentinesRose() {
		super(14, "Valentine's Rose",
				"TODO",
				Rarity.EVENT, 0, "https://TODO");
	}

	@Override
	public boolean conditionnalDrop(Account dropper) {
		return true;
	}
}
