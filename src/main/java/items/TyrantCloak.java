package items;

import accounts.Account;

public class TyrantCloak extends Item {
	public TyrantCloak() {
		super(14, "Tyrant Cloak",
				"TODO",
				Rarity.MYTHICAL, 100, "https://TODO");
	}

	@Override
	public boolean conditionnalDrop(Account dropper) {
		return true;
	}
}
